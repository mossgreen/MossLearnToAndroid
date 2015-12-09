package ac.unitec;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutTestActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.main); // set the layout to main.xml
        
        Button button1 = (Button) findViewById(R.id.button1); // bind to button1
        button1.setOnClickListener(this);
        
        Button button2 = (Button) findViewById(R.id.button2); // bind to button2
        button2.setOnClickListener(this);
        
        Button addDetails = (Button) findViewById(R.id.btnAddDetail); // bind to btnAddDetail
        
        if(addDetails != null)
        {
        	addDetails.setOnClickListener(new OnClickListener()
        	{
				@Override
				public void onClick(View v)
				{
					Log.e("addDetails","button was clicked");
						
					// bind to our pre-defined linear layout
					LinearLayout dynamicPanel = (LinearLayout) findViewById(R.id.dynamicPanel); // bind to our layout
					
					// construct a TextView object
					TextView childTextView = new TextView(getApplicationContext());
					childTextView.setText("Test"); // set some basic properties
					
					// add the new widget to our pre-defined LinearLayout
					dynamicPanel.addView(childTextView);					
				}
			});
        }
    }

	@Override
	public void onClick(View v) {
		Button btnPressed = (Button)v;
		
		// determine which button was pressed
		
		if(btnPressed.getId() == R.id.button1)
		{
			// create a Toast message from available string resources
			
			final String button1 = getResources().getString(R.string.btn1) + " pressed";
			
			Toast toast = Toast.makeText(getApplicationContext(), button1, Toast.LENGTH_SHORT);
			toast.show();
			
			// use system log to indicate button1 pressed
			Log.e("button1","clicked");
		}
		else if(btnPressed.getId() == R.id.button2)
		{
			// create a Toast message from available string resources
			final String button2 = getResources().getString(R.string.btn2) + " pressed";
			
			Toast toast = Toast.makeText(getApplicationContext(), button2, Toast.LENGTH_SHORT);
			toast.show();
			
			// use system log to indicate button2 pressed			
			Log.e("button2","clicked");
		}		
	}
}