package com.example.lab2;


import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		setContentView(new DrawView(this.getApplicationContext()));
		
		setContentView(new ViewBlat(this.getApplicationContext()));
//		setContentView(new DrawView(this.getApplicationContext()));
		
//		setContentView(R.layout.activity_main);

	}
}