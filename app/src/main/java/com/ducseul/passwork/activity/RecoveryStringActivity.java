package com.ducseul.passwork.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ducseul.passwork.R;
import com.ducseul.passwork.helper.KEY;

import java.util.Arrays;

public class RecoveryStringActivity extends AppCompatActivity {

    private LinearLayout recovery_combination;
    private Button btnContinues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_string);

        doInitWidget();
        doInitEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doInitWidget() {
        recovery_combination = findViewById(R.id.recovery_combination);
        renderRecoveryWord();

        btnContinues = findViewById(R.id.btnContinues);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void renderRecoveryWord() {
        Arrays.stream(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getRecoveryComb()).forEach(str -> {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View custLayout = inflater.inflate(R.layout.layout_recovery_word, null, false);
            TextView recovery_word = custLayout.findViewById(R.id.recovery_word);
            recovery_word.setText(" - "+ str.toString());
            recovery_combination.addView(custLayout);
        });
    }

    private void doInitEvent() {
        btnContinues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClickBtnContinue(v);
            }
        });
    }

    private void doClickBtnContinue(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}