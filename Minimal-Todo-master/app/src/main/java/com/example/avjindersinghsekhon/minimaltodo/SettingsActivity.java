package com.example.avjindersinghsekhon.minimaltodo;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/*
this is setting activity
for users to change the app setting
now, only has the function of change theme
 */
public class SettingsActivity extends AppCompatActivity{

    @Override
    protected void onResume() {
        super.onResume();
//        app.send(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        @moss
        get the saved theme of this app
        check and set the theme
         */
        String theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        //@lv theme is light or dark
        if(theme.equals(MainActivity.LIGHTTHEME)){
            setTheme(R.style.CustomStyle_LightTheme);
        }
        else{
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //@lv set up back arrow format
        /*
        @moss
         PorterDuff.Mode.SRC_ATOP 取下层图像非交集部门与上层图像交集部门
         */
        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if(backArrow!=null){
            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        }

        //@lv set up back arrow on action bar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }

        //@lv The FragmentManager class provides methods that
        // allow you to add, remove, and replace fragments to an activity at runtime
        // in order to create a dynamic experience.
        FragmentManager fm= getFragmentManager();
        fm.beginTransaction().replace(R.id.mycontent, new SettingsFragment()).commit();
    }

    /*
    @moss
    trigger when clicked the back arrow
    will navigate to the mainActivity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
