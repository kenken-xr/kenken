package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class firstPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        //两秒钟进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                //启动主页面
                startActivity(new Intent(firstPage.this, reminder.class));
                //关闭当前页面
                finish();
            }
        },2000);
    }
}