package com.example.m.droide;

import com.example.m.droide.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.Display;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;


    private SystemUiHider mSystemUiHider;
   private  View.OnTouchListener mDelayHideTouchListener;

    @TargetApi(11)
    private void hidebar() {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);   //new
        getActionBar().hide();                                   //new
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        hidebar();

        setContentView(R.layout.activity_fullscreen);

        Display display = getWindowManager().getDefaultDisplay();

        final View contentView = findViewById(R.id.imageView1);
        final ImageView mImageView = (ImageView)findViewById(R.id.imageView1);

        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        mSystemUiHider.hide();
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSystemUiHider.hide();
            }
        });



        int bigedge = display.getWidth();
        int smalledge = display.getHeight();
        if (bigedge < smalledge) {
            bigedge = smalledge;
            smalledge = display.getWidth();
        }

        Bitmap newImage = Bitmap.createBitmap(bigedge, smalledge,
                Config.ARGB_8888);

        Canvas c = new Canvas(newImage);
//        c.drawBitmap(bm, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Style.FILL);
        paint.setTextSize(20);
        c.drawText("Some Text", 0, 25, paint);

        mImageView.setImageBitmap(newImage);


    }


    @Override
       protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        mSystemUiHider.hide();
    }

}
