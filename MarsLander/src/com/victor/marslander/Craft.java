package com.victor.marslander;

import android.graphics.Matrix;
import android.graphics.Path;
import android.util.Log;

public class Craft {
	protected class CraftModel {
		private static final float SIDE_THRUSTER_DURATION = 1.0f;// the left/right thruster last times duration
		private static final float MAIN_THRUSTER_DURATION = 1.0f;// the main thruster last times duration
		private static final float THRUSTER_ACCEL = 7.0f; //The accelerate of thruster
		private static final float FUEL_CONSUME = 2.0f; // Consumed fuel on each fire
		// state
		private float headAngle = 0.0f; // in degree;
		private boolean _isMainThrusterOn = false; // if main thruster fired
		private float _mainThrustTimespan;// control the time of main thruster's
		private boolean _isLeftThrusterOn = false;// if left thruster fired
		private float _leftThrustTimespan;
		private boolean _isRightThrusterOn = false;// if right thruster fired
		private float _rightThrustTimespan;
		private float _fuelRemaining = 100.00f;

		// position
		private float _offsetX = 0; //in meter
		private float _offsetY = 0; //in meter

		// velocity in meters/second
		private float _veloX = 0;
		private float _veloY = 0;
		private float _g = 0; // the accelerate's value

		// acceleration in meters/second^2
		private float _accelX = 0;
		private float _accelY = 0;

		CraftModel(float g) {
			_g = g;
			_accelY = _g;
		}

		/**
		 * Turn right when the craft remains enough fuel.
		 */
		protected void turnRight() {
			if (_isLeftThrusterOn || _fuelRemaining <= 0)
				return;
			_fuelRemaining = _fuelRemaining - FUEL_CONSUME;
			headAngle += 18.0;
			_leftThrustTimespan = 0.0f;
			_isLeftThrusterOn = true;
		}
		
		/**
		 * Turn left when the craft remains enough fuel.
		 */
		protected void turnLeft() {
			if (_isRightThrusterOn || _fuelRemaining <= 0)
				return;
			_fuelRemaining = _fuelRemaining - FUEL_CONSUME;
			headAngle -= 18.0;
			_rightThrustTimespan = 0.0f;
			_isRightThrusterOn = true;
		}
		
		/**
		 * Thrust when the craft remains enough fuel.
		 */
		protected void thrust() {
			if (_isMainThrusterOn || _fuelRemaining <= 0)
				return;
			_mainThrustTimespan = 0.0f;
			_fuelRemaining = _fuelRemaining - FUEL_CONSUME;
			_isMainThrusterOn = true;
		}

		protected float getFuelRemaining() {
			return _fuelRemaining;
		}

		protected float getOffsetX() {
			return _offsetX;
		}

		protected float getOffsetY() {
			return _offsetY;
		}

		protected float getAngle() {
			return headAngle;
		}

		protected boolean IsLeftThrusterOn() {
			return _isLeftThrusterOn;
		}

		protected boolean IsRightThrusterOn() {
			return _isRightThrusterOn;
		}
		
		protected boolean IsMainThrusterOn() {
			return _isMainThrusterOn;
		}

		/**
		 * Update the status and position of the craft.
		 * @param dt 
		 */
		protected void update(float dt) {
			// s = vt + 1/2at^2
			_offsetX = _veloX * dt + _accelX * dt * dt/2;
			_offsetY = _veloY * dt + _accelY * dt * dt/2;
			
			//v' = v + at
			_veloX += _accelX * dt;
			_veloY += _accelY * dt;
			
			
			if (_isLeftThrusterOn) {
				_accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
				_accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + _g;
				_leftThrustTimespan += dt;
				if (_leftThrustTimespan >= SIDE_THRUSTER_DURATION) {
					_isLeftThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}

			if (_isRightThrusterOn) {
				_accelX = 5 * (float)Math.sin(Math.toRadians(headAngle));
				_accelY = 5 * -(float)Math.cos(Math.toRadians(headAngle)) + _g;
				_rightThrustTimespan += dt;
				if (_rightThrustTimespan >= SIDE_THRUSTER_DURATION) {
					_isRightThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}
			
			if (_isMainThrusterOn) {
				_accelX = THRUSTER_ACCEL * (float)Math.sin(Math.toRadians(headAngle));
				_accelY = THRUSTER_ACCEL * -(float)Math.cos(Math.toRadians(headAngle)) + _g;
				_mainThrustTimespan += dt;
				if (_mainThrustTimespan >= MAIN_THRUSTER_DURATION){
					_isMainThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}
		}
	}

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

	private int _posX = 0; 
	private int _posY = 0; 
	private float _pixelMeterRatio;
	private CraftModel _craftModel;

	
	Craft(int posX, int posY, float g, float r) {
		_craftModel = new CraftModel(g);
		_posX = posX;
		_posY = posY;
		_pixelMeterRatio = r;
	}
	
	/**
	 * Get the craft's outline.
	 * @return The path of the craft
	 */
	public Path genOutline(){
		Path outline = new Path();
		outline.moveTo(_posX + WIDTH /2, _posY);
		outline.lineTo(_posX + WIDTH, _posY + HEIGHT /2);
		outline.lineTo(_posX + WIDTH, _posY + HEIGHT);
		outline.lineTo(_posX, _posY + HEIGHT);
		outline.lineTo(_posX, _posY + HEIGHT / 2);
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
		_craftModel.update(dt);
		_posX += _pixelMeterRatio * _craftModel.getOffsetX();
		_posY += _pixelMeterRatio * _craftModel.getOffsetY();
	}
	
	public float getFuelRemaining() {
		return _craftModel.getFuelRemaining();
	}
	
	public float getX() {
		return _posX;
	}
	
	public void setX(int x){
		_posX = x;
	}

	public float getY() {
		return _posY;
	}
	public void setY(int y){
		_posY = y;
	}
	
	public float getCenterX() {
		return _posX + WIDTH / 2;
	}

	public float getCenterY() {
		return _posY + HEIGHT / 2;
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
		return _craftModel.getAngle();
	}

	public boolean IsLeftThrusterOn() {
		return _craftModel.IsLeftThrusterOn();
	}

	public boolean IsRightThrusterOn() {
		return _craftModel.IsRightThrusterOn();
	}
	
	public boolean IsMainThrusterOn(){
		return _craftModel.IsMainThrusterOn();
	}
	
	public void turnRight() {
		_craftModel.turnRight();
	}

	public void turnLeft() {
		_craftModel.turnLeft();
	}
	public void thrust() {
		_craftModel.thrust();
	}
}
