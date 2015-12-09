package com.example.assginment01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Paintview extends View implements OnTouchListener{
	Paint paint;
	float x;
	float y;
	
	

	public Paintview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		paint = new Paint();
	}

	public Paintview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paint.setColor(Color.RED);
		canvas.drawCircle(x, y, 50, paint);
		

	}
	
	
	
	
	@Override
	//public boolean onTouchEvent(MotionEvent event) {
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
		invalidate();
		return true; //return true to consume event from the event queue
	}
	
	
}