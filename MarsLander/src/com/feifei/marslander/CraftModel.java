package com.feifei.marslander;

import android.content.Context;
import android.text.TextDirectionHeuristic;
import android.content.Context;

class CraftModel {
	
	
	
	
	// the left/right thruster last times duration
	private  final float SIDE_THRUSTER_DURATION = 1.0f;
	private static final float MAIN_THRUSTER_DURATION = 1.0f;// the main thruster last times duration
	private static final float THRUSTER_ACCEL = 7.0f; //The accelerate of thruster
	private static final float FUEL_CONSUME = 2.0f; // Consumed fuel on each fire
	
	
	
	
	
	
	// state
	private float headAngle = 0.0f; // in degree;
	private boolean isMainThrusterOn = false; // if main thruster fired
	private float mainThrustTimespan;// control the time of main thruster's
	private boolean isLeftThrusterOn = false;// if left thruster fired
	private float leftThrustTimespan;
	private boolean isRightThrusterOn = false;// if right thruster fired
	private float rightThrustTimespan;
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
		if (isLeftThrusterOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle += 18.0;
		leftThrustTimespan = 0.0f;
		isLeftThrusterOn = true;
	}
	
	/**
	 * Turn left when the craft remains enough fuel.
	 */
	protected void turnLeft() {
		if (isRightThrusterOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle -= 18.0;
		rightThrustTimespan = 0.0f;
		isRightThrusterOn = true;
	}
	
	/**
	 * Thrust when the craft remains enough fuel.
	 */
	protected void thrust() {
		if (isMainThrusterOn || fuelRemaining <= 0)
			return;
		mainThrustTimespan = 0.0f;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		isMainThrusterOn = true;
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

	protected boolean IsLeftThrusterOn() {
		return isLeftThrusterOn;
	}

	protected boolean IsRightThrusterOn() {
		return isRightThrusterOn;
	}
	
	protected boolean IsMainThrusterOn() {
		return isMainThrusterOn;
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
		
		
		if (isLeftThrusterOn) {
			accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			leftThrustTimespan += dt;
			if (leftThrustTimespan >= SIDE_THRUSTER_DURATION) {
				isLeftThrusterOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isRightThrusterOn) {
			accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
			accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			rightThrustTimespan += dt;
			if (rightThrustTimespan >= SIDE_THRUSTER_DURATION) {
				isRightThrusterOn = false;
				accelX = 0;
				accelY = g;
			}
		}
		
		if (isMainThrusterOn) {
			accelX = THRUSTER_ACCEL * (float)Math.sin(Math.toRadians(headAngle));
			accelY = THRUSTER_ACCEL * -(float)Math.cos(Math.toRadians(headAngle)) + g;
			mainThrustTimespan += dt;
			if (mainThrustTimespan >= MAIN_THRUSTER_DURATION){
				isMainThrusterOn = false;
				accelX = 0;
				accelY = g;
			}
		}
	}
}