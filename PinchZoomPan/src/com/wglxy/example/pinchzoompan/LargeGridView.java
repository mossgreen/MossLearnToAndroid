package com.wglxy.example.pinchzoompan;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * This view displays a grid of rectangles that can be zoomed in and out.
 * The grid is N times the size of the view.
 * The zoom point is the center of the view.
 * <p> This class is a subclass of PanZoomView, which provides most of the code to
 * support zooming and panning.
 */

public class LargeGridView extends PanZoomView {
 
    static public final int NumSquaresAlongSide = 9;
    static public final int CanvasSizeMultiplier = 3;
    static public final int NumSquaresAlongCanvas = CanvasSizeMultiplier * NumSquaresAlongSide;
    static public final int NumTypes = 24;
 
    // Variables that control placement and translation of the canvas.
    // Initial values are for debugging on 480 x 320 screen. They are reset in onDraw.
    private float mMaxCanvasWidth = 960;
    private float mMaxCanvasHeight = 960;
    private float mHalfMaxCanvasWidth = 480;
    private float mHalfMaxCanvasHeight = 480;
    private float mOriginOffsetX = 320;
    private float mOriginOffsetY = 320;
    private float mSquareWidth = 32;         // use float for more accurate placement
    private float mSquareHeight = 32;
    private float mHalfSquareWidth = 16;
    private float mHalfSquareHeight = 16;
 
    private Random mRandomObject = new Random (System.currentTimeMillis ());
    private final int [] mImageIds = {R.drawable.square_01, R.drawable.square_02,
                                      R.drawable.square_03, R.drawable.square_04,
                                      R.drawable.square_05, R.drawable.square_06,
                                      R.drawable.square_07, R.drawable.square_08 };
    private Bitmap [] mBitmaps = null;
    private int [] [] mGrid = null;
 
/**
 * Constructors for the view.
 */

public LargeGridView (Context context) {
    super (context);
}
 
public LargeGridView (Context context, AttributeSet attrs) {
    super (context, attrs);
}
 
public LargeGridView (Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}
 
/**
 * Draw image squares along the diagonal of the view.
 * Draw N squares in the visible portion of the view and M outside the view.
 * The canvas object is already set up to be drawn on. That means that all translations and scaling
 * operations have already been done.
 *
 * @param canvas Canvas
 * @return void
 */
 
public void drawOnCanvas (Canvas canvas) {
 
    int x, y;
    float fx, fy;
 
    Paint paint = new Paint();
 
    int vh = getHeight ();
    int vw = getWidth ();
    //Log.d ("Multitouch", "LargeGridView w: " + vw  + " h: " + vh);
 
    // Set width and height to be used for the rectangle to be drawn.
    int ih = (int) Math.floor (mSquareHeight);
    int iw = (int) Math.floor (mSquareWidth);
 
    // Set up the bitmaps to be displayed. Set up the grid.
    Bitmap [] bitmaps = getBitmapsArray ();
    int [] [] grid = getGridArray ();
 
 
    // Put one extra square in. It's there as a little check that the other calculations are right.
    x = 200; 
    y = 48; 
 
    Bitmap b1 = bitmaps [0];
    canvas.drawBitmap (b1, x, y, paint);
 
    //
    // Draw squares down the diagonal of the view
    //
    x = 0; 
    y = 0; 
    Rect dest = new Rect (x, y, iw, ih);
    float dx = 0, dy = 0;
    for (int j = 0; j < NumSquaresAlongCanvas; j++) {
        int bindex = grid [j][j];
        b1 = bitmaps [bindex];

        int dxi = (int) Math.round (dx);
        int dyi = (int) Math.round (dy);
        dest.offsetTo (dxi, dyi);
        canvas.drawBitmap (b1, null, dest, paint);

        dx = dx + mSquareWidth;
        dy = dy + mSquareHeight;
    }
 
    // Draw a circle at the focus point so it's clear if scaling is working.
    // Do this last so it shows on top of everything else.
    fx = mFocusX;
    fy = mFocusY;
    if (mScaleDetector.isInProgress()) paint.setColor (Color.WHITE);
    else paint.setColor (Color.YELLOW);
    canvas.drawCircle (fx, fy, 4, paint);
 
    //Log.d ("Multitouch", "Focus " + mFocusX + " " + mFocusY + " " + fx + " " + fy);
}
 
/**
* Get an array of bitmaps, chosen randomly from the mImageIds.
*
 * @return Bitmap []
*/
 
Bitmap [] getBitmapsArray () {
    if (mBitmaps == null) {
       mBitmaps = new Bitmap [NumTypes];
       for (int j = 0; j < NumTypes; j++) {
           int i = randomInt (0, mImageIds.length - 1);
           int rid = mImageIds [i];
           //Log.d ("Multitouch", "Random image: " + i + " r: " + rid);
           Bitmap b1 = BitmapFactory.decodeResource (mContext.getResources(), rid);
           mBitmaps [j] = b1;
       }
 
    }
    return mBitmaps;
}
 
/**
 * Get 2-d grid of integers that indicate which bitmap is displayed at that point.
 *
 * @return int [] []
 */
 
int [] [] getGridArray () {
    if (mGrid == null) {
       mGrid = new int [NumSquaresAlongCanvas] [NumSquaresAlongCanvas];
       for (int i = 0; i < NumSquaresAlongCanvas; i++)
       for (int j = 0; j < NumSquaresAlongCanvas; j++) {
           int index = randomInt (0, NumTypes - 1);
           mGrid [i][j] = index;
       }
 
    }
    return mGrid;
}
 
 
/**
 * onDraw
 */
 
@Override public void onDraw(Canvas canvas) {
    // This subclass of PanZoomView overrides the general purpose onDraw method
    // implemented in PanZoomView. It still needs to do the standard onDraw so call "superOnDraw"
    // that is provided in PanZoomView for that purpose.
    superOnDraw(canvas);
   
    canvas.save();
 
    // Get the width and height of the view.
    int viewH = getHeight (), viewW = getWidth ();
 
    // Because we are displays a region N times the view size. The top left point of the
    // view is located at a point that is some multiple of the width and height.
    // For a canvas size of 3, the multiple is 1; for 4, 1.5, for 5, 2;
    mOriginOffsetX = ((float) (CanvasSizeMultiplier - 1) * viewW) / 2;
    mOriginOffsetY = ((float) (CanvasSizeMultiplier - 1) * viewH) / 2;
    mMaxCanvasWidth      = (float) (CanvasSizeMultiplier * viewW);
    mMaxCanvasHeight     = (float) (CanvasSizeMultiplier * viewH);
    mHalfMaxCanvasWidth  = ((float) CanvasSizeMultiplier * viewW) / 2;
    mHalfMaxCanvasHeight = ((float) CanvasSizeMultiplier * viewH) / 2;

    // Set width and height to be used for the squares.
    mSquareWidth  = (float) viewW / (float) NumSquaresAlongSide;
    mSquareHeight = (float) viewH / (float) NumSquaresAlongSide;
    mHalfSquareWidth  = mSquareWidth / 2;
    mHalfSquareHeight = mSquareHeight / 2;
 
    // The canvas is translated by the amount we have scrolled and the standard
    // amount to move the origin of the canvas up and left so the 3x wide region
    // is centered in the view.
    // (Note: mPosX and mPosY are defined in PanZoomView.)
    float x = 0, y = 0;
    mPosX0 = mOriginOffsetX;
    mPosY0 = mOriginOffsetY;
    x = mPosX - mPosX0;
    y = mPosY - mPosY0;
    canvas.translate (x, y);
 
    // The focus point for zooming is the center of the displayable region.
    // That point is defined by half the canvas width and height.
    mFocusX = mHalfMaxCanvasWidth;
    mFocusY = mHalfMaxCanvasWidth;
    canvas.scale (mScaleFactor, mScaleFactor, mFocusX, mFocusY);
 
    // Do the drawing operation for the view.
    drawOnCanvas (canvas);
 
    canvas.restore();
 
}
 
/**
 * Return a random number in the range: minVal to maxVal.
 *
 */

public int randomInt (int minVal, int maxVal) {
    Random r = mRandomObject;
      int range = maxVal - minVal;
      int offset = (int) Math.round (r.nextFloat () * range);
      return minVal + offset;
}
 
/**
 * Return the resource id of the sample image. Note that this class always returns 0, indicating
 * that there is no sample drawable.
 *
 * @return int
 */
 
public int sampleDrawableId () {
    return 0;
}
 
/**
 * Return true if panning is supported.
 *
 * @return boolean
 */
 
public boolean supportsPan () {
    return false;
}
 
/**
 * Return true if scaling is done around the focus point of the pinch.
 *
 * @return boolean
 */
 
public boolean supportsScaleAtFocusPoint () {
    return false;
}
 
/**
 * Return true if pinch zooming is supported.
 *
 * @return boolean
 */
 
public boolean supportsZoom () {
    return true;
}
 
} // end class
