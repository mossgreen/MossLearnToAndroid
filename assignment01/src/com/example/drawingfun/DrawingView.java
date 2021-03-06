package com.example.drawingfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Main class of the application. This view presents a canvas that users can
 * draw on with pen or shapes
 * 
 * @author Feifei GU <gufeifei@outlook.com> studentID:1452941
 * 
 * @version 20151216
 * @since java 1.7
 */

public class DrawingView extends View {
	// drawing path
	private Path drawPath;
	// the user paths drawn with drawPaint will be drawn onto the canvas,
	// which is drawn with canvasPaint
	private Paint drawPaint, canvasPaint;
	// initial color
	private int paintColor = getResources().getInteger(R.integer.initcolor);;

	// canvas
	private Canvas drawCanvas;
	// canvas bitmap
	private Bitmap canvasBitmap;
	// variables for the brush sizes
	// and for keeping track of the last brush size used when the user switches
	// to the eraser
	// so that we can revert back to the correct size when users decide to
	// switch back to drawing
	private float brushSize, lastBrushSize;
	// variables act as a flag for whether the user is currently erasing or not,
	// and are indicating the shape
	private boolean erase = false;
	private boolean isPaint = true;
	private boolean isTri = false;
	private boolean isRect = false;
	private boolean isCir = false;

	private float cirRadius, triHeight, rectLength; // to store the three
													// dimension values

	float preX, preY; // prepare for QuadTo method, which will smooth the draw

	/**
	 * constructor of this class, which holds two params
	 * 
	 * @param context
	 * @param attrs
	 */
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupDrawing();
	}

	/** get drawing area setup for interaction. */
	private void setupDrawing() {
		// We use the dimension value for the medium sized brush to begin with
		brushSize = getResources().getInteger(R.integer.small_size);
		lastBrushSize = brushSize;

		// instantiate the drawing Path and Paint objects:
		drawPath = new Path();
		drawPaint = new Paint();

		// set the initial color:
		drawPaint.setColor(paintColor);

		// set the initial path properties
		// Setting the anti-alias, stroke join and cap styles will make the
		// user's drawings appear smoother.
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(brushSize);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		// instantiating the canvas Paint object:
		canvasPaint = new Paint(Paint.DITHER_FLAG);

	}

	/**
	 * method will be called when the custom View is assigned a size.
	 * 
	 * @param width
	 *            new width of the drawing area
	 * @param height
	 *            new height of the drawing area
	 * @param width
	 *            old width of the drawing area
	 * @param height
	 *            old height of the drawing area
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// instantiate the drawing canvas and bitmap using the width and height
		// values
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);

	}

	/** method that used to draw view. */
	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}

	/**
	 * method to detect user touch and draw the right shape * @param motionEvent
	 * the event happened when users are touching on the screen
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// retrieve the X and Y positions of the user touch
		float touchX = event.getX();
		float touchY = event.getY();

		if (isPaint) {

			switch (event.getAction()) {
			// When the user touches the View, we move to that position to start
			// drawing
			case MotionEvent.ACTION_DOWN:

				drawPath.moveTo(touchX, touchY);
				preX = touchX;
				preY = touchY;

				break;

			// When they move their finger on the View, we draw the path along
			// with their touch
			case MotionEvent.ACTION_MOVE:

				// QUad to curves using a quadratic line (basically an ellipse
				// of some sort).
				// QuadTo will smooth out jaggedies where they turn.
				drawPath.quadTo(preX, preY, (touchX + preX) / 2, (touchY + preY) / 2);
				preX = touchX;
				preY = touchY;
				break;

			// When they lift their finger up off the View, we draw the Path and
			// reset it for the next drawing operation
			case MotionEvent.ACTION_UP:
				drawCanvas.drawPath(drawPath, drawPaint);
				drawPath.reset();
				break;
			}

		} else if (isTri) {

			Path triPath = new Path();

			// to get the height of the triangle
			triHeight = getResources().getInteger(R.integer.tri_height);
			triPath.moveTo(touchX, touchY - triHeight);
			triPath.lineTo(touchX + triHeight, touchY + triHeight / 2);
			triPath.lineTo(touchX - triHeight, touchY + triHeight / 2);
			triPath.lineTo(touchX, touchY - triHeight);
			drawCanvas.drawPath(triPath, drawPaint);

		} else if (isCir) {

			// to get the radius of the circle
			cirRadius = getResources().getInteger(R.integer.cir_radius);
			drawCanvas.drawCircle(touchX, touchY, cirRadius, drawPaint);

		} else if (isRect) {
			Path rectPath = new Path();

			// to get the length of the rectangle
			rectLength = getResources().getInteger(R.integer.rect_length);

			rectPath.moveTo(touchX - rectLength, touchY - rectLength);
			rectPath.lineTo(touchX + rectLength, touchY - rectLength);
			rectPath.lineTo(touchX + rectLength, touchY + rectLength);
			rectPath.lineTo(touchX - rectLength, touchY + rectLength);
			rectPath.lineTo(touchX - rectLength, touchY - rectLength);
			drawCanvas.drawPath(rectPath, drawPaint);

		}

		// Calling invalidate will cause the onDraw method to execute
		invalidate();
		return true;
	}

	/**
	 * method to set color the drawPaint
	 * 
	 * @param newColor
	 *            new color is the color that will set to the drawPaint
	 */
	public void setColor(String newColor) {
		// to set color, start by invalidating the View
		invalidate();

		// next, parse and set the color for drawing
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);

	}

	/**
	 * method to set brush size
	 * 
	 * @param newSize
	 *            newSize is the size that will set to the brush
	 */
	public void setBrushSize(float newSize) {

		// passing the value from the dimensions file
		// update the brush size with the passed value
		// update the Paint object to use the new size
		float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize,
				getResources().getDisplayMetrics());
		brushSize = pixelAmount;
		drawPaint.setStrokeWidth(brushSize);
	}

	/**
	 * method to set the lastBrushSize
	 * 
	 * @param lastSize
	 */
	public void setLastBrushSize(float lastSize) {
		lastBrushSize = lastSize;
	}

	/**
	 * method to to get the LastBrushSiz
	 * 
	 * @return
	 */
	public float getLastBrushSize() {
		return lastBrushSize;
	}

	/**
	 * make sure whether user is choosing eraser Initially we will assume that
	 * the user is drawing, not erasing
	 * 
	 * @param isErase
	 */
	public void setErase(boolean isErase) {
		// set erase true or false
		// first update the flag variable
		erase = isErase;

		// then, alter the Paint object to erase or switch back to drawing
		if (erase) {
			drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		} else {
			drawPaint.setXfermode(null);
		}
	}

	/**
	 * to set whether is using the paint function
	 * 
	 * @param isPaint
	 */
	public void setPaint(boolean isPaint) {
		this.isPaint = isPaint;
	}

	/**
	 * to set whether is using the triangle shape
	 * 
	 * @param isTri
	 */
	public void setTri(boolean isTri) {
		this.isTri = isTri;
	}

	/**
	 * to set whether is using the rectangle shape
	 * 
	 * @param isRect
	 */
	public void setRect(boolean isRect) {
		this.isRect = isRect;
	}

	/**
	 * to set the variable of isCir
	 * 
	 * @param isCir
	 */
	public void setCir(boolean isCir) {
		this.isCir = isCir;
	}

	/**
	 * to start a new drawing, simply clears the canvas and updates the display
	 */
	public void startNew() {
		drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}

}
