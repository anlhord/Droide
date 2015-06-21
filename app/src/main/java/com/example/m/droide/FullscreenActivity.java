package com.example.m.droide;

import android.graphics.Color;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Paint.Style;

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

    // bisect optimal letter size
    private PointF b = new PointF(1.0f ,30.0f );


    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);

    Style = Paint.Style.FILL;

/*
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    paint.setTypeface(Typeface.MONOSPACE);

    paint.setColor(Color.BLACK);
    paint.setTextSize((float) 15.);
*/

    /*
    Paint paint = new Paint();
    paint.setColor(Color.BLACK);
    paint.setTextSize((float) 15.);
    float width = paint.measureText("_");
*/
}
