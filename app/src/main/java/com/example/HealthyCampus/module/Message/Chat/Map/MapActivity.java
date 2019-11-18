package com.example.HealthyCampus.module.Message.Chat.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.AmapListAdapter;
import com.example.HealthyCampus.common.data.form.MapForm;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.custom_dialog.MapDialog;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.VISIBLE;
import static cn.jpush.im.android.api.model.UserInfo.Field.address;


public class MapActivity extends BaseActivity<MapContract.View, MapContract.Presenter> implements MapContract.View, AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener, AmapListAdapter.onItemClick {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvFunction)
    TextView tvFunction;
    @BindView(R.id.mvMap)
    MapView mMapView;
    @BindView(R.id.rvResult)
    RecyclerView rvResult;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.ivNow)
    ImageView ivNow;
    @BindView(R.id.ivMove)
    ImageView ivMove;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnCanel)
    Button btnCanel;
    @BindView(R.id.currentAddress)
    TextView currentAddress;
    @BindView(R.id.mapLayout)
    RelativeLayout mapLayout;
    @BindView(R.id.moveLayout)
    LinearLayout moveLayout;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.moveAddress)
    TextView moveAddress;

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private AmapListAdapter amapListAdapter;
    private AMap aMap;
    private Marker locMarker;
    private PoiSearch.Query query;
    private BitmapDescriptor nowDescripter, movingDescriptor;
    private List<PoiItem> pois = new ArrayList<>();
    private String cityCode = "";
    private PoiSearch poiSearch;
    private InputMethodManager mImm;
    private StringBuilder stringBuilder = new StringBuilder();
    private LatLng nowlatLng, moveLatLng;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.chats_map);
    }

    @Override
    protected MapContract.Presenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initMap();
        initEdit();
        initRecyleView();
    }

    private void setCustomActionBar() {
        tvTitle.setText("当前定位");
        ivBack.setVisibility(VISIBLE);
        tvFunction.setText("发送");
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    private void initRecyleView() {
        amapListAdapter = new AmapListAdapter(this, pois, this);
        rvResult.setAdapter(amapListAdapter);
        rvResult.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvResult.setHasFixedSize(true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        initLocation();
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        aMap = mMapView.getMap();
        aMap.setLocationSource(this);// 设置定位监听
        initLocationMark();


        if (locMarker == null) {
            locMarker = aMap.addMarker(new MarkerOptions());
        }
//        else {
//            locMarker.remove();
//        }
//        locMarker.setIcon(movingDescriptor);

        //ChatActivity跳转传来目标定位数据的处理
        Intent intent = getIntent();
        MapForm mapForm = intent.getParcelableExtra("map");
        if (null != mapForm) {
            Log.e("MapActivity123456:", "mapForm.toString():" + mapForm.toString());
            String loca[] = mapForm.location.split(",");
            for (int i = 0; i < loca.length; i++) {
                Log.e("MapActivity123456:", "loca[" + i + "]:" + loca[i]);
            }
            Log.e("MapActivity123456:", " mapForm.location:" + mapForm.location);
            moveLatLng = new LatLng(Double.valueOf(loca[0]), Double.valueOf(loca[1]));

            aMap.clear(true);
            etSearch.setText("");
            //图标
            MarkerOptions centerMarkerOption = new MarkerOptions().position(moveLatLng).icon(movingDescriptor);
            locMarker = aMap.addMarker(centerMarkerOption);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveLatLng, 15));
            //视图
            showMapHidden(false);
            amapListAdapter.clearAll();
            ivClear.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnCanel.setVisibility(View.GONE);
            //位置
            moveLayout.setVisibility(VISIBLE);
            stringBuilder.setLength(0);
            stringBuilder.append(mapForm.address);
            moveAddress.setText(stringBuilder.toString());
        }
    }

    private void initLocationMark()
    {
        //定位图标
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);//高德地图标志的摆放位置
        //放大缩小控件
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//地图缩放控件的摆放位置
        //目前位置图标
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        locationStyle.myLocationIcon(nowDescripter);// 设置小蓝点的图标
        aMap.setMyLocationStyle(locationStyle);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    //位置查询
    private void poiSearch() {
        Log.e("MapActivity123456:", etSearch.getText().toString().trim());
        query = new PoiSearch.Query(etSearch.getText().toString().trim(), "", cityCode);
        query.setPageSize(15);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        poiSearch.setQuery(query);
        poiSearch.searchPOIAsyn();
    }

    //初始化定位数据
    private void initLocation() {
        //初始化client
        mlocationClient = new AMapLocationClient(this.getApplicationContext());
        // 设置定位监听
        mlocationClient.setLocationListener(this);
        //定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置为高精度定位模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setInterval(1000);
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mlocationClient.startLocation();
    }

    //初始化图标
    private void initMap() {
        // initLocation();//初始化定位参数
        nowDescripter = BitmapDescriptorFactory.fromResource(R.drawable.now_location);
        movingDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_location);
        poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
    }

    //定位改变的处理
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null
                && aMapLocation.getErrorCode() == 0) {
            double longitude = aMapLocation.getLongitude();
            double latitude = aMapLocation.getLatitude();
//            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            LatLng location = new LatLng(latitude, longitude);
            changeLocation(location);
//            poiSearch();
            nowlatLng = location;
            cityCode = aMapLocation.getCityCode();
            currentAddress.setText(aMapLocation.getAddress());
            mListener.onLocationChanged(aMapLocation);      //为定位图标设置相应的处理，aMapLocation为点击图标的位置
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            stringBuilder.setLength(0);
            currentAddress.setText(stringBuilder.toString());
            Log.e("AmapActivity123456", errText);
        }
        aMap.clear(true);
        Log.e("AmapActivity123456", "***********************************");
    }

    //改变图片的Mark
    private void changeLocation(LatLng location) {
        MarkerOptions centerMarkerOption = new MarkerOptions().position(location).icon(nowDescripter);
        locMarker = aMap.addMarker(centerMarkerOption);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));       //移动地图
    }


    private void initEdit() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivClear.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
                btnCanel.setVisibility(s.toString().length() > 0 ? View.GONE : View.VISIBLE);
                btnSearch.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showMapHidden(hasFocus);
                showSearchHidden(etSearch.getText().toString().trim().length() <= 0);

            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapHidden(true);
                showSearchHidden(etSearch.getText().toString().trim().length() <= 0);
            }
        });

    }


    @OnClick(R.id.ivClear)
    public void ivClear() {
        etSearch.setText("");
        amapListAdapter.clearAll();
    }

    @OnClick(R.id.btnSearch)
    public void btnSearch() {
        poiSearch();
        softInputHide();
    }


    @Override
    public void onBackPressed() {
    }


    @OnClick(R.id.btnCanel)
    public void btnCanel() {
        showMapHidden(false);
        ivClear.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        btnCanel.setVisibility(View.GONE);
        softInputHide();
    }

    @OnClick(R.id.ivNow)
    public void ivNow() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowlatLng, 15)); tvTitle.setText("当前定位");
    }

    @OnClick(R.id.ivMove)
    public void ivMove() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveLatLng, 15)); tvTitle.setText("目标定位");
    }


    @OnClick(R.id.tvFunction)
    public void tvFunction() {
        if (null == moveLatLng) {
            MapForm mapForm = new MapForm(currentAddress.getText().toString().trim(), nowlatLng);
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("map", mapForm);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mMapView)
            mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        if (null == mListener) {
            mListener = onLocationChangedListener;      //定位所需的监听
            initLocation();
        }
    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    private void softInputHide() {
        mImm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (pois == null) {
                    pois = poiResult.getPois();
                } else {
                    pois.clear();
                    pois.addAll(poiResult.getPois());
                }
                if (pois != null && pois.size() > 0) {
                    rvResult.setVisibility(VISIBLE);
                    if (amapListAdapter == null) {
                        amapListAdapter = new AmapListAdapter(this, pois, this);
                        rvResult.setAdapter(amapListAdapter);
                    } else {
                        amapListAdapter.refresh();
                    }
//
                } else if (pois != null && pois.size() == 0) {
                    rvResult.setVisibility(View.GONE);
                    ToastUtil.show(this, "没有找到您想要的结果");
                    Log.e("MapActivity" + "123456", "-------------------------------------------");
                }

            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    //地图和查询RecycleView的显隐
    private void showMapHidden(boolean val) {
        if (val) {
            rvResult.setVisibility(View.VISIBLE);
            mapLayout.setVisibility(View.GONE);
        } else {
            rvResult.setVisibility(View.GONE);
            mapLayout.setVisibility(View.VISIBLE);
        }
    }

    //查询控件的显隐
    private void showSearchHidden(boolean val) {
        if (val) {
            ivClear.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnCanel.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            btnCanel.setVisibility(View.GONE);
        }
    }

    //目标定位和当前定位的选择框
    public void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_map, null);
        MapDialog mapDialog = new MapDialog(this,view, R.style.DialogMap);
        mapDialog.getWindow().findViewById(R.id.ivNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapForm mapForm = new MapForm(currentAddress.getText().toString().trim(), nowlatLng);
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("map", mapForm);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                mapDialog.dismiss();
                finish();
            }
        });
        mapDialog.getWindow().findViewById(R.id.ivMove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapForm   mapForm = new MapForm(stringBuilder.toString().trim(), moveLatLng);
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("map", mapForm);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                mapDialog.dismiss();
                finish();
            }
        });
        mapDialog.show();
    }

    //回调，目标定位
    @Override
    public void relocation(String address, LatLng latLng) {
        aMap.clear(true);
        etSearch.setText("");
        //图标
        MarkerOptions centerMarkerOption = new MarkerOptions().position(latLng).icon(movingDescriptor);
        locMarker = aMap.addMarker(centerMarkerOption);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //视图
        showMapHidden(false);
        amapListAdapter.clearAll();
        ivClear.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        btnCanel.setVisibility(View.GONE);
        //位置
        moveLayout.setVisibility(VISIBLE);
        stringBuilder.setLength(0);
        stringBuilder.append(address);
        moveAddress.setText(stringBuilder.toString());
        moveLatLng = latLng;
        tvTitle.setText("目标定位");
    }
}
