package com.example.m.droide;

//  import com.example.m.droide.util.DocRow;

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

import java.util.HashMap;

/**
 */
public class FullscreenActivity extends Activity {

    Bitmap newImage;
    public Canvas c;

    public HashMap<Integer, char[]> document = new HashMap<Integer, char[]>();
    ;
    public int row = 0;


    public double ydisp = 0.0;

    // pixel width of scrollbar

    private static final int SCROLLBAR_WIDTH = 54;



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
/*


////// problem=not work on old android

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);   //new
        getActionBar().hide();                                   //new
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


              */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        hidebar();

        setContentView(R.layout.activity_fullscreen);






        reDraw();

    }


    protected void reDraw() {

        Display display = getWindowManager().getDefaultDisplay();



        final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);

        int bigedge = display.getWidth();
        int smalledge = display.getHeight();
        if (bigedge < smalledge) {
            bigedge = smalledge;
            smalledge = display.getWidth();
        }



        newImage = Bitmap.createBitmap(bigedge, smalledge,
                Config.ARGB_8888);

        c = new Canvas(newImage);
//        c.drawBitmap(bm, 0, 0, null);

        c.drawColor(0xffffffff);



        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Style.FILL);
        paint.setTextSize(15);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.MONOSPACE);

        for (int i = 0; i < 100; i++) {
            String r = Long.toHexString(Double.doubleToLongBits(Math.random()));
            String rr = r+r+r+r+r+r+r+r+r+r+r+r;
            char[] row = rr.toCharArray();
            document.put(i, row);
        }
        for (int i = 0; i < 41; i++) {
            c.drawText(document.get(i), 0, 83, 0, 15*i + ((int)ydisp)%15, paint);
        }

        Bitmap scrollBar = Bitmap.createBitmap(SCROLLBAR_WIDTH, smalledge,
                Config.ARGB_8888);


        int foo = 0;


        for (int y = 0; y < smalledge; y++) {
            for (int x = 0; x < SCROLLBAR_WIDTH; x++) {


                if ((foo & 1)==0) {

                    scrollBar.setPixel(x, y, Color.BLACK);
                }

                foo++;
            }

        }


        c.drawBitmap(scrollBar, bigedge- SCROLLBAR_WIDTH, 0, null);

        mImageView.setImageBitmap(newImage);




    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

       float lastX=e.getX();
       float lastY=e.getY();


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        };

        ydisp =  Math.random();


        return true;
    }



}
