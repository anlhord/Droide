package com.example.m.droide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by m on 19.6.2015.
 */
public class CoolView extends ImageView {

    public int bigedge = 800;  // some defaults
    public int smalledge = 600;

    public HashMap<Integer, char[]> document = new HashMap<Integer, char[]>();

    float fontopy =0.f; // negative?
    float fontboty = 0.f;

    public double ydisp = 0.0;

    // pixel width of scrollbar

    public static final int SCROLLBAR_WIDTH = 54;
    public float fontsize = 15.f;
    public float fontsizep = 15.f;

    public float charw = 7.f;


    public Bitmap newImage;


    public CoolView(Context context) {
        super(context);
        sharedConstructing(context);
    }
    public CoolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }

    public CoolView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {

        CoolDocument();

        setWillNotDraw(false);
        super.setClickable(true);
        super.setOnTouchListener(new PrivateOnTouchListener());
        super.requestFocus();


    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);

    }

    public void CoolDocument() {
        for (int i = 0; i < 100; i++) {
            String r = Long.toHexString(Double.doubleToLongBits(Math.random()));
            String rr = r+r+r+r+r+r+r+r+r+r+r+r;
            char[] row = rr.toCharArray();
            document.put(i, row);
        }
 //       document.put(4, Long.toHexString(bigedge).toCharArray());
    }

    @Override
    protected void onDraw(Canvas c) {

        super.onDraw(c);

        Bitmap newImage = Bitmap.createBitmap(bigedge, smalledge,
                Bitmap.Config.ARGB_8888);

        c.drawColor(0xffffffff);

        Paint wpaint = new Paint();
        wpaint.setStyle(Paint.Style.FILL);
        wpaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        wpaint.setTypeface(Typeface.MONOSPACE);

        wpaint.setColor(Color.WHITE);
        wpaint.setTextSize(fontsize);


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.MONOSPACE);

        paint.setColor(Color.BLACK);
        paint.setTextSize(fontsize);


/*
// added this to measure the text dimension.
        Paint.FontMetrics fm = new Paint.FontMetrics();
        paint.getFontMetrics(fm);
        float top = fm.top;
        float width = paint.measureText("_");
        float bottom = fm.bottom;
/**/
        for (int i = 0; i < 41; i++) {
            c.drawText(document.get(i), 0, 83, 0, fontsizep * i, paint);
/*
            // small selection
            c.drawRect(5*charw, (i-1)*fontsizep + fontboty/2,
                    10*charw , i*fontsizep + fontboty, paint);
            c.drawText(document.get(8), 5, 10-5, 5*charw, fontsizep*i, wpaint);
*/
        }



        Bitmap scrollBar = Bitmap.createBitmap(SCROLLBAR_WIDTH, smalledge,
                Bitmap.Config.ARGB_8888);


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

        this.setImageBitmap(newImage);

    }


    private class PrivateOnTouchListener implements OnTouchListener {

        private PointF last = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent e) {

            PointF curr = new PointF(e.getX(), e.getY());


            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    last.set(curr);
                    break;
                case MotionEvent.ACTION_MOVE:

 //                   String r = Long.toHexString(Double.doubleToLongBits(curr.x - last.x));
  //                  document.put(1, r.toCharArray());

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            };

 //           if (touchImageViewListener != null) {
 //               touchImageViewListener.onMove();
 //           }

            ydisp =  Math.random();



            //here need to



           CoolDocument();

            invalidate();
            postInvalidate();

            return true;
        }
    }
}
