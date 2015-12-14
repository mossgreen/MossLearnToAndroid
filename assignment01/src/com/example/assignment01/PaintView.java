package com.example.assignment01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug.IntToString;

public class PaintView extends View {
	
	final Paint paint = new Paint();
	public boolean isBigger = false;
	
	//drawing path
	private Path drawPath;
	//drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	//initial color
	private int paintColor = 0xFF660000;
	//canvas
	private Canvas offScreenCanvas;
	//canvas bitmap
	private Bitmap offScreenBitmap;
	
	private boolean isTri = false;
	


	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
	    setupDrawing();

	}
	
	private void setupDrawing(){
		//get drawing area setup for interaction   
		drawPath = new Path();
		drawPaint = new Paint();
		
		// set the initial color:
		drawPaint.setColor(paintColor);
		
		// set the initial path properties:
		//for now we set an arbitrary brush size. Setting the anti-alias, 
		//stroke join and cap styles will make the user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(5);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		
		
		// instantiating the canvas Paint object
		//we set dithering by passing a parameter to the constructor
		canvasPaint = new Paint(Paint.DITHER_FLAG);
		}

	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(offScreenBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent event) {
		
		float touchX = event.getX();
		float touchY = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(touchX, touchY);
		    break;
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    break;
		case MotionEvent.ACTION_UP:
			offScreenCanvas.drawPath(drawPath, drawPaint);
		    drawPath.reset();
		    break;
		default:
		    return false;
		}
		
		
		invalidate(); // force a screen re-draw
		return true; // actually consume the event
	}
	
	public void setColor(String newColor){
		//set color     
		invalidate();
		
		//parse and set the color for drawing
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);
		}
	
	public void setShape(String shape){
		invalidate();
		
		if(shape == "triangle"){
			isTri = true;
		}
//		else if(){
//			
//		}
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		// create and re-create the off screen bitmap to capture the state of our drawing
		// this operation will reset the user's drawing
		
		offScreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		offScreenCanvas = new Canvas(offScreenBitmap);
	}	
}
