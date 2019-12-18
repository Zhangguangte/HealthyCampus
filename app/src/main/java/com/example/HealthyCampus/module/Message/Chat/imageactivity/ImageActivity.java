package com.example.HealthyCampus.module.Message.Chat.imageactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageLoaderManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SHOW_PATH;


public class ImageActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImmersionBar();
        setContentView(R.layout.chats_image);
        mViewPager = (ViewPager) findViewById(R.id.image_viewpager);
        Intent intent = getIntent();
        List<String> images = intent.getStringArrayListExtra("images");
        int cur = intent.getIntExtra("curPosition", 0);
        mViewPager.setAdapter(new ImageAdapter(this, images));
        mViewPager.setCurrentItem(cur);
    }

    private void initImmersionBar() {
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //白色字体
        StatusBarUtil.setStatusBarDarkTheme(this, false);
    }


    private class ImageAdapter extends PagerAdapter {
        private LayoutInflater inflater;
        private DisplayImageOptions options;
        private Activity imageActivity;
        private List<String> mImages;

        ImageAdapter(Context context, List<String> images) {
            inflater = LayoutInflater.from(context);
            imageActivity = (Activity) context;
            mImages = images;
            options = ImageLoaderManager.getImageOptions();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.chats_pager_image, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    imageActivity.finish();
                }
            });
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            ImageLoader.getInstance().displayImage(PICTURE_SHOW_PATH +mImages.get(position), imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                    Log.v("ImageActivity123", "\n开始imageUri：" + imageUri);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
//                            message = "Input/Output error";
                            message = "资源丢失";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

    }

}
