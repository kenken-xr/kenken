package com.example.myapplication2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cat.cc.taglibrary.view.ImageDotLayout;
import com.rosefinches.smiledialog.SmileDialog;
import com.rosefinches.smiledialog.SmileDialogBuilder;
import com.rosefinches.smiledialog.enums.SmileDialogType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageDotActivity extends AppCompatActivity {

    private ImageDotLayout imageDotLayout;
    ImageDotLayout.coor cor;
    int count=0;
    Button image_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dot);

        image_message=findViewById(R.id.image_dot_message);

        @SuppressLint("ResourceAsColor") SmileDialog dialog = new SmileDialogBuilder(ImageDotActivity.this, SmileDialogType.WARNING)
                .setContentText("选择两个具有标志性特征的坐标，" +
                        "\n选择一个点后点击“上传坐标”再选择下一个点" +
                        "\n点击“清除标记”删除所有标记点" +
                        "\n①不要选择弱纹理和反光区域，尽量选择角点、例如门框;"+
                    "\n②选择光照良好区域。")//设置内容文字
                .setConformButton("确定") //设置确认按钮文字
                .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                .setContentTextColor(Color.BLACK) //设置内容字体颜色
                .setCanceledOnTouchOutside(true) //点击区域外是否消失
                .setWindowAnimations(R.style.dialog_style) //设置动画style
                .build();

        dialog.show();

        Button button2=findViewById(R.id.button2);
        Button upload_coor=findViewById(R.id.button3);
        EditText editText=findViewById(R.id.name1);
        imageDotLayout = (ImageDotLayout) findViewById(R.id.idl_idl_photo);

        image_message.setOnClickListener(v->{
            @SuppressLint("ResourceAsColor") SmileDialog dialog2 = new SmileDialogBuilder(ImageDotActivity.this, SmileDialogType.WARNING)
                    .setContentText("选择两个具有标志性特征的坐标，" +
                            "\n选择一个点后点击“上传坐标”再选择下一个点" +
                            "\n点击“清除标记”删除所有标记点" +
                            "\n①不要选择弱纹理和反光区域，尽量选择角点、例如门框;"+
                            "\n②选择光照良好区域。")//设置内容文字
                    .setConformButton("确定") //设置确认按钮文字
                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
                    .setWindowAnimations(R.style.dialog_style) //设置动画style
                    .build();

            dialog2.show();
        });

        //设置背景图片
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }
            }
        }
        //String s = Environment.getExternalStorageDirectory().getAbsolutePath()+"/image/girl.jpg";
        //System.out.println("path:"+s);
        Intent intent=getIntent();
        Uri uri=Uri.parse(intent.getStringExtra(MainActivity.Image_message));
        imageDotLayout.setImage(uri.toString());
        String filepath=MainActivity.getPath(this,uri);
        File file=new File(filepath);
        String image_name=file.getName();

        imageDotLayout.setOnImageClickListener(new ImageDotLayout.OnImageClickListener() {
            @Override
            public void onImageClick(ImageDotLayout.IconBean bean) {
                //可以一系列处理后再添加标签

                cor=imageDotLayout.addIcon(bean);
                System.out.println("cor1:"+cor.x);
                System.out.println("cor1:"+cor.y);
            }
        });

        upload_coor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=Float.toString(cor.x);
                String str1=Float.toString(cor.y);
                String str2=str+","+str1;
                MainActivity.getString(str2);
                MainActivity.uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt",Integer.toString(count));
                //MainActivity.getString(image_name);
                //MainActivity.uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt","imagename.txt");
                showMessage("上传成功");
                count++;
                if(count>1){
                    count=0;
                    startActivity(new Intent(ImageDotActivity.this, Measure.class));
                }
            }
        });

        //initIcon();
        imageDotLayout.setOnIconClickListener(new ImageDotLayout.OnIconClickListener() {
            @Override
            public void onIconClick(View v) {
                ImageDotLayout.IconBean bean= (ImageDotLayout.IconBean) v.getTag();
                Toast.makeText(ImageDotActivity.this,"位置="+bean.id,Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDotLayout.removeAllIcon();
            }
        });
    }

    private void initIcon() {
        final List<ImageDotLayout.IconBean> iconBeanList = new ArrayList<>();
        ImageDotLayout.IconBean bean = new ImageDotLayout.IconBean(0, 0.3f, 0.4f, null);
        iconBeanList.add(bean);
        bean = new ImageDotLayout.IconBean(1, 0.5f, 0.4f, null);
        iconBeanList.add(bean);
        //监听图片是否加载完成
        imageDotLayout.setOnLayoutReadyListener(new ImageDotLayout.OnLayoutReadyListener() {
            @Override
            public void onLayoutReady() {
                imageDotLayout.addIcons(iconBeanList);
            }
        });
    }
    public  void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}