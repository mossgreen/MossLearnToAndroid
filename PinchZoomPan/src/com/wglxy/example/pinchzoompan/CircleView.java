package com.wglxy.example.pinchzoompan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;

/**
 * This view displays a few rectangles that can be panned and zoomed.
 * It is a subclass of PanZoomView, which provides most of the code to
 * support zooming and panning.
 */

public class CircleView extends PanZoomView {

/**
 */
public CircleView (Context context) {
    super (context);
}

public CircleView (Context context, AttributeSet attrs) {
    super (context, attrs);
}

public CircleView (Context context, AttributeSet attrs, int defStyle) {
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

    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    Point p1 = new Point (200, 200);
    canvas.drawCircle (p1.x, p1.y, 100, paint);

    // Draw a circle at the focus point
    if (mScaleDetector.isInProgress()) paint.setColor (Color.WHITE);
    else paint.setColor (Color.YELLOW);
    canvas.drawCircle (mFocusX, mFocusY, 2, paint);

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
    return true;
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
