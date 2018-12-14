package com.exmple.chenye.choosepictest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private String mReason;
    private int mPermissionCode;
    private String mPermission;
    //private PermissionCallback mCallback;
    private Context mContext;
    private PermissionCallback mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    protected void requestPermissionWithReason(Context context, int permissionCode, String permission) {

        this.mPermission = permission;
        this.mPermissionCode = permissionCode;
        this.mContext = context;
       // this.mCallback = callback;

        // 检查自身是否有此权限
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(context, permission)) {
            // 如果没有，就去申请权限

            ActivityCompat.requestPermissions((Activity)mContext, new String[]{mPermission}, mPermissionCode);


        } else {
            // 如果有，则调用callback

        }
    }



    // 系统回调的permission结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mPermissionCode
                && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            // 权限申请成功
            Log.i("result=", "成功");
            mCallback.onPermissionResult(true, mPermissionCode);
        } else {
            // 权限申请失败时，判断是否需要告诉用户原因
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                // 如果需要解释原因，则弹窗告诉用户
                showReasonDialog();
            } else {
                // 如果不需要，则返回权限申请失败
                mCallback.onPermissionResult(false, mPermissionCode);
            }
        }
    }

    // 弹窗展示权限申请原因,设定为protected是为了方便修改其它权限解释方式
    protected void showReasonDialog() {
        new AlertDialog.Builder(mContext)
                .setMessage(mReason)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mCallback.onPermissionResult(false, mPermissionCode);
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions((Activity)mContext, new String[]{mPermission}, mPermissionCode);
            }
        }).create().show();
    }
}


