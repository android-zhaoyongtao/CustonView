package com.zyt.uxin.custonview.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.zyt.uxin.custonview.R;

/**
 * 整个动画拆分成了三部分
 * <p>
 * Created by jiayuanbin on 2017-9-23.
 */

public class MapView extends View {

    //变的那一半,Y轴方向旋转角度
    private float degreeY;
    //Z轴方向（平面内）旋转的角度
    private float degreeZ;

    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;
    private DisplayMetrics displayMetrics;
    private float newZ;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MapView);
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.MapView_mv_background);
        a.recycle();

        if (drawable != null) {
            bitmap = drawable.getBitmap();
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flip_board);
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();

        this.displayMetrics = getResources().getDisplayMetrics();
        this.newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //画变换的一半
        //先旋转，再裁切，再使用camera执行3D动效,**然后保存camera效果**,最后再旋转回来
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);//画布移动到图中心点
        canvas.rotate(-degreeZ);//四,画布旋转
        canvas.clipRect(0, centerY, centerX, -centerY);//二,裁剪一半
        camera.rotateY(degreeY * (1 - degreeZ / 360));//三,相机沿y轴在旋转
        camera.applyToCanvas(canvas);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.rotate(degreeZ);//一,转过去个角度
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //画不变换的另一半
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, centerY, 0, -centerY);
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

    }

    /**
     * 启动动画之前调用，把参数reset到初始状态
     */
    public void reset() {
        degreeY = 0;
        degreeZ = 0;
    }


    @Keep
    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @Keep
    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

}
