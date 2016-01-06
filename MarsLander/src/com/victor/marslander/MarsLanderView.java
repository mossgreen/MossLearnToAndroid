package com.victor.marslander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This class is mainly used for draw the mars lander view and pre-loads corresponding resources into bitmap.
 *
 * @version 1.0
 */
public class MarsLanderView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private static final String APP_TAG = "MARS_LANDER";
	private static final int REFRESH_RATE = 20;

	SurfaceHolder _holder;

	// display object
	private Bitmap _bmBackground;
	private Bitmap _bmCraft;
	private Bitmap _bmCraftFallLeft;
	private Bitmap _bmCraftFallRight;
	private Bitmap _bmBoom;
	private Bitmap _bmWon;
	private Bitmap _bmSideFlame;
	private Bitmap _bmMainFlame;
	private Paint _paintGround;
	Matrix _matrix = new Matrix();

	// game using
	private int _screenWidth = 480;
	private int _screenHeight = 640;

	Thread _mainThread;
	private MarsLanderScene _scene;

	public MarsLanderView(Context context) {
		super(context);
	}

	public MarsLanderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarsLanderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		_screenWidth = w;
		_screenHeight = h;
		init();
	}

	public MarsLanderScene getScene() {
		return _scene;
	}

	/**
	 * Pre-loads corresponding resources into bitmap.
	 */
	public void init() {
		Log.d(APP_TAG, "init SurfaceView");

		//
		_scene = new MarsLanderScene(_screenWidth, _screenHeight);

		Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.craft);
		_bmCraft = Bitmap.createScaledBitmap(temp, Craft.WIDTH, Craft.HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.crashed_left);
		_bmCraftFallLeft = Bitmap.createScaledBitmap(temp, Craft.HEIGHT, Craft.WIDTH, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.crashed_right);
		_bmCraftFallRight = Bitmap.createScaledBitmap(temp, Craft.HEIGHT, Craft.WIDTH, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
		_bmBoom = Bitmap.createScaledBitmap(temp, 50, 38, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.won);
		_bmWon = Bitmap.createScaledBitmap(temp, temp.getWidth(), temp.getHeight(), true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
		_bmSideFlame = Bitmap.createScaledBitmap(temp, Craft.SIDE_FLAME_WIDTH, Craft.SIDE_FLAME_HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
		_bmMainFlame = Bitmap.createScaledBitmap(temp, Craft.MAIN_FLAME_WIDTH, Craft.MAIN_FLAME_HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		_bmBackground = Bitmap.createScaledBitmap(temp, _screenWidth, _screenHeight, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		Shader mShader = new BitmapShader(temp, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
		_paintGround = new Paint();
		_paintGround.setShader(mShader);

		_holder = getHolder();
		_holder.setFormat(PixelFormat.TRANSPARENT);
		_holder.addCallback(this);
	}

	@Override
	public void run() {
		while (true) {
			Canvas canvas = null;
			try {
				synchronized (_holder) {
					// update model
					if (_scene.getState() == MarsLanderScene.STATE_RUNNING)
						_scene.update();

					// drawing
					canvas = _holder.lockCanvas();
					doDraw(canvas);
				}
				Thread.sleep(REFRESH_RATE);
			} catch (Exception e) {
			} finally {
				if (canvas != null) {
					_holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	/**
	 * Draw background, craft, fuel, crash and win etc.
	 * @param canvas The draw canvas.
	 */
	private void doDraw(Canvas canvas) {
		canvas.drawBitmap(_bmBackground, 0, 0, null);// draw background
		if (_scene.getState() == MarsLanderScene.STATE_READY) { // show the first view of this app, draw the craft at the center on screen.
			canvas.drawBitmap(_bmCraft, (_screenWidth - Craft.WIDTH) / 2, _screenHeight / 2, null);
			return;
		}

		// get the ground points map
		Path path = _scene.getMars().getGround();
		canvas.drawPath(path, _paintGround);

		// get the craft instance
		Craft craft = _scene.getCraft();
		
		// draw remains fuel
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		paint.setTextSize(40);
		canvas.drawText("Fuel: " + craft.getFuelRemaining(), _screenWidth - 250, 50, paint);
		
		_matrix.reset();
		_matrix.setTranslate(craft.getX(), craft.getY());
		_matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
		
		if (_scene.getState() == MarsLanderScene.STATE_CRASHED) {// if the craft crashed
			canvas.drawBitmap(_bmCraft, _matrix, null);
			canvas.drawBitmap(_bmBoom, craft.getX(), craft.getCenterY(), null);
			return;
		} else if (_scene.getState() == MarsLanderScene.STATE_FALL_LEFT) { //crashed when the craft landed failed on the left side.
			canvas.drawBitmap(_bmCraftFallLeft, craft.getX(), craft.getCenterY(), null);
			return;
		} else if (_scene.getState() == MarsLanderScene.STATE_FALL_RIGHT) {//crashed when the craft landed failed on the right side.
			canvas.drawBitmap(_bmCraftFallRight, craft.getCenterX(), craft.getCenterY(), null);
			return;
		} else if (_scene.getState() == MarsLanderScene.STATE_WON) {// landed successfully
			canvas.drawBitmap(_bmCraft, _matrix, null);
			canvas.drawBitmap(_bmWon, (_screenWidth - _bmWon.getWidth())/2, _screenHeight / 2, null);
		}
		
		canvas.drawBitmap(_bmCraft, _matrix, null);// draw the craft

		if (_scene.getState() == MarsLanderScene.STATE_RUNNING) {
			// flame
			if (craft.IsLeftThrusterOn()) {
				_matrix.reset();
				_matrix.setTranslate(craft.getLeftFlamePosX(), craft.getSideFlamePosY());
				_matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(_bmSideFlame, _matrix, null);
			}

			if (craft.IsRightThrusterOn()) {
				_matrix.reset();
				_matrix.setTranslate(craft.getRightFlamePosX(), craft.getSideFlamePosY());
				_matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(_bmSideFlame, _matrix, null);
			}

			if (craft.IsMainThrusterOn()) {
				_matrix.reset();
				_matrix.setTranslate(craft.getMainFlamePosX(), craft.getMainFlamePosY());
				_matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(_bmMainFlame, _matrix, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		if (action == MotionEvent.ACTION_DOWN) {
			if (event.getX() < _screenWidth / 2) {
				_scene.getCraft().turnRight();
			} else {
				_scene.getCraft().turnLeft();
			}
		} else if(action == MotionEvent.ACTION_UP) {
			Log.i("FFF", "AAAAAA");
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_mainThread = new Thread(this);
		if (_mainThread != null)
			_mainThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				_mainThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}