package com.example.HealthyCampus.module.Mine.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.CustomizationActivity;
import com.example.HealthyCampus.module.Mine.Service.Lecture.LectureActivity;
import com.example.HealthyCampus.module.Mine.Service.Library.LibraryActivity;
import com.example.HealthyCampus.module.Mine.Service.Weather.WeatherActivity;
import com.example.HealthyCampus.module.Mine.Service.WebView.WebViewActivity;
import com.example.HealthyCampus.module.Mine.Service.timeTable.TimeTableActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class ServiceActivity extends BaseActivity<ServiceContract.View, ServiceContract.Presenter> implements ServiceContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    @BindView(R.id.sceneryLayout)
    LinearLayout sceneryLayout;
    @BindView(R.id.canteenLayout)
    LinearLayout canteenLayout;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service);
    }

    @Override
    protected ServiceContract.Presenter createPresenter() {
        return new ServicePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.mine_service);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @OnClick(R.id.sceneryLayout)
    public void sceneryLayout(View view) {
        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("X-XSS-Protection", "1; mode=block");
        WebViewActivity.actionShow(getContext(), getString(R.string.mine_service_overall_situation), ConstantValues.BASE_URL_SCENERY, extraHeaders);
    }

    @OnClick(R.id.englishLayout)
    public void englishLayout(View view) {
        WebViewActivity.actionShow(getContext(), getString(R.string.mine_service_level_4and6_title), ConstantValues.BASE_URL_ENGLISH, null);
    }

    @OnClick(R.id.deliverLayout)
    public void deliverLayout(View view) {
        WebViewActivity.actionShow(getContext(), getString(R.string.mine_service_deliver_search), ConstantValues.BASE_URL_DELIVER, null);
    }

    @OnClick(R.id.canteenLayout)
    public void canteenLayout(View view) {
        String title = getResources().getStringArray(R.array.recipes_week)[DateUtils.getWeek()];
        CustomizationActivity.actionShow(getContext(), title);
    }

    @OnClick(R.id.weatherLayout)
    public void weatherLayout(View view) {
        WeatherActivity.actionShow(getContext());
    }

    @OnClick(R.id.libraryLayout)
    public void libraryLayout(View view) {
        startActivity(new Intent(this, LibraryActivity.class));
    }

    @OnClick(R.id.lectureLayout)
    public void lectureLayout(View view) {
        startActivity(new Intent(this, LectureActivity.class));
    }

    @OnClick(R.id.timeTableLayout)
    public void timeTableLayout(View view) {
        startActivity(new Intent(this, TimeTableActivity.class));
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public Context getContext() {
        return this;
    }


}
