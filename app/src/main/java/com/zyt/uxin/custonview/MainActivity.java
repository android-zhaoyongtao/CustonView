package com.zyt.uxin.custonview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addContentView();
        setContentView(R.layout.activity_main);
        findViewById(R.id.clickabletextview).setOnClickListener(this);
        findViewById(R.id.customview).setOnClickListener(this);
        findViewById(R.id.animatoractivity).setOnClickListener(this);
        findViewById(R.id.waveactivity).setOnClickListener(this);
        findViewById(R.id.rectactivity).setOnClickListener(this);
        findViewById(R.id.relativeactivity).setOnClickListener(this);
        findViewById(R.id.newviewactivity).setOnClickListener(this);
        findViewById(R.id.MapViewActivity).setOnClickListener(this);
        findViewById(R.id.DifferentSizeActivity).setOnClickListener(this);
        findViewById(R.id.VectorCarActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clickabletextview:
                startActivity(new Intent(this, CLicktextviewActivity.class));
                break;
            case R.id.customview:
                startActivity(new Intent(this, CustonViewActivity.class));
                break;
            case R.id.animatoractivity:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.waveactivity:
                startActivity(new Intent(this, WaveVIewActivity.class));
                break;
            case R.id.rectactivity:
                startActivity(new Intent(this, RectActivity.class));
                break;
            case R.id.relativeactivity:
                startActivity(new Intent(this, RelativeLayoutActivity.class));
                break;
            case R.id.newviewactivity:
                startActivity(new Intent(this, NewViewActivity.class));
                break;
            case R.id.MapViewActivity:
                startActivity(new Intent(this, MapViewActivity.class));
                break;
            case R.id.DifferentSizeActivity:
                startActivity(new Intent(this, DifferentSizeActivity.class));
                break;
            case R.id.VectorCarActivity:
                startActivity(new Intent(this, VectorCarActivity.class));
                break;
        }
    }
}
