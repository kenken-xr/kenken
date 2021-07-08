package com.example.myapplication2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class reminder extends AppCompatActivity {
    Button Btn1;
    TextView mTextView = null;
    SpannableString msp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Btn1=findViewById(R.id.btn_1);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(reminder.this, HomePage.class);
                startActivity(intent);
            }
        });

        mTextView = (TextView)findViewById(R.id.firstPage_text);
        //创建一个 SpannableString对象
        msp = new SpannableString("温馨提示\n\n" +
                "欢迎使用“测量仪”！\n" +
                "为了更好地使用产品功能，需征求您的允许，获得以下权限:\n\n" +
                "设备信息：\n" +
                "用于根据不同的设备及系统版本来提供不同的功能和体验，获取产品的使用情况及Bug信息，帮助我们不断改进产品功能和体验。\n" +
                "存储：\n" +
                "用于数据缓存，页面缓存，优化页面中的展示效果，提升响应速度。\n" +
                "网络：\n" +
                "允许app知道手机当前是否联网,用于展示数据存储。\n" +
                "相机：\n" +
                "允许获取当前周边环境以用于测量\n" +
                "定位：\n" +
                "允许获取当前位置以用于海拔测量\n" +
                "麦克风：\n" +
                "用于收集当前环境噪音程度\n");

        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        msp.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //温馨提示
        msp.setSpan(new RelativeSizeSpan(0.9f), 45, 234, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //权限

        //设置字体颜色
        msp.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 234, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //温馨提示
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 46, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设备信息
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 111, 114, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //存储
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 146, 148, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //网络
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 176, 178, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //相机
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 196, 198, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //定位
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 216, 219, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //麦克风

        //设置居中
        msp.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setText(msp);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}