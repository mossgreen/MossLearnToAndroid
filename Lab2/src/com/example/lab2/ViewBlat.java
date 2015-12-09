package com.example.lab2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility")
public class ViewBlat extends View implements OnTouchListener {
	
	//setup canvas and paint methods
	 Paint paint = new Paint();
	 Canvas canvas = new Canvas();

	 float x;
	 float y;
	 
	 /*  MY QUESTIONS:
	  
	1. where should I put the method invalidate()? in onDraw or onTouch?
	2. what's the difference between
	     canvas.drawBitmap(bmp, x, y, null);  
	     canvas.drawBitmap(bmp, x, y, paint);  
	*/
     
	 
	@SuppressLint("ClickableViewAccessibility")
	public ViewBlat(Context context){
		super(context);
		
		///setup an event handle to handle touch events 
		setOnTouchListener(this);
	}



	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas){

		 Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
	     canvas.drawBitmap(bmp, x, y, null);  
	     canvas.drawCircle(x+400, y+400,40, paint);
	     
	     invalidate(); //force screen redraw
	     
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			x = event.getX();
			y = event.getY();
			break;
			
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			break;

		}	
		return true; //return true to consume event from the event queue
	}

}
