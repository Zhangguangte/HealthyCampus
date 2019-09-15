package com.example.HealthyCampus.module.HomePage.list;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.youth.banner.loader.ImageLoader;

/**
 * OK
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.placeholder_img_loading)
                .crossFade()
                .centerCrop()
                .into(imageView);
        ;
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
