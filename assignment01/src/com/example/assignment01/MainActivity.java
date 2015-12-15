package com.example.assignment01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	private PaintView paintView; //This represents the instance of the custom View that we added to the layout.
	
	//represents the paint color button in the palette, the drawing button, eraser button, and new_draw button
	private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn, exitBtn;
	private ImageButton currShape, triBtn, cirBtn, rectBtn;
	private float smallBrush, mediumBrush, largeBrush; // to store the three dimension values

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		paintView = (PaintView)findViewById(R.id.drawing);//retrieving a reference from the layout
		
		//we now want to retrieve the first paint color button in the palette area, which is initially going to be selected. 
		//First retrieve the Linear Layout it is contained within:
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);

		
		//Get the first button and store it as the instance variable
		//use a different drawable image on the button to show that it is currently selected:
		currPaint = (ImageButton)paintLayout.getChildAt(0);
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
		
		//retrieve a reference to the draw button from the layout and set up a click listener
		drawBtn = (ImageButton)findViewById(R.id.draw_btn);
		drawBtn.setOnClickListener(this);
		
		//retrieve a reference to the eraser button from the layout and set up a click listener
		eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
		eraseBtn.setOnClickListener(this);
		
		//retrieve a reference to the new button from the layout and set up a click listener
		newBtn = (ImageButton)findViewById(R.id.new_btn);
		newBtn.setOnClickListener(this);
		
		//retrieve a reference to the save button from the layout and set up a click listener
		saveBtn = (ImageButton)findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(this);
		
		exitBtn = (ImageButton)findViewById(R.id.exit_btn);
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
//		currShape
		
		
		triBtn = (ImageButton)findViewById(R.id.triangle);
		triBtn.setOnClickListener(this);
		
		cirBtn = (ImageButton)findViewById(R.id.circle);
		cirBtn.setOnClickListener(this);
		
		rectBtn = (ImageButton)findViewById(R.id.rectangle);
		rectBtn.setOnClickListener(this);
		
		
		
		//Instantiate brushes
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		
		//set the initial brush size
		paintView.setBrushSize(smallBrush);

	}
	
    //use chosen color
	public void paintClicked(View view){
		//first check that the user has clicked a paint color that is not the currently selected one:
		if(view != currPaint){
			
			//retrieve the tag we set for each button in the layout, representing the chosen color：
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			
			//update color
			paintView.setColor(color);
			
			//update the UI to reflect the new chosen paint and set the previous one back to normal:
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint = (ImageButton)view;
			}
		
		//When the user has been erasing and clicks a paint color button, 
		//we will assume that they want to switch back to drawing.
		paintView.setErase(false);
		
		//set the brush size back to the last one used when drawing rather than erasing
		paintView.setBrushSize(paintView.getLastBrushSize());
	}
	
	@Override
	public void onClick(View view){
		//check for clicks on the  buttons 
		
		
		if(view.getId()==R.id.draw_btn){
		    //draw button clicked
	    	//set the size as soon as the user clicks a brush size button, 
	    	//then immediately dismiss the Dialog
	        paintView.setBrushSize(smallBrush);
	        paintView.setLastBrushSize(smallBrush);
	        paintView.setPaint(true);
	        paintView.setErase(false);
			
		}else if(view.getId()==R.id.circle){
		    //while circle button is clicked,
	        paintView.setBrushSize(smallBrush);
	        paintView.setLastBrushSize(smallBrush);
			paintView.setPaint(false);
	        paintView.setErase(false);
	        paintView.setRect(false);

	        paintView.setCir(true);

			
		}else if(view.getId()==R.id.triangle){
		    //while triangle button is clicked, 
	        paintView.setBrushSize(smallBrush);
	        paintView.setLastBrushSize(smallBrush);
			paintView.setPaint(false);
	        paintView.setErase(false);
	        paintView.setCir(false);
	        paintView.setRect(false);

	        paintView.setTri(true);

			
		}else if(view.getId()==R.id.rectangle){
		    //while rect button is clicked,
	        paintView.setBrushSize(smallBrush);
	        paintView.setLastBrushSize(smallBrush);
			paintView.setPaint(false);
	        paintView.setErase(false);
	        paintView.setCir(false);
	        paintView.setTri(false);
	        paintView.setRect(true);

			
		}else if(view.getId()==R.id.erase_btn){
//while eraser button is clicked, switch to erase - choose size
			
	        paintView.setPaint(true);

			//When the user clicks the button, it will display a dialog presenting them with the three button sizes
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Eraser size:");
			
			//set layout
			brushDialog.setContentView(R.layout.brush_chooser);
			
			//listen for clicks on the three size buttons, this is the small one:
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        paintView.setErase(true);
			        paintView.setBrushSize(smallBrush);
			        brushDialog.dismiss();
			    }
			});
			
			//listen for clicks on the three size buttons, this is the medium one:
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        paintView.setErase(true);
			        paintView.setBrushSize(mediumBrush);
			        brushDialog.dismiss();
			    }
			});
			
			//listen for clicks on the three size buttons, this is the large one:
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        paintView.setErase(true);
			        paintView.setBrushSize(largeBrush);
			        brushDialog.dismiss();
			    }
			});
			
			brushDialog.show(); //Finally, display the Dialog
			
		}else if(view.getId()==R.id.new_btn){
		    //while new button is clicked
			
			//to verify that the user definitely wants to start a new drawing
			//this alert dialog lets user change their mind, 
			//calling a new method if they decide to go ahead and start a new drawing, in which case the current drawing is cleared
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New drawing");
			newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        paintView.startNew();
			        paintView.setBrushSize(smallBrush);
			        paintView.setLastBrushSize(smallBrush);
			        paintView.setPaint(true);
			        paintView.setErase(false);
			        dialog.dismiss();
			    }
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			    	paintView.setBrushSize(smallBrush);
			        paintView.setLastBrushSize(smallBrush);
			        paintView.setPaint(true);
			        paintView.setErase(false);
			        dialog.cancel();
			    }
			});
			newDialog.show(); //Finally, display the Dialog
		}else if(view.getId()==R.id.save_btn){
            //while save drawing button is clicked
			
			
			//to verify that the user definitely wants to save the drawing
			//this alert dialog lets user change their mind, 
			//calling a save method if they decide to go ahead and save the drawing, in which case the current drawing will be saved
			AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
			saveDialog.setTitle("Save drawing");
			saveDialog.setMessage("Save drawing to device Gallery?");
			
			//If the user chooses to go ahead and save, we need to output the currently displayed View as an image
			saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        //start by enabling the drawing cache
			    	paintView.setDrawingCacheEnabled(true);
			    	
			    	//We use the insertImage method to attempt to write the image to the media store for images on the device, 
			    	//	which should save it to the user gallery.
			    	//We pass the content resolver, drawing cache for the displayed View, 
			    	// a randomly generated UUID string for the filename with PNG extension and a short description. 
			    	//This method returns the URL of the image created, or null if the operation was unsuccessful,
			    	String imgSaved = MediaStore.Images.Media.insertImage(
			    		    getContentResolver(), paintView.getDrawingCache(),
			    		    UUID.randomUUID().toString()+".png", "drawing");
			    	
			    	//give user feedback
			    	if(imgSaved!=null){
			    	    Toast savedToast = Toast.makeText(getApplicationContext(), 
			    	        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
			    	    savedToast.show();
			    	}
			    	else{
			    	    Toast unsavedToast = Toast.makeText(getApplicationContext(), 
			    	        "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
			    	    unsavedToast.show();
			    	}
			    	
			    	//Destroy the drawing cache so that any future drawings saved won't use the existing cache
			    	paintView.destroyDrawingCache();

			    }
			});
			saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			saveDialog.show();
		}

	}

}
