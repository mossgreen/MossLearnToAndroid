package com.example.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


//i'd like to extends SurfaceView rather than View, since SV is more efficient than View
public class PaintView extends View {
	
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
		drawPath = new Path();
		drawPaint = new Paint();
        
		//Next set the initial color:
		drawPaint.setColor(paintColor);

		
//		Now set the initial path properties:
//		 Setting the anti-alias, stroke join and cap styles will make the user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		
//		instantiating the canvas Paint object:
		canvasPaint = new Paint(Paint.DITHER_FLAG);

		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		 draw the canvas and the drawing path:
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
		
		
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
//		Inside the method, retrieve the X and Y positions of the user touch:
		float touchX = event.getX();
		float touchY = event.getY();

//		The MotionEvent parameter to the onTouchEvent method will let us respond to particular touch events. 
//		The actions we are interested in to implement drawing are down, move and up. 
//		Add a switch statement in the method to respond to each of these:
		
		switch (event.getAction()) {
		
//		When the user touches the View, we move to that position to start drawing. 
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(touchX, touchY);
		    break;
		    
//		When they move their finger on the View, we draw the path along with their touch.
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    break;
		    
//		When they lift their finger up off the View, we draw the Path and reset it for the next drawing operation
		case MotionEvent.ACTION_UP:
		    drawCanvas.drawPath(drawPath, drawPaint);
		    drawPath.reset();
		    break;
		default:
		    return false;
		}
		invalidate(); //will cause the onDraw method to execute.
		return true; //return true to consume event from the event queue
	}
	
//	 	set the color
		public void setColor(String newColor){
			
//		start by invalidating the View:
			invalidate();
			
//		Next parse and set the color for drawing:
			paintColor = Color.parseColor(newColor);
			drawPaint.setColor(paintColor);		
		}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		//create & re-create t he off screen bitmap to capture the state of our drawing 
		//instantiate the drawing canvas and bitmap using the width and height values://		
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}
}
