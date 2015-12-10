package com.example.assignment01;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


//i'd like to extends SurfaceView rather than View, since SV is more efficient than View
public class PaintView extends View {
	Paint paint ;
	Paint eraser ;
    Bitmap cacheBitmap = null;  
    Canvas cacheCanvas = null; 
    private Bitmap clear_bitmap; // eraser icon
    private boolean flag_eraser = false; //whether it's a eraser
    private boolean flag_up = true; //whether the screen is touched
	
	private float mX;  
	private float mY;  
	
	//this is the parameter of nexus
    final int VIEW_WIDTH = 320;  
    final int VIEW_HEIGHT = 480; 

//	private final Paint mGesturePaint = new Paint();
	private Path mPath = new Path();

	// define additional constructors so that PaintView will work with out layout file
	public PaintView(Context context) {
		super(context);
		
		setup();
	}

	public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		setup();
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setup();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		cacheBitmap = Bitmap.createBitmap(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec), Config.ARGB_8888);
	}

	public void setup()
	{
//		setOnTouchListener(this); // define event listener and start intercepting events 
		
		clear_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		
		setPaint();  
		setEraser();
        
//        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT,Config.ARGB_8888);
        cacheCanvas = new Canvas();
        
        cacheCanvas.setBitmap(cacheBitmap);
        
		
	}
	
	private void setPaint() {
		//set the paint
		paint = new Paint();
		paint.setStyle(Style.STROKE);  // have to: import android.graphics.Paint;  
		paint.setAntiAlias(true);  // make the line itself more smooth
		paint = new Paint(paint.DITHER_FLAG); //according to the API: bit mask for the flag enabling dithering
		paint.setDither(true);  
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);  
		paint.setStyle(Paint.Style.STROKE);  
		//paint.setStyle(Paint.Style.FILL);   //Dot not use it, will generate shadow effect
		//paint.setStyle(Paint.Style.FILL_AND_STROKE);  //Dot not use it, will generate shadow effect
		paint.setStrokeJoin(Paint.Join.ROUND);  
		paint.setStrokeCap(Paint.Cap.ROUND);
		
//		flag_eraser = false; // not a eraser now
	}

	private void setEraser() {
		//set the eraser
		eraser = new Paint();
		eraser.setAlpha(0);
//		eraser.setColor(Color.TRANSPARENT);
		paint.setColor(0xFF000000);  // used for eraser
		eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		eraser.setAntiAlias(true);
		eraser.setDither(true);
		eraser.setStyle(Paint.Style.STROKE);
		eraser.setStrokeJoin(Paint.Join.ROUND);
		eraser.setStrokeCap(Paint.Cap.ROUND);
		eraser.setStrokeWidth(60);
		paint = eraser;
		clear_bitmap = Bitmap.createScaledBitmap(clear_bitmap, 60, 60, true);
		flag_eraser = true;
	}

	
	
//    public void clear(){
//    	
//    	// create a transparent bitmap that has the same size with the screen
//    	cacheBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(),Config.ARGB_8888);
//    	cacheCanvas = new Canvas();
//    	
//    	//set the cachecanvas to the memory
//    	cacheCanvas.setBitmap(cacheBitmap);
//    	
//    	invalidate();
//    	
//    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// this is the default one
		canvas.drawPath(mPath, paint); //use the canvas to draw the the picture that made of multi-points
		
		
//		Paint bmpPaint = new Paint();
//		canvas.drawBitmap(cacheBitmap, 0,0, bmpPaint);
//		
//		if(!flag_eraser){
//			if(!flag_up){
//				canvas.drawBitmap(clear_bitmap, mX-clear_bitmap.getWidth()/2,mY-clear_bitmap.getHeight()/2, null);
//			}
//			// 沿着path绘制
//			// 这里使用cacheCanvas是防止Xfermode绘图出现绘图时黑框
//			cacheCanvas.drawPath(mPath, paint);
//		}else{
//			// 沿着path绘制,没有这行不会动态绘制
//			canvas.drawPath(mPath, paint);
//		}
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) { 

		// get the x,y coordinates of the MotionEvent.ACTION_MOVE event
		switch (event.getAction()) {
		
			case MotionEvent.ACTION_DOWN:
				touchDown(event);
				break;

			case MotionEvent.ACTION_MOVE:
				touchMove(event);
				break;
				
				
//				case MotionEvent.ACTION_UP:
//					touchUp();
//					break;

		}	
		invalidate(); //// force a screen re-draw
		return true; //return true to consume event from the event queue
	}
	
	private void touchDown(MotionEvent event){
		mPath.reset(); //redraw the path, hide the previous path
		
		float x = event.getX();
		float y = event.getY();
		
		mX = x;
		mY = y;
		
		flag_up = false;
		mPath.moveTo(x, y); // this is the starting point 
	}
	
	private void touchMove(MotionEvent event){
		final float x = event.getX();
		final float y = event.getY();
		
		final float previousX = mX;
		final float previousY = mY;
		
		final float dx = Math.abs(x-previousX);
		final float dy = Math.abs(y-previousY);
		
		//draw a 贝塞尔绘制曲线  if dx > 3
		if(dx >= 3 || dy >= 3){
			float cX = (x + previousX) / 2;
			float cY = (y + previousY) / 2;
			
			// do 贝塞尔绘制曲线  again to make the line more smooth?
			mPath.quadTo(previousX, previousY, cX, cY);
			
			mX = x;
			mY = y;
		}
	}
//	
//	private void touchUp(){
//		flag_up = true;
//		cacheCanvas.drawPath(mPath, paint);
//		mPath.reset();
//		
//	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		//create & re-create t he off screen bitmap to capture the state of our drawing 
		// this operation will reset the user's drawing
		
		cacheBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
		cacheCanvas = new Canvas(cacheBitmap);
	}
}
