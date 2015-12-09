package com.example.lab03f;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	// Buttons of the calculator
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

    // TextView to show the result of the calculation
    private TextView mResultTextView;

    // result string that is always set to the TextView
    private String mResultText = "0";

    // two strings to store the values of the numbers that should be added
    private String mFirstNum = "0";
    private String mSecondNum = "0";

    // boolean to check if a number has already be entered
    private boolean mNumberEntered = false;

    // boolean to check if a result is shown at the display
    private boolean mShowResult = false;
    
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            /*
             * TODO STEP 01: Connect the result-TextView to an object and set the
             * result text to it
             */

            initButtons();
    }

    private void initButtons()
    {
            /*
             * TODO STEP 02: Connect the Buttons to objects and set the
             * OnClicklistener to it
             */
    }
//
//    @Override
//    public void onClick(View v)
//    {
//            switch (v.getId())
//            {
//            case R.id.zeroButton:
//
//                    getNewNumber("0");
//
//                    break;
//
//            case R.id.oneButton:
//
//                    getNewNumber("1");
//
//                    break;
//
//            case R.id.twoButton:
//
//                    getNewNumber("2");
//
//                    break;
//
//            case R.id.threeButton:
//
//                    getNewNumber("3");
//
//                    break;
//
//            case R.id.fourButton:
//
//                    getNewNumber("4");
//
//                    break;
//
//            case R.id.fiveButton:
//
//                    getNewNumber("5");
//
//                    break;
//
//            case R.id.sixButton:
//
//                    getNewNumber("6");
//
//                    break;
//
//            case R.id.sevenButton:
//
//                    getNewNumber("7");
//
//                    break;
//
//            case R.id.eightButton:
//
//                    getNewNumber("8");
//
//                    break;
//
//            case R.id.nineButton:
//
//                    getNewNumber("9");
//
//                    break;
//
//            case R.id.resultButton:
//
//                    addNumbers();
//
//                    break;
//
//            case R.id.plusButton:
//
//                    addNumbers();
//                    mShowResult = false;
//
//                    break;
//
//            case R.id.clearButton:
//
//                    // TODO STEP 05: Clear all values and reset the booleans.
//
//                    break;
//            }
//
//            mResultTextView.setText(mResultText);
//    }

    private void getNewNumber(String number)
    {
            /*
             * TODO STEP 03: Implement a method that adds a new number to an string
             * to get a number with 1 or more digits. NOTE: the number zero only is
             * at the first position if there is only one digit.
             */
    }

    private void addNumbers()
    {
            /*
             * TODO STEP 04: Implement a method to get the result of two numbers. Do
             * the calculations in another class (Calculator).
             */
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
