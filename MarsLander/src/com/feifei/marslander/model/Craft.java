package com.feifei.marslander.model;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.text.TextDirectionHeuristic;
import android.content.Context;

/**
 * @author credit to com.victor.marslander, modified by Feifei GU, Jan/11/2016 Assignment
 *         of subject ISCG7424 Mobile Application Development
 * 
 */
public class Craft {
	
	// constant
	public static final int WIDTH = 50;
	public static final int HEIGHT = 73;
	public static final int SIDE_FLAME_WIDTH = 12;
	public static final int SIDE_FLAME_HEIGHT = 16;
	public static final int MAIN_FLAME_WIDTH = 20;
	public static final int MAIN_FLAME_HEIGHT = 27;

	private static final float SIDE_FLAME_OFFSET_X = WIDTH / 2 - SIDE_FLAME_WIDTH;
	private static final float SIDE_FLAME_OFFSET_Y = 27.0f;
	private static final float MAIN_FLAME_OFFSET_X = -MAIN_FLAME_WIDTH / 2;
	private static final float MAIN_FLAME_OFFSET_Y = 27.0f;

	// the left/right thruster last times duration
	private final float SIDE_ENGINE_DURATION = 1.0f;
	private static final float MAIN_ENGINE_DURATION = 1.0f; 
	
	// The accelerate of thruster
	private static final float MAIN_ENGINE_ACCEL = 7.0f; 
	private static final float SIDE_ENGINE_ACCEL = 5.0f; 

	//fuel consumption of each thruster
	private static final float FUEL_CONSUME = 2.0f; 

	// craft state
	private boolean isMainEngineOn = false;
	private float mainEngineTimespan;
	private boolean isLeftEngineOn = false;
	private float leftEngineTimespan;
	private boolean isRightEngineOn = false;
	private float rightEngineTimespan;
	private float fuelRemaining = 100.00f;

	// position
	private float offsetX = 0; // in meter
	private float offsetY = 0; // in meter

	// velocity in meters/second
	private float veloX = 0;
	private float veloY = 0;


	// acceleration in meters/second^2
	private final float GRAVITY = 2.0f; 
	private float g = GRAVITY; 
	private float accelX = 0;
	private float accelY = GRAVITY;

	// position of craft in coordinate 
	private int posX = 0;
	private int posY = 0;
	private float pixelMeterRatio = 8;
	
	//angel of the craft
	private float headAngle = 0.0f; 
	private final float ANGLE_CHANGED = 18.0f;


	/**
	 * constructor 
	 * @param posX x position of craft in coordinate
	 * @param posY y position of craft in coordinate
	 */
	public Craft(int posX, int posY) {

		this.posX = posX;
		this.posY = posY;
	}


	/**
	 * Get the craft's outline.
	 * wrap the craft by using path
	 * 
	 * @return The path of the craft
	 */
	public Path genOutline() {
		
		/* five points indicate five positions of the craft
		 * from the top point of the craft
		 * to right side from top to bottom
		 * then bottom from right to left
		 * then left side, from bottom to top
		 * at last, go back to the top point of craft
		 */
		
		Path outline = new Path();
		outline.moveTo(posX + WIDTH / 2, posY);
		outline.lineTo(posX + WIDTH, posY + HEIGHT / 2);
		outline.lineTo(posX + WIDTH, posY + HEIGHT);
		outline.lineTo(posX, posY + HEIGHT);
		outline.lineTo(posX, posY + HEIGHT / 2);
		outline.close();

		//matrix for transforming coordinates of craft object
		Matrix m = new Matrix();
		m.postRotate(getAngle(), getCenterX(), getCenterY());
		outline.transform(m);

		return outline;
	}

	/**
	 * Turn right when the craft remains enough fuel.
	 */
	public void turnRight() {
		
		/* if the left engine is already on
		 *  or the craft's fuel gauge is empty
		 *  the craft cannot turn right
		 *  otherwise, the craft will rotate to the right a little bit
		 *  and set the left engine status to true
		 *  this movement will consume certain fuel
		 */
		if (isLeftEngineOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle += ANGLE_CHANGED;
		leftEngineTimespan = 0.0f;
		isLeftEngineOn = true;
	}

	/**
	 * Turn left when the craft remains enough fuel.
	 */
	public void turnLeft() {
		
		/* if the right engine is is already on
		 *  or the craft's fuel gauge is empty
		 *  the craft cannot turn left
		 *  otherwise, the craft will rotate to the right a little bit
		 *  and set the right engine status to true
		 *  this movement will consume certain fuel
		 */
		if (isRightEngineOn || fuelRemaining <= 0)
			return;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		headAngle -= ANGLE_CHANGED;
		rightEngineTimespan = 0.0f;
		isRightEngineOn = true;
	}

	/**
	 * thrust when the craft remains enough fuel.
	 * and consume fuel when engine started
	 */
	public void thrust() {
		
		/* if the main engine is already on
		 *  or the craft's fuel gauge is empty
		 *  the craft cannot thrust up
		 *  otherwise, the craft will thrust a little bit
		 *  and set the main engine status to true
		 *  this movement will consume certain fuel
		 */
		if (isMainEngineOn || fuelRemaining <= 0)
			return;
		mainEngineTimespan = 0.0f;
		fuelRemaining = fuelRemaining - FUEL_CONSUME;
		isMainEngineOn = true;
	}

	/**
	 * getter of remaining fuel
	 * @return remaining fuel
	 */
	public float getFuelRemaining() {
		return fuelRemaining;
	}

	/**
	 * getter of offsetX of craft
	 * @return
	 */
	public float getOffsetX() {
		return offsetX;
	}

	/**
	 * getter of offsetY of craft
	 * @return
	 */
	public float getOffsetY() {
		return offsetY;
	}

	/**
	 * getter of head angle of craft
	 * @return
	 */
	public float getAngle() {
		return headAngle;
	}

	/**
	 * getter the status of left engine
	 * @return
	 */
	public boolean IsLeftEngineOn() {
		return isLeftEngineOn;
	}

	/**
	 * getter the status of right engine
	 * @return
	 */
	public boolean IsRightEngineOn() {
		return isRightEngineOn;
	}

	/**
	 * getter of the status of main engine
	 * @return
	 */
	public boolean IsMainEngineOn() {
		return isMainEngineOn;
	}

	/**
	 * getter of the x position of craft
	 * @return
	 */
	public float getX() {
		return posX;
	}

	/**
	 * setter of x position of craft
	 * @param x
	 */
	public void setX(int x) {
		posX = x;
	}

	/**
	 * getter of y position of craft
	 * @return
	 */
	public float getY() {
		return posY;
	}

	/**
	 * setter of y position of craft
	 * @param y
	 */
	public void setY(int y) {
		posY = y;
	}

	/**
	 * getter of center x position of craft
	 * @return
	 */
	public float getCenterX() {
		return posX + WIDTH / 2;
	}

	/**
	 * getter of center y position of craft
	 * @return
	 */
	public float getCenterY() {
		return posY + HEIGHT / 2;
	}

	/**
	 * getter of left flame x position of the craft
	 * @return
	 */
	public float getLeftFlamePosX() {
		return getCenterX() - SIDE_FLAME_OFFSET_X - SIDE_FLAME_WIDTH;
	}

	/**
	 * getter of the right flame  x position
	 * @return
	 */
	public float getRightFlamePosX() {
		return getCenterX() + SIDE_FLAME_OFFSET_X;
	}

	/**
	 * getter of side engine's flame y position
	 * @return
	 */
	public float getSideFlamePosY() {
		return getCenterY() + SIDE_FLAME_OFFSET_Y;
	}

	/**
	 * getter of the main engine's flame's x position
	 * @return
	 */
	public float getMainFlamePosX() {
		return getCenterX() + MAIN_FLAME_OFFSET_X;
	}

	/**
	 * getter of main engine's main flame's y position
	 * @return
	 */
	public float getMainFlamePosY() {
		return getCenterY() + MAIN_FLAME_OFFSET_Y;
	}

	/**
	 * Simulated physical formula
	 * Update the status and position of the craft.
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		// s = vt + 1/2at^2
		offsetX = veloX * dt + accelX * dt * dt / 2;
		offsetY = veloY * dt + accelY * dt * dt / 2;

		// v' = v + at
		veloX += accelX * dt;
		veloY += accelY * dt;
		
		/*
		 * if any engine is on, 
		 * there will be an accelerate to the x and y position align with the head_angle of the craft
		 * thrust time decided by the state controller of each engine
		 * 
		 * */

		if (isLeftEngineOn) {
			accelX = SIDE_ENGINE_ACCEL * (float) Math.sin(Math.toRadians(headAngle));
			accelY = SIDE_ENGINE_ACCEL * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			leftEngineTimespan += dt;
			if (leftEngineTimespan >= SIDE_ENGINE_DURATION) {
				isLeftEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isRightEngineOn) {
			accelX = SIDE_ENGINE_ACCEL * (float) Math.sin(Math.toRadians(headAngle));
			accelY = SIDE_ENGINE_ACCEL * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			rightEngineTimespan += dt;
			if (rightEngineTimespan >= SIDE_ENGINE_DURATION) {
				isRightEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		if (isMainEngineOn) {
			accelX = MAIN_ENGINE_ACCEL * (float) Math.sin(Math.toRadians(headAngle));
			accelY = MAIN_ENGINE_ACCEL * -(float) Math.cos(Math.toRadians(headAngle)) + g;
			mainEngineTimespan += dt;
			if (mainEngineTimespan >= MAIN_ENGINE_DURATION) {
				isMainEngineOn = false;
				accelX = 0;
				accelY = g;
			}
		}

		posX += pixelMeterRatio * getOffsetX();
		posY += pixelMeterRatio * getOffsetY();
	}
}
