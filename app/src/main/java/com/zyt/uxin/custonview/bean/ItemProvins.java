package com.zyt.uxin.custonview.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;


public class ItemProvins {
    private int color;//区域颜色
    private Path path;//路径
    private RectF rectF;//矩形区域
    private boolean isNeedDraw;//绘制开关
    private String provinceName;//区域文字
    private Paint paintText;//文字和选中边框的的画笔

//    public ItemProvins(Path path) {
//        this.path = path;
//        initTextPaint();
//    }
//
//    public ItemProvins( Path path, String provinceName) {
//        this.path = path;
//        this.provinceName = provinceName;
//        initTextPaint();
//    }

    public ItemProvins(Path path, String provinceName, RectF rectf) {
        this.path = path;
        this.provinceName = provinceName;
        this.rectF = rectf;
        initTextPaint();
    }

    private void initTextPaint() {
        paintText = new Paint();
        paintText.setColor(Color.GREEN);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setStrokeWidth(1);
        paintText.setTextSize(26);
    }

    public void drawChinaMap(Canvas canvas, Paint paint, boolean isSelected) {
        if (isNeedDraw) {
            paint.setColor(color);
            canvas.drawPath(path, paint);

            if (isSelected) {
                canvas.drawPath(path, paintText);
//                canvas.drawRect(rectF, paintText);//矩形区域
            }
            if (!TextUtils.isEmpty(provinceName)) {
//                LogUtils.e("name", rectF.left + "  " + rectF.top + "  " + rectF.right + "  " + rectF.bottom);
                float widthText = paintText.measureText(provinceName);
                //1 用矩形中心点
                canvas.drawText(provinceName, rectF.centerX() - widthText / 2, rectF.centerY() - paintText.getStrokeWidth(), paintText);
                //2 用xy所有点的平均值
//                PointF center = getCenterPointByPath();
//                canvas.drawText(provinceName, center.x - widthText / 2, center.y - paintText.getStrokeWidth(), paintText);
                //3 用长*宽最大的地方
            }
        }

    }


    //获取中心点
    private PointF getCenterPointByPath() {
        PathMeasure pathMeasure = new PathMeasure(path, true);
        int length = (int) (pathMeasure.getLength() - 0.5f);
        float sumX = 0f;
        float sumY = 0f;
        for (int i = 0; i < length; i++) {
            float[] coords = new float[]{0f, 0f};
            pathMeasure.getPosTan(i, coords, null);
            sumX += coords[0];
            sumY += coords[1];
        }
        return new PointF(sumX / length, sumY / length);
    }

    public void setDrawableColor(int color) {
        this.color = color;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isNeedDraw() {
        return isNeedDraw;
    }

    public void setNeedDraw(boolean needDraw) {
        isNeedDraw = needDraw;
    }

    public boolean handlerToucthOnclick(float rawX, float rawY) {
        boolean result = false;
//        RectF rectF = new RectF();
//        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        if (region.contains((int) rawX, (int) rawY)) {
            result = true;
        }
        return result;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
