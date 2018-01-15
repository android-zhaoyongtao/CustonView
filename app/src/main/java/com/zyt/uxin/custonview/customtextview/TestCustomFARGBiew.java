package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TestCustomFARGBiew extends View {
    int color33 = Color.RED;//textcolor  BackgroundColor也类似于此属性
    // 创建 getter 方法
    public int getColor33() {
        return color33;
    }

    // 创建 setter 方法
    public void setColor33(int progress) {
        this.color33 = progress;
        invalidate();
    }

    public TestCustomFARGBiew(Context context) {
        this(context, null);
    }

    public TestCustomFARGBiew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCustomFARGBiew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);//设置线条宽度
    }
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(color33);
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,200,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),resolveSize(500,heightMeasureSpec));
    }
}
