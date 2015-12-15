package com.example.drawingfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
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
	//variables for the brush sizes
	//and for keeping track of the last brush size used when the user switches to the eraser
	//so that we can revert back to the correct size when users decide to switch back to drawing
	private float brushSize, lastBrushSize;
	//this variable act as a flag for whether the user is currently erasing or not
	private boolean erase=false;
	private boolean isPaint = true;
	private boolean isTri = false;
	private boolean isRect = false;
	private boolean isCir = false;
	
	
	public DrawingView(Context context, AttributeSet attrs){
	    super(context, attrs);
	    setupDrawing();
	}
	
	//get drawing area setup for interaction  
	private void setupDrawing(){
		//We use the dimension value for the medium sized brush to begin with
		brushSize = getResources().getInteger(R.integer.small_size);
		lastBrushSize = brushSize;
		
		//Second, instantiate the drawing Path and Paint objects:
		drawPath = new Path();
		drawPaint = new Paint();
		
		//Third, set the initial color:
		drawPaint.setColor(paintColor);
		
		//Next, set the initial path properties
		//Setting the anti-alias, stroke join and cap styles will make the user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(brushSize);
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
		
		
		
		if(isPaint){
			
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
			
		}else{
			if(isTri){
				
				Path triPath = new Path();
				triPath.moveTo(touchX,touchY-50f);
				triPath.lineTo(touchX+50f, touchY+25f);
				triPath.lineTo(touchX-50f,touchY+25f);
				triPath.lineTo(touchX, touchY-50f);
				drawCanvas.drawPath(triPath, drawPaint);
				
			}else if(isCir) {
				drawCanvas.drawCircle(touchX, touchY, 50, drawPaint);
				
			}else if(isRect) {
				Path rectPath = new Path();
				rectPath.moveTo(touchX-50f,touchY-50f);
				rectPath.lineTo(touchX+50f, touchY-50f);
				rectPath.lineTo(touchX+50f, touchY+50f);
				rectPath.lineTo(touchX-50f,touchY+50f);
				rectPath.lineTo(touchX-50f, touchY-50f);
				drawCanvas.drawPath(rectPath, drawPaint);

				
			}
		}

		
		//Calling invalidate will cause the onDraw method to execute
	    invalidate(); 
	    return true;
	}
	
	//set color
	public void setColor(String newColor){
		//to set color, start by invalidating the View
		invalidate();
		
		//next, parse and set the color for drawing
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);

	}
	
	//set brush size
	public void setBrushSize(float newSize){
		
		//passing the value from the dimensions file
		//update the brush size with the passed value
		//update the Paint object to use the new size
		float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
			    newSize, getResources().getDisplayMetrics());
			brushSize=pixelAmount;
			drawPaint.setStrokeWidth(brushSize);
		}
	
	//to set the LastBrushSiz 
	public void setLastBrushSize(float lastSize){
	    lastBrushSize=lastSize;
	}
	
	//to get the LastBrushSiz 
	public float getLastBrushSize(){
	    return lastBrushSize;
	}
	
	//Initially we will assume that the user is drawing, not erasing
	public void setErase(boolean isErase){
		//set erase true or false  
		//first update the flag variable
		erase=isErase;
		
		//then, alter the Paint object to erase or switch back to drawing
		if(erase){ 
			drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		}else{ 
			drawPaint.setXfermode(null);
		}
	}
	
	public void setPaint(boolean isPaint){
		this.isPaint = isPaint;
	}
	
	public void setTri(boolean isTri){
		this.isTri = isTri;
	}
	
	public void setRect(boolean isRect){
		this.isRect = isRect;
	}
	
	public void setCir(boolean isCir){
		this.isCir = isCir;
	}
	
	//to start a new drawing, simply clears the canvas and updates the display
	public void startNew(){
	    drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
	    invalidate();
	}

}
