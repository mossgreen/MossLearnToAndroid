package com.victor.marslander.mars;

import java.util.Random;
import java.util.Vector;

import com.victor.marslander.craft.CraftView;

import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

public class MarsLanderScene {

	// constants
	private final float GRAVITY = 2.0f;
	private final float PIXELMETERRATIO = 20;
	public static final int STATE_READY = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_WON = 2;
	public static final int STATE_CRASHED = 3;
	public static final int STATE_FALL_LEFT = 4;
	public static final int STATE_FALL_RIGHT = 5;
	private int state = STATE_READY;

	// sence
	private int screenWidth;// the screen width value
	private int screenHeight;// the screen height value
	private long timeStamp; // get the time stamp
	private final Random rdm = new Random(System.currentTimeMillis());//get a random value via Returns the current time in milliseconds

	// object
	private CraftView craft;
	private MarsView mars;

	public MarsLanderScene(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		init();
	}

	public void setScreenWidth(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		init();
	}

	public void startGame() {
		init();
		state = STATE_RUNNING;
	}

	/**
	 * Create the craft and obtain the time stamp value via the current 
	 * timestamp of the most precise timer available on the local system, in nanoseconds.
	 */
	private void init() {
		// create craft
		int posX = CraftView.CRAFT_WIDTH * 2 + rdm.nextInt(screenWidth - CraftView.CRAFT_WIDTH * 4);
		craft = new CraftView(posX, 0, GRAVITY, PIXELMETERRATIO);

		mars = new MarsView(0.3f, screenWidth, screenHeight, (int) (CraftView.CRAFT_WIDTH * 1.3f), CraftView.CRAFT_WIDTH * 2, 300);
		//
		timeStamp = System.nanoTime();
	}

	public CraftView getCraft() {
		return craft;
	}

	public MarsView getMars() {
		return mars;
	}

	public int getState() {
		return state;
	}

	/**
	 * Update all of the physical position points and does crash test.
	 */
	public void update() {
		final long t = System.nanoTime();
		final float dt = (float) (t - timeStamp) * (1.0f / 1000000000.0f);// 
		craft.update(dt);

		// side
		if (craft.getCenterX() < 0) {
			craft.setX(screenWidth - CraftView.CRAFT_WIDTH / 2);
		} else if (craft.getCenterX() > screenWidth) {
			craft.setX(-CraftView.CRAFT_WIDTH / 2);
		}

		// crash test
		Path p = craft.genOutline();
		p.op(mars.getGround(), Path.Op.INTERSECT);//Set this path to the result of applying the Op to this path and the specified path. 
		if (!p.isEmpty()) {
			if (!isFlatGround(craft.getX(), CraftView.CRAFT_WIDTH)) {
				state = STATE_CRASHED;
			} else {
				if (craft.getAngle() > 0) {
					state = STATE_FALL_RIGHT;
				} else if (craft.getAngle() < 0) {
					state = STATE_FALL_LEFT;
				} else {
					state = STATE_WON;
				}
			}
		}
		timeStamp = t;
	}

	/**
	 * Test for whether the craft landed on the flat ground.
	 * @param x The x point value.
	 * @param w The distance from x.
	 * @return boolean True for landed successfully otherwise return false.
	 */
	private boolean isFlatGround(float x, int w) {
		Vector<Point> points = mars.getGroundPoints();
		int len = points.size();
		int i = 1;
		for (; i < len; i++) {
			if (points.elementAt(i).x >= x) {
				// find the start point
				break;
			}
		}

		Point pStart = points.elementAt(i - 1);
		Point pEnd = points.elementAt(i);

		if (pStart.y != pEnd.y) {
			// slope
			return false;
		}

		// consequent flat ground
		for (i = i + 1; i < len; i++) {
			if (points.elementAt(i).y != pEnd.y) {
				break;
			} else {
				pEnd = points.elementAt(i);
			}
		}

		if (pEnd.x - pStart.x > w) {
			// enough
			return true;
		}

		// other
		return false;
	}
}
