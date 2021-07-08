package com.example.myapplication2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static final String Image_message="com.example.myapplication2.MESSAGE";
    private static final String TAG = "MainActivity";
    private Button button;
    private TextView textView;
    private File outputImage;
    private File file1=null;
    private Uri uri;
    private TextToSpeech tts;
    private String filepath ;
    private String parent_path;

    public List<String> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<String> imagelist) {
        this.imagelist = imagelist;
    }

    private List<String> imagelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button choose = findViewById(R.id.choose);
        Button upload=findViewById(R.id.upload);
        Button upload2=findViewById(R.id.upload2);
        Button uploadtxt=findViewById(R.id.uploadtxt);
        Button measure=findViewById(R.id.measure);
        Button download=findViewById(R.id.download);
        EditText filenameEditText=findViewById(R.id.name1);
        //ImageView view=findViewById(R.id.imageView);

        choose.setOnClickListener(v->
        {
            chooseFile();
        });
        upload.setOnClickListener(v->{
            showMessage(uploadFile(filepath,filenameEditText.getText().toString()) ? "上传成功" : "上传失败");
        });
        upload2.setOnClickListener(v->{
            upload2();
            File file=new File(imagelist.get(0));
            String filename=file.getName();
            getString(filename);
            showMessage(uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt","image0_name.txt")?"上传成功":"上传失败");
            //getAllFiles("/storage/emulated/0/Android/data/com.example.myapplication2/files/Pictures","jpg");
        });
        uploadtxt.setOnClickListener(v->{
            getString(filenameEditText.getText().toString());
            showMessage(uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt","distance.txt")?"上传成功":"上传失败");
        });
        measure.setOnClickListener(v->{
            //measure();
            Intent intent=new Intent(this, Measure.class);
            startActivity(intent);
        });
        download.setOnClickListener(v -> {
            System.out.println("imagelist: "+ imagelist);
            File file =downloadFile(imagelist.get(0));
            if(file==null)
                showMessage("no image");
            else{
                Intent intent=new Intent(this,ImageDotActivity.class);
                String uri_image=Uri.fromFile(file).toString();
                intent.putExtra(Image_message,uri_image);
                startActivity(intent);
//                view.setImageURI(null);
//                view.setImageURI(Uri.fromFile(file));
//                System.out.println("path: "+Uri.fromFile(file));
                showMessage("download success");
            }
        });
    }

    public void click(View v){
        Toast.makeText(this,"提示内容",Toast.LENGTH_SHORT).show();
    }

    private static final String TAG1 = "FileChoose";
    public static final int CHOOSE_FILE_CODE = 0;
    public void chooseFile(){
        String [] permissions=new String[]{
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };//所需权限
        if(
                ActivityCompat.checkSelfPermission(this,permissions[0])!=PackageManager.PERMISSION_GRANTED||
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
                    }
                }
                else{
                    uri=data.getData();
                }

//                Uri uri = data.getData();
//                Log.e(TAG, "onActivityResult: uri=" + uri);
//                 filepath = getPath(this, uri);
//                Log.e(TAG, "onActivityResult: filepath=" + filepath);
//                File file = new File(filepath);
//                parent_path=file.getParentFile().getPath();
//                Log.e(TAG, "onActivityResult: parent_path=" + parent_path);
//                Log.e(TAG, "onActivityResult: file size=" + file.length() + ", file name=" + file.getName());
            }
        } else {
            Log.e(TAG, "onActivityResult failed: resultCode=" + resultCode);
        }
        System.out.println(imagelist);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    public static List<String> getAllFiles(String dirPath, String _type) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if(files==null){//判断权限
            return null;
        }

        List<String> fileList = new ArrayList<String>();
        for (File _file : files) {//遍历目录
            if(_file.isFile() && _file.getName().endsWith(_type)){
                String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
//                Log.d("LOGCAT","fileName:"+fileName);
//                Log.d("LOGCAT","filePath:"+filePath);
                try {
                    fileList.add(filePath);
                }catch (Exception e){
                }
            } else if(_file.isDirectory()){//查询子目录
                getAllFiles(_file.getAbsolutePath(), _type);
            } else{
            }
        }
        System.out.println(fileList);
        return fileList;
    }
    private void upload2(){
        //List<String> filepaths=getAllFiles("/storage/emulated/0/Android/data/com.example.myapplication2/files/Pictures","jpg");

        for(int i=0;i<imagelist.size();i++){
            String path=imagelist.get(i);
            File file=new File(path);
            String filename=file.getName();
            uploadFile(path,filename);
        }
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
        System.out.println("xxxx1");
        OkHttpClient okhttp = new OkHttpClient();
        File file = new File(path);
        if(path.isEmpty() || !file.exists())
            return false;
        System.out.println("xxxx2");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",filename,RequestBody.create(MediaType.parse("multipart/form-data"), new File(path)))
                .addFormDataPart("filename",filename)
                .build();
        System.out.println("xxxx3");
        if(body==null){
            System.out.println("xxxx4");
        }
        FutureTask<Boolean> task = new FutureTask<>(()->
        {
            System.out.println("xxxx5");
            try
            {
                System.out.println("xxxx6");
                ResponseBody responseBody = okhttp.newCall(
//                        new Request.Builder().post(body).url("http://192.168.43.14:8080/upload").build()
//                        new Request.Builder().post(body).url("http://192.168.1.187:8080/upload").build()
                        new Request.Builder().post(body).url("http://192.168.56.209:8080/upload").build()  //kenken
                ).execute().body();
                System.out.println("xxxx6");
                if(responseBody != null)
                {System.out.println("xxxx7");
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

    private void measure(){
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.xinmang.camera.measure.altimeter");
        if (intent != null) {
            intent.putExtra("type", "110");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    public File downloadFile(String filepath1)
    {
        System.out.println("imagelist: "+imagelist);
        System.out.println("imagelist.get(0): "+imagelist.get(0));
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
//                    new Request.Builder().post(body).url("http://1192.168.1.187:8080/download").build()
                new Request.Builder().post(body).url("http://192.168.1.187:8080/download").build()  //cug
//                    new Request.Builder().post(body).url("http://192.168.43.209:8080/upload").build()  //kenken
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
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        }

    }

    public  void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void e(String message)
    {
        Log.e("LOG_E",message);
    }

    //将返回的图片显示出来
    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                picture.setImageBitmap(bitmap);
                saveToServer(outputImage);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
    }


    private void saveToServer(File mFile) {
        String address = "http://122.51.247.132:8987/infer";
        RequestBody image = RequestBody.create(MediaType.parse("image/jpg"), mFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img",mFile.getName(),image)
                .build();
        HttpUtil.sendOkHttpPostRequest(address,requestBody,new Callback() {

            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: "+"访问失败");
            }


            public void onResponse(Call call, Response response) throws IOException {
                final String Text = response.body().string();
                Log.d(TAG, "onResponse: "+Text);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(Text);
                    }
                });
            }
        });
    }*/


}