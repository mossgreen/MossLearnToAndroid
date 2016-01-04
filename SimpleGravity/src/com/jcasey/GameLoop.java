package com.jcasey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameLoop extends SurfaceView implements Runnable,
		SurfaceHolder.Callback, OnTouchListener {
	
	/**
	 * @author John Casey 11/04/2014
	 * Sample code for subject ISCG7424 Mobile Application Development
	 * 
	 * Code illustrates collision detection and models gravity using synthetic time.
	 */
	
	public static final double INITIAL_TIME = 3.5;
	static final int REFRESH_RATE = 20;
	static final int GRAVITY = 1;
	
	Thread main;
	
	Paint paint = new Paint();
	
	Bitmap background;
	
	int xcor[] = { 0, 200, 190, 218, 260, 275, 298, 309, 327, 336, 368, 382,
			448, 462, 476, 498, 527, 600, 600, 0, 0 };
	int ycor[] = { 616, 540, 550, 605, 605, 594, 530, 520, 520, 527, 626, 636,
			636, 623, 535, 504, 481, 481, 750, 750, 616 };
	
	Canvas offscreen;
	Bitmap buffer;
	
	boolean downPressed = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	Boolean gameover = false;
	
	float x, y;
	int width = 0;
	
	double t = INITIAL_TIME;
	
	Path path;
	public GameLoop(Context context) {
		super(context);

		init();
	}

	public GameLoop(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init();
	}

	public GameLoop(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}


	public void init()
	{
		path = new Path();

		for (int i = 0; i < xcor.length; i++) {
			path.lineTo(xcor[i], ycor[i]);
		}

		setOnTouchListener(this);

		getHolder().addCallback(this);
	}

	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		width = w;
		
		x = width /2;
	}

	public void run() {
		while(true)
		{
			while (!gameover)
			{
				Canvas canvas = null;
				SurfaceHolder holder = getHolder();
				synchronized (holder) {
					canvas = holder.lockCanvas();
	
					canvas.drawColor(Color.BLACK);
	
					paint.setColor(Color.RED);
					canvas.drawPath(path, paint);
					
					// s = ut + 0.5 gt^2
					
					// not that the initial velocity (u) is zero so I have not put ut into the code below
					y = (int) y + (int) ((0.5 * (GRAVITY * t * t)));
					
					t = t + 0.01; // increment the parameter for synthetic time by a small amount
					
					boolean bottomLeft = contains(xcor, ycor, x-25, y+25);
					boolean bottomRight = contains(xcor, ycor, x+25, y+25);
					
					if (bottomLeft || bottomRight)
					{
						paint.setColor(Color.GREEN);
						canvas.drawRect(x-25, y-25, x+25, y+25, paint);
						
						t = INITIAL_TIME; // reset the time variable
						
						gameover = true;
					}
					else
					{
						canvas.drawRect(x-25, y-25, x+25, y+25, paint);
					}
				}
	
				try {
					Thread.sleep(REFRESH_RATE);
				} catch (Exception e) {
				}
	
				finally
				{
					if (canvas != null)
					{
						holder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
		
	}

	public boolean contains(int[] xcor, int[] ycor, double x0, double y0) {
		int crossings = 0;

		for (int i = 0; i < xcor.length - 1; i++) {
			int x1 = xcor[i];
			int x2 = xcor[i + 1];

			int y1 = ycor[i];
			int y2 = ycor[i + 1];

			int dy = y2 - y1;
			int dx = x2 - x1;

			double slope = 0;
			if (dx != 0) {
				slope = (double) dy / dx;
			}

			boolean cond1 = (x1 <= x0) && (x0 < x2); // is it in the range?
			boolean cond2 = (x2 <= x0) && (x0 < x1); // is it in the reverse
														// range?
			boolean above = (y0 < slope * (x0 - x1) + y1); // point slope y - y1

			if ((cond1 || cond2) && above) {
				crossings++;
			}
		}
		return (crossings % 2 != 0); // even or odd
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		main = new Thread(this);
		if (main != null)
			main.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry)
		{
			try
			{
				main.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
				// try again shutting down the thread
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		x = event.getX();
		y = event.getY();

		t = 3;

		gameover = false;

		return true;
	}
	
	public void reset()
	{	
		gameover = false;
		
		x = width /2;
		y = 0;
		t = 0;
	}
}
