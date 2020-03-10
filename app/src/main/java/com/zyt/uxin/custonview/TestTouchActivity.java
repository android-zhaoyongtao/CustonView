package com.zyt.uxin.custonview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class TestTouchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup);

    }

    public void button1Click(View view) {
        Log.e("zhao6", "button1Click");
    }
    /*
    onTouchEvent(MotionEvent event) 中Down事件return true的view将执行后续所有事件
    button的click事件是在onTouchEvent的UP事件下触发，
    将焦点移出button view后再MOVE中将会setPress false将标识改变，然后UP中的click事件将会被拦截
    longClick事件是postDealy机制
     */
}
