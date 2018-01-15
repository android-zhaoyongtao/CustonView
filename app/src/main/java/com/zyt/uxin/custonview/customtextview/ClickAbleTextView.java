package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zyt.uxin.custonview.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * http://blog.csdn.net/lmj623565791/article/details/24252901
 * 1、自定义View的属性
 * 2、在View的构造方法中获得我们自定义的属性
 * [ 3、重写onMesure ]
 * 4、重写onDraw
 * 我把3用[]标出了，所以说3不一定是必须的，当然了大部分情况下还是需要重写的。
 * <p>
 * 1、自定义View的属性，首先在res/values/  下建立一个attrs.xml ， 在里面定义我们的属性和声明我们的整个样式。
 * 布局文件使用自己命名空间
 * 构造方法获取自定义属性
 */

public class ClickAbleTextView extends View {
    static String TAG = "ClickAbleTextView";

    public ClickAbleTextView(Context context) {
        this(context, null);
    }

    public ClickAbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public ClickAbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我自定义的样式属性
         *
         * @param context
         * @param attrs
         * @param defStyle
         */

//        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClickAbleTextView, defStyleAttr, 0);
//        int attr = a.getIndexCount();
//        switch (attr) {
//            case R.styleable.ClickAbleTextView_titleText:
//                mTitleText = a.getString(attr);
//                break;
//            case R.styleable.ClickAbleTextView_titleTextColor:
//                // 默认颜色设置为黑色
//                mTitleTextColor = a.getColor(attr, Color.BLACK);
//                break;
//            case R.styleable.ClickAbleTextView_titleTextSize:
//                // 默认设置为16sp，TypeValue也可以把sp转化为px
//                mTitleTextSize = a.getDimensionPixelSize(attr, 16);
//                break;
//        }
//        a.recycle();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClickAbleTextView);
        mTitleText = ta.getString(R.styleable.ClickAbleTextView_titleText);
        mTitleTextColor = ta.getColor(R.styleable.ClickAbleTextView_titleTextColor, Color.BLACK);
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.ClickAbleTextView_titleTextSize, 16);
        ta.recycle();
        /**
         * 获得绘制文本的宽和高-mBound
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
//        mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
        Log.d(TAG, "getTextBounds()-mBound:" + mBound.toString());
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
//                postInvalidate() 方法在非 UI 线程中调用，通知 UI 线程重绘。
//                invalidate() 方法在 UI 线程中调用，重绘当前 UI。
//                invalidate();
                postInvalidate();
            }
        });
    }

    private String randomText() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        int x1 = getWidth() / 2 - mBound.width() / 2;//5
        int x2=getPaddingLeft();//10
        int y1 = getHeight() / 2 + mBound.height() / 2;//136
        int y2 = getPaddingTop()+mBound.height();//136
        canvas.drawText(mTitleText, x2, y2, mPaint);//在绘制文字的时候把坐标填成 (0, 0)，文字并不会显示在 View 的左上角，而是会几乎完全显示在 View 的上方，到了 View 外部看不到的位置：
        Log.d(TAG, "getWidth():" + getWidth() + ";mBound.width():" + mBound.width() + ";getHeight():" + getHeight() + ";mBound.height():" + mBound.height());
        //getWidth():1080;mBound.width():321;getHeight():300;mBound.height():111
    }

    /*
    当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法”：
    重写之前先了解MeasureSpec的specMode,一共三种类型：
    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
    UNSPECIFIED：表示子布局想要多大就多大，很少使用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, heigh;//测量下宽高
        /*
        SpecMode三种模式：
        UNSPECIFIED：父容器没有给子控件任何限制，子View可以设置为任意大小。
        EXACTLY：父容器已经计算出子控件的具体尺寸（子控件设置了 match_parent 或 具体dp），这时候子控件的大小就等于 SpecSize 的值了
        AT_MOST：子控件没有设置具体大小（设置 wrap_content），所以父容器建议最大 SpecSize 给当前控件，如果当前空间不设置大小（调用 setMeasuredDimension(int width , int height)）方法，当前控件大小就是父容器建议最大 SpecSize
         */
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            width = getPaddingLeft() + mBound.width() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heigh = heightSize;
        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            heigh = getPaddingTop() + mBound.height() + getPaddingBottom();
        }
        int finalW = resolveSize( getPaddingLeft() + mBound.width() + getPaddingRight(),widthMeasureSpec);
        int finalH = resolveSize(getPaddingTop() + mBound.height() + getPaddingBottom(),heightMeasureSpec);

        setMeasuredDimension(width, heigh);
    }
}
