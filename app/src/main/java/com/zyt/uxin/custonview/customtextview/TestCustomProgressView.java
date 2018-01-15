package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class TestCustomProgressView extends android.support.v7.widget.AppCompatTextView {
    int progress2 = 0;
    // 创建 getter 方法
    public int getProgress2() {
        return progress2;
    }

    // 创建 setter 方法
    public void setProgress2(int progress) {
        this.progress2 = progress;
        setText(String.valueOf(progress));
//        invalidate();
    }

    public TestCustomProgressView(Context context) {
        this(context, null);
    }

    public TestCustomProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }
}
