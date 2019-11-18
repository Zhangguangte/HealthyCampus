package com.example.HealthyCampus.common.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.example.HealthyCampus.R;

public class SystemHelper {
    private SystemHelper() {
    }

    /**
     * 调用系统浏览器
     *
     * @param context
     * @param url
     */
    public static void SystemBrowser(Context context, String url) {

        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_uri = Uri.parse(url);
        intent.setData(content_uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.tips_select_browser)));
        }
    }

}
