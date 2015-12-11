package com.example.drawingfun;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	private DrawingView drawView; //This represents the instance of the custom View that we added to the layout.
	private ImageButton currPaint; //This represents the paint color button in the palette

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawView = (DrawingView)findViewById(R.id.drawing);//retrieving a reference from the layout
		
		//we now want to retrieve the first paint color button in the palette area, which is initially going to be selected. 
		//First retrieve the Linear Layout it is contained within:
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
		
		//Get the first button and store it as the instance variable:
		currPaint = (ImageButton)paintLayout.getChildAt(0);

		//use a different drawable image on the button to show that it is currently selected:
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));


	}
	
    //use chosen color
	public void paintClicked(View view){
		//first check that the user has clicked a paint color that is not the currently selected one:
		if(view != currPaint){
			
			//retrieve the tag we set for each button in the layout, representing the chosen color：
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			
			//update color
			drawView.setColor(color);
			
			//update the UI to reflect the new chosen paint and set the previous one back to normal:
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint = (ImageButton)view;
			}



	}

}
