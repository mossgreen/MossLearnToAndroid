package com.victor.marslander;

import com.victor.marslander.R;
import com.victor.marslander.R.id;
import com.victor.marslander.R.layout;
import com.victor.marslander.R.string;
import com.victor.marslander.game.GameView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MarsLanderActivity extends Activity {
	private static final String APP_TAG = "MARSLANDER";
    private  final int MENUSTART = 0;
    private GameView view;
    private ImageButton bmMainFlame, btnLeftThruster, btnRightThruster;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENUSTART, 0, R.string.menu_start); // Add a start menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == MENUSTART) {
			view.getScene().startGame();
			return true;
		}

        return false;
    }
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(APP_TAG, "create activity");
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.lander_layout); // set the content view or our widget lookups will fail
        view = (GameView)findViewById(R.id.MarsLanderView);
        bmMainFlame = (ImageButton)findViewById(R.id.btnThruster);
        bmMainFlame.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				view.getScene().getCraft().thrustUp();
			}
		});
        
        btnLeftThruster = (ImageButton)findViewById(R.id.btnLeftThruster);
        btnLeftThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				view.getScene().getCraft().turnRight();
			}
		});
		
        btnRightThruster = (ImageButton)findViewById(R.id.btnRightThruster);
        btnRightThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				view.getScene().getCraft().turnLeft();
			}
		});
    }
}
