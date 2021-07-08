package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class DownloadView extends AppCompatActivity {

    HomePage homePage=new HomePage();

    public static final String Image_message="com.example.myapplication2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_view);

        Button download=findViewById(R.id.download_start);

        download.setOnClickListener(v -> {
            List<String> imagelist =homePage.getImagelist();
            File file =downloadFile(imagelist.get(0));

            System.out.println("imagelist(DownloadView): "+imagelist);

            if(file==null)
                showMessage("no image");
            else{
                Intent intent=new Intent(this,ImageDotActivity.class);
                String uri_image= Uri.fromFile(file).toString();
                intent.putExtra(Image_message,uri_image);
                startActivity(intent);
//                view.setImageURI(null);
//                view.setImageURI(Uri.fromFile(file));
//                System.out.println("path: "+Uri.fromFile(file));
                showMessage("download success");
            }
        });
//        download.setOnClickListener(v -> {
//            File file =downloadFile(firstimage);
//            if(file==null)
//                showMessage("no image");
//            else{
//                Intent intent=new Intent(this,ImageDotActivity.class);
//                String uri_image=Uri.fromFile(file).toString();
//                intent.putExtra(Image_message,uri_image);
//                startActivity(intent);
////                view.setImageURI(null);
////                view.setImageURI(Uri.fromFile(file));
////                System.out.println("path: "+Uri.fromFile(file));
//                showMessage("download success");
//            }
//        });
    }

    public File downloadFile(String filepath1)
    {
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
//                    new Request.Builder().post(body).url("http://192.168.43.14:8080/download").build()
//                    new Request.Builder().post(body).url("http://192.168.1.187:8080/download").build()  //gis
//                    new Request.Builder().post(body).url("http://172.27.218.116:8080/download").build()  //cug
                    new Request.Builder().post(body).url("http://192.168.56.209:8080/download").build()  //kenken
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