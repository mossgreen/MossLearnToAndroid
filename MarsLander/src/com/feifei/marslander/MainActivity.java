package com.feifei.marslander;
import com.feifei.marslander.game.*;
import com.feifei.marslander.model.Craft;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainActivity extends Activity {
	private static final String APP_TAG = "MARSLANDER";
    private  final int MENU_START = 0;
    private GameView gameView;
    private ImageButton bmMainFlame, btnLeftThruster, btnRightThruster;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start); // Add a start menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == MENU_START) {
			gameView.getGameModel().startGame();
			return true;
		}

        return false;
    }
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        
        Craft craft = new Craft(this);
        
        setContentView(R.layout.lander_layout); // set the content view or our widget lookups will fail
        gameView = (com.feifei.marslander.game.GameView)findViewById(R.id.MarsLanderView);
        bmMainFlame = (ImageButton)findViewById(R.id.btnThruster);
        bmMainFlame.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				gameView.getGameModel().getCraft().thrust();
			}
		});
        
        btnLeftThruster = (ImageButton)findViewById(R.id.btnLeftThruster);
        btnLeftThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.getGameModel().getCraft().turnRight();
			}
		});
		
        btnRightThruster = (ImageButton)findViewById(R.id.btnRightThruster);
        btnRightThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.getGameModel().getCraft().turnLeft();
			}
		});
    }
}
