package com.example.HealthyCampus.common.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.module.MainActivity;

public class DialogUtil {

    //加载中
    public static ProgressDialog progressDialog;

    public static MaterialDialog.Builder ProgressView(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)// 初始化建造者
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .titleColor(Color.BLACK)// 内容
                .progress(true, 0);
        return builder;
    }

    /*自定义消息的加载进度条*/
    public static void showProgressDialog(Context context, String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /*隐藏正在加载的进度条*/
    public static void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void showPopMenu(View view, Context context) {
        PopupMenu menu = new PopupMenu(context, view);
        menu.getMenuInflater().inflate(R.menu.delete_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_item:
                        ToastUtil.show(context, "Delete Selected");
                        break;
                }
                return true;
            }
        });
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                ToastUtil.show(context, "关闭了");
            }
        });
        menu.show();
    }

    public static void AlertDialog(Context context,String message,String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message.trim().replace(" 　　","\n"))
                .setIcon(R.drawable.find_recipes_recommend_dialog);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }




}
