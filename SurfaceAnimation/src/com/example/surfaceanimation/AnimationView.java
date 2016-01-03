package com.example.surfaceanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

class AnimationView extends SurfaceView implements Runnable, SurfaceHolder.Callback, OnTouchListener
{
	
    private float startX;
	private float startY;
	private float finalX;
	private float finalY;
	private double tantheta;
	int degrees = 0;
	
	int x;
	int y;
	
	final Paint paint = new Paint();
	
	private static final int HALF_WIDTH = 80;
	Thread animation = null;
	private boolean running;

	public AnimationView(Context context) {
		super(context);
		

		getHolder().addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height) {
		
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// start the animation thread once the surface has been created
		
		animation = new Thread(this);
		running = true;
		
		setOnTouchListener(this);
		
		
		x = holder.getSurfaceFrame().right / 2;
		y = holder.getSurfaceFrame().bottom / 2;
		animation.start(); // start a new thread to handle this activities animation
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;
		if(animation != null)
		{
			try {
				animation.join();  // finish the animation thread and let the animation thread die a natural death
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		paint.setAntiAlias(true);
		while(running)
		{					
			Canvas canvas = null;
			SurfaceHolder holder = getHolder();
			synchronized(holder)
			{
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.BLACK);
				
				paint.setColor(Color.RED);
				
				canvas.rotate((float) tantheta, x, y);
				canvas.drawRect(x-HALF_WIDTH,y-HALF_WIDTH,x+HALF_WIDTH,y+HALF_WIDTH, paint);
				
				degrees ++;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
			if(event.getActionMasked() == MotionEvent.ACTION_MOVE)
			{
				if(event.getPointerCount() == 2)
				{
					startX = event.getX(0);
					startY = event.getY(0);
					
					finalX = event.getX(1);
					finalY = event.getY(1);
					
					float opposite = finalY - startY;
					float adjacent = finalX - startX;
					
					tantheta = Math.toDegrees(Math.atan2(opposite,adjacent));
					
					
					// translate into the right quadrant
					if(tantheta < 0)
					{
						tantheta = Math.abs(tantheta);
					}
					else
					{
						tantheta = 360 - tantheta;
					}
				}
				else
				{
					startX = event.getX(0);
					startY = event.getY(0);
					
					float opposite = y - startY;
					float adjacent = x - startX;
					
					tantheta = Math.toDegrees(Math.atan2(opposite,adjacent));
				}
			}
			return true;
	}
}