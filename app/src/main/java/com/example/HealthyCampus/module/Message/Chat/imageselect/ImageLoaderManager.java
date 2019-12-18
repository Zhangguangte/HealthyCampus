package com.example.HealthyCampus.module.Message.Chat.imageselect;

import android.graphics.Bitmap;

import com.example.HealthyCampus.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


public class ImageLoaderManager {
    private static DisplayImageOptions mOptions;

    public static DisplayImageOptions getImageOptions() {
        if (null == mOptions) {
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.picture_loading)
                    .showImageForEmptyUri(R.mipmap.chatting_picture)
                    .showImageOnFail(R.mipmap.chatting_picture)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .build();

        }
        return mOptions;
    }


}
