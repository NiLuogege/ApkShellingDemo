package com.sfysoft.android.xposed.shelling;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sfysoft.android.xposed.shelling.adapter.MainListViewAdapter;
import com.sfysoft.android.xposed.shelling.bean.AppBean;
import com.sfysoft.android.xposed.shelling.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niluogege on 2020/4/27.
 */
public class MainActivity extends AppCompatActivity {
    private ListView mLv_list;
    private ArrayList<AppBean> mAllPackageList = new ArrayList<>();
    private ArrayList<AppBean> CommonPackageList = new ArrayList<>();



    private CheckBox mCb_checkbox;
    private MainListViewAdapter mMainListViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initData();
        initView();
        PermissionUtils.initPermission(this);
    }

    private void initData() {
        mAllPackageList = getPackageList();
    }

    private void initView() {
        mLv_list = (ListView) findViewById(R.id.lv_list);

        mCb_checkbox = (CheckBox) findViewById(R.id.cb_checkbox);
        mMainListViewAdapter = new MainListViewAdapter(this, CommonPackageList);
        mCb_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //需要显示 系统 app
                    mMainListViewAdapter.setData(mAllPackageList);
                } else {
                    mMainListViewAdapter.setData(CommonPackageList);
                }
            }
        });

        mLv_list.setAdapter(mMainListViewAdapter);
    }

    public ArrayList<AppBean> getPackageList() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        ArrayList<AppBean> appBeans = new ArrayList<>();

        for (PackageInfo packageInfo : packages) {
            AppBean appBean = new AppBean();
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                appBean.isSystemApp = false;
            } else {
                // 系统应用
                appBean.isSystemApp = true;
            }
            appBean.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            appBean.packageName = packageInfo.packageName;
            appBean.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());

            appBeans.add(appBean);

            if (!appBean.isSystemApp) {
                CommonPackageList.add(appBean);
            }

        }
        return appBeans;
    }
}
