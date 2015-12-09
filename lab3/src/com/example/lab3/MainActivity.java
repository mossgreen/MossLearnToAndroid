package com.example.lab3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  implements OnClickListener{
	
	private Button mButton;
	private Button nButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton = (Button)findViewById(R.id.button1);
		nButton = (Button)findViewById(R.id.button2);
		
		mButton.setOnClickListener(this); //to catch the button-click event
		nButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button1){
			TextView textView = (TextView) findViewById(R.id.textView1);
			textView.setText("change the text ");
			Toast toast = Toast.makeText(getApplicationContext(), "what's wrong!", Toast.LENGTH_SHORT);
			toast.show();
		}
		else if (v.getId() == R.id.button2) {
			TextView textView = (TextView) findViewById(R.id.textView1);
			textView.setText("change the text to something else ");
			
		}
		
	}

}