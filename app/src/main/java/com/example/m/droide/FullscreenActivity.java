package com.example.m.droide;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;


public class FullscreenActivity extends Activity {

    private void hidebar() {

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        hidebar();

        setContentView(R.layout.activity_fullscreen);

        // get info about display

        Display display = getWindowManager().getDefaultDisplay();

        final CoolView mImageView = (CoolView) findViewById(R.id.imageView1);


        // cool obtain screen dimensions
       int big = display.getWidth();
       int small = display.getHeight();
        if (big < small) {
            int t= big;            big = small;            small = t;
        }
        mImageView.bigedge = big;
        mImageView.smalledge = small;
    }

}
