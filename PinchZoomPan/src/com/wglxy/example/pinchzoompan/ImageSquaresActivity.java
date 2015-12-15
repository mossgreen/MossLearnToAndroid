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

import com.wglxy.example.pinchzoompan.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * This activity displays a grid of image squares.
 * The image can be panned or zoomed.
 */

public class ImageSquaresActivity extends Activity {

/**
 * onCreate
 */

@Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.image_squares);
}

} // end class
