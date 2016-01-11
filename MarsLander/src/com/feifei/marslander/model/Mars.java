package com.feifei.marslander.model;

import java.util.Random;
import java.util.Vector;

import android.graphics.Path;
import android.graphics.Point;

/**
 * @author credit to com.victor.marslander, modified by Feifei GU, Jan/11/2016.
 *         Assignment of subject ISCG7424 Mobile Application Development
 * 
 */
public class Mars {

	// constants for building the ground line
	private final int MIN_WIDTH = 30;
	private final int BASE_HEIGHT = 50;
	private final float MIN_GRADIENT = 0.2f;
	public final int GROUND_MAX_HEIGHT = 300;
	private int minSingleFlatWidth;
	private int maxSingleFlatWidth;
	private int remainingFlatWidth;
	private int remainingSlopeWidth;
	private float flatRemainingRatio = 0.3f;
	private int screenHeight;

	// will generate random points of the map
	private final Random rdm = new Random(System.currentTimeMillis());
	private Vector<Point> map;
	private Path ground;

	/**
	 * constructor of Mars
	 * 
	 * @param scrWidth
	 *            screen width
	 * @param scrHeight
	 *            screen height
	 * @param minWidth
	 *            the minimal width of flat ground, set to the width of a craft
	 * @param maxWidth
	 *            the maximal width of flat ground, set to twice the width of a
	 *            craft
	 */
	public Mars(int scrWidth, int scrHeight, int minWidth, int maxWidth) {
		this.minSingleFlatWidth = minWidth;
		this.maxSingleFlatWidth = maxWidth;
		this.remainingFlatWidth = (int) (flatRemainingRatio * scrWidth);
		this.remainingSlopeWidth = scrWidth - remainingFlatWidth;
		this.screenHeight = scrHeight;
	}

	/**
	 * Generates map
	 */
	private void generateMap() {
		// initialize the map container
		map = new Vector<Point>();
		ground = null;

		Point currP, newP;

		// start from the left bottom of the screen
		// than extends to right of the screen
		map.add(new Point(0, screenHeight));
		currP = new Point(0, screenHeight - BASE_HEIGHT - rdm.nextInt(GROUND_MAX_HEIGHT));
		map.add(currP);

		// generate the first slope
		newP = getSlopeEnd(currP);
		if (newP != null) {
			currP = newP;
			map.add(currP);
		}

		// add new slope in the map if there is enough space for another slope
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
	 * getter of ground points.
	 * 
	 * @return Vector The ground points
	 */
	public Vector<Point> getGroundPoints() {

		// return the map container if it's not null
		if (map != null) {
			return map;
		}

		getGround();
		return map;
	}

	/**
	 * Gets the ground path.
	 * 
	 * @return The ground path
	 */
	public Path getGround() {
		if (ground != null) {
			return ground;
		}

		generateMap();

		Point p;
		ground = new Path();
		int len = map.size();
		for (int i = 0; i < len; i++) {
			p = map.elementAt(i);
			ground.lineTo(p.x, p.y);
		}
		ground.close();
		return ground;
	}

	/**
	 * Obtain the flat point
	 * 
	 * @param curr
	 *            The current point
	 * @return Point The flat point
	 */
	private Point getFlatEnd(Point curr) {

		if (remainingFlatWidth <= 0) {
			return null;
		}
		int width = 0;

		/*
		 * if the remaining width less than the width of the max single flat
		 * ground width value draw new point
		 */
		if (remainingFlatWidth <= maxSingleFlatWidth) {
			// last one
			width = remainingFlatWidth;
			remainingFlatWidth = 0;
			return new Point(curr.x + width, curr.y);
		}

		// generate random width of the new ground
		width = rdm.nextInt(maxSingleFlatWidth - minSingleFlatWidth) + minSingleFlatWidth;
		remainingFlatWidth -= width;
		return new Point(curr.x + width, curr.y);
	}

	/**
	 * Get the next slope end point
	 * 
	 * @param curr
	 *            The current point
	 * @return The slope point
	 */
	private Point getSlopeEnd(Point curr) {
		if (remainingSlopeWidth <= 0) {
			return null;
		}

		int width = MIN_WIDTH;
		if (remainingSlopeWidth > MIN_WIDTH) {
			width = rdm.nextInt(remainingSlopeWidth) + 1;
		}

		int y = curr.y;
		float r = (float) Math.abs(y - curr.y) / width;
		while (r < MIN_GRADIENT) {
			y = screenHeight - BASE_HEIGHT - rdm.nextInt(GROUND_MAX_HEIGHT);
			r = (float) Math.abs(y - curr.y) / width;
		}

		remainingSlopeWidth -= width;
		return new Point(curr.x + width, y);
	}

}
