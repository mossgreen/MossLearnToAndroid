package com.jcasey;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View
{
//	public static final int RADIUS = 30;
//	public static final int DELTA = 4; // TODO change code below to use this constant

	public ArrayList<BBall> arrayList = new ArrayList<BBall>();
	
	BBall bball1 = new BBall(50f,50f,10,1);
	BBall bball2= new BBall(100f,100f,15,3);
	BBall bball3= new BBall(150f,150f,30,5);
	BBall bball4= new BBall(200f,200f,20,20);

	
	int width;
	int height;

	public DrawView(Context context)
	{
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO alter code so that we have multiple bouncing circles
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		
		arrayList.add(bball1);
		arrayList.add(bball2);
		arrayList.add(bball3);
		arrayList.add(bball4);
		
		// loop the arrayList, which stores instances of bouncing balls
		// painting new balls with their new x_y locations
		paintBalls(canvas, paint);
		
		// set boundaries for every bouncing ball
		// if they heat the wall, they go backward
		setBoundaries();
		
		
		
//		// force the screen to be redrawn
//		invalidate();
	}

	private void paintBalls(Canvas canvas, Paint paint) {
		
		for(int i=0; i<arrayList.size(); i++){
			
			canvas.drawCircle(arrayList.get(i).getX(), arrayList.get(i).getY(), arrayList.get(i).getRadius(), paint);
			arrayList.get(i).setX(arrayList.get(i).getX()+arrayList.get(i).getSpeed());
			arrayList.get(i).setY(arrayList.get(i).getX()+arrayList.get(i).getSpeed());
			
		}
	}
	

	private void setBoundaries() {
		
		for(int j = 0; j<arrayList.size();j++){
			
			// hit the east wall
			if(arrayList.get(j).getX()+arrayList.get(j).getRadius() >width){
				arrayList.get(j).setSpeed(-arrayList.get(j).getSpeed()); 
			}
			
			//hit the south wall
			if(arrayList.get(j).getY()+arrayList.get(j).getRadius() >height){
				arrayList.get(j).setSpeed(-arrayList.get(j).getSpeed()); 
			}
			
			// hit the west wall
			if(arrayList.get(j).getX()-arrayList.get(j).getRadius() < 0)
			{
				arrayList.get(j).setSpeed(arrayList.get(j).getSpeed()); 
			}
			
			// hit the north wall
			if(arrayList.get(j).getY()-arrayList.get(j).getRadius() < 0)
			{
				arrayList.get(j).setSpeed(arrayList.get(j).getSpeed()); // change increment value so that ball bounces off bottom wall
			}		
			
			
			float currentX = arrayList.get(j).getX();
			float currentY = arrayList.get(j).getY();
			
			for(int k = 0; k<arrayList.size(); k++){
				float run = currentX - arrayList.get(k).getX();
				float rise = currentY - arrayList.get(k).getY();
				double distance = Math.sqrt(Math.pow(run, 2)+Math.pow(rise, 2));
				
				if(distance != 0 && distance <= arrayList.get(j).getRadius()+arrayList.get(k).getRadius()){
					double angle = Math.atan2(run, rise);
					
					arrayList.get(j).setX((float)(Math.cos(angle)*arrayList.get(j).getSpeed()));
					arrayList.get(j).setY((float)(Math.sin(angle)*arrayList.get(j).getSpeed()));
				}
			}
			
		}
	}

	
	// use the onSizeChanged method to keep track of the width and height of the screen
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		height = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}