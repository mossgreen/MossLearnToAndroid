package com.jcasey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_activity);
        
        
        
        Button btnExplicit = (Button)findViewById(R.id.btnExplicit);
        btnExplicit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v)
			{
				Intent message = new Intent();
				message.setClass(MainActivity.this, SimpleActivity.class);

                startActivityForResult(message, 0);
			}
		});
        
        Button btnImplicit = (Button)findViewById(R.id.btnImplicit);
        
        btnImplicit.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                
                Intent message = new Intent();
                
                message.setAction("com.jcasey.OPEN_SIMPLE_ACTIVITY");
                startActivity(message);
                
                
            }});
        
        Button btnDataBundle = (Button)findViewById(R.id.btnDataBundle);
        btnDataBundle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent message = new Intent();
                
                message.setAction("com.jcasey.OPEN_SIMPLE_ACTIVITY");
				
                // add some key-value pairs to our message
                message.putExtra("clientId", "jcasey");
                message.putExtra("firstName","John");
                message.putExtra("lastName","Casey");
                
                startActivityForResult(message, Activity.RESULT_OK);
			}
		});
        
        
        Button btnActionView = (Button)findViewById(R.id.btnActionView);
        btnActionView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent message = new Intent();
				message.setAction(Intent.ACTION_VIEW);
				message.setDataAndType(Uri.parse("http://www.google.co.nz/"), "text/html");

				startActivity(message);
			}
		});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent message) {
        
        // also check the requestCode ...
        
        if(resultCode == Activity.RESULT_OK)
        {
            Bundle extras = message.getExtras();
            String result = extras.getString("RESULT");
            String data = extras.getString("DATA");

            TextView txtResult = (TextView)findViewById(R.id.txtResult);
            txtResult.setText(result +"\n" + data);
        }
    }
}