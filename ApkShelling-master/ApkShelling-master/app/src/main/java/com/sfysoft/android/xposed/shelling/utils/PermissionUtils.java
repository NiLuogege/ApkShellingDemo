package com.sfysoft.android.xposed.shelling.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2018/9/18.
 */

public class PermissionUtils {


    //判断哪些权限 未授权
    private static List<String> mPermissionList = new ArrayList<>();


    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,};


    /**
     * 初始化权限的方法
     */
    public static void initPermission(Activity activity) {

        for (String permission : PERMISSIONS) {
            mPermissionList.add(permission);
        }

        ActivityCompat.requestPermissions(activity, mPermissionList.
                toArray(new String[mPermissionList.size()]), 1);

    }


}
