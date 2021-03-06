package com.example.drawingfun;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Main class of the application.
 * This activity presents a canvas that users can draw on with pen or shapes
 * like triangle, circle and rectangle.
 * 
 * @author Feifei GU <gufeifei@outlook.com>
 * 
 * @version 20151216
 * @since java 1.7
 */
public class MainActivity extends Activity {
	private DrawingView drawView; // This represents the instance of the custom
									// View that we added to the layout.

	// represents the paint color button in the palette, the drawing button,
	// eraser button, and new_draw button
	private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn, exitBtn, shareBtn;
	private ImageButton  triBtn, cirBtn, rectBtn;
	private float smallBrush, mediumBrush, largeBrush; // to store the three
														// dimension values

	/**
	 * Called when the activity is first created.
	 * retrieving references from xml file to create layout and other
	 * elements that will be shown on the main interface
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// retrieving a reference from the layout
		drawView = (DrawingView) findViewById(R.id.drawing);

		// we now want to retrieve the first paint color button in the palette
		// area, which is initially going to be selected.
		// First retrieve the Linear Layout it is contained within:
		LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);

		// to initialize the function buttons, like share,eraser, exit, etc..
		initFuncs();

		// to initialize the shape buttons, like triangle, rectangle and circle
		initShapes(paintLayout);

		// initialize brushes with different sizes
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);

		// set the initial brush size
		drawView.setBrushSize(smallBrush);

	}

	/**
	 * to initialize the function buttons, like share,eraser, exit, etc..
	 */
	private void initFuncs() {

		// initialize eraser button
		initEraserBtn();

		// initialize the "new" button
		initNewBtn();

		// initialize the save button
		initSaveBtn();

		shareBtn = (ImageButton) findViewById(R.id.mail_btn);
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// start by enabling the drawing cache
				drawView.setDrawingCacheEnabled(true);

				// find the url
				String path = MediaStore.Images.Media.insertImage(getContentResolver(), drawView.getDrawingCache(),
						"Image Description", null);
				Uri uri = Uri.parse(path);

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(intent, "Share Image"));

				// Destroy the drawing cache so that any future drawings saved
				// won't use the existing cache
				drawView.destroyDrawingCache();

			}
		});

		// retrieve a reference to the save button from the layout and set up a
		// click listener
		exitBtn = (ImageButton) findViewById(R.id.exit_btn);
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * to initialize the save button in the bottom of the interface
	 *
	 */
	private void initSaveBtn() {
		// retrieve a reference to the save button from the layout and set up a
		// click listener
		saveBtn = (ImageButton) findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while save drawing button is clicked

				// to verify that the user definitely wants to save the drawing
				// this alert dialog lets user change their mind,
				// calling a save method if they decide to go ahead and save the
				// drawing, in which case the current drawing will be saved
				AlertDialog.Builder saveDialog = new AlertDialog.Builder(v.getContext());
				saveDialog.setTitle("Save drawing");
				saveDialog.setMessage("Save drawing to device Gallery?");

				// If the user chooses to go ahead and save, we need to output
				// the currently displayed View as an image
				saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// start by enabling the drawing cache
						drawView.setDrawingCacheEnabled(true);

						// We use the insertImage method to attempt to write the
						// image to the media store for images on the device,
						// which should save it to the user gallery.
						// We pass the content resolver, drawing cache for the
						// displayed View,
						// a randomly generated UUID string for the filename
						// with PNG extension and a short description.
						// This method returns the URL of the image created, or
						// null if the operation was unsuccessful,
						String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
								drawView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");

						// give user feedback
						if (imgSaved != null) {
							Toast savedToast = Toast.makeText(getApplicationContext(), "Drawing saved to Gallery!",
									Toast.LENGTH_SHORT);
							savedToast.show();
						} else {
							Toast unsavedToast = Toast.makeText(getApplicationContext(),
									"Oops! Image could not be saved.", Toast.LENGTH_SHORT);
							unsavedToast.show();
						}

						// Destroy the drawing cache so that any future drawings
						// saved won't use the existing cache
						drawView.destroyDrawingCache();

					}
				});
				saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				saveDialog.show();
			}
		});
	}

	/**
	 * to initialize the "new" button in the bottom of the interface
	 *
	 */
	private void initNewBtn() {
		// retrieve a reference to the new button from the layout and set up a
		// click listener
		newBtn = (ImageButton) findViewById(R.id.new_btn);
		newBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while new button is clicked

				// to verify that the user definitely wants to start a new
				// drawing
				// this alert dialog lets user change their mind,
				// calling a new method if they decide to go ahead and start a
				// new drawing, in which case the current drawing is cleared
				AlertDialog.Builder newDialog = new AlertDialog.Builder(v.getContext());
				newDialog.setTitle("New drawing");
				newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
				newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						drawView.startNew();
						drawView.setBrushSize(smallBrush);
						drawView.setLastBrushSize(smallBrush);
						drawView.setPaint(true);
						drawView.setErase(false);
						dialog.dismiss();
					}
				});
				newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						drawView.setBrushSize(smallBrush);
						drawView.setLastBrushSize(smallBrush);
						drawView.setPaint(true);
						drawView.setErase(false);
						dialog.cancel();
					}
				});
				newDialog.show(); // Finally, display the Dialog
			}
		});
	}

	/**
	 * to initialize the eraser button in the bottom of the interface, which
	 * allows user to choose from three different sizes
	 *
	 */
	private void initEraserBtn() {
		// retrieve a reference to the eraser button from the layout and set up
		// a click listener
		eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
		eraseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while eraser button is clicked, switch to erase - choose size

				drawView.setPaint(true);

				// When the user clicks the button, it will display a dialog
				// presenting them with the three button sizes
				final Dialog brushDialog = new Dialog(v.getContext());
				brushDialog.setTitle("Eraser size:");

				// set layout
				brushDialog.setContentView(R.layout.brush_chooser);

				// listen for clicks on the three size buttons, this is the
				// small one:
				ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
				smallBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						drawView.setErase(true);
						drawView.setBrushSize(smallBrush);
						brushDialog.dismiss();
					}
				});

				// listen for clicks on the three size buttons, this is the
				// medium one:
				ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
				mediumBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						drawView.setErase(true);
						drawView.setBrushSize(mediumBrush);
						brushDialog.dismiss();
					}
				});

				// listen for clicks on the three size buttons, this is the
				// large one:
				ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
				largeBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						drawView.setErase(true);
						drawView.setBrushSize(largeBrush);
						brushDialog.dismiss();
					}
				});

				brushDialog.show(); // Finally, display the Dialog
			}
		});
	}

	/**
	 * to initialize the shape buttons, including pen, triangle, circle and
	 * rectangle
	 * 
	 * @param   LinearLayout 
	 * 	the linearlayout that we stored shapes
	 */
	private void initShapes(LinearLayout paintLayout) {
		// Get the first button and store it as the instance variable
		// use a different drawable image on the button to show that it is
		// currently selected:
		currPaint = (ImageButton) paintLayout.getChildAt(0);
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

		// retrieve a reference to the draw button from the layout and set up a
		// click listener
		drawBtn = (ImageButton) findViewById(R.id.draw_btn);
		drawBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// draw button clicked
				// set the size as soon as the user clicks a brush size button,
				// then immediately dismiss the Dialog
				drawView.setBrushSize(smallBrush);
				drawView.setLastBrushSize(smallBrush);
				drawView.setPaint(true);
				drawView.setErase(false);

			}
		});

		triBtn = (ImageButton) findViewById(R.id.triangle);
		triBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while triangle button is clicked,
				drawView.setBrushSize(smallBrush);
				drawView.setLastBrushSize(smallBrush);
				drawView.setPaint(false);
				drawView.setErase(false);
				drawView.setCir(false);
				drawView.setRect(false);

				drawView.setTri(true);
			}
		});

		cirBtn = (ImageButton) findViewById(R.id.circle);
		cirBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while circle button is clicked
				drawView.setBrushSize(smallBrush);
				drawView.setLastBrushSize(smallBrush);
				drawView.setPaint(false);
				drawView.setErase(false);
				drawView.setRect(false);
				drawView.setCir(true);
			}
		});

		rectBtn = (ImageButton) findViewById(R.id.rectangle);
		rectBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// while rect button is clicked
				drawView.setBrushSize(smallBrush);
				drawView.setLastBrushSize(smallBrush);
				drawView.setPaint(false);
				drawView.setErase(false);
				drawView.setCir(false);
				drawView.setTri(false);
				drawView.setRect(true);

			}
		});
	}

	/**
	 * Handle a click on the colors which registered in the xml file
	  * @param View
	 *            view is the button that clicked, which is a imagebutton
	 */
	public void paintClicked(View view) {
		// first check that the user has clicked a paint color that is not the
		// currently selected one:
		if (view != currPaint) {

			// retrieve the tag we set for each button in the layout,
			// representing the chosen color：
			ImageButton imgView = (ImageButton) view;
			String color = view.getTag().toString();

			// update color
			drawView.setColor(color);

			// update the UI to reflect the new chosen paint and set the
			// previous one back to normal
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint = (ImageButton) view;
		}

		// When the user has been erasing and clicks a paint color button,
		// we will assume that they want to switch back to drawing.
		drawView.setErase(false);

		// set the brush size back to the last one used when drawing rather than
		// erasing
		drawView.setBrushSize(drawView.getLastBrushSize());
	}

}
