package com.zyt.uxin.custonview.measure;

import android.content.Context;
import android.icu.util.Measure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SameWidthHeightImageview extends android.support.v7.widget.AppCompatImageView {

    public SameWidthHeightImageview(Context context) {
        super(context);
    }

    public SameWidthHeightImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SameWidthHeightImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //需求:宽高一样的imageview
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//测量赋值
//        int measuredWidth = getMeasuredWidth();//取值
//        int measuredHeight = getMeasuredHeight();
//        if (measuredWidth>measuredHeight) {//修改值
//            measuredWidth =measuredHeight;
//        }else {
//            measuredHeight =measuredWidth;
//        }
//        setMeasuredDimension(measuredWidth,measuredHeight);//保存值
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec压缩数据,尺寸类型mode,尺寸数值size
        //需求:全部自己计算
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//不调用这个
        //需要遵守父view传递进来的widthMeasureSpec等限制
        //android:layout_width="300dp"这类事给它的父view看得
        //计算自己的宽呀高呀
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int mywidth = 300;
        int finalwidth = resolveSize(mywidth, widthMeasureSpec);
        setMeasuredDimension(finalwidth, heightMeasureSpec);
    }
}
