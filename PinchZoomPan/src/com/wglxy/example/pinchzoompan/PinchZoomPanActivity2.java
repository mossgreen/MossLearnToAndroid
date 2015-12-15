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

public class PinchZoomPanActivity2 extends Activity {

/**
 * onCreate
 */

@Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.basic_example);
    //setContentView(R.layout.zoom2);
}

} // end class
