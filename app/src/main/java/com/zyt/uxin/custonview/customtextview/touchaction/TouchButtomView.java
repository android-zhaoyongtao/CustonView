package com.zyt.uxin.custonview.customtextview.touchaction;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TouchButtomView extends android.support.v7.widget.AppCompatButton {


    public TouchButtomView(Context context) {
        super(context);
    }

    public TouchButtomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchButtomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("zhao6", "b_ACTION__DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("zhao6", "b_ACTION__MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("zhao6", "b_ACTION__UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("zhao6", "b_ACTION__CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }


}
