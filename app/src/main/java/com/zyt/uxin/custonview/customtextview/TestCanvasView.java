package com.zyt.uxin.custonview.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.Locale;

//1-3
//Paint 的四类方法：颜色类、效果类、文字绘制相关以及初始化类
//Android中moveTo、lineTo、quadTo、cubicTo、arcTo详解（实例）
public class TestCanvasView extends View {
    private int drawNumber = 0;

    public void setDrawNumber(int drawNumber) {
        this.drawNumber = drawNumber;
        requestLayout();
    }

    int customHeight = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);//设置颜色
        paint.setStrokeWidth(5);//设置线条宽度
        paint.setTextSize(50);//设置文字大小
        canvas.drawText("↓ onDraw:" + drawNumber, 830, 80, paint);
        switch (drawNumber) {
            case 1:
                onDraw1(canvas);
                break;
            case 2:
                onDraw2(canvas);
                break;
            case 3:
                onDraw3(canvas);
                break;
            case 4:
                onDraw4(canvas);
                break;
            case 5:
                onDraw5(canvas);
                break;
            case 6:
                onDraw6(canvas);
                break;
            case 7:
                onDraw7(canvas);
                break;
            case 8:
                customHeight = 200;
                break;
            case 9:
                customHeight = 200;
                break;
            case 10:
                customHeight = 200;
                break;
            default:
                canvas.drawText(" default:↓", 700, 80, paint);
                break;
        }
        canvas.drawLine(200, 10, getMeasuredWidth(), 10, paint);

    }

    static String TAG = "TestCanvalView";

    public TestCanvasView(Context context) {
        this(context, null);
    }

    public TestCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //demo1
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw1(Canvas canvas) {
        //Paint 类的几个最常用的方法。具体是：
        Paint paint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);//设置绘制模式
        paint.setColor(Color.GREEN);//设置颜色
        paint.setStrokeWidth(5);//设置线条宽度
        paint.setTextSize(20);//设置文字大小
        paint.setAntiAlias(true);//设置抗锯齿开关
        paint.setStrokeCap(Paint.Cap.SQUARE);//线头形状有三种
        canvas.drawCircle(300, 300, 200, paint);
        canvas.drawOval(400, 50, 700, 200, paint);//椭圆
        RectF rf = new RectF(100, 500, 600, 900);
        canvas.drawRoundRect(rf, 50, 25, paint);
        canvas.drawArc(rf, 90, 180, true, paint);//弧形

        Path path = new Path();//Path 可以描述直线、二次曲线、三次曲线、圆、椭圆、弧形、矩形、圆角矩形。把这些图形结合起来，就可以描述出很多复杂的图形
//        path.addArc(200,200,400,400,-225,225);
//        path.arcTo(400, 200, 600, 400, -180, 225, false);
//        path.lineTo(400, 542);

        path.rLineTo(300, 300);//相对坐标
//        path.rQuadTo(x1,y1,x2,y2);//绘制圆滑曲线，即贝塞尔曲线。(x1,y1) 为控制点，(x2,y2)为结束点。
        //mPath.cubicTo(x1, y1, x2, y2, x3, y3) (x1,y1) 为控制点，(x2,y2)为控制点，(x3,y3) 为结束点。

//        path.moveTo(500,500);
        path.lineTo(700, 500);

//        path.arcTo(100, 100, 300, 300, -90, 90, true); // 强制移动到弧形起点（无痕迹）
        path.close();//当前的子图形封闭

        path.setFillType(Path.FillType.EVEN_ODD);//交叉填充
        path.setFillType(Path.FillType.WINDING);//全填充

        canvas.drawPath(path, paint);
        customHeight = 1000;
    }

    //练习1
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw2(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);//设置绘制模式
        //扇形百分比图
        paint.setStrokeWidth(5);//设置线条宽度
        paint.setAntiAlias(true);//设置抗锯齿开关
        RectF rf = new RectF(200, 200, 700, 700);
        paint.setColor(Color.YELLOW);//设置颜色
        canvas.drawArc(rf, -45, 45, false, paint);
        paint.setColor(Color.RED);//设置颜色
        canvas.drawArc(rf, 0, 15, true, paint);
        paint.setColor(Color.GRAY);//设置颜色
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//设置绘制模式
        canvas.drawArc(rf, 15, 10, true, paint);
        paint.setColor(Color.GREEN);//设置颜色
        canvas.drawArc(rf, 25, 45, true, paint);
        paint.setColor(Color.BLUE);//设置颜色
        canvas.drawArc(rf, 70, 100, true, paint);
        paint.setColor(Color.RED);//设置颜色
        rf = new RectF(180, 180, 680, 680);
        canvas.drawArc(rf, 170, 145, true, paint);
        customHeight = 1000;
    }

    //demo2
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw3(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        Paint 设置颜色的方法有两种：一种是直接用 Paint.setColor/ARGB() 来设置颜色，另一种是使用 Shader 来指定着色方案。
        paint.setColor(Color.GREEN);//设置颜色
        paint.setShader(null);
        Shader shader = new LinearGradient(100, 100, 800, 800, Color.GREEN, Color.RED, Shader.TileMode.CLAMP);
        Shader shader1 = new RadialGradient(400, 400, 400, Color.GREEN, Color.RED, Shader.TileMode.CLAMP);
        Shader shader2 = new SweepGradient(300, 500, Color.GREEN, Color.RED);
//        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP );//可以绘制圆形的bitmap
//        shader = new ComposeShader(shader,shader1, PorterDuff.Mode.LIGHTEN);
        paint.setShader(shader);//不直接用 Shader 这个类，而是用它的几个子类。具体来讲有 LinearGradient RadialGradient SweepGradient BitmapShader ComposeShader 这么几个

        //对颜色进行第二层处理。有三个子类：LightingColorFilter PorterDuffColorFilter 和 ColorMatrixColorFilter
        ColorFilter colorFilter = new LightingColorFilter(0xff00ff, 0x000000);//第一个参数用来乘,第二个用来加;去掉G色.// mul 用来和目标像素相乘，add 用来和目标像素相加
        colorFilter = new LightingColorFilter(0xffffff, 0x003000);//G色加强
//        colorFilter =new PorterDuffColorFilter(Color.YELLOW,PorterDuff.Mode.LIGHTEN);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.5f);
        colorFilter = new ColorMatrixColorFilter(matrix);
        paint.setColorFilter(colorFilter);
        paint.setTextSize(30);
        canvas.drawText("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十", 100, 400, paint);//shader写字也可以

//        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
//        paint.setXfermode(xfermode);// setXfermode() 用来处理源图像和 View 已有内容的关系。
//        canvas.drawRect(100, 100, 200, 800, paint);
//        paint.setXfermode(null); // 用完及时清除 Xfermode
        customHeight = 400;
    }

    protected void onDraw4(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);//设置绘制模式
        paint.setStrokeWidth(50);//设置线条宽度
        paint.setStrokeCap(Paint.Cap.ROUND);//线头形状有三种
        paint.setStrokeJoin(Paint.Join.MITER);//拐角的形状:MITER 尖角、 BEVEL 平角和 ROUND 圆角
        paint.setStrokeMiter(4);//设置MITER情况下延长线的最大值
        Path path = new Path();
        path.moveTo(100, 100);
        path.rLineTo(400, 200);
        path.lineTo(100, 300);
        canvas.drawPath(path, paint);
        paint.setTextSize(300);
        canvas.drawText("口拐刀", 100, 650, paint);

        paint.setStrokeWidth(0);//宽度为 0 时称作「hairline mode 发际线模式
        paint.setDither(true);//加抖动.16 位色的 ARGB_4444 或者 RGB_565 的时候，开启它才会有比较明显的效果
        paint.setFilterBitmap(true);//使用双线性过滤来绘制 Bitmap, 放大绘制 Bitmap 的时候


        paint.setStrokeWidth(3);
        path.reset();
        path.moveTo(110, 800);
        path.rLineTo(100, 200);
        path.rLineTo(100, -250);
        path.rLineTo(100, 200);
        path.rLineTo(100, -150);
        path.rLineTo(200, 350);
        path.rLineTo(200, -400);

//        PathEffect 分为两类，
//          单一效果的 CornerPathEffect DiscretePathEffect DashPathEffect PathDashPathEffect ，
//          和组合效果的 SumPathEffect ComposePathEffect。
        PathEffect pathEffect;
//        pathEffect = new CornerPathEffect(100);//所有拐角变成圆角。
        //segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量
//        pathEffect = new DiscretePathEffect(20, 10);//线条进行随机的偏离，让轮廓变得乱七八糟。乱七八糟的方式和程度由参数决定..
        pathEffect = new DashPathEffect(new float[]{20, 15, 10, 5}, 10);//使用虚线来绘制线条.数组画线长度、空白长度.第二个参数 phase 是虚线的偏移量
//        pathEffect =  new PathDashPathEffect(dashPath, 40, 0, TRANSLATE);//使用一个 Path图形 来绘制「虚线。style:TRANSLATE：位移 ROTATE：旋转 MORPH：变体
//        pathEffect = new SumPathEffect(path1,path2);//按照两种 PathEffect 分别对目标进行绘制。
//        pathEffect= new ComposePathEffect(dashEffect, discreteEffect);//先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 叠加使用另一个 PathEffect
        paint.setPathEffect(pathEffect);//轮廓设置效果
        canvas.drawPath(path, paint);

        paint.reset();
        paint.setTextSize(80);
        paint.setShadowLayer(10, 0, 0, Color.GREEN);//之后的绘制内容下面加一层阴影。文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
        //如果要清除阴影层，使用 clearShadowLayer() 。
//        MaskFilter maskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID);//NORMAL: 内外都模糊绘制 SOLID: 内部正常绘制，外部模糊  INNER: 内部模糊，外部不绘制 OUTER: 内部不绘制，外部模糊
//        maskFilter = new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10);//浮雕效果的 MaskFilter
//         paint.setMaskFilter(maskFilter);
        canvas.drawText("abcdefABCDEF", 100, 1200, paint);

        Path desPath = new Path();
        paint.getFillPath(path, desPath);//获取这个实际 Path
//        paint.getTextPath();//主要是用于图形和文字的装饰效果的位置计算，比如自定义的下划线效果

//        drawText() 相关
        paint.setTextSize(20);//文字大小,文字间隔,文字效果,特别多
        paint.set(paint);//把 paint:src 的所有属性全部复制过来。
        paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//批量设置 flags。相当于依次调用它们的 set 方法
        customHeight = 1600;
    }

    //text↓
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw5(Canvas canvas) {
        //Canvas 的文字绘制方法有三个：drawText() drawTextRun() 和 drawTextOnPath()。
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setTextSize(50);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        String text = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz一二三四五六七八九十abcdefghijklmnopqrstuvwxyz";
        canvas.drawText(text, 100, 200, paint);//y为baseline
        canvas.drawText(text, 2, 8, 100, 300, paint);//只能绘制单行的文字，而不能换行
//        canvas. drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint)

        Path path = new Path();
        path.moveTo(110, 200);
        path.rLineTo(100, 200);
        path.rLineTo(100, -250);
        path.rLineTo(100, 200);
        path.rLineTo(100, -150);
        path.rLineTo(200, 350);
        path.rLineTo(200, -400);
        paint.setPathEffect(new CornerPathEffect(100));//所有拐角变成圆角)
        canvas.drawPath(path, paint);

        canvas.drawTextOnPath(text, path, 0, 10, paint);//沿着一条 Path 来绘制文字

        path.reset();
        path.moveTo(100, 500);
        path.lineTo(600, 500);
        path.lineTo(600, 900);
        path.lineTo(100, 900);
        path.close();
        paint.setPathEffect(null);
        canvas.drawPath(path, paint);
        paint.setPathEffect(new CornerPathEffect(100));//所有拐角变成圆角,画圆角图的一种方法.paint可以setbitmapshader
        canvas.drawPath(path, paint);
        paint.setColor(Color.RED);
        int off = 20;
        canvas.drawRoundRect(off + 100, off + 500, off + 600, off + 900, 100, 0, paint);//画圆图的一种方法
        customHeight = 1200;
    }

    @SuppressLint("NewApi")
    protected void onDraw6(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        String text = "abcdefghijkl\nmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz一二三四五六七八九十abcdefghijklmnopqrstuvwxyz";
        //StaticLayout 支持换行
        TextPaint textPaint = new TextPaint();
        textPaint.set(paint);
//        spacingmult 是行间距的倍数，通常情况下填 1 就好；
//        spacingadd 是行间距的额外增加值，通常情况下填 0 就好；
//        includeadd 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。
        StaticLayout staticLayout1 = new StaticLayout(text, textPaint, 900, Layout.Alignment.ALIGN_NORMAL, 1, 10, true);

        String text2 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint, 900, Layout.Alignment.ALIGN_CENTER, 1, 10, true);
        staticLayout1.draw(canvas);
        canvas.save();//作用是用来保存画布的状态和取出保存的状态的,不会对其他的元素进行影响

        canvas.translate(0, 280f);
        staticLayout2.draw(canvas);
        canvas.restore();
//        paint.setTypeface(Typeface.SERIF);
//        canvas.drawText("HelloabcyjABC五六七", 100, 450, paint);
//        paint.setTypeface(Typeface.DEFAULT_BOLD);
//        canvas.drawText("HelloabcyjABC五六七", 100, 500, paint);
//        paint.setTypeface(Typeface.SANS_SERIF);
//        canvas.drawText("HelloabcyjABC五六七", 100, 600, paint);
//        paint.setTypeface(Typeface.MONOSPACE);
//        canvas.drawText("HelloabcyjABC五六七", 100, 700, paint);
//        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));
//        canvas.drawText(text, 100, 800, paint);
        text = "HelloabcyjABC五六七雨骨赵条微沿海";
        paint.setFakeBoldText(false);//粗体
        paint.setStrikeThruText(false);//中划线删除
        paint.setUnderlineText(false);//下滑线
        paint.setTextSkewX(-0.2f);//倾斜度
//        paint.setTextScaleX(1.0f);//横向放缩
        paint.setLetterSpacing(0.0f);//设置字符间距
        paint.setFontFeatureSettings("smcp");// 设置 "small caps"  //用 CSS 的 font-feature-settings 的方式来设置文字。类似汝墙效果
        paint.setTextAlign(Paint.Align.LEFT);//文字对齐方式,在这个当前x坐标的哪边画
        paint.setTextLocale(Locale.CHINA);//语言
        paint.setHinting(Paint.HINTING_OFF);//字体微调,无用
        paint.setElegantTextHeight(true);//v把「大高个」文字的高度恢复为原始高度；
        paint.setSubpixelText(true);//开启次像素级的抗锯齿
        paint.setLinearText(true);//设置文本缓存
        canvas.drawText(text, 10, 500, paint);

        //测量文字尺寸类
        canvas.drawText(text, 100, 600, paint);
//        canvas.drawText(text, 100, 600 + paint.getFontSpacing(), paint);//获取推荐的行距。手动换行的距离计算
//        canvas.drawText(text, 100, 600 + paint.getFontSpacing() * 2, paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();//字体上的一些高度数值
//        FontMetrics.ascent：float 类型。//限制普通字符的顶部和底部范围
//        FontMetrics.descent：float 类型。
//        FontMetrics.top：float 类型。//限制所有字形（ glyph ）的顶部和底部范围。
//        FontMetrics.bottom：float 类型。
//        FontMetrics.leading：float 类型。//上行的 bottom 线和下行的 top 线的距离
        float ascent = paint.ascent();//这俩可以快捷获取
        float descent = paint.descent();

        //@return the font's recommended interline spacing.
        float spacing = paint.getFontMetrics(new Paint.FontMetrics());//赋值给这个
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);//文字的显示范围。紧贴着文字的
        Log.d(TAG, bounds.toString());
        canvas.drawRect(bounds.left + 100, bounds.top + 600, bounds.right + 100, bounds.bottom + 600, paint);
        float textWidth = paint.measureText(text);//获取文字宽度,measureText() 测出来的宽度总是比 getTextBounds() 大一点点
        float[] widths = new float[text.length()];
        paint.getTextWidths(text, widths);//获取字符串中每个字符的宽度，并把结果填入参数 widths

        float[] measuredWidth = {0};//把截取的文字宽度（如果宽度没有超限，则为文字总宽度）赋值给 measuredWidth[0]。
        int breakText = paint.breakText(text, true, 300, measuredWidth);//截取的文字个数（如果宽度没有超限，则是文字的总个数
//        measureForwards//测量方向

        float runAdvance = paint.getRunAdvance(text, 0, text.length(), 0, text.length(), false, 4);//计算出某个字符处光标的 x 坐标
        canvas.drawLine(100 + runAdvance, 600 - 60, 100 + runAdvance, 600 + 20, paint);
//        ↑ 测量最后一个文字时等价于 measureText(text) 的宽度,但getRunAdvance能自动跨过表情符号

        //advance 是给出的位置的像素值
        int offsetForAdvance = paint.getOffsetForAdvance(text, 0, text.length(), 0, text.length(), false, 5f);//给出一个位置的像素值，计算出文字中最接近这个位置的字符偏移量（即第几个字符最接近这个坐标）
        boolean a = paint.hasGlyph("a");//检查指定的字符串中是否是一个单独的字形
        customHeight = 1000;
    }

    @SuppressLint("NewApi")
    protected void onDraw7(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(80);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);//设置绘制模式
        String s1 = "三个月你胖了公斤";
        String s2 = "100";

        int index = s1.indexOf("公斤");
        float v = paint.measureText(s1, 0, index);
        canvas.drawText(s1, 0, index, 10, 500, paint);//三个月你胖了
        paint.setTextSize(150);
        paint.setColor(Color.BLACK);
        float v2 = paint.measureText(s2);//获取textwidth, 比getTextBounds() 大一点点
        canvas.drawText(s2, 10 + v, 500, paint);//100
        paint.setTextSize(80);
        paint.setColor(Color.GREEN);
        canvas.drawText(s1, index, s1.length(), 10 + v + v2, 500, paint);//公斤

        //2方法
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        s1 = "三个月你胖了公斤现在斤了";//三个月你胖了100公斤现在300斤了
        canvas.drawText(s1, 0, 6, 10, 700, paint);//三个月你胖了
        float[] widths = new float[s1.length()];
        int textWidths = paint.getTextWidths(s1, widths);//测量每个字宽度
        float vv1 = 0, vv2 = 0;
        for (int i = 0; i < widths.length; i++) {
            if (i < 6) {
                vv1 += widths[i];
            } else if (i < 10) {
                vv2 += widths[i];
            }
        }
        paint.setTextSize(70);
        paint.setColor(Color.RED);
        float off1 = paint.measureText("100");
        canvas.drawText("100", vv1 + 10, 700, paint);//100
        canvas.drawText("300", vv1 + off1 + vv2 + 10, 700, paint);//300
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        canvas.drawText(s1, 6, 10, vv1 + off1 + 10, 700, paint);//公斤现在
        canvas.drawText(s1, 10, s1.length(), vv1 + off1 + off1 + vv2 + 10, 700, paint);//斤了

        //
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(150);
        paint.setAntiAlias(true);
        String aaJjQPqp = "AaJjQPqp屮";
        int offx =100,offy=1000;
        paint.setColor(Color.GREEN);
        canvas.drawText(aaJjQPqp, offx, offy, paint);
        Rect bounds = new Rect();
        paint.getTextBounds(aaJjQPqp, 0, aaJjQPqp.length(), bounds);
        float textWidth = paint.measureText(aaJjQPqp);
        paint.setColor(Color.BLACK);
        canvas.drawRect(offx + bounds.left, offy + bounds.top, offx + bounds.right, offy + bounds.bottom, paint);

        Paint.FontMetrics f = paint.getFontMetrics();

//        Field[] declaredFields = Paint.FontMetrics.class.getDeclaredFields();
//        for (Field field : declaredFields) {
//            try {
//                field.setAccessible(true);
//                float o = (float) field.get(fontMetrics);
//                canvas.drawLine(offx,offy+o,offx+textWidth,offy+o,paint);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        float interline_spacing = paint.getFontMetrics(new Paint.FontMetrics());


        float top = offy+f.top;
        float ascent = offy+f.ascent;
        float descent = offy+f.descent;
        float bottom = offy+f.bottom;
        paint.setColor(Color.BLACK);
        canvas.drawLine(offx,top,offx+textWidth,top,paint);
        paint.setColor(Color.RED);
        canvas.drawLine(offx,ascent,offx+textWidth,ascent,paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(offx,descent,offx+textWidth,descent,paint);
        paint.setColor(Color.GRAY);
        canvas.drawLine(offx,bottom,offx+textWidth,bottom,paint);
    }

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
