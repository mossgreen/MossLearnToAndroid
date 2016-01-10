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

/**
 * Main class of this application.
 * This activity presents a menu bar on the top of this application
 * click the item can start the game
 * @author gufeifei <gufeifei@outlook.com>
 * 
 * @version 1.0
 * @since java 1.8
 *
 */
public class MainActivity extends Activity {
	
	private final int MENU_START = 0;
	
	// represents a reference to gamepanel class
	private GamePanel gamePanel;
	
	//initialize the image_buttons on the main picture of this app
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
			gamePanel.getGameLoop().startGame();
			return true;
		}

		return false;
	}

	/**
	 * Called when the activity is first created.
	 * retrieving references from xml file to create layout and other
	 * elements that will be shown on the main interface
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the content view 
		setContentView(R.layout.lander_layout); 
		
		gamePanel = (GamePanel) findViewById(R.id.MarsLanderView);
		bmMainFlame = (ImageButton) findViewById(R.id.btnThruster);
		bmMainFlame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gamePanel.getGameLoop().getCraft().thrust();
			}
		});

		btnLeftThruster = (ImageButton) findViewById(R.id.btnLeftThruster);
		btnLeftThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gamePanel.getGameLoop().getCraft().turnRight();
			}
		});

		btnRightThruster = (ImageButton) findViewById(R.id.btnRightThruster);
		btnRightThruster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gamePanel.getGameLoop().getCraft().turnLeft();
			}
		});
	}
}
