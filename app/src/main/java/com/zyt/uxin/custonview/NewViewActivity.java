package com.zyt.uxin.custonview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AnalogClock;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.ExpandableListView;
import android.widget.ImageSwitcher;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.RemoteViews;
import android.widget.Scroller;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

/**
 * Created by zhaoyongtao on 2017/12/12.
 */

public class NewViewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addContentView();
        setContentView(R.layout.activity_newview);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SearchView searchView = findViewById(R.id.searchView);
        // 设置搜索文本监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(NewViewActivity.this, "onQueryTextSubmit()" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                        if (!TextUtils.isEmpty(newText)) {
//                            mListView.setFilterText(newText);
//                        } else {
//                            mListView.clearTextFilter();
//                        }
                Toast.makeText(NewViewActivity.this, "onQueryTextChange()" + newText, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        TextSwitcher textSwitcher = null;//文本上下切换
        ImageSwitcher imageSwitcher = null;
        ViewSwitcher viewSwitcher;//ViewSwitcher切换View时的动画
        RatingBar ratingBar;//星星
        SeekBar seekBar;
        ProgressBar progressBar;
        DigitalClock digitalClock;
        AnalogClock analogClock;
        TextClock textClock;
        AutoCompleteTextView autoCompleteTextView;//提示输入
        CalendarView calendarView;//MaterialCalendarView
        Switch aSwitch;//左右开关
        ToggleButton toggleButton;//内部圆角的button
        Chronometer chronometer;//计时器
        DatePicker datePicker;//日期选择
        ExpandableListView expandableListView;//嵌套
//        CursorAdapter与CursorFilter机制//对显示的条目进行过滤
        NumberPicker numberPicker;
        QuickContactBadge quickContactBadge;//联系人快捷标识,
        RemoteViews remoteViews;//远程视图.桌面小组件

        Scroller mScroller = new Scroller(this, new LinearInterpolator());//平滑滑动应用
//        mScroller.startScroll(0, 0, 100, 100, 3000);
//        @Override
//        public void computeScroll () {//view的方法中
//            //判断是否还在滚动，还在滚动为true
//            if (mScroller.computeScrollOffset()) {
//                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//                //更新界面
//                postInvalidate();
//                isMove = true;
//            }
//        }

        ShareActionProvider shareActionProvider = new ShareActionProvider(this);//实现分享功能
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int i = keyCode;
        return super.onKeyDown(keyCode, event);
    }
}
