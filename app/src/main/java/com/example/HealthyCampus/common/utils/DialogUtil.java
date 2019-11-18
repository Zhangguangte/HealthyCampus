package com.example.HealthyCampus.common.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;

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
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
        }
    }


}
