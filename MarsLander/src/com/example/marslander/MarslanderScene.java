package com.example.marslander;

import java.util.Random;
import java.util.Vector;

import android.graphics.Path;
import android.graphics.Point;
import android.widget.FrameLayout;

public class MarslanderScene {

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
	private int _screenWidth; // the screen width value
	private int _screenHeight;
	private long _timeStamp;
	private final Random _rdm = new Random(System.currentTimeMillis());

	// object
	private Craft _craft;
	private Mars _mars;

	public MarslanderScene(int screenWidth, int screenHeight) {
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

	// create the craft and obtain the time stamp value via the current
	// timestamp of the most precise timer available on the local system, in
	// nanoseconds.

	private void init() {
		// create craft
		int posX = Craft.WIDTH * 2 + _rdm.nextInt(_screenWidth - Craft.WIDTH * 4);
		_craft = new Craft(posX, 0, GRAVITY, PIXEL_METER_RATIO);

		_mars = new Mars(0.3f, _screenWidth, _screenHeight, (int) (Craft.WIDTH * 1.3f), Craft.WIDTH * 2, 300);

		_timeStamp = System.nanoTime();
	}

	public Craft getCraft() {
		return _craft;
	}

	public Mars getmars() {
		return _mars;
	}

	public int getState() {
		return _state;
	}

	// update all the physical position points and does crash test.

	public void update() {
		final long t = System.nanoTime();
		final float dt = (float) (t - _timeStamp) * (1.0f / 1000000000.0f);
		_craft.update(dt);

		// side
		if (_craft.getCenterX() < 0) {
			_craft.setX(_screenWidth - Craft.WIDTH / 2);
		} else if (_craft.getCenterX() > _screenWidth / 2) {
			_craft.setX(-Craft.WIDTH / 2);
		}

		// crash test

		Path p = _craft.genOutLine();
		p.op(_mars.getGround(), Path.Op.INTERSECT); // set this path to the
													// result of applying

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

	// test for whether the craft landed o nthe flat ground.
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
		Point pend = points.elementAt(i);

		if (pStart.y != pend.y) {
			// slope
			return false;
		}

		// consequent flat ground
		for (i = i + 1; i < len; i++) {
			if (points.elementAt(i).y != pend.y) {
				break;
			} else {
				pend = points.elementAt(i);
			}
		}

		if (pend.x - pStart.x > w) {
			return true;
		}

		return false;
	}

}
