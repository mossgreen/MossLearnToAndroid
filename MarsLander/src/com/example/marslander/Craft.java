package com.example.marslander;

import android.graphics.Matrix;
import android.graphics.Path;

public class Craft {
	protected class CraftModel{
		private static final float SIDE_THRUSTER_DURATION = 1.0f;
		private static final float MAIN_THRUSTER_DURATION = 1.0f;
		private static final float THRUSTER_ACCEL = 7.0f;
		private static final float FUEL_CONSUME = 2.0f;
		
		//state
		private float _headAngle = 0.0f; //indegee
		private boolean _isMainThrusterOn = false;
		private float _mainThrustTimespan;
		private boolean _isleftThrusterOn = false;
		private float _leftThrustTimespan;
		private boolean _isRightThrusterOn = false;
		private float _rightThrustTimespan;
		private float _fuelRemaining = 100.00f;
		
		//position
		private float _offsetX = 0;
		private float _offsetY = 0;
		
		//velocity in meters/second
		private float _veloX = 0;
		private float _veloY = 0;
		private float _g= 0;

		//acceleration in meters/ second^2
		private float _accelX = 0;
		private float _accelY = 0;
		
		public CraftModel(float g) {
			// TODO Auto-generated constructor stub
			_g = g;
			_accelY = _g;
		}
		
		//turn right when the craft remains enough fuel.
		protected void turnRight(){
			if(_isleftThrusterOn || _fuelRemaining <= 0){
				return;
			}
			_fuelRemaining = _fuelRemaining-FUEL_CONSUME;
			_headAngle += 18.0;
			_leftThrustTimespan = 0.0f;
			_isleftThrusterOn = true;
		}
		
		//turn left when the craft remians enought fuel
		protected void turnLeft(){
			if(_isRightThrusterOn || _fuelRemaining <= 0){
				return;
			}
			_fuelRemaining = _fuelRemaining - FUEL_CONSUME;
			_headAngle -= 18.0;
			_rightThrustTimespan = 0.0f;
			_isRightThrusterOn = true;
		}
		
		//thrust when the craft remians enought fuel
		protected void thrust(){
			if(_isMainThrusterOn || _fuelRemaining <= 0){
				return;
			}
			_mainThrustTimespan = 0.0f;
			_fuelRemaining = _fuelRemaining -FUEL_CONSUME;
			_isMainThrusterOn = true;
		}
		
		protected float getFuelRemaining(){
			return _fuelRemaining;
		}
		
		protected float getOffSetX(){
			return _offsetX;
		}
		
		protected float getOffSetY(){
			return _offsetY;
		}
		
		protected float getAngle(){
			return _headAngle;
		}
		
		protected boolean IsLeftThrusterOn(){
			return _isleftThrusterOn;
		}
		
		protected boolean IsRightThrusterOn(){
			return _isRightThrusterOn;
		}
		
		protected boolean IsMainthrusterOn(){
			return _isMainThrusterOn;
		}
		
		//update the status and position of the craft
		protected void update(float dt){
			//s = vt + 1/2at^2
			_offsetX  = _veloX * dt + _accelX * dt * dt/2;
			_offsetY = _veloY *dt + _accelY *dt * dt/2;
			
			
			//v' = v=at
			_veloX += _accelX * dt;
			_veloY += _accelY * dt;
			
			if(_isleftThrusterOn){
				_accelX = 5 *(float)Math.sin(Math.toRadians(_headAngle));
				_accelY = 5* -(float)Math.cos(Math.toRadians(_headAngle)) + _g;
				
				_leftThrustTimespan += dt;
				if(_leftThrustTimespan >= SIDE_THRUSTER_DURATION){
					_isleftThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}
			
			if(_isRightThrusterOn){
				_accelX = 5 *(float)Math.sin(Math.toRadians(_headAngle));
				_accelY = 5 * -(float)Math.cos(Math.toRadians(_headAngle))+_g;
				_rightThrustTimespan += dt;
				if(_rightThrustTimespan >= SIDE_THRUSTER_DURATION){
					_isRightThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}
			
			if(_isMainThrusterOn){
				_accelX = THRUSTER_ACCEL * (float)Math.sin(Math.toRadians(_headAngle));
				_accelX = THRUSTER_ACCEL * -(float)Math.cos(Math.toRadians(_headAngle))+ _g;
				_mainThrustTimespan += dt;
				if(_mainThrustTimespan >= MAIN_THRUSTER_DURATION){
					_isMainThrusterOn = false;
					_accelX = 0;
					_accelY = _g;
				}
			}
			
		}
	
	}
	
	//constant
	public static final int WIDTH = 50;
	public static final int HEIGHT = 73;
	public static final int SIDE_FLAME_WIDTH = 12;
	public static final int SIDE_FLAME_HEIGHT = 16;
	public static final int MAIN_FLAME_WIDTH = 20;
	public static final int MAIN_FLAME_HEIGHT = 27;
	
	private static final float SIDE_FLAME_OFFSET_X = WIDTH / 2 - SIDE_FLAME_WIDTH;
	private static final float SIDE_FLAME_OFFSET_Y = 27.0F;
	private static final float MAIN_FLAME_OFFSET_X = -MAIN_FLAME_WIDTH /2;
	private static final float MAIN_FLAME_OFFSET_Y = 27.0F;
	
	private int _posX = 0;
	private int _posY = 0;
	private float _pixelMeterratio;
	private CraftModel _craftModel;
	
	Craft(int posX, int posY, float g, float r){
		_craftModel = new CraftModel(g);
		_posX = posX;
		_posY = posY;
		_pixelMeterratio = r;
	}
	
	//get the craft's outline
	public Path genOutLine(){
		Path outline = new Path();
		outline.moveTo(_posX+WIDTH/2, _posY);
		outline.lineTo(_posX+WIDTH, _posY+HEIGHT/2);
		outline.lineTo(_posX+WIDTH, _posY + HEIGHT);
		outline.lineTo(_posX, _posY+HEIGHT);
		outline.lineTo(_posX, _posY+HEIGHT/2);
		outline.close();
		
		Matrix m = new Matrix();
		m.postRotate(getAngle(), getCenterX(),getCenterY());
		outline.transform(m);
		
		return outline;
		
	}
	
	//update the x ,y position
	public void update (float dt){
		_craftModel.update(dt);
		_posX += _pixelMeterratio * _craftModel.getOffSetX();
		_posY += _pixelMeterratio * _craftModel.getOffSetY();
	}
	
	public float getFuelRemaining(){
		return _craftModel.getFuelRemaining();
	}
	
	public float getX(){
		return _posX;
	}
	
	public void setX(int x){
		_posX = x;
	}
	
	public float getY(){
		return _posY;
	}
	
	public void setY(int y){
		_posY = y;
	}
	
	public float getCenterX(){
		return _posX + WIDTH/2;
	}
	
	public float getCenterY(){
		return _posY + HEIGHT/2;
	}
	
	public float  getLeftFlamePosX(){
		return getCenterX()- SIDE_FLAME_OFFSET_X - SIDE_FLAME_WIDTH;
	}
	
	public float getRightFlamePosX(){
		return getCenterX() + SIDE_FLAME_OFFSET_X;
	}
	
	public float getSideFlamePosY(){
		return getCenterY() + MAIN_FLAME_OFFSET_Y;
	}
	
	public float getMainFlamePosX(){
		return getCenterX() + MAIN_FLAME_OFFSET_X;
	}
	
	public float getMainFlamePosY(){
		return getCenterX() + MAIN_FLAME_OFFSET_Y;
	}
	
	public float getAngle(){
		return _craftModel.getAngle();
	}
	
	public boolean IsLeftThrusterOn(){
		return _craftModel.IsLeftThrusterOn();
	}
	
	public boolean IsRightthrusterOn(){
		return _craftModel.IsRightThrusterOn();
	}
	
	public boolean IsMainthrusterOn(){
		return _craftModel.IsMainthrusterOn();
	}
	
	public void turnRight(){
		_craftModel.turnRight();
	}
	
	public void turnLeft(){
		_craftModel.turnLeft();
	}
	
	public void thrust(){
		_craftModel.thrust();
	}
	
	
	
	
	
	
	
	
	
	

}
