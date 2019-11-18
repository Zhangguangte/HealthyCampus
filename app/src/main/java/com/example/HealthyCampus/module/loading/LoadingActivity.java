package com.example.HealthyCampus.module.Loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.HealthyCampus.module.Message.Chat.Map.MapActivity;
import com.example.HealthyCampus.module.Mine.Login.LoginActivity;

import butterknife.BindView;

/**
 * OK
 */
public class LoadingActivity extends BaseActivity<LoadingContract.View, LoadingContract.Presenter> implements LoadingContract.View {//, Observer<Long>

    private int i = 0;

    private long mTotalTime = 3;

    @BindView(R.id.background_iv)
    ImageView backgroundIv;
    @BindView(R.id.logo_iv)
    ImageView logoIv;

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
        logoIv.startAnimation(createLogoAnimation());
        backgroundIv.startAnimation(createBackgroundAnimation());
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
        //  startCountDown();
    }

//    /**
//     * 开启倒计时
//     */
//    private void startCountDown() {
//        Observable.interval(1, TimeUnit.SECONDS)
//                .map(aLong -> mTotalTime - aLong)
//                .take(mTotalTime + 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this);
//    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Animation createBackgroundAnimation() {
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
                if (0 < i++) initHandle();
                else {
                    logoIv.startAnimation(createLogoAnimation());
                    backgroundIv.startAnimation(createBackgroundAnimation());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return scaleAnimationa;
    }

    @Override
    public Animation createLogoAnimation() {
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

    @Override
    public void jumpToMain() {
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }


}
