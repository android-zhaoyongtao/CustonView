package com.zyt.uxin.custonview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zyt.uxin.custonview.customtextview.MapView;

public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        initView();
    }

    /**
     * 整个动画被拆分成为三个部分
     * 1、绕Y轴3D旋转45度
     * 2、绕Z轴3D旋转270度
     * 3、不变的那一半（上半部分）绕Y轴旋转30度（注意，这里canvas已经旋转了270度，计算第三个动效参数时要注意）
     */
    private void initView() {
        mapView = findViewById(R.id.map_layout);

        int duration = 2000;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mapView, "degreeY", 0, -45);
        animator1.setDuration(duration);
        animator1.setStartDelay(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mapView, "degreeZ", 0, 360);
        animator2.setDuration(duration);
        animator2.setStartDelay(500);

        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator2);
        set.start();
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.reset();
                set.start();
            }
        });
    }

}
