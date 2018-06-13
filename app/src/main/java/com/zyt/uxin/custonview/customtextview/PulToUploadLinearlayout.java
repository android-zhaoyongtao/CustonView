package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * 滑动超过阈值就跳的LinearLayout
 * Created by zhaoyongtao on 2017/11/30.
 */
public class PulToUploadLinearlayout extends LinearLayout {
    Runnable runnable;

    public void setOnPullToThresholdListener(Runnable runnable) {
        this.runnable = runnable;
    }

    private static final long ANIM_TIME = 200;
    //滑到多大就回调,阈值
    private static final float THRESHOLD = 200f;

    private View recyclerView;

    // 记录recyclerview原始的布局位置
    private Rect originalRect = new Rect();
    // 在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;
    //是不是一次滑到阈值回调
    private boolean oneceMoved = false;
    // 如果按下时的X值
    private float startX;

    //阻尼指数
    private static final float OFFSET_RADIO = 0.5f;

    private boolean isRecyclerReuslt = false;
//    int mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));

    public PulToUploadLinearlayout(Context context) {
        super(context);
    }

    public PulToUploadLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PulToUploadLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        originalRect.set(recyclerView.getLeft(), recyclerView.getTop(), recyclerView.getRight(), recyclerView.getBottom());
    }

    /**
     * 加载布局后初始化,这个方法会在加载完布局后调用
     */
    @Override
    protected void onFinishInflate() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RecyclerView) {
                if (recyclerView == null) {
                    recyclerView = childAt;
                } else {
                    throw new RuntimeException("只能有一个RecyclerView");
                }
            }
        }

        if (recyclerView == null) {
            throw new RuntimeException("必须要有一个RecyclerView");
        }
        super.onFinishInflate();
    }

    /**
     * 位置还原
     */
    private void recoverLayout() {
        if (!isMoved) {
            return;//如果没有移动布局，则跳过执行
        }
        TranslateAnimation anim = new TranslateAnimation(recyclerView.getRight() - originalRect.right, 0, 0, 0);
        anim.setDuration(ANIM_TIME);
        recyclerView.startAnimation(anim);
        recyclerView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
        isMoved = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;  //不拦截  直接传递给子的view
    }

    private boolean isCanPull() {
        final RecyclerView.Adapter adapter = ((RecyclerView) recyclerView).getAdapter();
        if (null == adapter) {
            return true;
        }
        final int lastItemPosition = adapter.getItemCount() - 1;//最后是哪个
        final int lastVisiblePosition = ((LinearLayoutManager) ((RecyclerView) recyclerView).getLayoutManager()).findLastVisibleItemPosition();//可见的是哪个

        if (lastVisiblePosition >= lastItemPosition) {//是不是到尾了
            final int childIndex = lastVisiblePosition - ((LinearLayoutManager) ((RecyclerView) recyclerView).getLayoutManager()).findFirstVisibleItemPosition();
            final int childCount = ((RecyclerView) recyclerView).getChildCount();
            final int index = Math.max(childIndex, childCount - 1);
            final View lastVisibleChild = ((RecyclerView) recyclerView).getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getRight() <= recyclerView.getRight() - recyclerView.getLeft();
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (recyclerView == null) {
            return super.dispatchTouchEvent(ev);
        }

        boolean isTouchOutOfScrollView = ev.getX() > originalRect.right || ev.getX() < originalRect.left; //如果当前view的X上的位置
        if (isTouchOutOfScrollView) {//如果不在view的范围内
            if (isMoved) {      //当前容器已经被移动
                recoverLayout();
            }
            isRecyclerReuslt = false;
            return true;
        }

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //记录按下时的X
                startX = ev.getX();
                oneceMoved = true;
                isRecyclerReuslt = true;
                return super.dispatchTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                float scrollX = (ev.getX() - startX);
                if (oneceMoved && scrollX < -5 && isCanPull()) {//系统获取值太大,会造成划不动
                    int offset = (int) (scrollX * OFFSET_RADIO);//不会阻尼计算便宜多少合适...
                    recyclerView.layout(originalRect.left + offset, originalRect.top, originalRect.right + offset, originalRect.bottom);
                    isMoved = true;
                    isRecyclerReuslt = false;
                    if (Math.abs(scrollX) > THRESHOLD) {//过阈值了
//                        Prompt.showToast("scrollX<-150");
                        if (runnable != null) {
                            runnable.run();
                        }
                        oneceMoved = false;
                        recoverLayout();
                    }
                    return true;
                } else {
                    startX = ev.getX();
                    isMoved = false;
                    isRecyclerReuslt = true;
                    recoverLayout();
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL://有时候ACTION_UP不起来...
                recoverLayout();
                if (isRecyclerReuslt) {
                    return super.dispatchTouchEvent(ev);
                } else {
                    return true;
                }
            default:
                return super.dispatchTouchEvent(ev);
        }
    }

}