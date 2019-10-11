package com.example.HealthyCampus.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtil {

    public static MaterialDialog.Builder ProgressView(Context context)
    {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)// 初始化建造者
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .titleColor(Color.BLACK)// 内容
                .progress(true, 0);
        return builder;
    }


}
