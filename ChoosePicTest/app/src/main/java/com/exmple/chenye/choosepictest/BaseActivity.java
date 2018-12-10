package com.exmple.chenye.choosepictest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    private String mReason;
    private int mPermissionCode;
    private String mPermission;
    private PermissionCallback mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(23)
    protected void requestPermissionWithReason(String permission, String reason,
                                               int permissionCode, PermissionCallback callback) {
        mReason = reason;
        mCallback = callback;
        mPermission = permission;
        mPermissionCode = permissionCode;
        // 检查自身是否有此权限
        if (PackageManager.PERMISSION_DENIED == checkSelfPermission(permission)) {
            // 如果没有，就去申请权限
            requestPermissions(new String[]{permission}, mPermissionCode);
        } else {
            // 如果有，则调用callback
            callback.onPermissionResult(true, mPermissionCode);
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
            mCallback.onPermissionResult(true, mPermissionCode);
        } else {
            // 权限申请失败时，判断是否需要告诉用户原因
            if (shouldShowRequestPermissionRationale(permissions[0])) {
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
        new AlertDialog.Builder(this)
                .setMessage(mReason)
                .setNegativeButton("取消", (dialog, which) -> {
                    // 用户点击取消时，返回权限被拒绝
                    dialog.dismiss();
                    mCallback.onPermissionResult(false, mPermissionCode);
                })
                .setPositiveButton("确定", ((dialog, which) -> {
                    // 用户点击确定时，继续申请
                    dialog.dismiss();
                    requestPermissions(new String[]{mPermission}, mPermissionCode);
                }))
                .create().show();
    }

    // 权限申请回调的接口类
    public interface PermissionCallback {
        void onPermissionResult(boolean result, int permissionCode);
    }

}
