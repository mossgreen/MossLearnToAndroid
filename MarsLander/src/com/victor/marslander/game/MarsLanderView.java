package com.victor.marslander.game;

import com.victor.marslander.R;
import com.victor.marslander.R.drawable;
import com.victor.marslander.R.integer;
import com.victor.marslander.craft.CraftView;
import com.victor.marslander.mars.MarsLanderScene;

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

	private  final int REFRESH_RATE = getResources().getInteger(R.integer.REFRESH_RATE);

	SurfaceHolder holder;

	// display object
	private Bitmap bmBackground;
	private Bitmap bmCraft;
	private Bitmap bmCraftFallLeft;
	private Bitmap bmCraftFallRight;
	private Bitmap bmBoom;
	private Bitmap bmWon;
	private Bitmap bmSideFlame;
	private Bitmap bmMainFlame;
	private Paint paintGround;
	Matrix matrix = new Matrix();

	// game using
	private int screenWidth;
	private int screenHeight ;

	Thread mainThread;
	private MarsLanderScene scene;

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

		this.screenWidth = w;
		this.screenHeight = h;
		init();
	}

	public MarsLanderScene getScene() {
		return scene;
	}

	/**
	 * Pre-loads corresponding resources into bitmap.
	 */
	public void init() {
		

		//
		scene = new MarsLanderScene(screenWidth, screenHeight);

		Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.craftmain);
		bmCraft = Bitmap.createScaledBitmap(temp, CraftView.CRAFT_WIDTH, CraftView.CRAFT_HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.crashed_left);
		bmCraftFallLeft = Bitmap.createScaledBitmap(temp, CraftView.CRAFT_HEIGHT, CraftView.CRAFT_WIDTH, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.crashed_right);
		bmCraftFallRight = Bitmap.createScaledBitmap(temp, CraftView.CRAFT_HEIGHT, CraftView.CRAFT_WIDTH, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
		bmBoom = Bitmap.createScaledBitmap(temp, 50, 38, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.won);
		bmWon = Bitmap.createScaledBitmap(temp, temp.getWidth(), temp.getHeight(), true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
		bmSideFlame = Bitmap.createScaledBitmap(temp, CraftView.SIDE_FLAME_WIDTH, CraftView.SIDE_FLAME_HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
		bmMainFlame = Bitmap.createScaledBitmap(temp, CraftView.MAIN_FLAME_WIDTH, CraftView.MAIN_FLAME_HEIGHT, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		bmBackground = Bitmap.createScaledBitmap(temp, screenWidth, screenHeight, true);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		Shader mShader = new BitmapShader(temp, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
		paintGround = new Paint();
		paintGround.setShader(mShader);

		holder = getHolder();
		holder.setFormat(PixelFormat.TRANSPARENT);
		holder.addCallback(this);
	}

	@Override
	public void run() {
		while (true) {
			Canvas canvas = null;
			try {
				synchronized (holder) {
					// update model
					if (scene.getState() == MarsLanderScene.STATE_RUNNING)
						scene.update();

					// drawing
					canvas = holder.lockCanvas();
					doDraw(canvas);
				}
				Thread.sleep(REFRESH_RATE);
			} catch (Exception e) {
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	/**
	 * Draw background, craft, fuel, crash and win etc.
	 * @param canvas The draw canvas.
	 */
	private void doDraw(Canvas canvas) {
		canvas.drawBitmap(bmBackground, 0, 0, null);// draw background
		if (scene.getState() == MarsLanderScene.STATE_READY) { // show the first view of this app, draw the craft at the center on screen.
			canvas.drawBitmap(bmCraft, (screenWidth - CraftView.CRAFT_WIDTH) / 2, screenHeight / 2, null);
			return;
		}

		// get the ground points map
		Path path = scene.getMars().getGround();
		canvas.drawPath(path, paintGround);

		// get the craft instance
		CraftView craft = scene.getCraft();
		
		// draw remains fuel
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		paint.setTextSize(40);
		canvas.drawText("Fuel: " + craft.getFuelRemaining(), screenWidth - 250, 50, paint);
		
		matrix.reset();
		matrix.setTranslate(craft.getX(), craft.getY());
		matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
		
		if (scene.getState() == MarsLanderScene.STATE_CRASHED) {// if the craft crashed
			canvas.drawBitmap(bmCraft, matrix, null);
			canvas.drawBitmap(bmBoom, craft.getX(), craft.getCenterY(), null);
			return;
		} else if (scene.getState() == MarsLanderScene.STATE_FALL_LEFT) { //crashed when the craft landed failed on the left side.
			canvas.drawBitmap(bmCraftFallLeft, craft.getX(), craft.getCenterY(), null);
			return;
		} else if (scene.getState() == MarsLanderScene.STATE_FALL_RIGHT) {//crashed when the craft landed failed on the right side.
			canvas.drawBitmap(bmCraftFallRight, craft.getCenterX(), craft.getCenterY(), null);
			return;
		} else if (scene.getState() == MarsLanderScene.STATE_WON) {// landed successfully
			canvas.drawBitmap(bmCraft, matrix, null);
			canvas.drawBitmap(bmWon, (screenWidth - bmWon.getWidth())/2, screenHeight / 2, null);
		}
		
		canvas.drawBitmap(bmCraft, matrix, null);// draw the craft

		if (scene.getState() == MarsLanderScene.STATE_RUNNING) {
			// flame
			if (craft.IsLeftEngineOn()) {
				matrix.reset();
				matrix.setTranslate(craft.getLeftFlamePosX(), craft.getSideFlamePosY());
				matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(bmSideFlame, matrix, null);
			}

			if (craft.IsRightEngineOn()) {
				matrix.reset();
				matrix.setTranslate(craft.getRightFlamePosX(), craft.getSideFlamePosY());
				matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(bmSideFlame, matrix, null);
			}

			if (craft.IsMainEngineOn()) {
				matrix.reset();
				matrix.setTranslate(craft.getMainFlamePosX(), craft.getMainFlamePosY());
				matrix.postRotate(craft.getAngle(), craft.getCenterX(), craft.getCenterY());
				canvas.drawBitmap(bmMainFlame, matrix, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		if (action == MotionEvent.ACTION_DOWN) {
			if (event.getX() < screenWidth / 2) {
				scene.getCraft().turnRight();
			} else {
				scene.getCraft().turnLeft();
			}
		} else if(action == MotionEvent.ACTION_UP) {
			Log.i("FFF", "AAAAAA");
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mainThread = new Thread(this);
		if (mainThread != null)
			mainThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				mainThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}