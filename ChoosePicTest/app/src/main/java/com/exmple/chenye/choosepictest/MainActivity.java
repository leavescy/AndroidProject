package com.exmple.chenye.choosepictest;


import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 注意实现ActivityCompat.OnRequestPermissionsResultCallback，权限申请结果通过这个callback返回
 */
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALBUM_PERMISSION_REQUEST_CODE = 3;

    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;
    private static final int CHOOSE_PHONE = 3;

    private ImageView picture;
    private Button take_phone;
    private Button choose_from_album;
    private Uri imageUri;
    private Uri imageUriFromClip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picture = findViewById(R.id.picture);
        take_phone = findViewById(R.id.take_phone);
        take_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查自身是否有此权限
                if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 如果没有，就去申请权限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                } else {
                    // 如果有，则跳转拍摄页面
                    jumpToCapture();
                }
            }
        });

        choose_from_album = findViewById(R.id.choose_from_album);
        choose_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 检查自身是否有此权限
                if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 如果没有，就去申请权限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ALBUM_PERMISSION_REQUEST_CODE);
                } else {
                    // 如果有，则跳转到照片页面
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, CHOOSE_PHONE);
                }


            }
        });
    }

    // 系统回调的permission结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限申请成功，则跳转拍摄页面
                    jumpToCapture();
                } else {
//                // 权限申请失败时，判断是否需要告诉用户原因
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
//                    // 如果需要解释原因，则弹窗告诉用户
//                    showReasonDialog();
//                } else {
//                    // 如果不需要，则返回权限申请失败
//                }
                    /**
                     * 根据业务需要做处理，给用户提示之类的
                     */
                    Toast.makeText(this, "求爷爷告奶奶跟用户要权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case ALBUM_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限申请成功，则跳转照片页面
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, CHOOSE_PHONE);

                } else {
                    /**
                     * 根据业务需要做处理，给用户提示之类的
                     */
                    Toast.makeText(this, "求爷爷告奶奶跟用户要权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    private void jumpToCapture() {
        /**
         * 现在是调用系统的拍摄等功能，通过FileProvider来获取URI的话会有如下报错
         * W/Gallery2_Common_GalleryUtils: error . content://com.exmple.chenye.choosepictest.provider/external_files/output_image.jpg. Permission Denial: opening provider android.support.v4.content.FileProvider from ProcessRecord{6598edf 19200:com.android.gallery3d:crop/u0a24} (pid=19200, uid=10024) that is not exported from UID 10506
         12-14 10:58:01.217 1138-1153/? W/ActivityManager: Permission Denial: opening provider android.support.v4.content.FileProvider from ProcessRecord{6598edf 19200:com.android.gallery3d:crop/u0a24} (pid=19200, uid=10024) that is not exported from UID 10506
         12-14 10:58:01.217 1138-1153/? W/System.err: java.lang.SecurityException: Permission Denial: opening provider android.support.v4.content.FileProvider from ProcessRecord{6598edf 19200:com.android.gallery3d:crop/u0a24} (pid=19200, uid=10024) that is not exported from UID 10506
         12-14 10:58:01.218 1138-1153/? W/System.err:     at com.android.server.am.ActivityManagerService.getContentProviderImpl(ActivityManagerService.java:12256)
         *
         * 程序中对于FileProvider的使用情况较少
         */
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                /**
                 * 启动裁剪页面这里两个问题
                 * 1、输入输出需要区分开来，现在的写法是让页面从imageUri中读照片，裁剪的的照片输出到imageUriFromClip
                 * 所以intent.setDataAndType(imageUri, "image/*")
                 * intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromClip);
                 *
                 * 2、Uri暴露给其他应用使用需要授予权限，加上了个Flag
                 *
                 */
                if (resultCode == RESULT_OK) {

                    File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image_from_clip.jpg");
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    try {
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUriFromClip = Uri.fromFile(outputImage);
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromClip);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUriFromClip));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case CHOOSE_PHONE:
                if (resultCode == RESULT_OK) {
                    handleImageOnKitKat(data);
                }
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            }
            displayImage(imagePath);
        }


    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Fail to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
