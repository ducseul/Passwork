package com.ducseul.passwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.ducseul.passwork.R;
import com.ducseul.passwork.entity.DataObject;
import com.ducseul.passwork.helper.KEY;
import com.ducseul.passwork.helper.SharedPreferenceManager;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         gson = new Gson();
        Intent intent;

        SharedPreferenceManager instance_configProfile = SharedPreferenceManager.getInstance_ConfigProfile(getApplicationContext());
        boolean isDoConfigYet = instance_configProfile.getSharedPreferences().getBoolean(KEY.CONFIG.DO_CONFIG_OK, false);
        if(!isDoConfigYet){
            intent = new Intent(getApplicationContext(), ConfigPinActivity.class);
        } else {
            SharedPreferenceManager instance_appData = SharedPreferenceManager.getInstance_AppData(getApplicationContext());
            String value = instance_appData.getSharedPreferences().getString(KEY.APP_DATA_KEY, "{}");
            KEY.CACHE_DATA_OBJECT_MANAGER.setDataObject(gson.fromJson(value, DataObject.class));
            intent = new Intent(getApplicationContext(), PINVerifyActivity.class);
        }

        try {
            InputStream open = this.getAssets().open("auto_complete_title.json");
            int size = open.available();
            byte[] buffer = new byte[size];
            open.read(buffer);
            open.close();
            String json = new String(buffer, "UTF-8");
            ArrayList arrayList = new Gson().fromJson(json, ArrayList.class);
            HashMap<String, String> dict = new HashMap<>();
            for (Object data: arrayList){
                LinkedTreeMap<String, String> linkedTreeMap = (LinkedTreeMap<String, String>) data;
                dict.put(linkedTreeMap.get("title"), linkedTreeMap.get("url"));
            }
            KEY.CACHE_MANAGER.AUTO_COMPLETE_DICT = dict;
        } catch (Exception ignore){

        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 2000);
    }
}