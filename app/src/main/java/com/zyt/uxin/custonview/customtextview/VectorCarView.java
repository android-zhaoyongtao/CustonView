package com.zyt.uxin.custonview.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zyt.uxin.custonview.bean.ItemProvins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VectorCarView extends View {
    private List<ItemProvins> list;//区域集合
    private static final int originalWidth = 1000;//标准宽度
    private static final int originalHeight = 1000;//标准高度
    private float scaleX = 1;//x缩放
    private float scaleY = 1;//y缩放
    private Paint paint;//画笔
    private ItemProvins itemProvins = null;//上一个区域
    private Paint drawText;//字的画笔

    public VectorCarView(Context context) {
        super(context);
        init(context);
    }

    public VectorCarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VectorCarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VectorCarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        list = new ArrayList<>();
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        drawText = new Paint();
        drawText.setStrokeWidth(5);
        drawText.setTextSize(16);
        drawText.setColor(Color.WHITE);
        drawText.setAntiAlias(true);
        drawText.setStyle(Paint.Style.FILL);
        parseMap();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100://
                    if (list == null) {
                        return;
                    }
                    for (final ItemProvins itemProvins : list) {
                        itemProvins.setDrawableColor(Color.parseColor(getRandColorCode()));
                        itemProvins.setNeedDraw(true);
                        requestLayout();
                        postInvalidate();
//                        executorService.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                itemProvins.setDrawableColor(Color.parseColor(getRandColorCode()));
//                                itemProvins.setNeedDraw(true);
//                                handler.sendEmptyMessage(200);
//                            }
//                        });
                    }

                    break;
                case 200:
                    int numNeedDraw = 0;
                    for (int i = 0; i < list.size(); i++) {
                        ItemProvins itemProvins = list.get(i);
                        if (itemProvins.isNeedDraw()) {
                            numNeedDraw++;
                        }
                    }
//                    LogUtils.e("handleMessage", ">>>>>> 111111" + " 需要绘制的集合的大小" + numNeedDraw);
                    requestLayout();
                    postInvalidate();
                    break;
            }
        }
    };

    private void parseMap() {
        Path path1 = new Path();
        path1.moveTo(200, 200);
        path1.lineTo(800, 200);
        path1.lineTo(900, 500);
        path1.lineTo(100, 500);
        path1.close();
        RectF rectf1 = new RectF();
        path1.computeBounds(rectf1, true);
        list.add(new ItemProvins(path1, "一", rectf1));

        Path path2 = new Path();
        path2.moveTo(100, 500);
        path2.lineTo(900, 500);
        path2.lineTo(900, 800);
        path2.lineTo(100, 800);
        path2.close();
        RectF rectf2 = new RectF();
        path2.computeBounds(rectf2, true);
        list.add(new ItemProvins(path2, "二", rectf2));
        handler.sendEmptyMessage(100);
//        new Thread(new Runnable() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void run() {
//                float left = -1;
//                float top = -1;
//                float right = -1;
//                float bottom = -1;
//                //数显拿不到资源文件流
//                try {
//                    InputStream is = getResources().openRawResource(R.raw.china2);
//                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
//                    Document document = builder.parse(is);
//                    Element element = document.getDocumentElement();
//                    NodeList pathList = element.getElementsByTagName("path");
//                    for (int i = 0; i < pathList.getLength(); i++) {
//                        Element itemElement = (Element) pathList.item(i);
//                        String pathData = itemElement.getAttribute("d");
//                        String provinceName = itemElement.getAttribute("title");
//                        Path path = PathParser.createPathFromPathData(pathData);
////                        Path path = new Path();
////                        PathParser.PathDataNode[] nodes = PathParser.createNodesFromPathData(pathData);
////                        Point point = null;
////                        if (nodes != null) {
////                            try {
////                                PathParser.PathDataNode.nodesToPath(nodes, path);
////                            } catch (RuntimeException e) {
////                                throw new RuntimeException("Error in parsing " + pathData, e);
////                            }
////                            point = getCenterPointByNodes(nodes);
////                        }
//                        ItemProvins itemProvins = new ItemProvins(path, provinceName);
//                        list.add(itemProvins);
//                        RectF rectf = new RectF();
//                        path.computeBounds(rectf, true);
//                        left = left == -1 ? rectf.left : Math.min(left, rectf.left);
//                        top = left == -1 ? rectf.top : Math.min(top, rectf.top);
//                        right = left == -1 ? rectf.right : Math.max(right, rectf.right);
//                        bottom = left == -1 ? rectf.bottom : Math.max(bottom, rectf.bottom);
//                    }
//                    //生成一个大的矩形
//                    rectF = new RectF(left, top, right, bottom);
//                    handler.sendEmptyMessage(100);
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }


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
            scaleX = (float) (width - getPaddingLeft() - getPaddingRight()) / originalWidth;
        } else {
            width = getPaddingLeft() + originalWidth + getPaddingRight();
            scaleX = 1;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heigh = heightSize;
            scaleY = (float) (heigh - getPaddingTop() - getPaddingBottom()) / originalHeight;
        } else {
            heigh = getPaddingTop() + originalHeight + getPaddingBottom();
            scaleY = 1;
        }
        Log.e("zhaoy", "scaleX = " + scaleX);
        //等同于
//        int finalW = resolveSize( getPaddingLeft()   + getPaddingRight(),widthMeasureSpec);
//        int finalH = resolveSize(getPaddingTop()   + getPaddingBottom(),heightMeasureSpec);
        setMeasuredDimension(width, heigh);
    }

    //解析svg
    //进行绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.save();
//        LogUtils.e("yyh", "scale: " + scale + "   集合的大小： " + list.size());
        canvas.scale(scaleX, scaleY);
        try {
            if (list.size() > 0) {
                for (ItemProvins itemProvins : list) {
                    if (itemProvins.isNeedDraw()) {
                        itemProvins.drawChinaMap(canvas, paint, false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (itemProvins != null) {
            itemProvins.drawChinaMap(canvas, paint, true);
        }
//        if (rectF != null) {
//            String noteTip = "中国海域";
//            String noteTip2 = "该demo仅为学习android使用，我很爱国的";
//            float x = rectF.centerX() - drawText.measureText(noteTip) / 2;
//            float x2 = rectF.centerX() - drawText.measureText(noteTip2) / 2;
//            float y = rectF.bottom + 50;
//            canvas.drawText(noteTip, x, y, drawText);
//            canvas.drawText(noteTip2, x2, y + 50, drawText);
//        }
    }

    /**
     * 获取十六进制的颜色代码.例如 "#6E36B4" , For HTML ,
     *
     * @return String
     */
    private String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
//        LogUtils.e("");
        return "#" + r + g + b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handlerToucthOnclick(event.getX(), event.getY());
        return super.onTouchEvent(event);

    }

    private void handlerToucthOnclick(float x, float y) {
        if (list == null) {
            return;
        }

        for (ItemProvins item : list) {
            if (item.handlerToucthOnclick(x / scaleX, y / scaleY)) {
                itemProvins = item;
                break;
            }
        }
        postInvalidate();
    }
}
