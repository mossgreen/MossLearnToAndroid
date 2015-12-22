package com.example.assignment02;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove the title bar
		
		

        Display display = getWindowManager().getDefaultDisplay();
        
        DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
        
        setContentView(new AnimationView(getApplicationContext()));
   
	}

}
