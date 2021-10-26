package com.ducseul.passwork.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ducseul.passwork.R;

public class AdvanceSettingActivity extends AppCompatActivity {

    private ImageButton back_button;

    private RelativeLayout data_stage_wrapper;
    private ImageView data_stage_dropdown;
    private RelativeLayout data_stage_detail;

    private RelativeLayout session_setting_stage_wrapper;
    private ImageView session_setting_stage_dropdown;
    private RelativeLayout session_setting_stage_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setting);
        doInitWidget();
        doInitEvent();
    }

    private void doInitWidget() {
        back_button = findViewById(R.id.back_button);

        data_stage_wrapper = findViewById(R.id.data_stage_wrapper);
        data_stage_dropdown = findViewById(R.id.data_stage_dropdown);
        data_stage_detail = findViewById(R.id.data_stage_detail);

        session_setting_stage_wrapper = findViewById(R.id.session_setting_stage_wrapper);
        session_setting_stage_dropdown = findViewById(R.id.session_setting_stage_dropdown);
        session_setting_stage_detail = findViewById(R.id.session_setting_stage_detail);
    }

    private void doInitEvent() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data_stage_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDataStageDropdown(v);
            }
        });

        data_stage_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDataStageDropdown(v);
            }
        });

        session_setting_stage_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSessionSettingStageDropDown(v);
            }
        });

        session_setting_stage_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSessionSettingStageDropDown(v);
            }
        });
    }

    private void onClickSessionSettingStageDropDown(View v) {
        if (session_setting_stage_detail.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(data_stage_wrapper, new AutoTransition());
            session_setting_stage_detail.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(data_stage_wrapper, new AutoTransition());
            session_setting_stage_detail.setVisibility(View.GONE);
        }
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