package com.feifei.marslander;

import com.feifei.marslander.interfaces.Sprite;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;

public class Craft implements Sprite {
	

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

	private int posX = 0; 
	private int posY = 0; 
	private float pixelMeterRatio;
	private CraftModel craftModel;

	
	public Craft(int posX, int posY, float g, float r) {
//		craftModel = new CraftModel( g);
		this.posX = posX;
		this.posY = posY;
		pixelMeterRatio = r;
	}
	
	/**
	 * Get the craft's outline.
	 * @return The path of the craft
	 */
	public Path genOutline(){
		Path outline = new Path();
		outline.moveTo(posX + WIDTH /2, posY);
		outline.lineTo(posX + WIDTH, posY + HEIGHT /2);
		outline.lineTo(posX + WIDTH, posY + HEIGHT);
		outline.lineTo(posX, posY + HEIGHT);
		outline.lineTo(posX, posY + HEIGHT / 2);
		outline.close();
		
		Matrix m = new Matrix();
		m.postRotate(getAngle(), getCenterX(), getCenterY());
		outline.transform(m);
		
		return outline;
	}

	/**
	 * Update the x,y position.
	 * @param dt 
	 */
	public void update(float dt) {
		craftModel.update(dt);
		posX += pixelMeterRatio * craftModel.getOffsetX();
		posY += pixelMeterRatio * craftModel.getOffsetY();
	}
	
	public float getFuelRemaining() {
		return craftModel.getFuelRemaining();
	}
	
	public float getX() {
		return posX;
	}
	
	public void setX(int x){
		posX = x;
	}

	public float getY() {
		return posY;
	}
	public void setY(int y){
		posY = y;
	}
	
	public float getCenterX() {
		return posX + WIDTH / 2;
	}

	public float getCenterY() {
		return posY + HEIGHT / 2;
	}

	public float getLeftFlamePosX() {
		return getCenterX() - SIDE_FLAME_OFFSET_X - SIDE_FLAME_WIDTH;
	}

	public float getRightFlamePosX() {
		return getCenterX() + SIDE_FLAME_OFFSET_X;
	}

	public float getSideFlamePosY() {
		return getCenterY() + SIDE_FLAME_OFFSET_Y;
	}
	
	public float getMainFlamePosX() {
		return getCenterX() + MAIN_FLAME_OFFSET_X;
	}

	public float getMainFlamePosY() {
		return getCenterY() + MAIN_FLAME_OFFSET_Y;
	}
	
	public float getAngle() {
		return craftModel.getAngle();
	}

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
	public void thrust() {
		craftModel.thrust();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPos(Point pos) {
		// TODO Auto-generated method stub
		
	}
}
