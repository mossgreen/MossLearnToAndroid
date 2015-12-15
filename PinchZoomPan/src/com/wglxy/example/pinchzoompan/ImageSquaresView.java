package com.wglxy.example.pinchzoompan;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * This view displays a few rectangles that can be panned and zoomed.
 * It is a subclass of PanZoomView, which provides most of the code to
 * support zooming and panning.

 */

public class ImageSquaresView extends PanZoomView {

    static public final int MaxCanvasWidth = 960;
    static public final int MaxCanvasHeight = 960;
    static public final int HalfMaxCanvasWidth = 480;
    static public final int HalfMaxCanvasHeight = 480;

    private Random mRandomObject = new Random (System.currentTimeMillis ());
    private final int [] mImageIds = {R.drawable.square_01, R.drawable.square_02, R.drawable.square_03, R.drawable.square_04, 
                                R.drawable.square_05, R.drawable.square_06, R.drawable.square_07, R.drawable.square_08 };
    private Bitmap [] mBitmaps = null;

/**
 */
public ImageSquaresView (Context context) {
    super (context);
}

public ImageSquaresView (Context context, AttributeSet attrs) {
    super (context, attrs);
}

public ImageSquaresView (Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

/**
 * Do whatever drawing is appropriate for this view.
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

    paint.setColor(Color.RED);
    paint.setStrokeWidth(3);
    canvas.drawRect(30, 30, 60, 60, paint);
    paint.setStrokeWidth(0);
    paint.setColor(Color.CYAN);
    canvas.drawRect(33, 60, 77, 77, paint );
    paint.setColor(Color.YELLOW);
    canvas.drawRect(33, 33, 77, 60, paint );

    paint.setColor(Color.BLUE);
    Point p1 = new Point (200, 200);
    int h = 160, w = 40;
    canvas.drawRect (p1.x, p1.y, p1.x + w, p1.y + h, paint);

    paint.setColor(Color.GREEN);
    Point r0 = new Point (400, 600);
    h = 120; w = 80;
    canvas.drawRect (r0.x, r0.y, r0.x + w, r0.y + h, paint);

    // Read some of the bitmap objects from resources.

    x = HalfMaxCanvasWidth;
    y = HalfMaxCanvasHeight;

    Bitmap [] bitmaps = getBitmapsArray ();
    Bitmap b1 = bitmaps [0];
    //Drawable d1 = mContext.getResources().getDrawable (id1);
    //d1.setBounds(0, 0, d1.getIntrinsicWidth(), d1.getIntrinsicHeight());
    canvas.drawBitmap (b1, x, y, paint);

    Bitmap lastB = b1;
    for (int j = 1; j < mBitmaps.length; j++) {
        x += lastB.getWidth ();
        y += lastB.getHeight ();
        lastB = bitmaps [j];
        canvas.drawBitmap (lastB, x, y, paint);
    }

    // Draw a circle at the focus point.
    // Do this last so it shows on top of everything else.
    fx = mFocusX - mPosX0;
    fy = mFocusY - mPosY0;
    if (mScaleDetector.isInProgress()) paint.setColor (Color.WHITE);
    else paint.setColor (Color.YELLOW);
    canvas.drawCircle (fx, fy, 4, paint);
    //Log.d ("Multitouch", "Focus " + mFocusX + " " + mFocusY + " " + fx + " " + fy);

}

/**
 * description
 * 
 * @param paramName value that determines...
 * @return the result of ...
 */

Bitmap [] getBitmapsArray () {
    if (mBitmaps == null) {
       mBitmaps = new Bitmap [mImageIds.length];
       for (int j = 0; j < mImageIds.length; j++) {
           int i = randomInt (0, mImageIds.length - 1);
           int rid = mImageIds [i];
           Log.d ("Multitouch", "Random image: " + i + " r: " + rid);
           Bitmap b1 = BitmapFactory.decodeResource (mContext.getResources(), rid);
           mBitmaps [j] = b1;
       }

    }
    return mBitmaps;
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
 * This method performs whatever set up is necessary to do drawing. It is called by the constructor.
 * The default implementation checks to see if both panning and zooming are supported.
 * And it also locates the sample drawable resource by calling sampleDrawableId.
 * If that method returns 0, the sample image is not set up.
 * 
 * @return void
 */

protected void setupToDraw (Context context, AttributeSet attrs, int defStyle) {
    super.setupToDraw (context, attrs, defStyle);

    mPosX0 = 0 - HalfMaxCanvasWidth;
    mPosY0 = 0 - HalfMaxCanvasHeight;

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
    return true;
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
