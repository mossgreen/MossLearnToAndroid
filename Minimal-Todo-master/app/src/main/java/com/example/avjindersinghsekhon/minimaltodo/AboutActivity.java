package com.example.avjindersinghsekhon.minimaltodo;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private TextView mVersionTextView;
    private String appVersion = "0.1";
    private Toolbar toolbar;
    private TextView contactMe;
    String theme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        /*
        @moss
        in the onCreate phase, app gets the theme,
        if the theme is dark theme, just set the theme to dark
        otherwise, set the light theme

        then, set the color of the bakck arrow " <--" to white
        */
        theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        if(theme.equals(MainActivity.DARKTHEME)){
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        else{
            setTheme(R.style.CustomStyle_LightTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if(backArrow!=null){
            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        }
        try{

            /*
            @moss
            通过PackageManager可以获取手机端已安装的apk文件的信息
            PackageManager这个类是用来返回各种的关联了当前已装入设备了的应用的包的信息。你可以通过getPacageManager来得到这个类。 
            通过 PackageInfo  获取具体信息方法： 
            包名获取方法：packageInfo.packageName 
            icon获取获取方法：packageManager.getApplicationIcon(applicationInfo) 
            应用名称获取方法：packageManager.getApplicationLabel(applicationInfo) 
            使用权限获取方法：packageManager.getPackageInfo packageName,PackageManager.GET_PERMISSIONS).requestedPermissions
            */
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
        }
        catch (Exception e){
            e.printStackTrace();
        }


        /*
        @moss
        first, find the TextView, which named mverisionTextview from R.id
        second, set the text, which is from R.string, to this TextView 
        third, find the Toolbar, which named toolbar, from R.id
        fourth, find the TextView, which is named contactMe, from R.id

        set an on click listener to the contactMe info, which will trigger a app.send method
        */
        mVersionTextView = (TextView)findViewById(R.id.aboutVersionTextView);
        mVersionTextView.setText(String.format(getResources().getString(R.string.app_version), appVersion));
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        contactMe = (TextView)findViewById(R.id.aboutContactMe);

        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: sent me an email...
            }
        });

        /*
        @moss
        关于使用toobar 的layout:
        1. 在 activity_main.xml 里面添加 Toolbar 控件：
        2.在 MainActivity.java 中加入 Toolbar 的声明：
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        声明后，再将之用 setSupportActionBar 设定，Toolbar即能取代原本的 actionbar 了
        */
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
    }

    /*
    @moss
    this is the menu on the left top on the screen
    click this will navigate user back to main activity
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

                /*
                @moss
                NavUtils provides helper functionality for applications implementing recommended Android UI navigation patterns.
                public static String getParentActivityName (Activity sourceActivity)
                Return the fully qualified class name of sourceActivity's parent activity 
                as specified by a PARENT_ACTIVITY <meta-data> element within the activity element in the application's manifest.
                */
                if(NavUtils.getParentActivityName(this)!=null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
