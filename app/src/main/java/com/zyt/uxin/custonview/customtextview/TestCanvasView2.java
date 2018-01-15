package com.zyt.uxin.custonview.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.zyt.uxin.custonview.R;

//4-
//Paint 的四类方法：颜色类、效果类、文字绘制相关以及初始化类
public class TestCanvasView2 extends View {
    private int drawNumber = 0;

    public void setDrawNumber(int drawNumber) {
        this.drawNumber = drawNumber;
        requestLayout();
    }

    int customHeight = -1;
    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.cwf);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int bitmapWidth;
    int bitmapHeight;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);//设置颜色
        paint.setStrokeWidth(5);//设置线条宽度
        paint.setTextSize(50);//设置文字大小
        canvas.drawText("onDraw:" + drawNumber, 830, 80, paint);
        switch (drawNumber) {
            case 8:
                onDraw8(canvas);
                break;
            case 9:
                onDraw9(canvas);
                break;
            case 10:
                onDraw10(canvas);
                break;
            case 11:
                onDraw11(canvas);
                break;
            default:
                canvas.drawText(" default:↓", 700, 80, paint);
                break;
        }
        canvas.drawLine(200, 10, getMeasuredWidth(), 10, paint);
    }

    static String TAG = "TestCanvalView";

    public TestCanvasView2(Context context) {
        this(context, null);
    }

    public TestCanvasView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCanvasView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setStyle(Paint.Style.STROKE);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    //clipXXX  Matrix(矩阵) Camera
    @SuppressLint("NewApi")
    private void onDraw8(Canvas canvas) {
        //范围裁切有两个方法： clipRect() 和 clipPath()。裁切方法之后的绘制代码，都会被限制在裁切范围内。
        //1.1 clipRect()

//        canvas.save();
//        canvas.clipRect(100,100,600,700);
////        canvas.clipOutRect(100,100,300,300);
//        canvas.drawBitmap(bitmap,0,0,paint);
//        canvas.restore();
        Path path = new Path();
        path.moveTo(100, 150);
        path.arcTo(50, 50, 150, 150, -180, 90, false);
        path.lineTo(550, 50);
        path.arcTo(500, 50, 600, 150, -90, 90, false);
        path.lineTo(600, 750);
        path.arcTo(500, 700, 600, 800, 0, 90, false);
        path.lineTo(100, 800);
        path.arcTo(50, 700, 150, 800, 90, 90, false);
        path.lineTo(50, 100);
        path.close();

        canvas.save();
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
        //圆图还有drawpath,paint可以setbitmapshader
        //圆图还有drawroundrect
    }

    @SuppressLint("NewApi")
    private void onDraw9(Canvas canvas) {
        //    2 几何变换
//        使用 Canvas 来做常见的二维变换；
//        使用 Matrix 来做常见和不常见的二维变换；
//        使用 Camera 来做三维变换
        canvas.save();
        //此处如果有多个,应该到倒着写
//        canvas.translate(100, 200);
//        canvas.rotate(45,30,800);//px 和 py 是轴心的位置
//        canvas.scale(1.5f,1.3f,100,0);// px py 是放缩的轴心
        canvas.skew(0.5f, 0.5f);//sx 和 sy 是 x 方向和 y 方向的错切系数。
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

    }

    @SuppressLint("NewApi")
    private void onDraw10(Canvas canvas) {
//        使用 Matrix 来做变换
//        Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换；
//        Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas

        canvas.save();
        Matrix matrix = new Matrix();
//        matrix.postSkew(0.5f, 0.5f);
//        matrix.postTranslate(100, 200);
////        canvas.setMatrix(matrix);
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmap, 150, 150, paint);
        canvas.restore();
        //使用 Matrix 来做自定义变换
        canvas.save();
        float left = 100f, right = 600f, top = 100f, bottom = 900f;
        float[] pointsSrc = new float[]{left, top, right, top, left, bottom, right, bottom};
        float[] pointsDst = new float[]{left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};
        matrix.reset();
        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);//src 和 dst 是源点集合目标点集；srcIndex 和 dstIndex 是第一个点的偏移；pointCount 是采集的点的个数（个数不能大于 4，因为大于 4 个点就无法计算变换了）
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

    }

    private void onDraw11(Canvas canvas) {

        //使用 Camera 来做三维变换:旋转、平移、移动相机
        canvas.save();
        Camera camera = new Camera();
        camera.save();
        canvas.translate(bitmapWidth / 2, bitmapHeight / 2);//Canvas 的几何变换顺序是反的，所以要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面。
        camera.setLocation(0, 0, -30);//相机的默认位置是 (0, 0, -8)（英寸）。8 x 72 = 576，所以它的默认位置是 (0, 0, -576)（像素）
//        camera.rotateX(60);
        camera.rotate(60, 0, 0);
//        camera.translate(float x, float y, float z) 移动
        camera.applyToCanvas(canvas);
        canvas.translate(-bitmapWidth / 2, -bitmapHeight / 2);
        camera.restore();
        canvas.drawBitmap(bitmap, 10, 150, paint);
        canvas.restore();
        canvas.drawRect(10, 150, 10 + bitmapWidth, 150 + bitmapHeight, paint);//原来bitmap位置

    }

    /*绘制过程简述
    背景
    主体（onDraw()）
    子 View（dispatchDraw()）
    滑动边缘渐变和滑动条
    前景

    draw() 总调度方法
    public void draw(Canvas canvas) {
    ...
    drawBackground(Canvas); // 绘制背景（不能重写）
    onDraw(Canvas); // 绘制主体
    dispatchDraw(Canvas); // 绘制子 View
    onDrawForeground(Canvas); // 绘制滑动相关和前景
    ...
    }
    注意:1 在 ViewGroup 的子类中重写除 dispatchDraw() 以外的绘制方法时，可能需要调用 setWillNotDraw(false)；
    2 在重写的方法有多个选择时，优先选择 onDraw()。

    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (customHeight > 0) {
            setMeasuredDimension(widthMeasureSpec, resolveSize(customHeight, heightMeasureSpec));
        } else {
            setMeasuredDimension(widthMeasureSpec, resolveSize(1500, heightMeasureSpec));
        }
    }
}
