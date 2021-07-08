package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rosefinches.smiledialog.SmileDialog;
import com.rosefinches.smiledialog.SmileDialogBuilder;
import com.rosefinches.smiledialog.enums.SmileDialogType;

public class measureView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_view);

        Button measure=findViewById(R.id.measure_start);
        Button showMessage = findViewById(R.id.measure_message);

        @SuppressLint("ResourceAsColor") SmileDialog dialog = new SmileDialogBuilder(measureView.this, SmileDialogType.WARNING)
//                .setTitleText(R.string.app_name)
//                .setTitleText("222")//设置标题文字
//                .setTitleTextResColor(R.color.colorAccent) //设置标题字体颜色
                .setContentText("点击“开始测距”按钮进行距离量测") //设置内容文字
                .setContentTextColor(Color.BLACK) //设置内容字体颜色
                .setConformButton("确定") //设置确认按钮文字
                .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
//                .setCancelButton("取消") //设置取消按钮文字（默认隐藏，有文字，则显示）
//                .setCancelTextColor(Color.GREEN) //设置取消按钮文字颜色
//                .setCancelBgColor() //设置取消按钮背景颜色
//                .hideTitle() //隐藏标题
//                .hideIcon() //隐藏Icon
//                .setCancelable() //是否可以取消
                .setCanceledOnTouchOutside(true) //点击区域外是否消失
                .setWindowAnimations(R.style.dialog_style) //设置动画style
//                .setOnConformClickListener(listener) //设置确认监听事件
//                .setOnCancelClickListener(listener) //设置取消监听事件
                .build();

        dialog.show();

        measure.setOnClickListener(v->{
            //measure();
            Intent intent=new Intent(this, Measure.class);
            startActivity(intent);
        });
        showMessage.setOnClickListener(v->{
            @SuppressLint("ResourceAsColor") SmileDialog dialog2 = new SmileDialogBuilder(measureView.this, SmileDialogType.WARNING)
                    .setContentText("将手机按照屏幕提示移动，尽量选择一个光滑的平面。\n" +
                            "待屏幕出现标点(白色圆球)提示时，选择两点进行量测。\n" +
                            "点击左下角“回退键”取消标点，点击左上角“确定键”上传距离。") //设置内容文字
                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
                    .setConformButton("确定") //设置确认按钮文字
                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
//                    .setWindowAnimations(R.style.dialog_style) //设置动画style
                    .build();

            dialog2.show();
        });
    }
}