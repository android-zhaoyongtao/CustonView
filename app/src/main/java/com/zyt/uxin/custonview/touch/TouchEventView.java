package com.zyt.uxin.custonview.touch;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/*
触摸反馈
点击 触摸 滑动 本质原理
 */
public class TouchEventView extends ViewGroup {
    public TouchEventView(Context context) {
        super(context);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
    /*
    触摸事件分发:为了解决触摸冲突而设定的机制

     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //是事件分发的总的调度方法.控制着下面两个调用
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //再每个事件中都可以对事件流中进行监听,决定是否返回true
        //当接管事件(true)的时候会给子view发一个cancle事件
        //比如listview,在此,down事件return false,但是move事件 return true,实现滑动的拦截
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//event事件类型:按下,抬起,其他   坐标   其他各种信息
        //主要是down事件return true其他也会拦截,所以return false无影响,但还是统一吧.myfirsttargetview
        return super.onTouchEvent(event);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        //此方法是用来调用的,禁止父view再通过onintercepttouchevent()拦截,且递归,但仅限于当前事件流
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
