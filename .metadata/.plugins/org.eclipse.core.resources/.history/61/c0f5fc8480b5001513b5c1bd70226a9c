package com.victor.marslander.craft;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;

public class Craft {
	

	// set constants of Craft
	public static final int CRAFT_WIDTH = 50;
	public static final int CRAFT_HEIGHT = 73;
	public static final int SIDE_FLAME_WIDTH = 12;
	public static final int SIDE_FLAME_HEIGHT = 20;
	public static final int MAIN_FLAME_WIDTH = 20;
	public static final int MAIN_FLAME_HEIGHT = 50;

	//set constants of the flame of Craft
	private static final float SIDE_FLAME_OFFSET_X = CRAFT_WIDTH / 2 - SIDE_FLAME_WIDTH;
	private static final float SIDE_FLAME_OFFSET_Y = 27.0f;
	private static final float MAIN_FLAME_OFFSET_X = -MAIN_FLAME_WIDTH / 2;
	private static final float MAIN_FLAME_OFFSET_Y = 27.0f;

	// initialize the position of Craft
	private int posX = 0; 
	private int posY = 0; 
	private float pixelMeterRatio;
	
	//initialize a reference to CraftModel
	private CraftModel craftModel;

	/**
	 * constructor of Craft
	 * @param posX
	 * 	x_coordinate of Craft
	 * @param posY
	 * 	y_coordinate of Craft
	 * @param g
	 * 	the craft is affected by the gravity 
	 * @param r
	 */
	public Craft(int posX, int posY, float g, float r) {
		craftModel = new CraftModel( g);
		this.posX = posX;
		this.posY = posY;
		pixelMeterRatio = r;
	}
	
	/**
	 * Generate the craft's outline.
	 * @return The path of the craft
	 */
	public Path genOutline(){
		Path outline = new Path();
		outline.moveTo(posX + CRAFT_WIDTH /2, posY); // is the head point of craft
		outline.lineTo(posX + CRAFT_WIDTH, posY + CRAFT_HEIGHT /2);
		outline.lineTo(posX + CRAFT_WIDTH, posY + CRAFT_HEIGHT);
		outline.lineTo(posX, posY + CRAFT_HEIGHT);
		outline.lineTo(posX, posY + CRAFT_HEIGHT / 2);
		outline.close();
		
		
		//used to rotate the craft
		Matrix m = new Matrix();
		
		//Post_concats the matrix with the specified rotation.
		m.postRotate(getAngle(), getCenterX(), getCenterY());
		outline.transform(m);
		
		return outline;
	}

	/**
	 * Update the x,y position in coordinate
	 * @param dt 
	 * 	 hard to explain... 
	 */
	public void update(float dt) {
		craftModel.update(dt);
		posX += pixelMeterRatio * craftModel.getOffsetX();
		posY += pixelMeterRatio * craftModel.getOffsetY();
	}
	
	/**
	 * getter
	 * @return
	 * 	the fuel of the craft
	 */
	public float getFuelRemaining() {
		return craftModel.getFuelRemaining();
	}
	
	/**
	 * 
	 * @return
	 * 	the x position of craft in coordinate
	 */
	public float getX() {
		return posX;
	}
	
	/**
	 * set the x position of craft in coordinate
	 * @param x
	 * 	the x position of craft in coordinate
	 */
	public void setX(int x){
		posX = x;
	}

	/**
	 * getter of Y position of craft
	 * @return
	 * 	the Y position of craft in coordinate
	 */
	public float getY() {
		return posY;
	}
	
	/**
	 * setter of Y position of craft
	 * @param y
	 *  the Y position of craft in coordinate
	 */
	public void setY(int y){
		posY = y;
	}
	
	/**
	 * getter of X position of center point of craft in coordinate
	 * @return
	 *  the X position of the center of craft in coordinate
	 */
	public float getCenterX() {
		return posX + CRAFT_WIDTH / 2;
	}

	/**
	 * getter of Y position of center point  of craft in coordinate
	 * @return
	 *  the Y position of the center of craft in coordinate
	 */
	public float getCenterY() {
		return posY + CRAFT_HEIGHT / 2;
	}

	/**
	 * getter of the X position of the left Flame of craft in coordinate
	 * @return
	 * 	the x position of the left flame of the craft
	 */
	public float getLeftFlamePosX() {
		return getCenterX() - SIDE_FLAME_OFFSET_X - SIDE_FLAME_WIDTH;
	}

	/**
	 * getter of the X position of the right flame of craft
	 * @return
	 * 	the y position of the right flame of craft
	 */
	public float getRightFlamePosX() {
		return getCenterX() + SIDE_FLAME_OFFSET_X;
	}

	/**
	 * getter of the Y position of the flames of the craft
	 * @return
	 * the y position of the right flame of the craft
	 */
	public float getSideFlamePosY() {
		return getCenterY() + SIDE_FLAME_OFFSET_Y;
	}
	
	/**
	 * getter of the X position of the main flame of the craft
	 * @return
	 * 	the X position of the main flame
	 */
	public float getMainFlamePosX() {
		return getCenterX() + MAIN_FLAME_OFFSET_X;
	}

	/**
	 * getter of the Y position of the main flame of the craft
	 * @return
	 * 	the Y position of the main flame
	 */
	public float getMainFlamePosY() {
		return getCenterY() + MAIN_FLAME_OFFSET_Y;
	}
	
	/**
	 * getter of the angle that rotated of the craft
	 * @return
	 * 	the degree that craft rotates
	 */
	public float getAngle() {
		return craftModel.getAngle();
	}

	/**
	 * assess 
	 * @return
	 */
	public boolean IsLeftEngineOn() {
		return craftModel.IsLeftEngineOn();
	}

	public boolean IsRightEngineOn() {
		return craftModel.IsRightEngineOn();
	}
	
	public boolean IsMainEngineOn(){
		return craftModel.IsMainEngineOn();
	}
	
	public void turnRight() {
		craftModel.turnRight();
	}

	public void turnLeft() {
		craftModel.turnLeft();
	}
	public void thrustUp() {
		craftModel.thrustUp();
	}
}
