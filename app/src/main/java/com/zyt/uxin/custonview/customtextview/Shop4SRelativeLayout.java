package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zyt.uxin.custonview.utiles.LogUtils;

/**
 * Created by zhaoyongtao on 2018/1/18.
 */

public class Shop4SRelativeLayout extends RelativeLayout {
    public Shop4SRelativeLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public Shop4SRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public Shop4SRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Shop4SRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        LogUtils.d("Shop4SRelativeLayout", "width" + width + ";height:" + height);
//        paint.setColor(Color.argb(0,245,245,245));
        paint.setColor(Color.parseColor("#F5F5F5"));//#EBEBEB  #F6F6F6  ff7800
        paint.setShader(null);
        canvas.drawRect(0, 0, width, height, paint);

//        Path path2 = new Path();
//        path2.moveTo(width/2+50,0);
//        path2.rQuadTo(width/2-50,height,width*3/4,height);
//        path2.lineTo(width,height);
//        path2.lineTo(width,0);
//        path2.close();
//
//        paint.setColor(Color.GREEN);
//        canvas.drawPath(path2,paint);
//        canvas.save();
//        Path path2 = new Path();
//        path2.addArc(new RectF(width/2,-2*height,2*width,3*height/2),90,90);

        paint.setShader(new LinearGradient(width / 2, height, width, 0, Color.parseColor("#ff7800"), Color.parseColor("#F6F6F6"), Shader.TileMode.CLAMP));
//        canvas.clipPath(path2);
        canvas.drawArc(new RectF(width / 2, -2 * height, 2 * width, 3 * height / 2), 90, 90, true, paint);
//        canvas.restore();
        super.onDraw(canvas);
    }
}
