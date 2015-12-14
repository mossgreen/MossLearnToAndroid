package edu.unitec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Main Activity of the application. This class shows the steps of calculation
 * and allows to do new calculations.
 * 
 * @author Richard
 * 
 */
public class MainActivity extends Activity
{
	// initialise constants
	public static final int NEW_CALC = 100;

	// declare widgets
	private LinearLayout mLayout;
	private TextView mCalcStepsText;

	// initialise booleans
	private boolean mRemoveText = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/*
		 * TODO STEP 01: Initialise the linear layout and set the orientation to
		 * vertical (LinearLayout.VERTICAL)
		 */

		// create new button to start calculation
		Button button = new Button(this);
		button.setText("New calculation");

		/*
		 * TODO STEP 02: Set a OnClickListener to the button. In the
		 * onClick-method of the button you should start the calc activity for
		 * result and maybe remove older result texts.
		 */

		// initialise calculation steps text
		mCalcStepsText = new TextView(this);

		// create new text view
		TextView text = new TextView(this);
		text.setText("The steps of calculation are:");

		/*
		 * TODO STEP 03: Add the button and the text (not the calculation steps
		 * text) to the layout and set the layout to the content view of the
		 * application.
		 */
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		/*
		 * TODO STEP 06a: Check if the result is OK and extract the text from
		 * the intent.
		 */

		/*
		 * TODO STEP 06b: Set the new text to the calculation steps text view
		 * and add the text view to the layout
		 */
	}
}
