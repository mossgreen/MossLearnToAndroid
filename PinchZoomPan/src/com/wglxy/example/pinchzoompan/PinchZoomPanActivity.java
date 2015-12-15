package com.wglxy.example.pinchzoompan;

/*
 * Copyright (C) 2011-2012 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.wglxy.example.pinchzoompan.PanAndZoomListener.Anchor;
import com.wglxy.example.pinchzoompan.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * This activity displays an image in an image view and then sets up a touch event
 * listener so the image can be panned and zoomed.
 */

public class PinchZoomPanActivity extends Activity {

/**
 * onCreate
 */

@Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.main3);

    FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams (FrameLayout.LayoutParams.MATCH_PARENT, 
                                                                FrameLayout.LayoutParams.MATCH_PARENT, 
                                                                Gravity.TOP | Gravity.LEFT);
    FrameLayout view = new FrameLayout (this);
    setContentView (view);

    ImageView imageView = new ImageView(this);
    //Use line below for large images if you have hardware rendering turned on
    //imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    // Line below is optional, depending on what scaling method you want
    view.addView(imageView, fp);

    imageView.setScaleType(ImageView.ScaleType.MATRIX);
    view.setOnTouchListener(new PanAndZoomListener(view, imageView, Anchor.TOPLEFT));

    imageView.setImageResource (R.drawable.image_1024);
    
    /*
    FrameLayout container = (FrameLayout) findViewById (R.id.container);
    container.addView (view, fp);
	*/
    /*
    FrameLayout frame = (FrameLayout) findViewById (R.id.frame);
    ImageView iv = (ImageView) findViewById (R.id.image);
    if (iv != null) {
       iv.setOnTouchListener(new PanAndZoomListener(frame, iv, Anchor.TOPLEFT));
    }
    */

}

} // end class
