package com.ducseul.passwork.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ducseul.passwork.R;

public class AdvanceSettingActivity extends AppCompatActivity {

    private RelativeLayout data_stage_wrapper;
    private ImageView data_stage_dropdown;
    private RelativeLayout data_stage_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setting);
        doInitWidget();
        doInitEvent();
    }

    private void doInitWidget() {
        data_stage_wrapper = findViewById(R.id.data_stage_wrapper);
        data_stage_dropdown = findViewById(R.id.data_stage_dropdown);
        data_stage_detail = findViewById(R.id.data_stage_detail);
    }

    private void doInitEvent() {
        data_stage_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDataStageDropdown(v);
            }
        });
    }

    private void onClickDataStageDropdown(View v) {
        if (data_stage_detail.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(data_stage_wrapper, new AutoTransition());
            data_stage_detail.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(data_stage_wrapper, new AutoTransition());
            data_stage_detail.setVisibility(View.GONE);
        }
    }
}