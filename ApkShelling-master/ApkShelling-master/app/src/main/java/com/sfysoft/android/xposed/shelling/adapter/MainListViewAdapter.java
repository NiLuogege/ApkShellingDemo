package com.sfysoft.android.xposed.shelling.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sfysoft.android.xposed.shelling.R;
import com.sfysoft.android.xposed.shelling.bean.AppBean;
import com.sfysoft.android.xposed.shelling.utils.Constants;
import com.sfysoft.android.xposed.shelling.utils.Key;
import com.sfysoft.android.xposed.shelling.utils.SpUtil;

import java.util.ArrayList;


/**
 * Created by lyh on 2019/2/14.
 */
public class MainListViewAdapter extends BaseAdapter {


    private ArrayList<AppBean> data;


    private Context mContext;

    public MainListViewAdapter(Context context, ArrayList<AppBean> data) {
        this.mContext = context;
        this.data = data;
    }


    public void setData(ArrayList<AppBean> data) {

        this.data = data;

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.activity_list_item, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        AppBean appBean = data.get(position);

        holder.iv_appIcon.setImageBitmap(Constants.drawable2Bitmap(appBean.appIcon));
        holder.tv_appName.setText(appBean.appName);
        holder.tv_packageName.setText(appBean.packageName);


        holder.All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(position);
            }
        });
        return convertView;
    }

    private void Save(int position) {
        String packageName = data.get(position).packageName;
        SpUtil.putString(mContext, Key.APP_INFO, packageName);
        Log.e("MainListViewAdapter", packageName);
        Toast.makeText(mContext, "选择成功,请去xposed中启动插件并请重启手机！", Toast.LENGTH_LONG).show();
    }


//    private void CleanFlag() {
////        FileUtils.SaveLoadPackageFlag("0", Key.ConstructorFlag);
////        FileUtils.SaveLoadPackageFlag("0", Key.OnCreateFlag);
//
//        SpUtil.putString(mContext,Key.OnCreateFlag,"0");
//        SpUtil.putString(mContext,Key.ConstructorFlag,"0");
//    }


    private static class ViewHolder {
        TextView tv_appName, tv_packageName;
        LinearLayout All;
        ImageView iv_appIcon;

        ViewHolder(View convertView) {
            All = convertView.findViewById(R.id.ll_all);
            tv_packageName = convertView.findViewById(R.id.tv_packName);
            tv_appName = convertView.findViewById(R.id.tv_appName);
            iv_appIcon = convertView.findViewById(R.id.iv_appIcon);
        }

        static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
