package com.victor.marslander;

import java.util.Random;
import java.util.Vector;

import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

public class MarsLanderScene {

	// constants
	private final float GRAVITY = 2.0f;
	private final float PIXEL_METER_RATIO = 8;
	public static final int STATE_READY = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_WON = 2;
	public static final int STATE_CRASHED = 3;
	public static final int STATE_FALL_LEFT = 4;
	public static final int STATE_FALL_RIGHT = 5;
	private int _state = STATE_READY;

	// sence
	private int _screenWidth;// the screen width value
	private int _screenHeight;// the screen height value
	private long _timeStamp; // get the time stamp
	private final Random _rdm = new Random(System.currentTimeMillis());//get a random value via Returns the current time in milliseconds

	// object
	private Craft _craft;
	private Mars _mars;

	public MarsLanderScene(int screenWidth, int screenHeight) {
		_screenWidth = screenWidth;
		_screenHeight = screenHeight;
		init();
	}

	public void setScreenWidth(int screenWidth, int screenHeight) {
		_screenWidth = screenWidth;
		_screenHeight = screenHeight;
		init();
	}

	public void doStart() {
		init();
		_state = STATE_RUNNING;
	}

	/**
	 * Create the craft and obtain the time stamp value via the current 
	 * timestamp of the most precise timer available on the local system, in nanoseconds.
	 */
	private void init() {
		// create craft
		int posX = Craft.WIDTH * 2 + _rdm.nextInt(_screenWidth - Craft.WIDTH * 4);
		_craft = new Craft(posX, 0, GRAVITY, PIXEL_METER_RATIO);

		_mars = new Mars(0.3f, _screenWidth, _screenHeight, (int) (Craft.WIDTH * 1.3f), Craft.WIDTH * 2, 300);
		//
		_timeStamp = System.nanoTime();
	}

	public Craft getCraft() {
		return _craft;
	}

	public Mars getMars() {
		return _mars;
	}

	public int getState() {
		return _state;
	}

	/**
	 * Update all of the physical position points and does crash test.
	 */
	public void update() {
		final long t = System.nanoTime();
		final float dt = (float) (t - _timeStamp) * (1.0f / 1000000000.0f);// 
		_craft.update(dt);

		// side
		if (_craft.getCenterX() < 0) {
			_craft.setX(_screenWidth - Craft.WIDTH / 2);
		} else if (_craft.getCenterX() > _screenWidth) {
			_craft.setX(-Craft.WIDTH / 2);
		}

		// crash test
		Path p = _craft.genOutline();
		p.op(_mars.getGround(), Path.Op.INTERSECT);//Set this path to the result of applying the Op to this path and the specified path. 
		if (!p.isEmpty()) {
			if (!isFlatGround(_craft.getX(), Craft.WIDTH)) {
				_state = STATE_CRASHED;
			} else {
				if (_craft.getAngle() > 0) {
					_state = STATE_FALL_RIGHT;
				} else if (_craft.getAngle() < 0) {
					_state = STATE_FALL_LEFT;
				} else {
					_state = STATE_WON;
				}
			}
		}
		_timeStamp = t;
	}

	/**
	 * Test for whether the craft landed on the flat ground.
	 * @param x The x point value.
	 * @param w The distance from x.
	 * @return boolean True for landed successfully otherwise return false.
	 */
	private boolean isFlatGround(float x, int w) {
		Vector<Point> points = _mars.getGroundPoints();
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
			// enought
			return true;
		}

		// other
		return false;
	}
}
