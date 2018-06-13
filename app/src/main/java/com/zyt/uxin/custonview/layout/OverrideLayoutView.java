package com.zyt.uxin.custonview.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class OverrideLayoutView extends ViewGroup {

    public OverrideLayoutView(Context context) {
        super(context);
    }

    public OverrideLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverrideLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mTotalWidth;
    private int mTotalLength;

    //onMeasure()的重写对于ViewGroup来说包含三部分内容
    //1.调用每个子view的measure(),让子view自我测量
    //2.根据子view得出的尺寸,得出子view的位置,并吧它们 的位置保存下来
    //3.根据子view的位置和尺寸计算出自己的尺寸,并用setMeasureEimension()保存
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int usedWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams lp = childView.getLayoutParams();
//            lp.width:对应layout_width(转换过了的值)
            //wrap_content=>WRAP_CONENT
            // match_parent=>MATCH_PARENT
            //xxdp/xxsp=>具体的像素值

            int childWidthSpec;//测量后字view的width

            switch (lp.width) {
                case LayoutParams.MATCH_PARENT://填满父控件
                    int 可用宽度 = 0;//可用宽度,通过widthMeasureSpec计算得到的
                    switch (selfWidthMode) {
                        case MeasureSpec.EXACTLY:
                            可用宽度 = selfWidthSize;
//                            break;
                        case MeasureSpec.AT_MOST://和EXACTLY只有在测量自己的时候有区别
                            可用宽度 = selfWidthSize;//也是随便你用
                            childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSize - usedWidth, MeasureSpec.EXACTLY);//第一个子view可以用全部可用宽度去测量,但第二个可用得减去第一个用掉的,特殊情况,第一个match_parent就把空间全部占用了

                            break;
                        case MeasureSpec.UNSPECIFIED://可用空间,无限大
                            //这里父控件本身,match_parent,而子view又要求无限大空间,矛盾,此时将mode传UNSPECIFIED,size随便写吧,因为没用(新版android来说size也是有用的)
                            childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                            break;

                    }

                    break;
                case LayoutParams.WRAP_CONTENT://让子view自己去测量,且不能超过父view边界
//                    if (selfWidthMode == MeasureSpec.EXACTLY || selfWidthMode == MeasureSpec.AT_MOST) {
                    if ((selfWidthMode & (MeasureSpec.EXACTLY | MeasureSpec.AT_MOST)) != 0) {//等同于上行
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSize - usedWidth, MeasureSpec.AT_MOST);//第2个参数是AT_MOST,还是要让子view自己去测量的
                    } else {//MeasureSpec.UNSPECIFIED
                        childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;
                default://开发者写的固定尺寸值
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
                    break;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
    测量子view时,可以拿到每个子view的和位置,保存下来就好
    至于尺寸,而且99%的时候子view测得的尺寸就是最终尺寸,
    用的时候调用getMeasuredWidth 就行
    为什么保存它们?因为现在是测量阶段,在接下来layout()才会使用
     */
    /*
    关于保存子view位置的两点说明:
    1.并不是所有的layout都需要保存子view的位置
      比如LinearLayout,它的内容全都是横向竖向排开的 ,子view的位置可以再布局阶段通过一个个的尺寸累加起来得到
    2.有些时候对某些子view需要重复测量两次或多次才能得到正确的尺寸和位置
      给出新的MeasureSpec直到结果满意
      比如LinearLayout,对子view match_parent的测量
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
