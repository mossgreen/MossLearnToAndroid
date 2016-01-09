package com.feifei.marslander.model;

import java.util.Random;
import java.util.Vector;

import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

public class Mars {
	private final int MINWIDTH = 30;
	private final int BASEHEIGHT = 50;
	private final float MINGRADIENT = 0.2f;
	private int minSingleFlatWidth;
	private int maxSingleFlatWidth;
	private int remainingFlatWidth;
	private int remainingSlopeWidth;
	private int maxHeight;

	private int screenHeight;

	private final Random rdm = new Random(System.currentTimeMillis());
	private Vector<Point> map;
	private Path ground;

	public Mars(float r, int scrWidth, int scrHeight, int minWidth, int maxWidth, int maxHeight) {
		this.minSingleFlatWidth = minWidth;
		this.maxSingleFlatWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.remainingFlatWidth = (int) (r * scrWidth);
		this.remainingSlopeWidth = scrWidth - remainingFlatWidth;
		this.screenHeight = scrHeight;
	}

	/**
	 * Generates map
	 */
	private void generateMap() {
		//
		map = new Vector<Point>();
		ground = null;
		
		Point currP, newP;
		
		map.add(new Point(0, screenHeight));
		currP = new Point(0, screenHeight - BASEHEIGHT - rdm.nextInt(maxHeight));
		map.add(currP);

		//first slope
		newP = getSlopeEnd(currP);
		if (newP != null) {
			currP = newP;
			map.add(currP);
		}
		
		while (remainingSlopeWidth > 0 || remainingFlatWidth > 0) {
			if (rdm.nextBoolean()) {
				// flat
				newP = getFlatEnd(currP);
			} else {
				newP = getSlopeEnd(currP);
			}
			if (newP != null) {
				currP = newP;
				map.add(currP);
			}
		}
		
		map.add(new Point(currP.x, screenHeight));
		map.add(new Point(0, screenHeight));
	}
	
	/**
	 * Gets ground points.
	 * @return Vector The ground points
	 */
	public Vector<Point> getGroundPoints(){
		if(map != null){
			return map;
		}
		
		getGround();
		return map;
	}
	
	/**
	 * Gets the ground path.
	 * @return The ground path
	 */
	public Path getGround(){
		if(ground != null){
			return ground;
		}
		
		generateMap();
		
		Point p;
		ground = new Path();
		int len = map.size();
		for(int i=0; i<len; i++){
			p = map.elementAt(i);
			ground.lineTo(p.x, p.y);
		}
		ground.close();
		return ground;
	}

	/**
	 * Obtain the flat point
	 * @param curr The current point
	 * @return Point The flat point
	 */
	private Point getFlatEnd(Point curr) {

		if (remainingFlatWidth <= 0) {
			return null;
		}
		int width = 0;
		if (remainingFlatWidth <= minSingleFlatWidth * 2) {
			// last one
			width = remainingFlatWidth;
			remainingFlatWidth = 0;
			return new Point(curr.x + width, curr.y);
		}

		width = rdm.nextInt(maxSingleFlatWidth - minSingleFlatWidth) + minSingleFlatWidth;
		remainingFlatWidth -= width;
		return new Point(curr.x + width, curr.y);
	}

	/**
	 * Get the next slope end point
	 * @param curr The current point
	 * @return The slope point
	 */
	private Point getSlopeEnd(Point curr) {
		if (remainingSlopeWidth <= 0) {
			return null;
		}

		int width = MINWIDTH;
		if (remainingSlopeWidth > MINWIDTH) {
			width = rdm.nextInt(remainingSlopeWidth) + 1;
		}
		
		int y = curr.y;
		float r = (float)Math.abs(y - curr.y) / width;
		while(r < MINGRADIENT){
			y = screenHeight - BASEHEIGHT - rdm.nextInt(maxHeight);
			r =  (float)Math.abs(y - curr.y) / width;
		}
		
		remainingSlopeWidth -= width;
		return new Point(curr.x + width, y);
	}

}
