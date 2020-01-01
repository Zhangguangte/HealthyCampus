package com.example.HealthyCampus.module.Loading;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.utils.AppStatusTracker;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.MainActivity;
import com.example.HealthyCampus.module.Mine.Login.LoginActivity;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import butterknife.BindView;

/**
 * OK
 */
public class LoadingActivity extends BaseActivity<LoadingContract.View, LoadingContract.Presenter> implements LoadingContract.View {//, Observer<Long>

    @BindView(R.id.background_iv)
    ImageView backgroundIv;
    @BindView(R.id.logo_iv)
    ImageView logoIv;


    /**
     * 计步操作
     */

    private long TIME_INTERVAL_REFRESH = 3000;
    private static final int REFRESH_STEP_WHAT = 0;

    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private int mStepSum;

    private ISportStepInterface iSportStepInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected LoadingContract.Presenter createPresenter() {
        return new LoadingPresenter();
    }

    @Override
    protected void initView() {
        initStepCounter();
        initHandle();
//        logoIv.startAnimation(createLogoAnimation());
//        backgroundIv.startAnimation(createBackgroundAnimation());
    }

    /**
     * 计步器初始化
     */
    private void initStepCounter() {
        //初始化计步模块
        TodayStepManager.startTodayStepService(getApplication());
        //开启计步Service，同时绑定Activity进行aidl通信
        Intent intent = new Intent(this, TodayStepService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mStepSum = iSportStepInterface.getCurrentTimeSportStep();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

        //计时器
//        mhandmhandlele.post(timeRunable);
        //        //获取所有步数列表
//        if (null != iSportStepInterface) {
//            try {
//                String stepArray = iSportStepInterface.getTodaySportStepArray();
//                mStepArrayTextView.setText(stepArray);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void initHandle() {
        Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
        if (SPHelper.getString(SPHelper.ACCOUNT).equals("")) {
            intent.setClass(LoadingActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(getContext(), "登陆成功");
            intent.setClass(LoadingActivity.this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public Context getContext() {
        return this;
    }

    private Animation createBackgroundAnimation() {
        ScaleAnimation scaleAnimationa = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationa.setFillAfter(true);
        scaleAnimationa.setDuration(3000);
        scaleAnimationa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (0 < i++) initHandle();
//                else {
//                    logoIv.startAnimation(createLogoAnimation());
//                    backgroundIv.startAnimation(createBackgroundAnimation());
//                }
                initHandle();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return scaleAnimationa;
    }

    private Animation createLogoAnimation() {
        ScaleAnimation tvScaleAnim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation tvTranslateAnim = new TranslateAnimation(0, 0, 0, -40);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(tvScaleAnim);
        animationSet.addAnimation(tvTranslateAnim);
        animationSet.setDuration(3000);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STEP_WHAT: {
                    //每隔500毫秒获取一次计步数据刷新UI
                    if (null != iSportStepInterface) {
                        int step = 0;
                        try {
                            step = iSportStepInterface.getCurrentTimeSportStep();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        if (mStepSum != step) {
                            mStepSum = step;
                        }
                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);
                    break;
                }
            }
            return false;
        }
    }

//    /*****************计时器*******************/
//    private Runnable timeRunable = new Runnable() {
//        @Override
//        public void run() {
//            currentSecond = currentSecond + 1000;
//            //是否暂停
//            //递归调用本runable对象，实现每隔一秒一次执行任务
//            mhandmhandlele.postDelayed(this, 1000);
//        }
//    };

    //计时器
//    private Handler mhandmhandlele = new Handler();
//    private long currentSecond = 0;//当前毫秒数
}
