package com.example.assignment01;


import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private PaintView paintView;
	private ImageButton currColor;
	private ImageButton currShape;
	private ImageButton drawBtn;
	private float smallBrush, mediumBrush, largeBrush;
	
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		paintView = (PaintView) findViewById(R.id.drawing);
		GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);
		currColor = (ImageButton) gridLayout.getChildAt(0);
		currColor.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
		
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		
		drawBtn = (ImageButton)findViewById(R.id.line);
		drawBtn.setOnClickListener(this);

	}
	
	public void colorClicked(View view){
	    //use chosen color
		
		if(view!=currColor){
			//update color
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			
			//after retrieving the color tag, call the new method on the custom drawing View object
			paintView.setColor(color);
			
			
			//update the UI to reflect the new chosen paint and set the previous one back to normal:
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.pressed));
			currColor.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currColor=(ImageButton)view;
			
			}
	}
	
	public void shapeClicked(View view){
	    //use chosen color
	}
	
	
	
	@Override
	public void onClick(View v) {
	}
}