package com.victor.marslander;

import android.content.Context;
import android.text.TextDirectionHeuristic;
import android.content.Context;

class CraftModel {

	// the left/right Engine last times duration
	private final float SIDE_Engine_DURATION = 1.0f;
	private final float MAIN_Engine_DURATION = 1.0f;// the main Engine last
														// times duration
	private final float Engine_ACCEL = 7.0f; // The accelerate of Engine
	private final float FUEL_CONSUME = 5.0f; // Consumed fuel on each fire
	
	private float ANGEL_CHANGED = 18.0f;

	// initialize the state
	private float headAngle = 0f; // in degree;
	private boolean isMainEngineOn = false; // if main Engine fired
	private boolean isLeftEngineOn = false;// if left Engine fired
	private boolean isRightEngineOn = false;// if right Engine fired
	private float fuelRemaining = 100.00f;

	// initialize the timespan
	private float mainThrustTimespan;// control the time of main Engine's
	private float leftThrustTimespan;
	private float rightThrustTimespan;

	// position
	private float offsetX = 0; // in meter
	private float offsetY = 0; // in meter

	// velocity in meters/second
	private float veloX = 0;
	private float veloY = 0;
	private float g = 0; // the accelerate's value

	// acceleration in meters/second^2
	private float accelX = 0;
	private float accelY = 0;

	CraftModel(float g) {
		this.g = g;
		this.accelY = g;
	}

	/**
	 * Turn right when the craft remains enough fuel.
	 */
	protected void turnRight() {
		if (isLeftEngineOn || fuelRemaining <= 0) {
			return;
		} else {
			fuelRemaining = fuelRemaining - FUEL_CONSUME;
			headAngle += ANGEL_CHANGED;
			leftThrustTimespan = 0.0f;
			isLeftEngineOn = true;
		}

	}

	/**
	 * Turn left when the craft remains enough fuel.
	 */
	protected void turnLeft() {
		if (isRightEngineOn || fuelRemaining <= 0) {
			return;
		} else {
			fuelRemaining = fuelRemaining - FUEL_CONSUME;
			headAngle -= ANGEL_CHANGED;
			rightThrustTimespan = 0.0f;
			isRightEngineOn = true;
		}
	}

	/**
	 * Thrust when the craft remains enough fuel.
	 */
	protected void thrustUp() {
		if (isMainEngineOn || fuelRemaining <= 0) {
			return;
		} else {
			mainThrustTimespan = 0.0f;
			fuelRemaining = fuelRemaining - FUEL_CONSUME;
			isMainEngineOn = true;
		}
	}

	protected float getFuelRemaining() {
		return fuelRemaining;
	}

	protected float getOffsetX() {
		return offsetX;
	}

	protected float getOffsetY() {
		return offsetY;
	}

	protected float getAngle() {
		return headAngle;
	}

	protected boolean IsLeftEngineOn() {
		return isLeftEngineOn;
	}

	protected boolean IsRightEngineOn() {
		return isRightEngineOn;
	}

	protected boolean IsMainEngineOn() {
		return isMainEngineOn;
	}

	/**
	 * this code and Idea is from John Casey Update the status and position of
	 * the craft.
	 * 
	 * @param dt
	 */
	protected void update(float dt) {
		// s = vt + 1/2at^2
		offsetX = veloX * dt + accelX * dt * dt / 2;
		offsetY = veloY * dt + accelY * dt * dt / 2;

		// v' = v + at
		veloX += accelX * dt;
		veloY += accelY * dt;

		if (isLeftEngineOn) {
			accelX = 5 * (float) Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			leftThrustTimespan += dt;
			if (leftThrustTimespan >= SIDE_Engine_DURATION) {
				isLeftEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isRightEngineOn) {
			accelX = 5 * (float) Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			rightThrustTimespan += dt;
			if (rightThrustTimespan >= SIDE_Engine_DURATION) {
				isRightEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isMainEngineOn) {
			accelX = Engine_ACCEL * (float) Math.sin(Math.toRadians(headAngle));
			accelY = Engine_ACCEL * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			mainThrustTimespan += dt;
			if (mainThrustTimespan >= MAIN_Engine_DURATION) {
				isMainEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}
	}
}
