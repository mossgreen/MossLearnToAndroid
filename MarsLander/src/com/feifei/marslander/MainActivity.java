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
 * @author com.victor.marslander, modified by Feifei GU Jan/11/2016 Assignment
 *         of subject ISCG7424 Mobile Application Development
 * 
 */
public class MainActivity extends Activity {
	private final int MENU_START = 0;
	private GamePanel gamePanel;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Craft craft = new Craft(this);

		setContentView(R.layout.lander_layout); // set the content view or our
												// widget lookups will fail
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
