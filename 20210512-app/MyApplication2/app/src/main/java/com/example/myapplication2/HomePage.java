package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.rosefinches.smiledialog.SmileDialog;
import com.rosefinches.smiledialog.SmileDialogBuilder;
import com.rosefinches.smiledialog.enums.SmileDialogType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomePage extends AppCompatActivity {

    static final int CAMERA_REQUEST = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String TAG = "MainActivity";

    private static int REQUEST_CAMERA=1;
    private String mFilePath;
    private Bitmap bitmap;

    public boolean flag = false;

    public static List<String> getImagelist() {
        return imagelist;
    }

    public static void setImagelist(List<String> imagelist) {
        HomePage.imagelist = imagelist;
    }

//    private static List<String> imagelist=new ArrayList<>();
    public static List<String> imagelist=new ArrayList<>();


    Button jumpto;
    Button choose_photo;
    Button take_photo;
    Button upload_photo;
    Button photo_message;
    Button choose_message;
    Button clean;
//    Button upload_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        jumpto=findViewById(R.id.jumpto);
        choose_photo= findViewById(R.id.choose_photo);
        take_photo=findViewById(R.id.photo);
//        upload_photo=findViewById(R.id.upload_photo);
        photo_message=findViewById(R.id.home_camera_message);
        choose_message=findViewById(R.id.home_choose_message);
//        upload_message=findViewById(R.id.home_upload_message);
        clean=findViewById(R.id.clean);

        @SuppressLint("ResourceAsColor") SmileDialog dialog = new SmileDialogBuilder(HomePage.this, SmileDialogType.WARNING)
                .setContentText("①点击“+”开始拍照，进行场景图像提取工作;"+
                        "\n②点击“选择图片”选择刚刚拍摄好的图像;" +
                        "\n③点击“上传图片”将选择的图片上传至后台。")//设置内容文字
                .setConformButton("确定") //设置确认按钮文字
                .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                .setContentTextColor(Color.BLACK) //设置内容字体颜色
                .setCanceledOnTouchOutside(true) //点击区域外是否消失
                .setWindowAnimations(R.style.dialog_style) //设置动画style
                .build();

        dialog.show();


//        jumpto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(HomePage.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });

//        启用相机拍照
//        take_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Intent intent=new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                    }
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
//                                "com.example.myapplication2.fileprovider",
//                                photoFile);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        startActivityForResult(intent, CAMERA_REQUEST);
//                    }
//                }
////                startActivityForResult(intent,CAMERA_REQUEST);
//            }
//        });

        take_photo.setOnClickListener(v ->{
            dispatchTakePictureIntent();
        });
        choose_photo.setOnClickListener(v-> {
            chooseFile();

        });
//        upload_photo.setOnClickListener(v->{
//            upload();
//            if(imagelist.size()>0){
//                System.out.println(imagelist);
//                File file=new File(imagelist.get(0));
//                String filename=file.getName();
//                getString(filename);
//                showMessage(uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt","image0_name.txt")?"上传成功":"上传失败");
//                //getAllFiles("/storage/emulated/0/Android/data/com.example.myapplication2/files/Pictures","jpg");
//
//                if(file!=null){
//                    Intent intent=new Intent(HomePage.this,measureView.class);
//                    startActivity(intent);
//                }
//                else {
//                    showMessage("未选择图片");
//                }
//            }
//
//        });
        photo_message.setOnClickListener(v ->{
            @SuppressLint("ResourceAsColor") SmileDialog dialog3 = new SmileDialogBuilder(HomePage.this, SmileDialogType.WARNING)
                    .setContentText("使用相机进行场景图像提取工作：" +
                            "\n为保证三维建模效果," +
                            "\n  ①拍摄图像时一定要保证相片与相片之间的重叠度达到 80%以上;"+
                            "\n  ②相邻视角差异控制在 5-15 度之内，保证图像重叠度;" +
                            "\n  ③对选择的测距的两点进行多次多角度拍摄。") //设置内容文字
                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
                    .setConformButton("确定") //设置确认按钮文字
                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
                    .setWindowAnimations(R.style.dialog_style) //设置动画style
                    .build();

            dialog3.show();
        });

        choose_message.setOnClickListener(v ->{
            @SuppressLint("ResourceAsColor") SmileDialog dialog4 = new SmileDialogBuilder(HomePage.this, SmileDialogType.WARNING)
                    .setContentText("请选择2张以上的照片上传。") //设置内容文字
                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
                    .setConformButton("确定") //设置确认按钮文字
                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
//                    .setWindowAnimations(R.style.dialog_style) //设置动画style
                    .build();

            dialog4.show();
        });

        clean.setOnClickListener(v->{
            clean();
        });

//        upload_message.setOnClickListener(v ->{
//            @SuppressLint("ResourceAsColor") SmileDialog dialog5 = new SmileDialogBuilder(HomePage.this, SmileDialogType.WARNING)
//                    .setContentText("在选择完照片后点击该按钮将照片上传至后台。") //设置内容文字
//                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
//                    .setConformButton("确定") //设置确认按钮文字
//                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
//                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
//                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
////                    .setWindowAnimations(R.style.dialog_style) //设置动画style
//                    .build();
//
//            dialog5.show();
//        });
    }


    public static void getString(String str){
        String filePath=null;
        boolean hasSDCard=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(hasSDCard){
            filePath="/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt";
        }
        else{
            filePath =Environment.getDownloadCacheDirectory().toString() + File.separator +"hello.txt";
        }
        try{
            File file=new File(filePath);
            if(!file.exists()){
                File dir=new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outputStream=new FileOutputStream(file);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean uploadFile(String path,String filename)
    {
        OkHttpClient okhttp = new OkHttpClient();
        File file = new File(path);
        if(path.isEmpty() || !file.exists())
            return false;
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",filename,RequestBody.create(MediaType.parse("multipart/form-data"), new File(path)))
                .addFormDataPart("filename",filename)
                .build();
        if(body==null){
        }
        FutureTask<Boolean> task = new FutureTask<>(()->
        {
            try
            {
                ResponseBody responseBody = okhttp.newCall(
//                        ipconfig/all
//                        new Request.Builder().post(body).url("http://192.168.43.14:8080/upload").build()
//                        new Request.Builder().post(body).url("http://192.168.1.187:8080/upload").build()  //gis
//                        new Request.Builder().post(body).url("http://172.27.218.116:8080/upload").build()  //cug
                        new Request.Builder().post(body).url("http://192.168.56.209:8080/upload").build()  //kenken
                ).execute().body();
                if(responseBody != null)
                {
                    return Boolean.parseBoolean(responseBody.string());}
                return false;
            }
            catch (IOException e)
            {
                System.out.println(e.toString());
                return false;
            }
        });
        try
        {
            new Thread(task).start();
            return task.get();
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public  void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void upload() {
        for(int i=0;i<imagelist.size();i++){
            String path=imagelist.get(i);
            File file=new File(path);
            String filename=file.getName();
            uploadFile(path,filename);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.myapplication2.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    照片处理
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CAMERA_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    imageIV.setImageBitmap(bitmap);
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    选择图片
    public static final int CHOOSE_FILE_CODE = 0;
    public void chooseFile(){
        String [] permissions=new String[]{
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };//所需权限
        if(
                ActivityCompat.checkSelfPermission(this,permissions[0])!= PackageManager.PERMISSION_GRANTED||
                        ActivityCompat.checkSelfPermission(this,permissions[1]) != PackageManager.PERMISSION_GRANTED
        )//如果没有权限
        {
            ActivityCompat.requestPermissions(this,permissions,1);//申请权限
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*").addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE,true);
        try {
            startActivityForResult(Intent.createChooser(intent, "Choose File"), CHOOSE_FILE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri uri;
        //List<String> imagelist=new ArrayList<>();
        if (Activity.RESULT_OK == resultCode && data!=null) {
            if (requestCode == CHOOSE_FILE_CODE) {
                ClipData imageNames=data.getClipData();
                if(imageNames!=null){
                    for(int i=0;i<imageNames.getItemCount();i++){
                        Uri imageUri=imageNames.getItemAt(i).getUri();
                        String fpath=getPath(this,imageUri);
                        imagelist.add(fpath);
                        System.out.println(fpath);
                        System.out.println(HomePage.imagelist+"       222222222222222222222");
                    }
                }
                else{
                    uri=data.getData();
                }
            }
        } else {
            Log.e(TAG, "onActivityResult failed: resultCode=" + resultCode);
        }
        System.out.println(imagelist);

        upload();
        if(imagelist.size()>0){
            System.out.println(imagelist);
            File file=new File(imagelist.get(0));
            String filename=file.getName();
            getString(filename);
            showMessage(uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt","image0_name.txt")?"上传成功":"上传失败");
            //getAllFiles("/storage/emulated/0/Android/data/com.example.myapplication2/files/Pictures","jpg");

            if(file!=null){
//                Intent intent=new Intent(HomePage.this,measureView.class);
//                startActivity(intent);
                getDatasync("http://299x2r2594.qicp.vip/recon");
                Intent intent=new Intent(HomePage.this,DownloadView.class);
                startActivity(intent);
            }
            else {
                showMessage("未选择图片");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public void getDatasync(String url){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl","response.code()=="+response.code());
                        Log.d("kwwl","response.message()=="+response.message());
                        Log.d("kwwl","res=="+response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void clean(){
        getDatasync("http://299x2r2594.qicp.vip/clean");
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void showdialog(View view){
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("操作手册");
        alertdialogbuilder.setNegativeButton("取消", click2);
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }

    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0,int arg1) {
            arg0.cancel();
        }
    };

    public void click(View v){
        Toast.makeText(this,"提示内容",Toast.LENGTH_SHORT).show();
    }

}
