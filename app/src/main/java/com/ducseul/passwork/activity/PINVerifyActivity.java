package com.ducseul.passwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ducseul.passwork.R;
import com.ducseul.passwork.helper.EncodeEngine;
import com.ducseul.passwork.helper.KEY;

public class PINVerifyActivity extends AppCompatActivity {

    private EditText etNumberPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinverify);

        doInitWidget();
        doInitEvent();
    }

    private void doInitWidget() {
        etNumberPassword = findViewById(R.id.etNumberPassword);
    }

    private void doInitEvent() {
        etNumberPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EncodeEngine.hashPIN(s.toString().trim())
                        .equals(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getPIN_resolve())){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}