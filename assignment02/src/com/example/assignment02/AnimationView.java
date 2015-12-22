package com.example.assignment02;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimationView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
	private int x; // The x-coord for the pivot point (unchanged by the
					// rotation)

	private int y; // The y-coord for the pivot point (unchanged by the
					// rotation)

	private int degrees = 10; // the degrees that rotating

	Paint paint = new Paint();

	private int length = 100;
	Thread animation = null;
	private boolean isRunning;
	
	private Resources mResources;

	private Bitmap rocket;

	public AnimationView(Context context) {
		super(context);
		// to hook the custom SurfaceView up
		// This will cause the custom SurfaceView to call the methods:
		// surfaceChanged, surfaceCreated,
		// and surfaceDestroyed as the state of the SurfaceView changes and
		// responds to external events
		// such as the rotation of the screen.
		getHolder().addCallback(this);
		rocket = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {

		// setup your animation thread and connect it to SurfaceAnimate’s run()
		// method.
		// start the animation thread once the surface has been created
		
		// load images from files and set one of those to the main image
				

		animation = new Thread(this);
		isRunning = true;

		x = arg0.getSurfaceFrame().right / 2;
		y = arg0.getSurfaceFrame().bottom / 2;
		animation.start(); // start a new thread to handle this activities
							// animation

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// setup code to stop your animation thread.
		// use a Boolean value here to control the animation loop and allow the
		// thread to die a natural death.

		isRunning = false;
		if (animation != null) {
			try {
				animation.join(); // finish the animation thread and let the
									// animation thread die a natural death
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// method to draw a rotating rectangle

		// need extra variables to keep track of the pivot point,
		// degrees, and the size of the rectangle.
		while (isRunning) {
			Canvas canvas = null;
			SurfaceHolder holder = getHolder();

			synchronized (holder) {
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.BLACK);

//				paint.setColor(Color.GREEN);

//				canvas.rotate(degrees, x, y);
//				canvas.drawRect(x - length, y - length, x + length, y + length, paint);
				canvas.drawBitmap(rocket, x, y, paint);
			

				x += degrees;
				y += degrees;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			holder.unlockCanvasAndPost(canvas);

		}

	}

}
