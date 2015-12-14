package edu.unitec.calcs;

import android.util.Log;

/**
 * Class to calculate the result of the adding.
 * 
 * @author Richard *
 */
public class Calculator
{
	/**
	 * Static method to calculate the result of the adding
	 * 
	 * @param firstNum
	 *            First number to add
	 * @param secondNum
	 *            Second number to add
	 * @return The result of the adding
	 */
	public static int addNumbers(String firstNum, String secondNum)
	{
		// set values to 0
		int x = 0;
		int y = 0;

		// try to get correct integer values from the strings
		try
		{
			x = Integer.parseInt(firstNum);
			y = Integer.parseInt(secondNum);
		}
		catch (NumberFormatException e)
		{
			Log.e("Calculator", e.getMessage());
		}

		// return result of adding
		return x + y;
	}
}
