package com.example.HealthyCampus.application;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.example.HealthyCampus.BuildConfig;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.source.HomePageRepository;
import com.example.HealthyCampus.common.data.source.local.HomePageLocalDataSource;
import com.example.HealthyCampus.common.data.source.remote.HomePageRemoteDataSource;
import com.example.HealthyCampus.common.utils.StorageManager;
import com.example.HealthyCampus.framework.IAppInitialization;
import com.example.HealthyCampus.module.MainActivity;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * OK
 */
public class HealthMainInit implements IAppInitialization {

    private Context appContext;

    @Override
    public void onAppCreate(Application application) {

        appContext = application.getApplicationContext();

        initLog();
        initStorage();
        initRepository();
        initLeanCloud();
        initBugly();
        initUmengShare();
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
        Beta.smallIconId = R.drawable.logo1;
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
}
