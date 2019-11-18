package com.example.HealthyCampus.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.example.HealthyCampus.BuildConfig;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.source.local.FriendLocalDataSource;
import com.example.HealthyCampus.common.data.source.local.HomePageLocalDataSource;
import com.example.HealthyCampus.common.data.source.local.MessageLocalDataSource;
import com.example.HealthyCampus.common.data.source.local.UserLocalDataSource;
import com.example.HealthyCampus.common.data.source.remote.FriendRemoteDataSource;
import com.example.HealthyCampus.common.data.source.remote.HomePageRemoteDataSource;
import com.example.HealthyCampus.common.data.source.remote.MessageRemoteDataSource;
import com.example.HealthyCampus.common.data.source.remote.UserRemoteDataSource;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.data.source.repository.HomePageRepository;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.utils.StorageManager;


import com.example.HealthyCampus.framework.IAppInitialization;
import com.example.HealthyCampus.framework.helper.MySQLiteOpenHelper;
import com.example.HealthyCampus.greendao.DaoMaster;
import com.example.HealthyCampus.module.MainActivity;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;


/**
 * OK
 */
public class HealthMainInit implements IAppInitialization {

    private Context appContext;
    public MySQLiteOpenHelper helper;
    private DaoMaster master;

    @Override
    public void onAppCreate(Application application) {

        appContext = application.getApplicationContext();
        initLog();
        initStorage();
        initRepository();
        initLeanCloud();
        initBugly();
        initUmengShare();
        initJPush();
        setupDatabase(); //初始化数据库
        initCamera();
        initImageLoader();
    }

    private void initLog() {
        Logger.init(ConstantValues.DEBUG_TAG);
    }

    private void initStorage() {
        StorageManager.getInstance().setAppContext(appContext);
        StorageManager.getInstance().setRootDir(ConstantValues.DIR_ROOT);
        StorageManager.getInstance().setImageDir(ConstantValues.DIR_PICTURE);
    }

    private void initRepository() {
        HomePageRepository.initialize(HomePageRemoteDataSource.getInstance(), HomePageLocalDataSource.getInstance(appContext));
        UserRepository.initialize(UserRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(appContext));
        MessageRepository.initialize(MessageRemoteDataSource.getInstance(), MessageLocalDataSource.getInstance(appContext));
        FriendRepository.initialize(FriendRemoteDataSource.getInstance(), FriendLocalDataSource.getInstance(appContext));

//        NewsRepository.initialize(NewsRemoteDataSource.getInstance(), NewsLocalDataSource.getInstance(appContext));
//        WechatRepository.initialize(WechatRemoteDataSource.getInstance(), WechatLocalDataSource.getInstance(appContext));
//        ZhihuRepository.initialize(ZhihuRemoteDataSource.getInstance(), ZhihuLocalDataSource.getInstance(appContext));
//        CommonRepository.initialize(CommonRemoteDataSource.getInstance(), CommonLocalDataSource.getInstance(appContext));
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(appContext, ConstantValues.LEANCLOUD_ID, ConstantValues.LEANCLOUD_KEY);
        AVAnalytics.enableCrashReport(appContext, true);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
    }

    private void initBugly() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.showInterruptedStrategy = true;
        Beta.initDelay = 8 * 1000;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.smallIconId = R.drawable.ic_app_round_logo;
        Bugly.init(appContext, ConstantValues.BUGLY_ID, BuildConfig.DEBUG);
        //CrashReport.testJavaCrash();
    }


    private void initUmengShare() {
        PlatformConfig.setWeixin(ConstantValues.WECHAT_ID, ConstantValues.WECHAT_SECRET);
        PlatformConfig.setSinaWeibo(ConstantValues.SINA_KEY, ConstantValues.SINA_SECRET);
        PlatformConfig.setQQZone(ConstantValues.TENCENT_ID, ConstantValues.TENCENT_SECRET);
        Config.REDIRECT_URL = ConstantValues.SINA_REDIRECT_URL;
        Config.DEBUG = BuildConfig.DEBUG;
        Config.isJumptoAppStore = true;
        UMShareAPI.get(appContext);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);   //开启极光调试
        JPushInterface.init(appContext);   //实例化极光推送
        JMessageClient.init(appContext, true);     //实例化极光IM,并自动同步聊天记录
        JMessageClient.setDebugMode(true);
//        SMSSDK.getInstance().initSdk(mContext);    //初始化极光sms
        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);  //通知管理,通知栏开启，其他关闭


        //初始化utils
//        Utils.init(this);

//        //推送状态
//        SPHelper.setBoolean(JPUSH_ROAMING, true);
//        SPHelper.setBoolean(JPUSH_MUSIC, false);
//        SPHelper.setBoolean(JPUSH_VIB, false); /*震动开启状态*/
//        SPHelper.setString("JAPP_KEY", JAPP_KEY);
    }

    private void setupDatabase() {
        //是否开启调试
        MigrationHelper.DEBUG = true;
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        //数据库升级
        helper = new MySQLiteOpenHelper(appContext, "text");
        master = new DaoMaster(helper.getWritableDb());
    }

    private void initCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void initImageLoader() {
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(appContext);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(appContext)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .threadPriority(Thread.MAX_PRIORITY) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(100 * 1024 * 1024) //100m
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


}
