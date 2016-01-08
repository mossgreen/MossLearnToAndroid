package com.feifei.marslander;

import android.content.Context;
import android.text.TextDirectionHeuristic;
import android.content.Context;

class CraftModel {
	
	
	
	
	// the left/right thruster last times duration
	private  final float SIDE_ENGINE_DURATION = 1.0f;
	private static final float MAIN_ENGINE_DURATION = 1.0f;// the main thruster last times duration
	private static final float ENGINE_ACCEL = 7.0f; //The accelerate of thruster
	private static final float FUEL_CONSUME = 2.0f; // Consumed fuel on each fire
	
	
	
	
	
	
	// state
	private float headAngle = 0.0f; // in degree;
	private boolean isMainEngineOn = false; // if main thruster fired
	private float mainEngineTimespan;// control the time of main thruster's
	private boolean isLeftEngineOn = false;// if left thruster fired
	private float leftEngineTimespan;
	private boolean isRightEngineOn = false;// if right thruster fired
	private float rightEngineTimespan;
	private float fuelRemaining = 100.00f;

	// position
	private float offsetX = 0; //in meter
	private float offsetY = 0; //in meter

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
		if (isLeftEngineOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle += 18.0;
		leftEngineTimespan = 0.0f;
		isLeftEngineOn = true;
	}
	
	/**
	 * Turn left when the craft remains enough fuel.
	 */
	protected void turnLeft() {
		if (isRightEngineOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle -= 18.0;
		rightEngineTimespan = 0.0f;
		isRightEngineOn = true;
	}
	
	/**
	 * Engine when the craft remains enough fuel.
	 */
	protected void thrust() {
		if (isMainEngineOn || fuelRemaining <= 0)
			return;
		mainEngineTimespan = 0.0f;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		isMainEngineOn = true;
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
	 * this code and Idea is from John casey
	 * Update the status and position of the craft.
	 * @param dt 
	 */
	protected void update(float dt) {
		// s = vt + 1/2at^2
		offsetX = veloX * dt + accelX * dt * dt/2;
		offsetY = veloY * dt + accelY * dt * dt/2;
		
		//v' = v + at
		veloX += accelX * dt;
		veloY += accelY * dt;
		
		
		if (isLeftEngineOn) {
			accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			leftEngineTimespan += dt;
			if (leftEngineTimespan >= SIDE_ENGINE_DURATION) {
				isLeftEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isRightEngineOn) {
			accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			rightEngineTimespan += dt;
			if (rightEngineTimespan >= SIDE_ENGINE_DURATION) {
				isRightEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}
		
		if (isMainEngineOn) {
			accelX = ENGINE_ACCEL * (float)Math.sin(Math.toRadians(headAngle));
			accelY = ENGINE_ACCEL * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			mainEngineTimespan += dt;
			if (mainEngineTimespan >= MAIN_ENGINE_DURATION){
				isMainEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}
	}
}
