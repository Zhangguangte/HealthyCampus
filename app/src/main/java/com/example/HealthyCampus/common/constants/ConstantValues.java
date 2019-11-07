package com.example.HealthyCampus.common.constants;

import com.example.HealthyCampus.BuildConfig;

import java.io.File;

public class ConstantValues {

    //全局配置
    public static final String DEBUG_TAG = "Campus_Health";

    public static final String DIR_ROOT = "Campus_Health";
    public static final String DIR_PICTURE = DIR_ROOT + File.separator + "pictures";

    //接口地址
    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/"; // HOST地址:知乎接口
    public static final String BASE_URL_HEALTH = "http://192.168.0.104:8083/"; // HOST地址:后台接口

    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;

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
    public static final String JAPP_KEY = "4771768eedbbaf7afe9b81c5";

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
    public static final int VIEW_MESSAGE_NO = 25;

    //登录
    public static int RESULT_CODE_USERNAME = 1101;
    public static String KEY_USERNAME = "KEY_USERNAME";

    public static final String COUNTRYCODE = "86";


}
