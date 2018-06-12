package com.zyt.uxin.custonview;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zyt.uxin.custonview.customtextview.TestCanvasView;
import com.zyt.uxin.custonview.customtextview.TestCanvasView2;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class CustonViewActivity extends AppCompatActivity {

    TestCanvasView testCanvasView1;
    TestCanvasView testCanvasView2;
    TestCanvasView testCanvasView3;
    TestCanvasView testCanvasView4;
    TestCanvasView testCanvasView5;
    TestCanvasView testCanvasView6;
    TestCanvasView testCanvasView7;
    TestCanvasView2 testCanvasView8;
    TestCanvasView2 testCanvasView9;
    TestCanvasView2 testCanvasView10;
    TestCanvasView2 testCanvasView11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        testCanvasView1 =  findViewById(R.id.testCanvasView1);
        testCanvasView2 =  findViewById(R.id.testCanvasView2);
        testCanvasView3 =  findViewById(R.id.testCanvasView3);
        testCanvasView4 =  findViewById(R.id.testCanvasView4);
        testCanvasView5 =  findViewById(R.id.testCanvasView5);
        testCanvasView6 =  findViewById(R.id.testCanvasView6);
        testCanvasView7 =  findViewById(R.id.testCanvasView7);
        testCanvasView8 =  findViewById(R.id.testCanvasView8);
        testCanvasView9 =  findViewById(R.id.testCanvasView9);
        testCanvasView10 =  findViewById(R.id.testCanvasView10);
        testCanvasView11 =  findViewById(R.id.testCanvasView11);

        //\
        testCanvasView1.setDrawNumber(1);
        testCanvasView2.setDrawNumber(2);
        testCanvasView3.setDrawNumber(3);
        testCanvasView4.setDrawNumber(4);
        testCanvasView5.setDrawNumber(5);
        testCanvasView6.setDrawNumber(6);
        testCanvasView7.setDrawNumber(7);

        testCanvasView8.setDrawNumber(8);
        testCanvasView9.setDrawNumber(9);
        testCanvasView10.setDrawNumber(10);
        testCanvasView11.setDrawNumber(11);
    }
}
