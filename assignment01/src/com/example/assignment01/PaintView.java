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
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;
	
	Bitmap offScreenBitmap;
	Canvas offScreenCanvas;


	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
	    setupDrawing();

	}
	
	private void setupDrawing(){
		//get drawing area setup for interaction   
		drawPath = new Path();
		drawPaint = new Paint();
		
		//Next set the initial color:
		drawPaint.setColor(paintColor);
		
		//Now set the initial path properties:
		//for now we set an arbitrary brush size. Setting the anti-alias, 
		//stroke join and cap styles will make the user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
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
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
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
		    drawCanvas.drawPath(drawPath, drawPaint);
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
		
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);
		}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		// create and re-create the off screen bitmap to capture the state of our drawing
		// this operation will reset the user's drawing
		
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
//		offScreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//		offScreenCanvas = new Canvas(offScreenBitmap);
	}	
}
