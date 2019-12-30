package com.example.HealthyCampus.common.constants;

import android.os.Environment;

import com.example.HealthyCampus.BuildConfig;

import java.io.File;

public class ConstantValues {

    //全局配置
    public static final String DEBUG_TAG = "Campus_Health";

    public static final String DIR_ROOT = "Campus_Health";
    public static final String DIR_PICTURE = DIR_ROOT + File.separator + "pictures";

    //接口地址
    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/"; // HOST地址:知乎接口
    public static final String BASE_URL_HEALTH = "http://192.168.2.109:8087/"; // HOST地址:后台接口
    public static final String BASE_URL_GETE = "http://192.168.2.109:8097/GETE/"; // HOST地址:后台接口
    public static final String BASE_URL_BAIDU = "https://aip.baidubce.com/";      // HOST地址:百度API
    public static final String BASE_URL_NEWS = "http://www.cpoha.com.cn";              // HOST地址:养生网

    //服务接口
    public static final String BASE_URL_SCENERY = "https://720.vrqjcs.com/t/a8d628ccc39951e9";              // HOST地址:全景地址
    public static final String BASE_URL_ENGLISH = "http://weixiao.qq.com/apps/public/cet/index.html";       // HOST地址:查询四六级
    public static final String BASE_URL_DELIVER = "http://m.kuaidi100.com/index_all.html";       // HOST地址:查询快递
    public static final String BASE_URL_WEATHER = "http://weather.123.duba.net/static/weather_info/";       // HOST地址:天气


    //医药
//    public static final String BASE_URL_IMG = "http://tnfs.tngou.net/image";

    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;


    //请求码
    public static final int RC_PERMISSION = 0;



    //Leancloud
    public static final String LEANCLOUD_ID = "WIpY3lbfr1v5TH1iotHksBDw-gzGzoHsz";
    public static final String LEANCLOUD_KEY = "80gt4yHgq9VrRcbRU9vak8ES";

    //Bugly
    public static final String BUGLY_ID = "1465e76353";

    //wechat
    public static final String WECHAT_ID = "wxefc902e8f8470d1e";
    public static final String WECHAT_SECRET = "72a5382a3bd2315e7edddcf727b5551a";

    //sina
    public static final String SINA_KEY = "2770119752";
    public static final String SINA_SECRET = "9fcbb5c7b0bc071cf8c184527d2541b4";
    public static final String SINA_REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

    //QQ Qzone
    public static final String TENCENT_ID = "1109884848";
    public static final String TENCENT_SECRET = "hOVIgFQL7vwpEHj1";

    //MobSMSSDK
    public static final String MOBSMSSDK_ID = "2c9b8ddbdb4b2";
    public static final String MOBSMSSDK_SECRET = "71f3a0e022ec6007c72fbf16d2f6b56e";

    //极光
//    public static final String JAPP_KEY = "4771768eedbbaf7afe9b81c5";

    //http请求配置
    public static final boolean HTTP_CACHE_ENABLE = !BuildConfig.DEBUG; // 是否开启OkHTTP缓存
    public static final String HTTP_CACHE_DIR = "httpcache"; // OkHTTP请求缓存目录名称
    public static final int HTTP_CACHE_TIME = 60 * 60 * 72; // OkHTTP请求缓存时间：72小时
    public static final int HTTP_CACHE_MAXSIZE = 1024 * 1024 * 10; // OkHTTP请求缓存大小：10MB

    //app状态
    public static final int STATUS_FORCE_KILLED = -1;
    public static final int STATUS_LOGOUT = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_ONLINE = 2;
    public static final int STATUS_KICK_OUT = 3;


    //参数key
    public static final String KEY_HOME_ACTION = "key_home_action";
    public static final String KEY_HEALTH_ARTICLE_TITLE = "key_health_article_title";
    public static final String KEY_HEALTH_ARTICLE_ID = "key_health_article_id";
    public static final String KEY_HEALTH_ARTICLE_IMAGE = "key_health_article_image";

    //VIEW
    //HomePage
    public static final int VIEW_HEALTH_BANNER = 20;
    public static final int VIEW_HEALTH_SUMMARY = 21;
    public static final int VIEW_HEALTH_DATE = 22;
    public static final int VIEW_HEALTH_DOCOTOR_DISPLAY = 23;

    //Message
    public static final int VIEW_MESSAGE_ITEM = 24;

    //登录
    public static int RESULT_CODE_USERNAME = 1101;
    public static String KEY_USERNAME = "KEY_USERNAME";
    public static final String COUNTRYCODE = "86";

    //聊天
    public final static int TAKE_PHOTO = 1;
    public final static int PICK_PHOTO = 2;
    public final static int MAP_LOCATION = 3;
    public final static int USER_CARD = 4;
    public final static int PICK_FILE = 5;
    public final static int PICK_VEDIO = 6;

    //一周食谱
    public final static int RECIPES_TITLE = -1;
    public final static int RECIPES_CONTENT = -2;

    //食谱推荐
    public static final int RECIPES_RECOMMEDN = 0;
    public static final int RECIPES_TIPS = 1;

    //图片
    public final static String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/health/photo";      //存入SDK内
    public final static String PICTURE_SHOW_PATH = "file:///storage/emulated/0/health/photo/";                  //显示图片
    public final static String PICTURE_SDK_PATH = "/storage/emulated/0/health/photo/";                          //判断是否有文件

    //视频
    public final static String VEDIO_PATH = Environment.getExternalStorageDirectory() + "/health/vedio";        //存入SDK内
    public final static String VEDIO_SDK_PATH = "/storage/emulated/0/health/vedio/";      //判断是否有文件

    //视频之缩略图
    public final static String VEDIO_THUMBNAIL_PATH = Environment.getExternalStorageDirectory() + "/health/vedio/thumbnail";    //存入SDK内
    public final static String VEDIO_SHOW_THUMBNAIL_PATH = "file:///storage/emulated/0/health/vedio/thumbnail/";        //显示图片
    public final static String VEDIO_SDK_THUMBNAIL_PATH = "/storage/emulated/0/health/vedio/thumbnail/";       //判断是否有文件


    public final static String FILE_PATH = Environment.getExternalStorageDirectory() + "/health/file";        //存入SDK内
    public final static String FILE_SDK_PATH = "/storage/emulated/0/health/file/";               //判断是否有文件
    //    聊天
//    public static final int VIEW_CHAT_DIR_LEFT = 1;
//    public static final int VIEW_CHAT_DIR_RIGHT = 2;
    //底部刷新
    public static final int FOOTER_REFRESH = 0;
    public static final int CONTENT_REFRESH = 1;

    public static final int REQUEST_CODE = 0x11;

    //咨询
    public static final int HEALTH_INFORMATION = 0;
//    public static final int HEALTH_ADD = 1;

    public static final int HEALTH_PICTURE_DEFAULT = 2;
    public static final int HEALTH_PICTURE_ITEM = 3;

    public static final int HEALTH_PICTURE_INFOR_DEFAULT = 4;
    public static final int HEALTH_PICTURE_INFOR_ITEM = 5;
}
