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

public class RectanglesView extends PanZoomView {

/**
 */
public RectanglesView (Context context) {
    super (context);
}

public RectanglesView (Context context, AttributeSet attrs) {
    super (context, attrs);
}

public RectanglesView (Context context, AttributeSet attrs, int defStyle) {
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

    paint.setColor(Color.RED);
    paint.setStrokeWidth(3);
    canvas.drawRect(30, 30, 80, 80, paint);
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
