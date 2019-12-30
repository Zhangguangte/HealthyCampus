package com.example.HealthyCampus.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.HealthyCampus.common.constants.ConstantValues;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PictureUtil {

    private static final float BITMAP_SCALE = 0.4f;


    /**
     * String 转 Bitmap
     */
    public static Bitmap stringToBitmap(String imgString) {
        if (!imgString.equals("")) {
            // 拿到string
            // 利用base64转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);

            ByteArrayInputStream byInput = new ByteArrayInputStream(byteArray);

            // 生成bitmap
            return BitmapFactory.decodeStream(byInput);
        }
        return null;
    }

    /**
     * 将图片转换为字符串
     *
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
        Log.e("ClassifierCame123456", "bitmap" + bitmap);
        // 第一步 将bitmap 转换成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);

        // 利用base64将字节数组转换成字符串
        byte[] byteArray = byStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 图片文件转化成Bitmap
     */
    public static Bitmap fileTobitmap(String filename) {


        File param = new File(filename.replace("file://", ""));
        Log.e("PictureUtil" + "123456", "filename" + filename);
        Log.e("PictureUtil" + "123456", "param.exists" + param.exists());
        Log.e("PictureUtil" + "123456", "param.exists" + param.exists());

        return BitmapFactory.decodeFile(param.getPath());
    }


    /**
     * Bitmap存储到本地
     */
    public static void saveBmpToSd(Bitmap bm, String filename, int quantity, boolean recyle) {
        String dir = ConstantValues.PICTURE_PATH;
        if (bm == null) {
            return;
        }
        Log.e("PictureUtil" + "123456", "1");
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        File file = new File(dir + filename);
        OutputStream outStream = null;
        Log.e("PictureUtil" + "123456", "3");
        try {
            Log.e("PictureUtil" + "123456", "4");
            file.createNewFile();
            outStream = new FileOutputStream(file);
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
                bm.compress(Bitmap.CompressFormat.JPEG, quantity, outStream);
            else
                bm.compress(Bitmap.CompressFormat.PNG, quantity, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.e("PictureUtil" + "123456", "5");
            try {
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (recyle && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }


    //上传图片
    public static Map<String, RequestBody> upImage() {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder.addFormDataPart("file_name", bitmapStr);
//        return builder.build().parts();
        Map<String, RequestBody> params = new HashMap<>();

        File file1 = new File("/storage/emulated/0/Pictures/Screenshots/S61101-215302.jpg");
        File file2 = new File("/storage/emulated/0/Pictures/Screenshots/S00101-081433.jpg");


        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/json"), file1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("application/json"), file2);

        params.put("pictures\";filename=\"" + file1.getName(), requestBody1);
        params.put("pictures\";filename=\"" + file2.getName(), requestBody2);
        return params;

    }

    public static MultipartBody.Part upImage2() {
        File file = new File("/storage/emulated/0/Pictures/Screenshots/S00101-081433.jpg");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        return MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);
    }

    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

}
