package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;


/*
@moss
OnSharedPreferenceChangeListener是Android中SharedPreference文件发生变化的监听器。通常
 */

/*
@moss
这个类大概是： 当点击setting后，进入setting activity，每选中一下主题，都会进入这个Fragment
而fragment 是有自己的生命周期的，所以有 onCreate 之类的阶段，
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_layout);
   }

    /*
    @moss
    if the theme is change to dark theme,
    set the theme of mainactivity to dark theme
    then save the preference
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceKeys preferenceKeys = new PreferenceKeys(getResources());
        if(key.equals(preferenceKeys.night_mode_pref_key)){

            SharedPreferences themePreferences = getActivity().getSharedPreferences(MainActivity.THEME_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor themeEditor = themePreferences.edit();
            //We tell our MainLayout to recreate itself because mode has changed
            themeEditor.putBoolean(MainActivity.RECREATE_ACTIVITY, true);

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference(preferenceKeys.night_mode_pref_key);
            if(checkBoxPreference.isChecked()){

                themeEditor.putString(MainActivity.THEME_SAVED, MainActivity.DARKTHEME);
            }
            else{
                themeEditor.putString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
            }
            themeEditor.apply();

            getActivity().recreate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
