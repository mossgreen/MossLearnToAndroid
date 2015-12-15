/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.wglxy.example.pinchzoompan;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.*;

/**
 * This activity presents a list of all the activities in the SAMPLE category.
 * It allows the user to select one.
 *
 */

public class MainActivity extends ListActivity {


/**
 * Create a list of all the different sample activities in this app. 
 * Display it so user can select one.
 *
 */

@Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.launcher);

    Intent intent = getIntent();
    String path = intent.getStringExtra("com.example.android.apis.Path");

    if (path == null) {
        path = "";
    }

    setListAdapter(new SimpleAdapter(this, getData(path),
            android.R.layout.simple_list_item_1, new String[] { "title" },
            new int[] { android.R.id.text1 }));
    getListView().setTextFilterEnabled(true);
}


/**
 * Resume the activity.
 */

@Override public void onResume() {
    super.onResume();

    View v  = findViewById (R.id.wglxy_bar);
    if (v != null) {
       Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
       //anim1.setAnimationListener (new StartActivityAfterAnimation (i));
       v.startAnimation (anim1);
    }
}

/**
 * Handle a click on the Wglxy views at the bottom.
 *
 */    

public void onClickWglxy (View v) {
    Intent viewIntent = new Intent ("android.intent.action.VIEW", 
                                    Uri.parse("http://double-star.appspot.com/blahti/ds-download.html"));
    startActivity(viewIntent);
    
}

/**
 * getData
 */

protected List<Map<String, Object>> getData(String prefix) {
    List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    mainIntent.addCategory("com.wglxy.example.pinchzoompan.SAMPLE");

    PackageManager pm = getPackageManager();
    List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

    if (null == list)
        return myData;

    String[] prefixPath;
    String prefixWithSlash = prefix;

    if (prefix.equals("")) {
        prefixPath = null;
    } else {
        prefixPath = prefix.split("/");
        prefixWithSlash = prefix + "/";
    }

    int len = list.size();

    Map<String, Boolean> entries = new HashMap<String, Boolean>();

    for (int i = 0; i < len; i++) {
        ResolveInfo info = list.get(i);
        CharSequence labelSeq = info.loadLabel(pm);
        String label = labelSeq != null
                ? labelSeq.toString()
                : info.activityInfo.name;

        if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

            String[] labelPath = label.split("/");

            String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

            if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                addItem(myData, nextLabel, activityIntent(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name));
            } else {
                if (entries.get(nextLabel) == null) {
                    addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                    entries.put(nextLabel, true);
                }
            }
        }
    }

    Collections.sort(myData, sDisplayNameComparator);

    return myData;
}

/**
 * sDisplayNameComparator
 */

private final static Comparator<Map<String, Object>> sDisplayNameComparator =
    new Comparator<Map<String, Object>>() {
    private final Collator   collator = Collator.getInstance();

    public int compare(Map<String, Object> map1, Map<String, Object> map2) {
        return collator.compare(map1.get("title"), map2.get("title"));
    }
};

/**
 * activityIntent
 */

protected Intent activityIntent(String pkg, String componentName) {
    Intent result = new Intent();
    result.setClassName(pkg, componentName);
    return result;
}

/**
 * browseIntent
 */

protected Intent browseIntent(String path) {
    Intent result = new Intent();
    result.setClass(this, MainActivity.class);
    result.putExtra("com.example.android.apis.Path", path);
    return result;
}

/**
 * addItem
 */

protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
    Map<String, Object> temp = new HashMap<String, Object>();
    temp.put("title", name);
    temp.put("intent", intent);
    data.add(temp);
}

@Override
@SuppressWarnings("unchecked")
protected void onListItemClick(ListView l, View v, int position, long id) {
    Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

    Intent intent = (Intent) map.get("intent");
    startActivity(intent);
}

} // end MainActivity
