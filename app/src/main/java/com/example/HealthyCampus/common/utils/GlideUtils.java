package com.example.HealthyCampus.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.HealthyCampus.R;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class GlideUtils {


    private static RequestOptions requestOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading)
            .error(R.mipmap.picture_lose);

    private static RequestOptions medicineOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading)
            .error(R.mipmap.picture_lose)
            .centerCrop();

    private static RequestOptions diseaseOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading)
            .error(R.mipmap.picture_lose)
            .centerCrop();

    /**
     * glide加载图片
     *
     * @param view view
     * @param url  url
     */
    public static void display(ImageView view, String url) {
        display(view, url, R.drawable.loading, null, false);
    }

    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param defaultImage
     */
    public static void display(ImageView view, String url, @DrawableRes int defaultImage) {
        display(view, url, defaultImage, null, false);
    }

    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param isCircle
     */
    public static void display(ImageView view, String url, boolean isCircle) {
        display(view, url, R.drawable.loading, null, isCircle);
    }

    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param listener
     */
    public static void display(ImageView view, String url, RequestListener listener) {
        display(view, url, R.drawable.loading, listener, false);
    }


    /**
     * glide加载图片
     *
     * @param view         view
     * @param url          url
     * @param defaultImage defaultImage
     */
    public static void display(final ImageView view, String url,
                               @DrawableRes int defaultImage, RequestListener listener, boolean isCircle) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            if (url.endsWith(".gif")) {
                Glide.with(context)
                        .load(url)
                        .apply(requestOptions.dontAnimate())
                        .listener(listener)
                        .into(view);
            } else {
                if (isCircle) {
                    Glide.with(context)
                            .load(url)
                            .apply(requestOptions.transform(new GlideCircleTransform()))
                            .listener(listener)
                            .into(view);
                } else {
                    Glide.with(context)
                            .load(url)
                            .apply(requestOptions)
                            .listener(listener)
                            .into(view);
                }

            }

        } catch (Exception e) {
        }
    }

    private static class GlideCircleTransform extends BitmapTransformation {


        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    public static void displayMedicineImage(ImageView view, String url) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context)
                    .load(url)
                    .apply(medicineOptions) // 参数
                    .into(view);
        } catch (Exception e) {
        }
    }


    public static void displayBlurImage(ImageView view, String url) {

        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            RequestOptions blurOptions = RequestOptions
                    .bitmapTransform(new BlurTransformation(context, 23))
                    .placeholder(R.drawable.loading)
                    .error(R.mipmap.picture_lose)
                    .centerCrop();

            Glide.with(context)
                    .load(url)
                    .apply(blurOptions) // 参数
                    .into(view);
        } catch (Exception e) {
        }
    }


    public static class GlideBlurformation extends BitmapTransformation {
        private Context context;

        GlideBlurformation(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            return blurBitmap(context, toTransform, 3, outWidth, outHeight);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
        }

        /**
         * @param context   上下文对象
         * @param image     需要模糊的图片
         * @param outWidth  输入出的宽度
         * @param outHeight 输出的高度
         * @return 模糊处理后的Bitmap
         */
        @SuppressLint("ObsoleteSdkInt")
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        Bitmap blurBitmap(Context context, Bitmap image, float blurRadius, int outWidth, int outHeight) {
            // 将缩小后的图片做为预渲染的图片
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, outWidth, outHeight, false);
            // 创建一张渲染后的输出图片
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            // 创建RenderScript内核对象
            RenderScript rs = RenderScript.create(context);
            // 创建一个模糊效果的RenderScript的工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
            // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            // 设置渲染的模糊程度, 25f是最大模糊度
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                blurScript.setRadius(blurRadius);
            }
            blurScript.setInput(tmpIn); // 设置blurScript对象的输入内存
            blurScript.forEach(tmpOut);     // 将输出数据保存到输出内存中
            tmpOut.copyTo(outputBitmap); // 将数据填充到Allocation中
            return outputBitmap;
        }

    }

    public static void displayMapImage(ImageView view, int res) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context)
                    .load(res)
                    .apply(requestOptions) // 参数
                    .into(view);
        } catch (Exception e) {
        }

    }


}
