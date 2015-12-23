package com.example.marslander;

import java.util.Random;
import java.util.Vector;

import android.graphics.Path;
import android.graphics.Point;

public class Mars {
	
	private final int MIN_WIDTH = 30;
	private final int BASE_HEIGHT = 50;
	private final float MIN_GRADIENT = 0.2F;
	private int _minSingleFlatWidth;
	private int _maxSingleFlatWidth;
	private int _remainingFlatWidth;
	private int _remainingSlopeWidth;
	private int _maxHeight;
	
	private int _screenHeight;
	private final Random _rdm = new Random(System.currentTimeMillis());
	private Vector<Point> _map;
	private Path _ground;
	
	public Mars(float r, int scrWidth, int scrHeight, int minWidth, int maxWidth, int maxHeight){
		_minSingleFlatWidth = minWidth;
		_maxSingleFlatWidth = maxWidth;
		_maxHeight = maxHeight;
		_remainingFlatWidth = (int)(r*scrWidth);
		_remainingSlopeWidth = scrWidth - _remainingFlatWidth;
		_screenHeight = scrHeight;
	}
	
	//Generates map
	private void generateMap(){
		_map = new Vector<Point>();
		_ground = null;
		
		Point currP, newP;
		_map.add(new Point(0, _screenHeight));
		currP = new Point (0, -_screenHeight -BASE_HEIGHT - _rdm.nextInt(_maxHeight));
		_map.add(currP);
		
		//first slope
		newP = getSlopeEnd(currP);
		if(newP != null){
			currP = newP;
			_map.add(currP);
		}
		
		while(_remainingSlopeWidth > 0 || _remainingFlatWidth >0){
			if(_rdm.nextBoolean()){
				newP = getFlatEnd(currP);
			}else{
				newP = getSlopeEnd(currP);
			}
			
			if(newP != null){
				currP = newP;
				_map.add(currP);
			}
		}
		
		_map.add(new Point(currP.x, _screenHeight));
		_map.add(new Point(0, _screenHeight));
	}
	
//	gets ground points
	
	public Vector<Point> getGroundPoints(){
		if(_map != null){
			return _map;
		}
		
		getGround();
		return _map;
	}
		
	public Path getGround(){
			if(_ground != null){
				return _ground;
			}
			
			generateMap();
			
			Point p;
			_ground = new Path();
			int len = _map.size();
			for(int i = 0; i<len; i++){
				p = _map.elementAt(i);
				_ground.lineTo(p.x, p.y);
			}
			
			_ground.close();
			return _ground;
		}
		
		private Point getFlatEnd(Point curr){
			if(_remainingFlatWidth <= 0){
				return null;
			}
			int width = 0;
			if(_remainingFlatWidth <= _minSingleFlatWidth * 2){
				width = _remainingFlatWidth;
				_remainingFlatWidth = 0;
				return new Point(curr.x + width, curr.y);
			}
			width = _rdm.nextInt(_maxSingleFlatWidth - _minSingleFlatWidth) + _minSingleFlatWidth;
			_remainingFlatWidth -= width;
			return new Point(curr.x+width, curr.y);
		}
		
		private Point  getSlopeEnd(Point curr){
			if(_remainingFlatWidth <= 0){
				return null;
			}
			
			int width = MIN_WIDTH;
			if(_remainingSlopeWidth > MIN_WIDTH){
				width = _rdm.nextInt(_remainingSlopeWidth) + 1;
			}
			
			int y = curr.y;
			float r = (float) Math.abs(y - curr.y) /width;
			while(r <MIN_GRADIENT){
				y = _screenHeight - BASE_HEIGHT - _rdm.nextInt(_maxHeight);
				r = (float)Math.abs(y - curr.y) / width;
			}
			
			_remainingSlopeWidth -= width;
			return new Point(curr.x + width, y);
		}
	}
