package com.jcasey;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class SimpleGravity extends Activity {
    private GameLoop gameLoop;

	/**
	 * @author John Casey 11/04/2014
	 * Sample code for subject ISCG7424 Mobile Application Development
	 * 
	 * Code illustrates collision detection and models gravity using synthetic time.
	 */
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main); // set the content view or our widget lookups will fail
        
        gameLoop = (GameLoop)findViewById(R.id.gameLoop);
        
        final Button btnRestart = (Button)findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new OnClickListener()
        {	
			@Override
			public void onClick(View v)
			{
				gameLoop.reset();
				gameLoop.invalidate();
			}
		});        
    }
}