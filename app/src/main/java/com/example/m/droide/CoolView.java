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

    // 80 cols and 3 line number digits

    public static final int TOTALCOLS = 83;
    public static final boolean WIDESELECTION = true;

    // basically the size of the rectangle

    public int bigedge = 800;  // some defaults
    public int smalledge = 600;

    // basically the document

    public HashMap<Integer, char[]> document = new HashMap<Integer, char[]>();

    // basically main font size

    public float fontsize = 15.f;
    public float fontsizep = 15.f;  // fontsize + 1

    // basically font metrics

    public float charw = 7.f;
    float fontopy =0.f; // negative?
    float fontboty = 0.f;

    // pixel width of scrollbar

    public static final int SCROLLBAR_WIDTH = 54;


    // on screen letter drag

    int osd_sx = 0, osd_sy = 0, osd_ex = 0, osd_ey = 0;


    private void osd2oss() {
        // if start and end is
        if ((osd_sy > osd_ey) || ((osd_sy == osd_ey) && (osd_sx > osd_ex))) {
            oss_tx = osd_ex;
            oss_ty = osd_ey;
            oss_ex = osd_sx;
            oss_ey = osd_sy;
        } else {
            oss_tx = osd_sx;
            oss_ty = osd_sy;
            oss_ex = osd_ex;
            oss_ey = osd_ey;
        }
    }

    // on-screen-selection top x,y, end x,y
    // if endy < topy, rectangular selection

    int oss_tx = 3, oss_ty = 3, oss_ex = 7, oss_ey = 7;


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

        // on some screens up to 41

        for (int i = 0; i < 30; i++) {
            c.drawText(document.get(i), 0, 83, 0, (i+1) * fontsizep, paint);

            if (i >= oss_ty && i <= oss_ey) {
                // small selection

                int xl = oss_tx;
                int xr = oss_ex;

                if (WIDESELECTION && i != oss_ey) {
                    xr = 83;
                }

                if (WIDESELECTION && i != oss_ty) {
                    xl = 0;
                }

                c.drawRect(xl*charw, (i+0)*fontsizep + fontboty/2,
                        xr*charw , (i+1)*fontsizep + fontboty, paint);


                    if (xl < xr) {


                        c.drawText(document.get(i), xl, xr - xl, xl * charw, fontsizep * (i + 1), wpaint);


                    }

            }

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

                    osd_sx = (int) (0.2 + last.x / charw);
                    osd_sy = (int) (last.y / fontsizep);

                    osd2oss();

                    break;
                case MotionEvent.ACTION_MOVE:


                    osd_ex = (int) (0.2 + curr.x / charw);
                    osd_ey = (int) (curr.y / fontsizep);

                    osd2oss();

 //                   String r = Long.toHexString(Double.doubleToLongBits(curr.x - last.x));
  //                  document.put(1, r.toCharArray());

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:

                    osd_ex = (int) (0.2 + curr.x / charw);
                    osd_ey = (int) (curr.y / fontsizep);


                    osd2oss();

                    break;
            };

 //           if (touchImageViewListener != null) {
 //               touchImageViewListener.onMove();
 //           }




            //here need to



           CoolDocument();

  //          invalidate();
            postInvalidate();

            return true;
        }
    }
}
