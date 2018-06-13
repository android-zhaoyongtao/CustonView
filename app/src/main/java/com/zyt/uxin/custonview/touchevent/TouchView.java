package com.zyt.uxin.custonview.touchevent;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by zhaoyongtao on 2018/1/19.
 */

public class TouchView extends ViewGroup {
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private GestureDetectorCompat mDetector;//手势识别工具类。 有了这一神器，不需要对touch事件写一堆代码判断手势了。
    private VelocityTracker mVelocityTracker = null;//提供的用来记录滑动速度的一个类,可以监控手指移动的速度
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int actionMasked = event.getActionMasked();
        mDetector.onTouchEvent(event);//事件

        //VelocityTracker
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
//                Log.d("", "X velocity: " +
//                        VelocityTrackerCompat.getXVelocity(mVelocityTracker,
//                                pointerId));
//                Log.d("", "Y velocity: " +
//                        VelocityTrackerCompat.getYVelocity(mVelocityTracker,
//                                pointerId));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                break;
        }
        return super.onTouchEvent(event);
    }

    void init() {
//        mDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {  });
        mDetector = new GestureDetectorCompat(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;//最好都实现 onDown() 函数并且返回 true。这是因为所有的手势都是由 onDown() 消息开始的
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        mDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }


    @Override
    public void computeScroll() {//view的方法中
        Scroller mScroller = new Scroller(getContext(), new LinearInterpolator());
//            mScroller.startScroll(0, 0, 100, 100, 3000);
        //判断是否还在滚动，还在滚动为true
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //更新界面
            postInvalidate();
//                isMove = true;
        }
        //根据初始速度继续华东一段距离
//        mScroller.fling(int startX, int startY, int velocityX, int velocityY,int minX, int maxX, int minY, int maxY);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());//主要定义了UI中所使用到的标准常量
        viewConfiguration.getScaledTouchSlop();//获取触摸与滑动 的边界
        ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        viewConfiguration.getScaledMinimumFlingVelocity();//获取最小的滑动速度
    }
}
