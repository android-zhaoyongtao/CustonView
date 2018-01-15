package com.zyt.uxin.custonview;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyt.uxin.custonview.utiles.LogUtils;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class RectActivity extends AppCompatActivity {


    private RelativeLayout coinView;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testrect);
        coinView = findViewById(R.id.RelativeLayout);
        textView = findViewById(R.id.TextView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match();
            }
        });
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);

    }

    private void match() {
        LogUtils.d("RectActivity", "coinView.getX:" + coinView.getX());
        LogUtils.d("RectActivity", "coinView.getY:" + coinView.getY());
        LogUtils.d("RectActivity", "coinView.getTop:" + coinView.getTop());
        LogUtils.d("RectActivity", "coinView.getBottom:" + coinView.getBottom());
        LogUtils.d("RectActivity", "coinView.getLeft:" + coinView.getLeft());
        LogUtils.d("RectActivity", "coinView.getRight:" + coinView.getRight());
        LogUtils.d("RectActivity", "coinView.getWidth:" + coinView.getWidth());
        LogUtils.d("RectActivity", "coinView.getMeasuredWidth:" + coinView.getMeasuredWidth());

        Rect localVisibleRect = new Rect();
        coinView.getLocalVisibleRect(localVisibleRect);
        LogUtils.d("RectActivity", "coinView.getLocalVisibleRect:" + localVisibleRect.toShortString());
        int[] locationInWindow = new int[2];
        coinView.getLocationInWindow(locationInWindow);
        LogUtils.d("RectActivity", "coinView.getLocationInWindow:" + locationInWindow[0]+","+locationInWindow[1]);
        int[] locationOnScreen = new int[2];
        coinView.getLocationOnScreen(locationOnScreen);
        LogUtils.d("RectActivity", "coinView.getLocationOnScreen:" + locationOnScreen[0]+","+locationOnScreen[1]);

        Rect globalVisibleRect = new Rect();
        coinView.getGlobalVisibleRect(globalVisibleRect);
        LogUtils.d("RectActivity", "coinView.getGlobalVisibleRect:" + globalVisibleRect.toShortString());

        Rect childVisibleRect = new Rect();
        Point childPoint = new Point(0, 0);
        coinView.getChildVisibleRect(textView, childVisibleRect, childPoint);
        LogUtils.d("RectActivity", "coinView.getChildVisibleRect" + childVisibleRect.toShortString());
        LogUtils.d("RectActivity", "textView");
        Rect localVisibleRect2 = new Rect();
        textView.getLocalVisibleRect(localVisibleRect2);
        LogUtils.d("RectActivity", "textView.getLocalVisibleRect:" + localVisibleRect2.toShortString());
        int[] locationInWindow2 = new int[2];
        textView.getLocationInWindow(locationInWindow2);
        LogUtils.d("RectActivity", "textView.getLocationInWindow:" + locationInWindow2[0]+","+locationInWindow2[1]);
        int[] locationOnScreen2 = new int[2];
        textView.getLocationOnScreen(locationOnScreen2);
        LogUtils.d("RectActivity", "textView.getLocationOnScreen:" + locationOnScreen2[0]+","+locationOnScreen2[1]);

        Rect globalVisibleRect2 = new Rect();
        textView.getGlobalVisibleRect(globalVisibleRect2);
        LogUtils.d("RectActivity", "textView.getGlobalVisibleRect:" + globalVisibleRect2.toShortString());
    }
}
