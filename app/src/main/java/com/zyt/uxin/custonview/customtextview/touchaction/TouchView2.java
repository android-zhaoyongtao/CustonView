package com.zyt.uxin.custonview.customtextview.touchaction;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchView2 extends View {


    public TouchView2(Context context) {
        super(context);
    }

    public TouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("zhao6", "2ACTION_DOWN");
//                return false;
//             case MotionEvent.ACTION_MOVE:
//                Log.e("zhao6", "2ACTION_DOWN");
//                return true;
//            case MotionEvent.ACTION_UP:
//                Log.e("zhao6", "2ACTION_UP");
//                return false;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("zhao6", "2ACTION_CANCEL");
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("zhao6", "2ACTION_DOWN");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.e("zhao6", "2ACTION_MOVE");
                return false;
            case MotionEvent.ACTION_UP:
                Log.e("zhao6", "2ACTION_UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.e("zhao6", "2ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }
}
