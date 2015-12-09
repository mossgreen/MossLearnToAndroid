package com.jcasey;

public class BBall {
	
	private  int radius;
	private  float x;
	private  float y;
	private int speed;
	
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}


	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setY(float y) {
		this.y = y;
	}



	public BBall() {
		// TODO Auto-generated constructor stub
	}
	
	public BBall(float x, float y, int radius, int speed){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.speed = speed;
	}
	

}
