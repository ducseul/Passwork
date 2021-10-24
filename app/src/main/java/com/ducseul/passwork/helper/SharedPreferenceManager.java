package com.ducseul.passwork.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceManager {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private String PREF_NAME;
    private int PRIVATE_MODE = 0;

    private static SharedPreferenceManager sharedPreferenceDataManager;
    private static SharedPreferenceManager sharedPreferenceConfigManager;


    private class SharedPreferenceProfile {
        public static final String ANDROID_DATA = "android-data-manager";
        public static final String ANDROID_CONFIG = "android-config-manager";
    }

    public SharedPreferenceManager(Context mContext, String PREF_NAME) {
        this.mContext = mContext;
        this.PREF_NAME = PREF_NAME;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferenceManager getInstance_AppData(Context context) {
        if (sharedPreferenceDataManager == null) {
            sharedPreferenceDataManager = new SharedPreferenceManager(context, SharedPreferenceProfile.ANDROID_DATA);
        }
        return sharedPreferenceDataManager;
    }

    public static SharedPreferenceManager getInstance_ConfigProfile(Context context) {
        if (sharedPreferenceConfigManager == null) {
            sharedPreferenceConfigManager = new SharedPreferenceManager(context, SharedPreferenceProfile.ANDROID_CONFIG);
        }
        return sharedPreferenceConfigManager;
    }

    public SharedPreferences.Editor getmEditor() {
        return mEditor;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}