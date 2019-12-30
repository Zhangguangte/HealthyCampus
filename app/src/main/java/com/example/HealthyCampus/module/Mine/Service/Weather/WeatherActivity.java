package com.example.HealthyCampus.module.Mine.Service.Weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CityAdapter;
import com.example.HealthyCampus.common.adapter.WeatherAdapter;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.WeatherBean;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.utils.XmlParser;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;


public class WeatherActivity extends BaseActivity<WeatherContract.View, WeatherContract.Presenter> implements WeatherContract.View, CityAdapter.onItemClick {


    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvPm)
    TextView tvPm;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvWeather)
    TextView tvWeather;
    @BindView(R.id.tvWind)
    TextView tvWind;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvTemperature)
    TextView tvTemperature;
    @BindView(R.id.weatherLayout)
    LinearLayout weatherLayout;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.rvWeather)
    RecyclerView rvWeather;
    @BindView(R.id.rvCity)
    RecyclerView rvCity;

    private List<WeatherBean> weatherList = new LinkedList<>();

    private WeatherAdapter weatherAdapter;

    private int cityCode = 101210701;   //温州
    private Animation animation;

    private Map<String, String> cityMap = new HashMap<>();

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_weather);
    }

    @Override
    protected WeatherContract.Presenter createPresenter() {
        return new WeatherPresenter();
    }

    @Override
    protected void initView() {
        initBg();
        initWeatherRecyclerView();
    }

    public static void actionShow(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initCityRecyclerView();
        animation = AnimationUtils.loadAnimation(this, R.anim.refresh);
        ivRefresh.startAnimation(animation);//開始动画
        try {
            InputStream is = getAssets().open("city_code.xml");
            cityMap = XmlParser.parseCity(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData();
    }

    @Override
    public void onBackPressed() {
        if (rvCity.isShown()) {
            switchSearch(true);
        } else
            finish();
    }

    private void getData() {
        NetworkManager.getAsync(ConstantValues.BASE_URL_WEATHER + cityCode + ".html", new NetworkManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                ivRefresh.clearAnimation();
            }

            @Override
            public void requestSuccess(String result) {
                String data = result;
                data = data.substring(data.indexOf("(") + 1, data.indexOf(")"));
                Log.e("WeatherActivity" + "123456", "data" + data);
                try {
                    JSONObject rootObject = new JSONObject(data); //得到JSON数据的根对象
                    JSONObject jsObject = rootObject.getJSONObject("weatherinfo"); //取出weatherinfo对应的值
                    WeatherBean weather = new WeatherBean();
                    weather.setDate(rootObject.getString("update_time"));
                    weather.setCity(jsObject.getString("city"));
                    weather.setPm("PM:" + jsObject.getString("pm"));
                    weather.setTemp(jsObject.getString("temp") + "°");
                    weather.setWind(jsObject.getString("wd") + " " + jsObject.getString("ws") + " " + jsObject.getString("pm-level"));
//                    weather.setDate(jsObject.getString("date_y") + " " + jsObject.getString("week"));
                    weather.setHumidity(jsObject.getString("sd"));
                    weather.setPm_color(jsObject.getInt("pm"));
                    weather.setWeather(jsObject.getString("weather1"));
                    int current = DateUtils.chines2Number(jsObject.getString("week").trim());
                    weather.setWeek(current);
                    weather.setTemperature(jsObject.getString("temp1"));
                    weatherList.add(weather);
                    for (int i = 2; i < 7; i++) {
                        weather = new WeatherBean();
                        weather.setWeek(++current % 7);
                        weather.setWeather(jsObject.getString("weather" + i));
                        weather.setTemp(jsObject.getString("temp" + i));
                        weatherList.add(weather);
                    }
                    Log.e("WeatherActivity" + "123456", "000000000000000000");
                    Message message = Message.obtain();
                    myHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ivRefresh.clearAnimation();
                }

            }
        });
    }

    private void initWeatherRecyclerView() {
        weatherAdapter = new WeatherAdapter(getContext(), weatherList);
        rvWeather.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvWeather.setHasFixedSize(true);
        rvWeather.setAdapter(weatherAdapter);
    }

    private void initCityRecyclerView() {
        CityAdapter cityAdapter = new CityAdapter(getContext(), this);
        rvCity.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvCity.setHasFixedSize(true);
        rvCity.setAdapter(cityAdapter);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.ivRefresh)
    public void ivRefresh(View view) {
        ivRefresh.startAnimation(animation);//開始动画
        myHandler.postDelayed(() -> {
            weatherList.clear();
            weatherAdapter.notifyDataSetChanged();
            getData();
        }, 1000);
    }

    @OnClick(R.id.ivSearch)
    public void ivSearch(View view) {
        if (rvCity.isShown()) {
            String cityName = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(cityName)) {
                ToastUtil.show(getContext(), "输入不为空");
            } else {
                String cityCode = cityMap.get(cityName);
                if (TextUtils.isEmpty(cityCode)) {
                    ToastUtil.show(getContext(), "城市输入不合法，请重新输入");
                } else {
                    selectedCity(cityName);
                }
            }
        } else
            switchSearch(false);
    }

    private void initBg() {
        Date d = new Date();
        if (d.getHours() < 18 || d.getHours() > 7) {
            weatherLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.mine_service_weather_bg_day));
        } else {
            weatherLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.mine_service_weather_bg_night));
        }
    }

    /**
     * 更新界面数据
     */
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            tvCity.setText(weatherList.get(0).getCity());
            tvPm.setText(weatherList.get(0).getPm());
            tvTemp.setText(weatherList.get(0).getTemp());
            tvWind.setText(weatherList.get(0).getWind());
//            tvDate.setText(weatherList.get(0).getDate());
            tvDate.setText(weatherList.get(0).getDate().substring(weatherList.get(0).getDate().contains(" ") ? weatherList.get(0).getDate().indexOf(" ") : 0, weatherList.get(0).getDate().lastIndexOf(":")) + "更新");
            tvWeather.setText(weatherList.get(0).getWeather());
            tvHumidity.setText("湿度\t\t" + weatherList.get(0).getHumidity());
            tvTemperature.setText(weatherList.get(0).getTemperature());
            weatherList.remove(0);
            weatherAdapter.notifyDataSetChanged();
            ivRefresh.clearAnimation();

        }
    };

    @Override
    public void selectedCity(String cityName) {
        ivRefresh.startAnimation(animation);//開始动画
        weatherList.clear();
        weatherAdapter.notifyDataSetChanged();
        switchSearch(true);
        cityCode = Integer.parseInt(Objects.requireNonNull(cityMap.get(cityName)));
        getData();
    }

    private void switchSearch(boolean val) {
        if (val) {
            etSearch.setText("");
            searchLayout.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
            ivRefresh.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.GONE);
        } else {
            searchLayout.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
            ivRefresh.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            etSearch.setVisibility(View.VISIBLE);
        }

    }
}
