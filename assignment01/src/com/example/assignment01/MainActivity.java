package com.example.assignment01;


import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
//	private ImageButton currColor;
//	private ImageButton currShape;
//	private ImageButton drawBtn;
//	private float smallBrush, mediumBrush, largeBrush;
	private PaintView paintView;

	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
//		paintView = (PaintView) findViewById(R.id.drawing);
		
//		
//		LinearLayout paletteLayout = (LinearLayout)findViewById(R.id.paint_colors);
//		currColor = (ImageButton) paletteLayout.getChildAt(0);
//		currColor.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
		
//		smallBrush = getResources().getInteger(R.integer.small_size);
//		mediumBrush = getResources().getInteger(R.integer.medium_size);
//		largeBrush = getResources().getInteger(R.integer.large_size);
//		
//		drawBtn = (ImageButton)findViewById(R.id.line);
//		drawBtn.setOnClickListener(this);
//		
//
//		LinearLayout shapeLayout = (LinearLayout)findViewById(R.id.paint_shapes);
//		currShape = (ImageButton) paletteLayout.getChildAt(0);
//		currShape.setImageDrawable(getResources().getDrawable(R.drawable.pressed));

	}
//	
//	public void colorClicked(View view){
//	    //use chosen color
//		
//		if(view!=currColor){
//			//update color
//			ImageButton imgView = (ImageButton)view;
//			String color = view.getTag().toString();
//			
//			//after retrieving the color tag, call the new method on the custom drawing View object
//			paintView.setColor(color);
//			
//			
//			//update the UI to reflect the new chosen paint and set the previous one back to normal:
//			imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
//			currColor.setImageDrawable(getResources().getDrawable(R.drawable.paint));
//			currColor=(ImageButton)view;
//			
//			}
//	}
//	
//	public void shapeClicked(View view){
//	    //use chosen color
//	}
//	
//	
//	
	@Override
	public void onClick(View v) {
	}
}
