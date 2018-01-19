package com.zyt.uxin.custonview.customtextview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import com.zyt.uxin.custonview.utiles.DpOrPxUtils;
import com.zyt.uxin.custonview.utiles.LogUtils;

/**
 * https://juejin.im/post/5a3213486fb9a0450407e52f
 * Created by zhaoyongtao on 2018/1/11.
 */

public class WaveProgressView extends View {
    private Paint textPaint;
    private Paint wavePaint;
    private Path wavePath;
    private float waveWidth;//宽度.px
    private float waveHeight;//高度
    private float shendu;//容器深度
    int totalwidth;
    int totalheisht;
    int waveSum = 5;
    int tempAdd = 0;
    private int mTouchSlop;
    private float downY;

    public float getShendu() {
        return shendu;
    }

    public void setShendu(float shendu) {
        this.shendu = shendu;
        LogUtils.d("WaveProgressView", shendu + "");
    }

    ValueAnimator valueAnimator;

    private void init(Context context) {
        totalwidth = 800;
        totalheisht = 1000;
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
//        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);
        wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        wavePaint.setColor(Color.GRAY);
        wavePaint.setShader(new LinearGradient(0, 0, 0, totalheisht, Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP));
        wavePath = new Path();
        waveWidth = DpOrPxUtils.dip2px(context, 50);
        waveHeight = DpOrPxUtils.dip2px(context, 20);


        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shendu = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = resolveSize(totalwidth, widthMeasureSpec);
        int measuredHeight = resolveSize(totalheisht, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
        waveSum = (int) ((measuredWidth / waveWidth) + 1) >> 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.save();
//        Path path = new Path();
//        RectF ovalRect = new RectF(0, 0, totalwidth, totalheisht);
//        path.addOval(ovalRect, Path.Direction.CW);
//        canvas.clipPath(path);
//        canvas.drawPath(getWavePath(), wavePaint);//此行  关键代码
//        canvas.restore();
////        canvas.drawPath(getWavePath2(), wavePaint);
//        canvas.drawOval(ovalRect, textPaint);
//        canvas.drawText("高度百分比:" + (int) shendu + " : " + totalheisht, 0, shendu, textPaint);
//    }


    private Bitmap bitmap;//缓存bitmap
    private Canvas bitmapCanvas;//
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里用到了缓存技术
        bitmap = Bitmap.createBitmap(totalwidth, totalheisht, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapCanvas.drawOval(0, 0, totalwidth, totalheisht, circlePaint);
        wavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//根据绘制顺序的不同选择相应的模式即可
        bitmapCanvas.drawPath(getWavePath(), wavePaint);
        wavePaint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, null);

//        RectF ovalRect = new RectF(0, 0, totalwidth, totalheisht);
//        canvas.drawOval(ovalRect,wavePaint);
//
//        wavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawPath(getWavePath(), wavePaint);//此行  关键代码
//        wavePaint.setXfermode(null);
    }



    private float waveMovingDistance;//波浪平移的距离

    public Path getWavePath() {
        wavePath.reset();
        wavePath.moveTo(0, shendu);
        wavePath.rMoveTo(-waveMovingDistance, 0);
        for (int i = 0; i < waveSum + tempAdd; i++) {
            wavePath.rQuadTo(waveWidth / 2, waveHeight, waveWidth, 0);// (x1,y1) 为控制点，(x2,y2)为结束点
            wavePath.rQuadTo(waveWidth / 2, -waveHeight, waveWidth, 0);
            //等同于wavePath.rCubicTo(waveWidth , 2*waveHeight,waveWidth , -2*waveHeight,2*waveWidth, 0);
        }

        wavePath.lineTo(totalwidth, totalheisht);
        wavePath.lineTo(0, totalheisht);
//        wavePath.lineTo(, totalheisht);
        wavePath.close();
        return wavePath;
    }

    //  public Path getWavePath2() {
//        wavePath.reset();
//        wavePath.moveTo(0, 3*waveHeight);
//        for (int i = 0; i < 5; i++) {
//            wavePath.rCubicTo(waveWidth , 2*waveHeight,waveWidth , -2*waveHeight,2*waveWidth, 0);
//        }
//        return wavePath;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d("WaveProgressView", "onTouchEvent-gety():" + event.getY());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs(event.getY() - downY);
                if (dy > mTouchSlop) {
                    downY = event.getY();
                    startanim(shendu, event.getY());
                } else {

                }
                break;
            case MotionEvent.ACTION_UP:
                startanim(shendu, event.getY());
                break;
            default:
                break;
        }
//        directlyTo(event.getY());
        return true;
    }

    private void startanim(float start, float end) {
        float durtion = Math.abs(2000 * (start - end) / totalheisht);//从头到底用2秒好了
        valueAnimator.setDuration((long) durtion);
        valueAnimator.setFloatValues(start, end);
        valueAnimator.reverse();
        valueAnimator.start();
    }

    private void directlyTo(float end) {
        shendu = end;
        invalidate();
    }

    public WaveProgressView(Context context) {
        super(context);
        init(context);
    }

    public WaveProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    public WaveProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.d("WaveProgressView", "onFinishInflate()");
//        WaveProgressAnim anim = new WaveProgressAnim();
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setDuration(5000);
//        anim.setRepeatMode(Animation.RESTART);
//        this.startAnimation(anim);

//        ValueAnimator valueAnimator = new ValueAnimator();
//        valueAnimator.setDuration(5000);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                waveMovingDistance = waveSum * waveWidth * (float) animation.getAnimatedValue();
//                tempAdd = 1 + (int) ((waveMovingDistance / waveWidth) + 1) >> 1;
//                postInvalidate();
//                LogUtils.d("WaveProgressView", "waveMovingDistance:" + waveMovingDistance);
//            }
//        });
//        valueAnimator.setFloatValues(0, 1f);
//        valueAnimator.start();

        ValueAnimator valueAnimator2 = new ValueAnimator();
        valueAnimator2.setDuration(1000);
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveMovingDistance = (float) animation.getAnimatedValue();
                tempAdd = 2;
                postInvalidate();
                LogUtils.d("WaveProgressView", "waveMovingDistance:" + waveMovingDistance);
            }
        });
        valueAnimator2.setFloatValues(0, waveWidth * 2);
        valueAnimator2.start();
    }

//    public class WaveProgressAnim extends Animation {
//        //省略部分代码...
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            super.applyTransformation(interpolatedTime, t);
//            //波浪高度到达最大值后就不需要循环了，只需让波浪曲线平移循环即可
//            LogUtils.d("WaveProgressView", "WaveProgressAnim-interpolatedTime:"+interpolatedTime);
//            waveMovingDistance = waveSum * waveWidth * interpolatedTime;
//            tempAdd = 1 + (int) ((waveMovingDistance / waveWidth) + 1) >> 1;
//            postInvalidate();
//        }
//    }
}
