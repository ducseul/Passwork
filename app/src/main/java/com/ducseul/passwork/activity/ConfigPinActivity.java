package com.ducseul.passwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ducseul.passwork.R;
import com.ducseul.passwork.helper.EncodeEngine;
import com.google.gson.Gson;
import com.ducseul.passwork.helper.KEY;
import com.ducseul.passwork.helper.SharedPreferenceManager;

public class ConfigPinActivity extends AppCompatActivity {

    private EditText etNumberPassword;
    private Button btnContinue;

    private Gson gson;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_pin);
        gson = new Gson();
        loadingDialog = new LoadingDialog(this);

        doInitWidget();
        doInitEvent();
    }

    private void doInitWidget() {
        btnContinue = findViewById(R.id.btnContinue);
        etNumberPassword = findViewById(R.id.etNumberPassword);
    }

    private void doInitEvent() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnContinue(v);
            }
        });
    }

    private void onClickBtnContinue(View v) {
        loadingDialog.startLoadingAnimation();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                "https://random-word-api.herokuapp.com/word?number=6",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferenceManager instance_configProfile = SharedPreferenceManager.getInstance_ConfigProfile(getApplicationContext());
                        SharedPreferences.Editor editor = instance_configProfile.getmEditor();
                        editor.putBoolean(KEY.CONFIG.DO_CONFIG_OK, true);
                        editor.commit();

                        String[] listKeyword = gson.fromJson(response, String[].class);
                        KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().setRecoveryComb(listKeyword);
                        KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().setPIN_resolve(EncodeEngine.hashPIN(etNumberPassword.getText().toString().trim()));
                        Intent intent = new Intent(getApplicationContext(), RecoveryStringActivity.class);

                        SharedPreferenceManager instance_appData = SharedPreferenceManager.getInstance_AppData(getApplicationContext());
                        SharedPreferences.Editor editor1 = instance_appData.getmEditor();
                        editor1.putString(KEY.APP_DATA_KEY, gson.toJson(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance()));
                        editor1.commit();

                        loadingDialog.dismissLoadingAnimation();
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("FUCK3");
                    }
                });

        queue.add(getRequest);
    }
}