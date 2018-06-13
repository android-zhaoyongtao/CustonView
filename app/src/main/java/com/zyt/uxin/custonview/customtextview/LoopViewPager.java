package com.zyt.uxin.custonview.customtextview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;


import com.zyt.uxin.custonview.R;

import java.util.ArrayList;

/**
 * viewpager 包一层，添加了对应展示第几张的标识，并且可以无限滑动
 */

public class LoopViewPager extends FrameLayout {

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "LoopViewPager";
    private static final boolean DEBUG = true;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int MIN_DISTANCE_FOR_FLING = 200;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };
    private int mOffsetLimit = 1;
    private int mCurrentPos;
    private NextRunnable runnable;
    private PagerAdapter mAdapter;
    private VelocityTracker mVelocityTracker;
    private ArrayList<ViewPager.OnPageChangeListener> mListener;
    private float mDownX;
    private float mDownY;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mFlingDistance;
    private Scroller mScroller;
    private int mState = SCROLL_STATE_IDLE;
    private Drawable mDotChosed;
    private Drawable mDotNormal;
    private int mMarginDot;
    private int mMinShow = 2;//有两个才显示，1个点太奇怪了
    private FrameLayout.LayoutParams mParamsDot;
    private int mDotTop;
    private int mDotLeft;
    private DataSetObserver mObserever = new DataSetObserver() {
        @Override
        public void onChanged() {
            removeAllViews();
            populate(mCurrentPos);
            postDelayed(runnable, runnable.mDelay);
        }
    };

    public LoopViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        runnable = new NextRunnable(this);
        mScroller = new Scroller(getContext(), sInterpolator);
        final float density = getResources().getDisplayMetrics().density;
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
        mDotChosed = getContext().getResources().getDrawable(R.drawable.action_index_bj);
        mDotNormal = getContext().getResources().getDrawable(R.drawable.action_index_deg_bj);
        mDotNormal.setBounds(0, 0, mDotNormal.getMinimumWidth(), mDotNormal.getMinimumHeight());
        mDotChosed.setBounds(0, 0, mDotChosed.getMinimumWidth(), mDotChosed.getMinimumHeight());
        mMarginDot = mDotNormal.getMinimumWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDotNormal != null && mDotChosed != null && mAdapter != null) {
            if (mParamsDot == null) {
                final float density = getResources().getDisplayMetrics().density;
                mParamsDot = generateDotLayoutParams((int) (density * 10));
            }
            mDotTop = (mParamsDot.gravity & Gravity.TOP) == Gravity.TOP ? mParamsDot.topMargin : ((mParamsDot.gravity & Gravity.BOTTOM) == Gravity.BOTTOM ? getMeasuredHeight() - mParamsDot.bottomMargin - mDotNormal.getBounds().height() : (getMeasuredHeight() - mDotNormal.getBounds().height()) / 2);
            mDotLeft = (mParamsDot.gravity & Gravity.LEFT) == Gravity.LEFT ? mParamsDot.leftMargin : ((mParamsDot.gravity & Gravity.RIGHT) == Gravity.RIGHT ? getMeasuredWidth() - mParamsDot.leftMargin - mDotNormal.getBounds().width() : (getMeasuredWidth() - (mDotNormal.getBounds().width() + mMarginDot) * (mAdapter.getCount() - 1) - mDotChosed.getBounds().width()) / 2);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int parentWidth = right - left;
        int parentHeight = bottom - top;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();
            int childLeft = lp.layout * parentWidth + ((lp.gravity & Gravity.LEFT) != 0 ? lp.leftMargin : parentWidth - width - lp.rightMargin);
            int childTop = (lp.gravity & Gravity.TOP) != 0 ? lp.topMargin : parentHeight - height - lp.topMargin;
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
        scrollTo(mCurrentPos * parentWidth, 0);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mDotChosed != null && mDotNormal != null && mAdapter != null && mAdapter.getCount() >= mMinShow) {
            canvas.save();
            canvas.translate(getScrollX() + mDotLeft, mDotTop);
            for (int i = 0; i < mAdapter.getCount(); i++) {
                if (i == mCurrentPos) {
                    mDotChosed.draw(canvas);
                    canvas.translate(mDotChosed.getMinimumWidth() + mMarginDot, 0);
                } else {
                    mDotNormal.draw(canvas);
                    canvas.translate(mDotNormal.getMinimumWidth() + mMarginDot, 0);
                }
            }
            canvas.restore();
        }
    }

    /**
     * 自动循环播放
     *
     * @param delay，下一张的时间间隔，如果小于等于0取消自动播放
     */
    public void setAutoNext(final int delay) {
        postDelayed(runnable, runnable.mDelay = delay);
    }


    public void setDot(Drawable dotChosed, Drawable dotNormal) {
        this.mDotChosed = dotChosed;
        this.mDotNormal = dotNormal;
    }

    public void setDotLayoutParams(FrameLayout.LayoutParams params) {
        this.mParamsDot = params;
    }

    public void setMarginDot(int mMarginDot) {
        this.mMarginDot = mMarginDot;
    }

    public PagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(PagerAdapter adapter) {
        if (mAdapter != null) {
            removeAllViews();
            mAdapter.unregisterDataSetObserver(mObserever);
        }
        if (adapter != null) {
            mAdapter = adapter;
            mAdapter.registerDataSetObserver(mObserever);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter = null;
        }
    }

    public void showDot(FrameLayout.LayoutParams params) {

    }

    public int getOffscreenPageLimit() {
        return mOffsetLimit;
    }

    public void setOffscreenPageLimit(int limit) {
        mOffsetLimit = limit;
        mAdapter.notifyDataSetChanged();
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (mListener == null) {
            mListener = new ArrayList<ViewPager.OnPageChangeListener>();
        }
        mListener.add(listener);
    }

    public void clearOnPageChangeListeners() {
        if (mListener != null) {
            mListener.clear();
        }
    }


    public int getCurrentItem() {
        return mCurrentPos;
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    public void setCurrentItem(final int item, final boolean smoothScroll) {
        final int width = getWidth();
        if (width == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    setCurrentItemInner(smoothScroll, item * getWidth());
                }
            });
        } else {
            setCurrentItemInner(smoothScroll, item * width);
        }

    }

    private void setCurrentItemInner(boolean smoothScroll, int x) {
        if (smoothScroll) {
            smoothScrollTo(x);
        } else {
            scrollTo(x, 0);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean touchEvent = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(runnable);
                mVelocityTracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                postDelayed(runnable, runnable.mDelay);
                break;
        }
        return touchEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = Math.abs(ev.getRawX() - mDownX);
                float diffY = Math.abs(ev.getRawY() - mDownY);
                if (mAdapter != null && mAdapter.getCount() > 1 && diffX > mTouchSlop && diffX > diffY) {//只有一个 item 不要滑动， 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    if (mState == SCROLL_STATE_SETTLING) {//还没有停止，又按下了
                        setScrollState(SCROLL_STATE_IDLE);
                        mScroller.abortAnimation();
                        int newPos = mScroller.getFinalX() / getWidth();
                        if (newPos != mCurrentPos) {
                            populate(newPos);
                            setPageSelected(mCurrentPos);
                        }
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                scrollTo((int) (mDownX - event.getRawX() + mCurrentPos * getWidth()), 0);
                setScrollState(SCROLL_STATE_DRAGGING);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(mVelocityTracker, 0);
                if (Math.abs(mDownX - event.getRawX()) > mFlingDistance || Math.abs(initialVelocity) > mMinimumVelocity) {
                    if (mDownX < event.getRawX()) {
                        smoothScrollTo((mCurrentPos - 1) * getWidth());
                    } else {
                        smoothScrollTo((mCurrentPos + 1) * getWidth());
                    }
                } else {
                    smoothScrollTo(mCurrentPos * getWidth());
                }
                break;
        }
        return true;
    }

    private void populate(int newPos) {
        if (mAdapter == null) {
            return;
        }
        int count = mAdapter.getCount();
        if (count > 0) {
            if (DEBUG) {
                Log.i(TAG, mCurrentPos + " newpos " + newPos + "   populate  " + hashCode());
            }
            if (newPos <= -1) {
                mCurrentPos = count - 1;
            } else if (newPos >= count) {
                mCurrentPos = 0;
            } else {
                mCurrentPos = newPos;
            }
            int offsetLimit = Math.max(0, Math.min(count - 1, mOffsetLimit));
            int fist = mCurrentPos - offsetLimit;
            int last = mCurrentPos + offsetLimit;
            int childCount = getChildCount();
            ArrayList<LayoutParams> list = new ArrayList<LayoutParams>();
            SparseArray<View> cache = new SparseArray<View>();
            for (int i = 0; i < childCount; i++) {//如果是第一个和最后一个之间 就缓存上，其他的已经不需要缓存了
                View view = getChildAt(i);
                LayoutParams layoutParmas = (LayoutParams) view.getLayoutParams();
                if (layoutParmas.index < fist || layoutParmas.index > last) {
                    list.add(layoutParmas);
                } else {
                    cache.put(layoutParmas.index, view);
                }
            }
            for (LayoutParams layoutParams : list) {
                mAdapter.destroyItem(this, layoutParams.index, layoutParams.obj);
            }

            for (int i = fist, j = 0; i <= last; i++, j++) {//
                if (i < 0) {
                    View itemInfo = cache.get(i + count);
                    LayoutParams params;
                    if (itemInfo == null) {
                        Object res = mAdapter.instantiateItem(this, i + count);
                        params = (LayoutParams) getChildAt(getChildCount() - 1).getLayoutParams();
                        params.obj = res;
                    } else {
                        params = (LayoutParams) itemInfo.getLayoutParams();
                    }
                    params.index = i + count;//i=-1 当是0的时候，缓存前一个，index 是 adapter 的最后一个
                    params.layout = i;//i=-1  记录布局所在的位置
                } else if (i >= count) {
                    View itemInfo = cache.get(i);
                    LayoutParams params;
                    if (itemInfo == null) {
                        Object res = mAdapter.instantiateItem(this, i - count);
                        params = (LayoutParams) getChildAt(getChildCount() - 1).getLayoutParams();
                        params.obj = res;
                    } else {
                        params = (LayoutParams) itemInfo.getLayoutParams();
                    }
                    params.index = i - count;
                    params.layout = i;
                } else {
                    View itemInfo = cache.get(i);
                    LayoutParams params;
                    if (itemInfo == null) {
                        Object res = mAdapter.instantiateItem(this, i);
                        params = (LayoutParams) getChildAt(getChildCount() - 1).getLayoutParams();
                        params.obj = res;
                    } else {
                        params = (LayoutParams) itemInfo.getLayoutParams();
                    }
                    params.index = i;
                    params.layout = i;
                }
            }
            requestLayout();
        }
    }

    private void smoothScrollTo(int x) {
        if (DEBUG) {
            Log.i(TAG, mCurrentPos + "   smoothScrollTo  " + hashCode());
        }
        mScroller.startScroll(getScrollX(), 0, x - getScrollX(), 0);
        setScrollState(SCROLL_STATE_SETTLING);
        invalidate();
    }

    public void setScrollState(int state) {
        mState = state;
        if (mListener != null) {
            for (ViewPager.OnPageChangeListener onPageChangeListener : mListener) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }

    @Override
    public void computeScroll() {
        int width = getWidth();
        if (SCROLL_STATE_SETTLING == mState && width > 0) {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                if (mListener != null) {
                    int dx = mScroller.getFinalX() - getScrollX();
                    for (ViewPager.OnPageChangeListener onPageChangeListener : mListener) {
                        if (dx < 0) {
                            onPageChangeListener.onPageScrolled(mCurrentPos - 1, -dx / width, -dx);
                        } else {
                            onPageChangeListener.onPageScrolled(mCurrentPos, dx / width, dx);
                        }
                    }
                }
                invalidate();
            } else {
                setScrollState(SCROLL_STATE_IDLE);
                int newPos = getScrollX() / width;
                if (newPos != mCurrentPos) {
                    populate(newPos);
                    setPageSelected(mCurrentPos);
                }
            }
        }
    }

    private void setPageSelected(int newPos) {
        if (mListener != null) {
            for (ViewPager.OnPageChangeListener onPageChangeListener : mListener) {
                onPageChangeListener.onPageSelected(newPos);
            }
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            postDelayed(runnable, runnable.mDelay);
        } else {
            removeCallbacks(runnable);
        }
    }

    public void setLoop(boolean loop) {
        if (loop) {
            postDelayed(runnable, runnable.mDelay);
        } else {
            removeCallbacks(runnable);
        }
    }

    public FrameLayout.LayoutParams generateDotLayoutParams(int density) {
        FrameLayout.LayoutParams paramsDot = new FrameLayout.LayoutParams(-2, -2);
        paramsDot.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        paramsDot.bottomMargin = density;
        return paramsDot;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, -1, params);//每次都添加到最后一个 view
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        private Object obj;
        private int index;
        private int layout;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }

    private static class NextRunnable implements Runnable {
        private final LoopViewPager vp;
        private int mDelay;

        public NextRunnable(LoopViewPager loopViewPager) {
            this.vp = loopViewPager;
        }

        @Override
        public void run() {
            vp.removeCallbacks(this);
            if (mDelay == 0 || vp.mAdapter == null || vp.mAdapter.getCount() <= 1 || (vp.getContext() instanceof Activity && ((Activity) vp.getContext()).isFinishing())) {//如果没有任何引用了，证明 activity 销毁了，停止循环
                return;
            }
            vp.setCurrentItem(vp.mCurrentPos + 1, true);
            vp.postDelayed(this, mDelay);
            if (DEBUG) {
                Log.i(TAG, "run  " + hashCode() + "  " + vp.hashCode());
            }
        }
    }

}
