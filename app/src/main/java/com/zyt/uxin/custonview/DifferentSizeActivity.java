package com.zyt.uxin.custonview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

/**
 * textview不同汉字大小
 */

public class DifferentSizeActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_textsize);
        textView = findViewById(R.id.TextView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        String str = "划1痕\n20CM";
        str = "3-5CM\n划伤";
        textView.setText(getText(str, 16));
    }

    /**
     * @param str      全部展示文字
     * @param textSize \n符号前的汉字大小,\n符号后为默认字体大小
     * @return textView.setText(此返回值)
     */
    public SpannableString getText(String str, int textSize) {
        if (str == null) {
            return null;
        } else {
            int indexOf = str.indexOf("\n");
            if (indexOf < 0 || indexOf >= str.length()) {
                return new SpannableString(str);
            } else {
                SpannableString spannableString = new SpannableString(str);
                AbsoluteSizeSpan sizeSpan01 = new AbsoluteSizeSpan(textSize, true);
                spannableString.setSpan(sizeSpan01, 0, indexOf, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return spannableString;
            }
        }
    }
}
