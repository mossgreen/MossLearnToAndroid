package com.example.marslander;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MarsLanderActivity extends Activity {
	
	private static final String APP_TAG = "MARS_LANDER";
	private static final int MENU_START = 0;
	private MarsLanderView _view;
	private ImageButton _bmMainflame, _btnLeftThruster, _btnRightThruster;
	
	@Override
	public boolean onCreateOptionMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,MENU_START, 0, R.string.menu_start);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_START:
			_view.getScence().doStart();
			return true;
		}
		return false;
	}
	

	@Override
	protected void onCreate(Bundle savedinstanceState){
		Log.d(APP_TAG, "create activity");
		super.onCreate(savedinstanceState);
		setContentView(R.layout.lander_layout);//set the content view or our widget lookups will
		_view = (marslanderview)findViewById(R.id.marslanderview);
		_bmMainflame = (ImageButton)findViewById(R.id.tnThruster);
		_bmMainflame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_view.getScene.getCraft().thrust();
			}
		});
		_btnLeftThruster = (ImageButton)findViewById(R.id.btnLeftThruster);
		_btnLeftThruster.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_view.getScene().getcraft().turnRight();
			}
		});
		
		_btnRightThruster = (ImageButton)findViewById(R.id.btnRightThruster);
		_btnRightThruster.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View.getScene().getCraft().trunLeft();
			}
		});
		
	}
}
