package com.example.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener; 


//i'd like to extends SurfaceView rather than View, since SV is more efficient than View
public class PaintView extends View implements OnTouchListener{
	Paint paint;
    Bitmap cacheBitmap = null;  
    Canvas cacheCanvas = null; 
	
	private float mX;  
	private float mY;  
	
	//this is the parameter of nexus
    final int VIEW_WIDTH = 320;  
    final int VIEW_HEIGHT = 480; 

	private final Paint mGesturePaint = new Paint();
	private Path mPath = new Path();

	// define additional constructors so that PaintView will work with out layout file
	public PaintView(Context context) {
		super(context);
		
		setup();
	}

	public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		setup();
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setup();
	}

	public void setup()
	{
		setOnTouchListener(this); // define event listener and start intercepting events 
		// get the width and height of the screen
		
		
		
		paint = new Paint();
		paint.setStyle(Style.STROKE);  // have to: import android.graphics.Paint;  

		paint.setAntiAlias(true);  // make the line itself more smooth
		paint.setDither(true);  
		paint.setColor(Color.GREEN);
//		paint.setColor(0xFF000000);  // used for eraser
		paint.setStrokeWidth(2);  
		paint.setStyle(Paint.Style.STROKE);  
		//paint.setStyle(Paint.Style.FILL);   //Dot not use it, will generate shadow effect
		//paint.setStyle(Paint.Style.FILL_AND_STROKE);  //Dot not use it, will generate shadow effect
		paint.setStrokeJoin(Paint.Join.ROUND);  
		paint.setStrokeCap(Paint.Cap.ROUND);  

		
		// firstly added

        mGesturePaint.setAntiAlias(true);  
        mGesturePaint.setStyle(Style.STROKE);  // have to: import android.graphics.Paint;  
        mGesturePaint.setStrokeWidth(5);  
        mGesturePaint.setColor(Color.WHITE);  
        
        // secondly added
        
        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT,Config.ARGB_8888);
        cacheCanvas = new Canvas();
        
        cacheCanvas.setBitmap(cacheBitmap);
        
//        paint = new Paint(paint.DITHER_FLAG);
        
        
        

		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//canvas.drawCircle(x, y, 50, paint);
		//draw the off screen bitmap
		//canvas.drawBitmap(offScreenBitmap, 0, 0,paint);
		
		// this is the default one
		canvas.drawPath(mPath, paint); //use the canvas to draw the the picture that made of multi-points
		
		//second added
//		Paint bmpPaint = new Paint();
//        canvas.drawColor(0xFFAAAAAA);  
//
//		canvas.drawBitmap(cacheBitmap, 1, 1,bmpPaint);
//		canvas.drawPath(mPath, paint);
		
		
	}
	
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// get the x,y coordinates of the MotionEvent.ACTION_MOVE event
		switch (event.getAction()) {
		
			case MotionEvent.ACTION_DOWN:
				touchDown(event);
				break;
				
//			case MotionEvent.ACTION_UP:
//				x = event.getX();
//				y = event.getY();
//				break;
//				
			case MotionEvent.ACTION_MOVE:
				touchMove(event);
				break;

		}	
		invalidate(); //// force a screen re-draw
		return true; //return true to consume event from the event queue
	}
	
	private void touchDown(MotionEvent event){
		mPath.reset(); //redraw the path, hide the previous path
		
		float x = event.getX();
		float y = event.getY();
		
		mX = x;
		mY = y;
		
		mPath.moveTo(x, y); // this is the starting point 
	}
	
	private void touchMove(MotionEvent event){
		final float x = event.getX();
		final float y = event.getY();
		
		final float previousX = mX;
		final float previousY = mY;
		
		final float dx = Math.abs(x-previousX);
		final float dy = Math.abs(y-previousY);
		
		//draw a 贝塞尔绘制曲线  if dx > 3
		if(dx >= 3 || dy >= 3){
			float cX = (x + previousX) / 2;
			float cY = (y + previousY) / 2;
			
			// do 贝塞尔绘制曲线  again to make the line more smooth?
			mPath.quadTo(previousX, previousY, cX, cY);
			
			mX = x;
			mY = y;
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		//create & re-create t he off screen bitmap to capture the state of our drawing 
		// this operation will reset the user's drawing
		
		cacheBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
		cacheCanvas = new Canvas(cacheBitmap);
	}
}
