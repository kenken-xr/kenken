package com.example.myapplication2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        ImageView view=findViewById(R.id.imageView);
        Intent intent=getIntent();
        Uri image_uri=Uri.parse(intent.getStringExtra(MainActivity.Image_message));
        System.out.println("image_uri:  "+image_uri);
    /*    view.setImageURI(null);
        view.setImageURI(image_uri);*/
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setImageBitmap(bitmap);
/*        Canvas canvas=new Canvas(bitmap);
        Paint mPaint=new Paint();
        mPaint.setColor(0xff00ff00);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);*/

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=event.getX();
                float y=event.getY();
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    Log.e("point",x +","+y);
                    //canvas.drawPoint(x,y,mPaint);
                }
// 目标点的坐标
                float dst[] = new float[2];
                // 获取到ImageView的matrix
                Matrix imageMatrix = view.getImageMatrix();
                // 创建一个逆矩阵
                Matrix inverseMatrix = new Matrix();
                // 求逆，逆矩阵被赋值
                imageMatrix.invert(inverseMatrix);
                // 通过逆矩阵映射得到目标点 dst 的值
                inverseMatrix.mapPoints(dst, new float[]{x, y});
                float dstX = dst[0];
                float dstY = dst[1];
                Log.e("point_: ",dstX+","+dstY );
                return true;
            }
        });
    }
}