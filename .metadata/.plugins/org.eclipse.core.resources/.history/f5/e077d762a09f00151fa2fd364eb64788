package com.example.drawingfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View{
	//drawing path
	private Path drawPath;
	//the user paths drawn with drawPaint will be drawn onto the canvas, which is drawn with canvasPaint
	private Paint drawPaint, canvasPaint;
	//initial color
	private int paintColor = 0xFF660000;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;
	
	
	public DrawingView(Context context, AttributeSet attrs){
	    super(context, attrs);
	    setupDrawing();
	}
	
	//get drawing area setup for interaction  
	private void setupDrawing(){
		//First, instantiate the drawing Path and Paint objects:
		drawPath = new Path();
		drawPaint = new Paint();
		
		//Second, set the initial color:
		drawPaint.setColor(paintColor);
		
		//Third, set the initial path properties
		//Setting the anti-alias, stroke join and cap styles will make the user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		
		//instantiating the canvas Paint object:
		canvasPaint = new Paint(Paint.DITHER_FLAG);

		      
		}
	
	//method will be called when the custom View is assigned a size
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		//instantiate the drawing canvas and bitmap using the width and height values
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
		
		}
	
	@Override
	protected void onDraw(Canvas canvas) {
	//draw view
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//detect user touch     
		//retrieve the X and Y positions of the user touch
		float touchX = event.getX();
		float touchY = event.getY();
		
		
		switch (event.getAction()) {
		//When the user touches the View, we move to that position to start drawing
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(touchX, touchY);
		    break;
		    
		//When they move their finger on the View, we draw the path along with their touch   
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    break;
		    
		//When they lift their finger up off the View, we draw the Path and reset it for the next drawing operation
		case MotionEvent.ACTION_UP:
		    drawCanvas.drawPath(drawPath, drawPaint);
		    drawPath.reset();
		    break;
		}
		
		//Calling invalidate will cause the onDraw method to execute
	    invalidate(); 
	    return true;
	}
	
	public void setColor(String newColor){
		//to set color, start by invalidating the View
		invalidate();
		
		//next, parse and set the color for drawing
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);


		
		
	
	}

}
