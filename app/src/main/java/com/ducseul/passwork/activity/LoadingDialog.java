package com.ducseul.passwork.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.ducseul.passwork.R;

import java.util.concurrent.TimeUnit;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    private Integer animationJsonID;
    private boolean isActive;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
        isActive = false;
    }

    public LoadingDialog(Activity activity, int animationJsonID) {
        this.activity = activity;
        this.animationJsonID = animationJsonID;
        isActive = false;
    }

    public void startLoadingAnimation() {
        if (!isActive) {
            System.out.println("loading animation");
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View inflatedView = inflater.inflate(R.layout.loading_layout, null);
            if (animationJsonID != null) {
                LottieAnimationView loading_animation = inflatedView.findViewById(R.id.loading_animation);
                loading_animation.setAnimation(animationJsonID);
            }
            builder.setView(inflatedView);
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
            isActive = true;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(450, 420);
            dismissLoadingAnimation(10000); //auto dismiss after 10s for prevent leakedwindows
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void dismissLoadingAnimation() {
        try {
            if (isActive) {
                dialog.dismiss();
                isActive = false;
                System.out.println("Dismiss animation");
            } else {
                System.out.println("Not trying to close animation");
            }
        } catch (NullPointerException e) {
            Log.e(LoadingDialog.class.toString(), e.getMessage());
        }
    }

    public void dismissLoadingAnimation(int after) {
        try {
            if (isActive) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        isActive = false;
                    }
                }, after);
                System.out.println("Dismiss animation after " + after);
            } else {
                System.out.println("Not trying to close animation");
            }
        } catch (NullPointerException e) {
            Log.e(LoadingDialog.class.toString(), e.getMessage());
        }
    }
}
