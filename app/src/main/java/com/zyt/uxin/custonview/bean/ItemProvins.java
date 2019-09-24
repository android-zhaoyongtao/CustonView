package com.zyt.uxin.custonview.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;


public class ItemProvins {
    private int color;
    private Point point;//文字中心点
    private Path path;//路径
    private RectF rectF;//矩形区域
    private boolean isNeedDraw;
    private String provinceName;//文字
    private Paint paintText;

    public ItemProvins(Path path) {
        this.path = path;
        initTextPaint();
    }

    public ItemProvins(Point point, Path path, String provinceName) {
        this.point = point;
        this.path = path;
        this.provinceName = provinceName;
        initTextPaint();
    }

    public ItemProvins(Path path, String provinceName, RectF rectf) {
        this.point = point;
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

//            RectF rectF = new RectF();
//            path.computeBounds(rectF, true);
            if (isSelected) {
                canvas.drawPath(path, paintText);

//                canvas.drawRect(rectF, paintText);
            }
            if (!TextUtils.isEmpty(provinceName)) {
//                LogUtils.e("name", rectF.left + "  " + rectF.top + "  " + rectF.right + "  " + rectF.bottom);
                float widthText = paintText.measureText(provinceName);
                //1 矩形中心点
//                canvas.drawText(provinceName, rectF.centerX() - widthText / 2, rectF.centerY() - paint.getStrokeWidth(), paintText);
                //2 xy所有点的平均值
                PointF center = getCenterPointByPath();
                canvas.drawText(provinceName, center.x - widthText / 2, center.y - paintText.getStrokeWidth(), paintText);
                //3 长*宽最大的地方
            }
        }/*else{
            RectF rectF = new RectF();
            path.computeBounds(rectF,true);
            if(isSelected){
                paint.clearShadowLayer();
                paint.setStrokeWidth(1);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(color);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.STROKE);
                int strokeColor = 0xFFD0E8F4;
                paint.setColor(strokeColor);
                canvas.drawPath(path, paint);
                canvas.drawRect(rectF,paintText);
            }
            if(!TextUtils.isEmpty(provinceName)){
                LogUtils.e("name",rectF.left+"  " +rectF.top+"  " +rectF.right+"  " +rectF.bottom);
                float widthText = paint.measureText(provinceName);
                canvas.drawText(provinceName,rectF.centerX() - widthText/2,rectF.centerY() - paint.getStrokeWidth(),paintText);
            }
        }*/

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
