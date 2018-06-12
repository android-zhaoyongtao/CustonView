package com.zyt.uxin.custonview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.zyt.uxin.custonview.customtextview.TestCustomFARGBiew;
import com.zyt.uxin.custonview.customtextview.TestCustomProgressView;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class AnimatorActivity extends AppCompatActivity {

    private Button click;
    private TestCustomProgressView testCustomProgressView;
    private TestCustomFARGBiew testCustomFARGBiew, testCustomFARGBiew2;
    private TextView oag_animations;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
        click = findViewById(R.id.click);
        testCustomFARGBiew = findViewById(R.id.testCustomFARGBiew);
        testCustomFARGBiew2 = findViewById(R.id.testCustomFARGBiew2);
        testCustomProgressView = findViewById(R.id.testCustomProgressView);

        oag_animations = findViewById(R.id.oag_animations);
        textView = findViewById(R.id.textView);

        initListener();
        click.performClick();
    }

    private void initListener() {
        //        ClickAbleTextView cv = null;
//        ViewPropertyAnimator animate = cv.animate();
//        animate.setDuration(300);
//        animate.translationX(400);
        final long duition = 3000l;
        click.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ObjectAnimatorBinding")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ViewPropertyAnimator animate = v.animate();
//                animate.translationX(100);
//                animate.translationBy(100);//从控件原始的位置开始计算，再偏移100个像素
//                animate.translationXBy(100);//从当前位置，再向右偏移100个像素。
//                animate.rotationBy(300).setInterpolator(new LinearInterpolator());
                animate.rotationYBy(50).setInterpolator(new LinearInterpolator());
                animate.setDuration(duition).alpha(0.0f).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                }).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        //动画结束,走这里
                    }
                });

                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setIntValues(10, 15, 30);
                valueAnimator = ValueAnimator.ofInt(10, 15, 30);//等同于⤴️
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int i = (int) animation.getAnimatedValue();
                        System.out.println(i);
                    }
                });
                valueAnimator.start();

                //自定义进度 ObjectAnimator extends ValueAnimator
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(testCustomProgressView, "progress2", 1, 30);//会自动去寻找setProgress2方法
                objectAnimator.setStartDelay(300);//延迟
                objectAnimator.setDuration(duition);
                objectAnimator.start();

                //自定义颜色渐变属性
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofArgb(testCustomFARGBiew, "color33",  Color.GREEN,Color.RED,Color.GREEN);
                objectAnimator2.setDuration(duition);
                objectAnimator2.start();

                //自定义TypeEvaluator
                ObjectAnimator.ofObject(testCustomFARGBiew2, "color33",
                        new HsvEvaluator(), Color.GREEN, Color.RED,Color.GREEN).setDuration(duition).start();
                //多个一起
                oag_animations.animate().scaleX(1.5f).scaleY(1.2f).alpha(0.5f).rotationYBy(30).setDuration(duition);

                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(objectAnimator).with(objectAnimator).with(objectAnimator);
//                animatorSet.playSequentially(animator1, animator2);//依次的执行
//                animatorSet.playTogether(animator1, animator2);//一起执行
//                animatorSet.play(animator1).before(animator2);
//                animatorSet.play(animator1).after(animator2);
                animatorSet.start();

//                public void playTogether(Animator... items) {
//                    if (items != null) {
//                        AnimatorSet.Builder builder = play(items[0]);
//                        for (int i = 1; i < items.length; ++i) {
//                            builder.with(items[i]);
//                        }
//                    }
//                }
//                public void playSequentially(Animator... items) {
//                    if (items != null) {
//                        if (items.length == 1) {
//                            play(items[0]);
//                        } else {
//                            for (int i = 0; i < items.length - 1; ++i) {
//                                play(items[i]).before(items[i + 1]);
//                            }
//                        }
//                    }
//                }

                //PropertyValuesHolder 来对多个属性同时做动画
                //keyframe帧
                Keyframe keyframe1 = Keyframe.ofFloat(0.0f, 0);
                Keyframe keyframe2 = Keyframe.ofFloat(0.25f, -30);
                Keyframe keyframe3 = Keyframe.ofFloat(0.5f, 0);
                Keyframe keyframe4 = Keyframe.ofFloat(0.75f, 90);
                Keyframe keyframe5 = Keyframe.ofFloat(1.0f, 0);
                PropertyValuesHolder rotation = PropertyValuesHolder.ofKeyframe("rotation", keyframe1, keyframe2, keyframe3, keyframe4, keyframe5);
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.2f, 1.0f);
                PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.2f, 1.0f);
                PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.2f, 1.0f);
//                PropertyValuesHolder color = PropertyValuesHolder.ofInt("BackgroundColor", 0XFFFFFF00, 0XFF0000FF);
                PropertyValuesHolder backgroundColor = PropertyValuesHolder.ofObject("backgroundColor", new HsvEvaluator(), Color.GREEN, Color.YELLOW, Color.RED, Color.GREEN);
                PropertyValuesHolder textColor = PropertyValuesHolder.ofObject("TextColor", new HsvEvaluator(), Color.RED, Color.GRAY, Color.WHITE, Color.RED);
                ObjectAnimator.ofPropertyValuesHolder(textView, alpha, scaleX, scaleY, backgroundColor, textColor, rotation).setDuration(duition).start();

                //ValueAnimator 最基本
                //想要做动画的属性却没有 setter / getter 方法的时候，会需要用到它(ObjectAnimator.不能用)
                //viewPropertyAnimator、ObjectAnimator、ValueAnimator 这三种 Animator，它们其实是一种递进的关系：从左到右依次变得更加难用，也更加灵活
            }
        });

        //硬件加速,Canvas.drawXXX() 变成实际的像素这件事交给 GPU 来处理
//        view.setLayerType(LAYER_TYPE_SOFTWARE, null);
//        硬件加速可以使用 setLayerType() 来关闭硬件加速，但这个方法其实是用来设置 View Layer 的：
//
//        参数为 LAYER_TYPE_SOFTWARE 时，使用软件来绘制 View Layer，绘制到一个 Bitmap，并顺便关闭硬件加速；
//        参数为 LAYER_TYPE_HARDWARE 时，使用 GPU 来绘制 View Layer，绘制到一个 OpenGL texture（如果硬件加速关闭，那么行为和 VIEW_TYPE_SOFTWARE 一致）；
//        参数为 LAYER_TYPE_NONE 时，关闭 View Layer。
//        View Layer 可以加速无 invalidate() 时的刷新效率，但对于需要调用 invalidate() 的刷新无法加速。
//
//        View Layer 绘制所消耗的实际时间是比不使用 View Layer 时要高的，所以要慎重使用。
    }



    class HsvEvaluator implements TypeEvaluator<Integer> {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            // 把 ARGB 转换成 HSV
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180) {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180) {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360) {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360;
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            // 计算当前动画完成度（fraction）所对应的透明度
            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            // 把 HSV 转换回 ARGB 返回
            return Color.HSVToColor(alpha, outHsv);
        }
    }
}
