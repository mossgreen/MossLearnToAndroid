package com.example.lab2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

@SuppressLint("DrawAllocation")
public class DrawView extends View {
	
	Bitmap bitmap;
	
	
	public DrawView(Context context)
	{
		super(context);
	}
	
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE); // set a background color to the canvas
		Paint paint = new Paint(); // get a paint object which holds the color and style
		
		
		canvas.drawText("welcome to Mobile Programming", 5, 10, paint);
		
		// get the current width and height of the screen
		int canvasHeight = this.getHeight();
		int canvasWidth = this.getWidth();
		
		// get the distance for the center of the screen
		int widthOffset = canvasWidth/2;
		int heightOffset = canvasHeight/2;
		
		//step 01: change the color of the paint to green
		paint.setColor(Color.GREEN);
		
		//step 02: draw a filled square with a side length of 40
		//void drawRect(float left, float top, float right, float bottom, Paint paint)
		RectF rectF = new RectF(widthOffset, heightOffset, widthOffset+40, heightOffset+40); 
		canvas.drawRect(rectF, paint);
//		bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();  
//		canvas.drawBitmap(bitmap, null, rectF, null);
		
		//step 03a: change color to black and draw two lines
		paint.setColor(Color.rgb(0, 0, 0));// this is another way to set color, by using rgb
		paint.setStrokeWidth(1); //set the width of the line
		//public void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
		canvas.drawLine(widthOffset, heightOffset, widthOffset+40, heightOffset+40, paint); //line from top left to bottom right
		canvas.drawLine(widthOffset+40, heightOffset, widthOffset, heightOffset+40, paint); //line from top right to bottom left
		
		// learn to use public void drawLines (float[] pts, Paint paint)
		// will draw four frames for the rectangle by using red color
		
		paint.setColor(Color.BLUE); // set the color to red
		//following array is actually four points of the rectangle
		float[] pts = { widthOffset+1,heightOffset+1, widthOffset+41,heightOffset+1, //from the left top to right top
				widthOffset+41,heightOffset+1, widthOffset+41,heightOffset+41, //from right top to right bottom
				widthOffset+41,heightOffset+41, widthOffset+1,heightOffset+41, //from right bottom to left bottom
				widthOffset+1,heightOffset+41,widthOffset+1,heightOffset+1
		};
		paint.setStrokeWidth(1); // set the width of line
		canvas.drawLines(pts, paint);
		
		//step 03b: change color to red and draw a filled red circle with the diameter of 10
		// got a color.xml in res/values, I'll get the color out and use it here
		
		//Resources myColor = getResources();
		//int Mycolor = getResources().getColor(R.color.myRed);
		//paint.setColor(getResources().getColor(R.color.myRed));
		// public void drawCircle (float cx, float cy, float radius, Paint paint)
		
		//paint.setColor(getResources().getColor(R.color.myRed));
		//paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(widthOffset, heightOffset, 10, paint);
		
		//step 03c:change color to black and draw black circle
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1); //set the width of the line
		canvas.drawCircle(widthOffset+40, heightOffset+40, 10, paint);
		
		
		//step 04 try bitmap
		//Question: if I draw a bitmap, other pictures will lost
		 Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
         canvas.drawColor(Color.BLACK);  
         canvas.drawBitmap(bmp, 10, 10, null);  
         
         
		
	}
	
	

}