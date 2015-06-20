package com.example.m.droide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by m on 19.6.2015.
 */
public class CoolView extends ImageView {

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
        super.setClickable(true);
        super.setOnTouchListener(new PrivateOnTouchListener());

    }

    private class PrivateOnTouchListener implements OnTouchListener {


        @Override
        public boolean onTouch(View v, MotionEvent event) {



        }
    }
}
