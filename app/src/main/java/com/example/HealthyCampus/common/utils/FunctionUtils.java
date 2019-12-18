package com.example.HealthyCampus.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.example.HealthyCampus.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.example.HealthyCampus.common.constants.ConstantValues.FILE_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.FILE_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_SDK_THUMBNAIL_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_THUMBNAIL_PATH;

public class FunctionUtils {

    public static Map<String, String> MIME_Table = new HashMap<String, String>() {
        {
            put(".3gp", "video/3gpp");
            put(".apk", "application/vnd.android.package-archive");
            put(".asf", "video/x-ms-asf");
            put(".avi", "video/x-msvideo");
            put(".bin", "application/octet-stream");
            put(".bmp", "image/bmp");
            put(".c", "text/plain");
            put(".class", "application/octet-stream");
            put(".conf", "text/plain");
            put(".cpp", "text/plain");
            put(".doc", "application/msword");
            put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            put(".xls", "application/vnd.ms-excel");
            put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put(".exe", "application/octet-stream");
            put(".gif", "image/gif");
            put(".gtar", "application/x-gtar");
            put(".gz", "application/x-gzip");
            put(".h", "text/plain");
            put(".htm", "text/html");
            put(".html", "text/html");
            put(".jar", "application/java-archive");
            put(".java", "text/plain");
            put(".jpeg", "image/jpeg");
            put(".jpg", "image/jpeg");
            put(".js", "application/x-javascript");
            put(".log", "text/plain");
            put(".m3u", "audio/x-mpegurl");
            put(".m4a", "audio/mp4a-latm");
            put(".m4b", "audio/mp4a-latm");
            put(".m4p", "audio/mp4a-latm");
            put(".m4u", "video/vnd.mpegurl");
            put(".m4v", "video/x-m4v");
            put(".mov", "video/quicktime");
            put(".mp2", "audio/x-mpeg");
            put(".mp3", "audio/x-mpeg");
            put(".mp4", "video/mp4");
            put(".mpc", "application/vnd.mpohun.certificate");
            put(".mpe", "video/mpeg");
            put(".mpeg", "video/mpeg");
            put(".mpg", "video/mpeg");
            put(".mpg4", "video/mp4");
            put(".mpga", "audio/mpeg");
            put(".msg", "application/vnd.ms-outlook");
            put(".ogg", "audio/ogg");
            put(".pdf", "application/pdf");
            put(".png", "image/png");
            put(".pps", "application/vnd.ms-powerpoint");
            put(".ppt", "application/vnd.ms-powerpoint");
            put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            put(".prop", "text/plain");
            put(".rc", "text/plain");
            put(".rmvb", "audio/x-pn-realaudio");
            put(".rtf", "application/rtf");
            put(".sh", "text/plain");
            put(".tar", "application/x-tar");
            put(".tgz", "application/x-compressed");
            put(".txt", "text/plain");
            put(".wav", "audio/x-wav");
            put(".wma", "audio/x-ms-wma");
            put(".wmv", "audio/x-ms-wmv");
            put(".wps", "application/vnd.ms-works");
            put(".xml", "text/plain");
            put(".z", "application/x-compress");
            put(".zip", "application/x-zip-compressed");
            put("", "*/*");
        }
    };


    public static void openGalleryAudio(Activity mContext, int requstcode) {
        // 进入相册 不需要的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                ///  .selectionMode(1 > 1 ?
                //         PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                // .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                .videoMinSecond(1)
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(requstcode);//结果回调onActivityResult code
    }

    //打开文件
    public static void openFile(Activity mContext, int requestCode) {
        new MaterialFilePicker()
                .withActivity(mContext)
                .withRequestCode(requestCode)
                //       .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    //为视频文件设置缩略图
    public static void createThumbnail(String filePath, String urlpath) {
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(filePath);
            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
            File file = new File(VEDIO_THUMBNAIL_PATH);
            if (!file.exists())
                file.mkdirs();
            File f = new File(VEDIO_SDK_THUMBNAIL_PATH + urlpath);
            LogUtil.logE("FunctionUtils" + "123456", "f：" + f.exists());
            LogUtil.logE("FunctionUtils" + "123456", "file：" + file.exists());
            LogUtil.logE("FunctionUtils" + "123456", "urlpath：" + urlpath);

            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
            out.flush();
            out.close();
        } catch (RuntimeException e) {
            LogUtil.logE("FunctionUtils" + "123456", "视频文件已损坏");
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.logE("FunctionUtils" + "123456", "视频缩略图路径获取失败：" + e.toString());
            e.printStackTrace();
        }
    }

    //调用高德地图
    public static void callGaudMap(Context context, String address, String j, String w) {
        try {
            // 高德地图 先经度——后维度
            Intent intent = Intent
                    .getIntent("androidamap://viewMap?sourceApplication=校园健康"
                            + "&poiname="
                            + address
                            + "&lat="
                            + j
                            + "&lon="
                            + w
                            + "&dev=0");
            if (PackageManagerUtil.haveGaodeMap()) {
                context.startActivity(intent);
                ToastUtil.show(context, "高德地图正在启动");
            } else {
                ToastUtil.show(context, "高德地图没有安装");
                Intent i = new Intent();
                i.setData(Uri.parse("http://daohang.amap.com/index.php?id=201&CustomID=C021100013023"));    //跳转至浏览器，下载高德地图
                i.setAction(Intent.ACTION_VIEW);
                context.startActivity(i); //启动浏览器
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void saveVideoToSDK(String filePath, String mVoicePath) {
        //创建输入输出
        InputStream isFrom = null;
        OutputStream osTo = null;
        File file = new File(VEDIO_PATH);
        if (!file.exists())
            file.mkdirs();
        try {
            //设置输入输出流
            isFrom = new FileInputStream(filePath);
            osTo = new FileOutputStream(VEDIO_PATH + File.separator + mVoicePath);
            byte bt[] = new byte[1024];
            int len;
            while ((len = isFrom.read(bt)) != -1) {
                Log.e("ChatActivity" + "123456", "len = " + len);
                osTo.write(bt, 0, len);
            }
            Log.e("ChatActivity" + "123456", "保存录音完成。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osTo != null) {
                try {
                    //不管是否出现异常，都要关闭流
                    osTo.close();
                    Log.e("ChatActivity" + "123456", "关闭输出流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isFrom != null) {
                try {
                    isFrom.close();
                    Log.d("ChatActivity" + "123456", "关闭输入流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void saveFileToSDK(String filePath, String mFilePath) {
        //创建输入输出
        InputStream isFrom = null;
        OutputStream osTo = null;
        File file = new File(FILE_PATH);
        if (!file.exists())
            file.mkdirs();
        try {
            //设置输入输出流
            isFrom = new FileInputStream(filePath);
            osTo = new FileOutputStream(FILE_PATH + File.separator + mFilePath);
            byte bt[] = new byte[1024];
            int len;
            while ((len = isFrom.read(bt)) != -1) {
                Log.e("ChatActivity" + "123456", "len = " + len);
                osTo.write(bt, 0, len);
            }
            Log.e("ChatActivity" + "123456", "保存录音完成。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osTo != null) {
                try {
                    //不管是否出现异常，都要关闭流
                    osTo.close();
                    Log.e("ChatActivity" + "123456", "关闭输出流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isFrom != null) {
                try {
                    isFrom.close();
                    Log.d("ChatActivity" + "123456", "关闭输入流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    public static void openFile(Context context, String path, File file) {
        Intent intent = null;
        if (!TextUtils.isEmpty(path)) {
            if (path.endsWith(".html")) {        //打开HTML文件
                intent = new Intent("android.intent.action.VIEW");
                Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
                intent.setDataAndType(uri, "text/html");
            } else {
                intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //用于音频文件
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
                Uri uri = Uri.fromFile(file);
                Log.e("FunctionUtils" + "123456", "FileUtils.getFileName(path):" + FileUtils.getFileType(path));
                String type = MIME_Table.get(FileUtils.getFileType(path));
                if (null == type || "".equals(type)) {
                    ToastUtil.show(context, "没有找到打开该文件的应用程序");
                    return;
                } else {
                    intent.setDataAndType(uri, type);
                }
                Log.e("FunctionUtils" + "123456", "type:" + type);
            }
        }
        if (null == intent) {
            ToastUtil.show(context, "没有找到打开该文件的应用程序");
        } else {
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FunctionUtils" + "123456", "ActivityNotFoundException");
//                ToastUtil.show(context, "没有找到打开该文件的应用程序");
            }
        }
    }

    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }


}
