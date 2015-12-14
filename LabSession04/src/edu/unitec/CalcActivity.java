package edu.unitec;

import edu.unitec.calcs.Calculator;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main class of the application. This application is a kind of calculator to
 * add to numbers. The main activity handles the button inputs of the
 * application.
 * 
 * @author Richard
 */
public class CalcActivity extends Activity implements OnClickListener
{
	// declare buttons and text view
	private Button mZeroButton;
	private Button mOneButton;
	private Button mTwoButton;
	private Button mThreeButton;
	private Button mFourButton;
	private Button mFiveButton;
	private Button mSixButton;
	private Button mSevenButton;
	private Button mEightButton;
	private Button mNineButton;
	private Button mResultButton;
	private Button mPlusButton;
	private Button mClearButton;

	private TextView mResultTextView;

	// initialise strings and booleans
	private String mResultText = "0";
	private String mFirstNum = "0";
	private String mSecondNum = "0";

	private boolean mNumberEntered = false;
	private boolean mShowResult = false;

	// declare new string builder
	private StringBuilder mCalcSteps;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calc);

		// initialise text view
		mResultTextView = (TextView) findViewById(R.id.resultText); //resultText is the textview in xml
		mResultTextView.setText(mResultText);

		// initialise string builder
		mCalcSteps = new StringBuilder();

		// call method to initialise buttons
		initButtons();
	}

	/**
	 * Method to initialise buttons
	 */
	private void initButtons()
	{
		mZeroButton = (Button) findViewById(R.id.zeroButton);
		mZeroButton.setOnClickListener(this);

		mOneButton = (Button) findViewById(R.id.oneButton);
		mOneButton.setOnClickListener(this);

		mTwoButton = (Button) findViewById(R.id.twoButton);
		mTwoButton.setOnClickListener(this);

		mThreeButton = (Button) findViewById(R.id.threeButton);
		mThreeButton.setOnClickListener(this);

		mFourButton = (Button) findViewById(R.id.fourButton);
		mFourButton.setOnClickListener(this);

		mFiveButton = (Button) findViewById(R.id.fiveButton);
		mFiveButton.setOnClickListener(this);

		mSixButton = (Button) findViewById(R.id.sixButton);
		mSixButton.setOnClickListener(this);

		mSevenButton = (Button) findViewById(R.id.sevenButton);
		mSevenButton.setOnClickListener(this);

		mEightButton = (Button) findViewById(R.id.eightButton);
		mEightButton.setOnClickListener(this);

		mNineButton = (Button) findViewById(R.id.nineButton);
		mNineButton.setOnClickListener(this);

		mResultButton = (Button) findViewById(R.id.resultButton);
		mResultButton.setOnClickListener(this);

		mPlusButton = (Button) findViewById(R.id.plusButton);
		mPlusButton.setOnClickListener(this);

		mClearButton = (Button) findViewById(R.id.clearButton);
		mClearButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// get id of clicked button and handle actions
		switch (v.getId())
		{
		case R.id.zeroButton:

			// call method to add the number to a string
			getNewNumber("0");

			break;

		case R.id.oneButton:

			// call method to add the number to a string
			getNewNumber("1");

			break;

		case R.id.twoButton:

			// call method to add the number to a string
			getNewNumber("2");

			break;

		case R.id.threeButton:

			// call method to add the number to a string
			getNewNumber("3");

			break;

		case R.id.fourButton:

			// call method to add the number to a string
			getNewNumber("4");

			break;

		case R.id.fiveButton:

			// call method to add the number to a string
			getNewNumber("5");

			break;

		case R.id.sixButton:

			// call method to add the number to a string
			getNewNumber("6");

			break;

		case R.id.sevenButton:

			// call method to add the number to a string
			getNewNumber("7");

			break;

		case R.id.eightButton:

			// call method to add the number to a string
			getNewNumber("8");

			break;

		case R.id.nineButton:

			// call method to add the number to a string
			getNewNumber("9");

			break;

		case R.id.resultButton:

			if (mNumberEntered)
			{
				// call method to calculate the result
				addNumbers();

				/*
				 * TODO STEP 05a: Create a new intent and set the text of the
				 * string builder to it. Set the result to OK and add the
				 * intent.
				 */
				
				String message = mCalcSteps.toString();
				Intent intent = new Intent(CalcActivity.this, MainActivity.class);
				intent.putExtra("result", message);
				setResult(Activity.RESULT_OK,intent);
				
				
			

				// finish activity
				finish();
			}

			break;

		case R.id.plusButton:

			// call method to calculate the result
			addNumbers();

			// allow further actions
			mShowResult = false;

			break;

		case R.id.clearButton:

			// reset strings and booleans
			mResultText = "0";
			mFirstNum = "0";
			mSecondNum = "0";

			mNumberEntered = false;
			mShowResult = false;

			// TODO STEP 05b: Delete everything in the string builder
			mCalcSteps = new StringBuilder();
			
			break;
		}

		// set new text to the text view
		mResultTextView.setText(mResultText);
	}

	/**
	 * Method to add a new number to a string
	 * 
	 * @param number
	 *            The number to add
	 */
	private void getNewNumber(String number)
	{
		// check if further actions are allowed
		if (!mShowResult)
		{
			// check if a number is already entered
			if (mNumberEntered)
			{
				// check if first value of the number is 0
				if (mSecondNum.startsWith("0"))
					// replace 0 with new number
					mSecondNum = number;
				else
					// otherwise add number to the string
					mSecondNum = mSecondNum + number;

				// set new result text
				mResultText = mSecondNum;
			}
			else
			{
				// check if first value of the number is 0
				if (mFirstNum.startsWith("0"))
					// replace 0 with new number
					mFirstNum = number;
				else
					// otherwise add number to the string
					mFirstNum = mFirstNum + number;

				// set new result text
				mResultText = mFirstNum;
			}
		}
	}

	/**
	 * Method to add the numbers
	 */
	private void addNumbers()
	{
		// check if a number is already entered
		if (mNumberEntered)
		{
			// calculate the result of the values
			mResultText = "" + Calculator.addNumbers(mFirstNum, mSecondNum);

			/*
			 * TODO STEP 04b: Add further numbers to the string builder. The
			 * text should have a format like this: 5 + 6 = 11 + 3 = 14
			 */
			
			mResultText += Calculator.addNumbers(mResultText, mSecondNum)+"";

			/*
			 * set result to first number and reset the second number for
			 * further calculations
			 */
			mFirstNum = mResultText;
			mSecondNum = "0";
		}
		else
		{
			// allow to enter second number
			mNumberEntered = true;

			// TODO STEP 04a: Add the first number to the string builder
			mCalcSteps.append(mFirstNum);
		}

		// allow to show the result
		mShowResult = true;
	}
}