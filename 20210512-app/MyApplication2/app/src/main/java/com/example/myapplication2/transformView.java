package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class transformView extends AppCompatActivity {
    public static final String Image_message="com.example.myapplication2.MESSAGE";
    private String firstimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_view);
        Button trans=findViewById(R.id.transform_start);

        trans.setOnClickListener(v->{
            transform();
        });
    }
    public void transform(){
        getDatasync0("http://299x2r2594.qicp.vip/trans");
    }
    public void getDatasync0(String url){
        initClient();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    String res;
                    response = httpClient.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl","response.code()=="+response.code());
                        Log.d("kwwl","response.message()=="+response.message());
                        res =response.body().string();//response.body().string()只能调用一次
                        Log.d("kwwl","res=="+res);
                        if(res.equals("true"))
                        {
                            //Log.d("jinlaile:","进来了");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"尺度转换成功",Toast.LENGTH_SHORT).show();
                                }
                            });

                        };
                        if(res.equals("false"))
                        {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"尺度转换失败，请重新标点",Toast.LENGTH_SHORT).show();
                                }
                            });
                            File file =downloadFile(firstimage);
                            if(file==null)
                                showMessage("no image");
                            else{
                                Intent intent=new Intent(transformView.this,ImageDotActivity.class);
                                String uri_image= Uri.fromFile(file).toString();
                                intent.putExtra(Image_message,uri_image);
                                startActivity(intent);
                                //showMessage("download success");
                            }

                        }


                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    OkHttpClient httpClient = null;
    void initClient(){
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    public File downloadFile(String filepath1) {
        File file=new File(filepath1);
        String filename=file.getName();
        OkHttpClient okhttp = new OkHttpClient();
        if(filename == null || filename.isEmpty())
            return null;
        RequestBody body = new MultipartBody.Builder().addFormDataPart("filename",filename).build();
        System.out.println("file name: "+filename);
        FutureTask<File> task = new FutureTask<>(()->
        {
            ResponseBody responseBody = okhttp.newCall(
                    new Request.Builder().post(body).url("http://299x2r2594.qicp.vip/download").build()
            ).execute().body();
            if(responseBody != null)
            {
                if(getExternalFilesDir(null) != null)
                {
                    System.out.println(getExternalFilesDir(null).toString());
                    File filex = new File(getExternalFilesDir(null).toString() + "/" + filename);
                    try (
                            InputStream inputStream = responseBody.byteStream();
                            FileOutputStream outputStream = new FileOutputStream(filex)
                    )
                    {
                        byte[] b = new byte[1024];
                        int n;
                        if((n = inputStream.read(b)) != -1)
                        {
                            outputStream.write(b,0,n);
                            while ((n = inputStream.read(b)) != -1)
                                outputStream.write(b, 0, n);
                            return filex;
                        }
                        else
                        {
                            filex.delete();
                            return null;
                        }
                    }
                }
            }
            return null;
        });
        try
        {
            new Thread(task).start();
            return task.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public  void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}