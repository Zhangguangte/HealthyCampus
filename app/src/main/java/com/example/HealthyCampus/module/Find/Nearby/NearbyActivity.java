package com.example.HealthyCampus.module.Find.Nearby;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.amap.api.maps.AMapUtils;
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
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.RoadDetailAdapter;
import com.example.HealthyCampus.common.adapter.RoadListAdapter;
import com.example.HealthyCampus.common.data.Bean.RoadDetailBean;
import com.example.HealthyCampus.common.utils.FunctionUtils;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class NearbyActivity extends BaseActivity<NearbyContract.View, NearbyContract.Presenter> implements NearbyContract.View, AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener, RoadListAdapter.onItemClick {


    //自定义标题
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;       //标题布局
    @BindView(R.id.tvTitle)
    TextView tvTitle;               //标题
    @BindView(R.id.tvSwitch)
    TextView tvSwitch;              //切换（医院/药店）
    @BindView(R.id.ivFunction)
    ImageView ivFunction;           //退出路线
    @BindView(R.id.ivList)
    TextView ivList;                //列表
    @BindView(R.id.ivMap)
    TextView ivMap;                 //地图

    @BindView(R.id.rvList)
    RecyclerView rvList;                    //附近列表

    @BindView(R.id.mvMap)
    MapView mMapView;                       //地图

    //详细标题
    @BindView(R.id.DetailTitleLayout)
    LinearLayout DetailTitleLayout;             //详细标题布局
    @BindView(R.id.tvDetailDistant)
    TextView tvDetailDistant;                   //总距离
    @BindView(R.id.tvDetailCostTime)
    TextView tvDetailCostTime;                  //花费时间
    @BindView(R.id.ivTitleWay)
    ImageView ivTitleWay;                       //类型图标
    @BindView(R.id.rvDetail)
    RecyclerView rvDetail;                      //路线详细

    //底部布局
    @BindView(R.id.roadLayout)
    LinearLayout roadLayout;                    //底部布局
    @BindView(R.id.PositionInfLayout)
    LinearLayout PositionInfLayout;             //位置信息布局

    //大概信息
    @BindView(R.id.movePositionLayout)
    RelativeLayout movePositionLayout;          //大概信息布局
    @BindView(R.id.tvName)
    TextView tvName;                            //地点名称
    @BindView(R.id.tvDistant)
    TextView tvDistant;                         //总距离
    @BindView(R.id.tvSnippet)
    TextView tvSnippet;                         //地址摘要


    //底部按钮
    @BindView(R.id.roadButton)
    LinearLayout roadButton;                    //路线按钮
    @BindView(R.id.detailButton)
    LinearLayout detailButton;                  //查看详细按钮
    @BindView(R.id.showMapButton)
    LinearLayout showMapButton;                 //显示地图按钮
    @BindView(R.id.callButton)
    LinearLayout callButton;                 //调用高得


    //位置信息
    @BindView(R.id.PositionLayout)
    LinearLayout PositionLayout;            //位置信息布局
    @BindView(R.id.currentPosition)
    TextView currentPosition;               //当前位置
    @BindView(R.id.movePosition)
    TextView movePosition;                  //移动位置
    @BindView(R.id.EndLayout)
    LinearLayout EndLayout;                  //目标位置
    @BindView(R.id.StartLayout)
    LinearLayout StartLayout;                //当前位置

    @BindView(R.id.ivEnd)
    ImageView ivEnd;                         //移动位置图标
    @BindView(R.id.btnWalk)
    Button btnWalk;                         //走路路线
    @BindView(R.id.btnCar)
    Button btnCar;                         //汽车路线


    //路径信息
    @BindView(R.id.tvCostTitle)
    TextView tvCostTitle;           //路径信息标题
    @BindView(R.id.CostLayout)
    LinearLayout CostLayout;        //路径信息布局
    @BindView(R.id.ivWay)
    ImageView ivWay;                //站台图标
    @BindView(R.id.tvCostTime)
    TextView tvCostTime;            //花费时间
    @BindView(R.id.tvRoadCount)
    TextView tvRoadCount;           //经过路口


    //地图配置数据
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;

    private AMap aMap;
    private Marker marker;                                  //图标
    private MarkerOptions markerOption;                     //marker的设置数据
    private PoiSearch.Query query;                          //查询语句

    private BitmapDescriptor nowDescripter, walkDescripter, driverDescripter;         //自定义图标

    private String cityCode = "";
    private PoiSearch poiSearch;

    private boolean isFirstLoc = true;       //第一次加载

    //地址
    private double longitude = 0;           //当前位置经度
    private double latitude = 0;            //当前位置纬度
    private String currenAddress = "";       //当前地址
    private StringBuffer moveAddress = new StringBuffer();         //目标地址
    private LatLng movelatLng;              //移动到的位置

    private Animation showAnimation, fadeAnimation;     //动画
    private Polyline polyline;          //路线
    private RouteSearch.FromAndTo fromAndTo;
    private RouteSearch routeSearch;


    //路线详细数据
    private RoadDetailAdapter roadDetailAdapter;
    private List<RoadDetailBean> roadDetails = new LinkedList<>();

    //附近列表详细数据
    private RoadListAdapter roadListAdapter;
    private List<PoiItem> pois = new LinkedList<>();

    //查询数据
    private String search = "医院";       //查询内容
    private int type = 0;                 //查询类型，0为医院；1为药店

    private int vehicleType = 0;          //交通类型，0为走路；1为驾车

    private boolean change = false;     //是否更改选择的marker，是否切换到达路线类型

    private boolean showRoadLayout = false;     //在切换为列表时，是否显示底部布局

    private int row = 1;        //查询的页数

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_nearby_map);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        StatusBarUtil.setStatusBarDarkTheme(this, true);     //黑色字体
    }

    @Override
    protected NearbyContract.Presenter createPresenter() {
        return new NearbyPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initMap();
        initRoadDetailView();
        initRoadListView();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.find_nearby_hospital);
    }

    //初始化路径详细
    private void initRoadDetailView() {
        roadDetailAdapter = new RoadDetailAdapter(this, roadDetails);
        rvDetail.setLayoutManager(new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvDetail.setHasFixedSize(true);
        rvDetail.setItemAnimator(null);
        rvList.setItemViewCacheSize(8);
        rvDetail.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvDetail.setAdapter(roadDetailAdapter);
    }

    //初始化附近路径
    private void initRoadListView() {
        roadListAdapter = new RoadListAdapter(this, pois, this);
        rvList.setLayoutManager(new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvList.setHasFixedSize(true);
        rvList.setItemAnimator(null);
        rvList.setItemViewCacheSize(8);
        rvList.setAdapter(roadListAdapter);
        roadListAdapter.setRow(row);
    }


    @Override
    public void onBackPressed() {
        if (rvList.isShown()) {                                 //附近列表数据
            showOrExitList(false);
        } else if (rvDetail.isShown()) {
            //路线详细
            showOrExitDetailRoad(false);
        } else if (ivFunction.isShown()) {                      //详细数据路线
            showOrExitRoad(true);
        } else if (roadLayout.isShown()) {                //目标地点数据

            if (movelatLng != null)
                movelatLng = null;
            change = true;
            //目标数据隐藏
            roadLayout.setAnimation(fadeAnimation);
            roadLayout.setVisibility(View.GONE);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
        } else {
            finish();
        }
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgressDialog(getString(R.string.loading_footer_tips));

        //roadLayout的动画
        showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.y_push_up_in);
        fadeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.y_push_up_out);

        routeSearch = new RouteSearch(this);

        mMapView.onCreate(savedInstanceState);
        initLocation();
        aMap = mMapView.getMap();
        aMap.setLocationSource(this);       // 设置定位监听
        aMap.setMyLocationEnabled(true);   //激活activate

        initLocationMark();


    }

    private void initLocationMark() {
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

        aMap.setOnMarkerClickListener(this);        // 设置点击marker事件监听器
    }

    //位置查询
    private void poiSearch() {


        query = new PoiSearch.Query(search, "", cityCode);
        query.setPageSize(15);          // 设置每页最多返回多少条poiitem
        query.setPageNum(row);            //设置查询页码
        query.setDistanceSort(true);

        poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 5000, true));
        poiSearch.searchPOIAsyn();


        Log.e("NearbyActivity:" + "123456", "search" + search);
        Log.e("NearbyActivity:" + "123456", "cityCode" + cityCode);

    }

    //初始化定位数据
    private void initLocation() {
        //初始化client
        mlocationClient = new AMapLocationClient(this.getApplicationContext());

        mlocationClient.setLocationListener(this);               // 设置定位监听
        mLocationOption = new AMapLocationClientOption();        //定位参数

        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);  //设置为高精度定位模式
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
        nowDescripter = BitmapDescriptorFactory.fromResource(R.drawable.now_location);
        walkDescripter = BitmapDescriptorFactory.fromResource(R.drawable.map_road_walk);
        driverDescripter = BitmapDescriptorFactory.fromResource(R.drawable.map_road_driver);

    }

    //定位改变的处理
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null
                && aMapLocation.getErrorCode() == 0) {

            if (isFirstLoc) {
                //经纬度
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();

                cityCode = aMapLocation.getCityCode();      //城市编码
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));       //移动地图

                mListener.onLocationChanged(aMapLocation);          //定位监听，传入定位的数据
                currenAddress = aMapLocation.getAddress();          //当前位置
                poiSearch();                                        //查询附近医院

                isFirstLoc = false;
            }
            Log.e("NearbyActivity:" + "123456", "zzzzzzzzzzzzzzzzzzzzzzz");
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            Log.e("NearbyActivity:" + "123456", errText);
            ToastUtil.show(getContext(), errText);
            tvSwitch.setEnabled(false);
            ivList.setEnabled(false);
        }
        Log.e("NearbyActivity:" + "123456", "***********************************");
        dismissProgressDialog();
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
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
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


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("NearbyActivity:" + "123456", "i" + i);
        Log.e("NearbyActivity:" + "123456", "poiResult" + poiResult);
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (pois == null) {
                    pois = poiResult.getPois();
                    ToastUtil.show(this, getString(R.string.loading_success));
                } else {
                    if (poiResult.getPois().size() > 0) {
                        pois.clear();
                        pois.addAll(poiResult.getPois());
                    } else if (pois.size() > 0) {           //是否已经有数据
                        roadListAdapter.setIsLoad(false);
                        roadListAdapter.setRow(--row);
                        roadListAdapter.notifyDataSetChanged();
                        dismissProgressDialog();
                        ToastUtil.show(this, "数据到底");
                        Log.e("NearbyActivity:" + "123456", "row" + row);
                        return;
                    }
                }
                Log.e("NearbyActivity:" + "123456", "poiResult.getPois().size() " + poiResult.getPois().size());
                if (pois != null && pois.size() > 0) {
//                    LatLngBounds bounds = new LatLngBounds.Builder().build();
                    for (PoiItem poiItem : pois) {
                        Log.e("NearbyActivity:" + "123456", "poiItem" + poiItem.getTitle());
                        AddPosition(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude(), poiItem.getTitle(), poiItem);
                    }
                    if (pois.size() < 15)
                        roadListAdapter.setIsLoad(false);
                    rvList.scrollToPosition(0);

                } else if (pois != null && pois.size() == 0) {
                    roadListAdapter.setIsLoad(false);
                    ToastUtil.show(this, "没有找到您想要的结果");
                    Log.e("NearbyActivity:" + "123456", "-------------------------------------------");
                }

            }
        }
        Log.e("NearbyActivity:" + "123456", "row" + row);
        Log.e("NearbyActivity:" + "123456", "pois.size()" + pois.size());
        roadListAdapter.notifyDataSetChanged();
        dismissProgressDialog();
    }

    //添加查询地点的图标
    public void AddPosition(double x, double y, String address, PoiItem poiItem) {
        //自定义布局marker
        View markView = View.inflate(getContext(), R.layout.map_mark, null);
        TextView textView = markView.findViewById(R.id.titleName);
        textView.setText(address);
        if (type == 1) {
            ImageView imageView = markView.findViewById(R.id.ivIcon);
            imageView.setImageResource(R.drawable.map_pharmacy_location);
        }
        if (TextUtils.isEmpty(address))
            textView.setVisibility(View.GONE);
        //设置相应的图标
        markerOption = new MarkerOptions();
        markerOption.position(new LatLng(x, y));
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(PictureUtil.convertViewToBitmap(markView)));
        marker = aMap.addMarker(markerOption);
        marker.setObject(poiItem);
    }

    //走路路线查询
    private RouteSearch.WalkRouteQuery walkQueryData() {
        //初始化query对象，fromAndTo是包含起终点信息，walkMode是步行路径规划的模式 mode，计算路径的模式。SDK提供两种模式：RouteSearch.WALK_DEFAULT 和 RouteSearch.WALK_MULTI_PATH。
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WALK_DEFAULT);
        return query;
    }

    //驾车路线查询
    private RouteSearch.DriveRouteQuery driveQueryData() {
        //初始化query对象，fromAndTo是包含起终点信息，walkMode是步行路径规划的模式 mode，计算路径的模式。SDK提供两种模式：RouteSearch.WALK_DEFAULT 和 RouteSearch.WALK_MULTI_PATH。
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(
                fromAndTo, //路径规划的起点和终点
                RouteSearch.DrivingDefault, //驾车模式
                null, //途经点
                null, //示避让区域
                "" //避让道路
        );
        return query;
    }

    @OnClick(R.id.roadButton)
    public void roadButton(View view) {
        setRouteSearch();
        detailRoadShow();
    }

    @OnClick(R.id.EndLayout)
    public void EndLayout(View view) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(movelatLng, 18));        //定位到当前图标
    }

    @OnClick(R.id.StartLayout)
    public void StartLayout(View view) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));        //定位到当前图标
    }

    //到达目标地点的路线
    private void detailRoadShow() {
        if (vehicleType == 0)
            routeSearch.calculateWalkRouteAsyn(walkQueryData());                //走路
        else
            routeSearch.calculateDriveRouteAsyn(driveQueryData());              //驾车
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                if (null == driveRouteResult) {
                    ToastUtil.show(getContext(), getString(R.string.data_lose));
                    return;
                }
                float sumTime = 0;      //总时间
                MarkerOptions centerMarkerOption;

                initData();     //初始化一些数据

                List<DrivePath> pathList = driveRouteResult.getPaths();
                List<LatLng> driverPaths = new ArrayList<>();
                for (DrivePath dp : pathList) {
                    List<DriveStep> stepList = dp.getSteps();
                    for (DriveStep ds : stepList) {
                        List<LatLonPoint> points = ds.getPolyline();
                        for (LatLonPoint llp : points) {
                            driverPaths.add(new LatLng(llp.getLatitude(), llp.getLongitude()));
                        }
                        //设置路口图标
                        centerMarkerOption = new MarkerOptions().position(new LatLng(points.get(points.size() - 1).getLatitude(), points.get(points.size() - 1).getLongitude())).icon(driverDescripter);
                        aMap.addMarker(centerMarkerOption);
                    }
                    sumTime += dp.getDuration();     //计算路程所需时间
                    tvRoadCount.setText(getString(R.string.find_nearby_pass_road) + stepList.size() + "个");   //设置路口个数
                }

                tvCostTime.setText(getString(R.string.find_nearby_cost_time) + Math.round(sumTime / 60) + "分钟");   //路程所需时间


                //路线绘制
                polyline = aMap.addPolyline(new PolylineOptions()
                        .addAll(driverPaths)
                        .width(40)
                        .setUseTexture(true)  //是否开启纹理贴图
                        //绘制成大地线
                        .geodesic(false)
                        //设置纹理样式
                        .setCustomTexture(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_road_arrow)))
                        //设置画线的颜色
                        .color(R.color.bright_green));

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));   //到达当前定位地址

            }

            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                if (null == walkRouteResult) {
                    ToastUtil.show(getContext(), getString(R.string.data_lose));
                    return;
                }
                float sumTime = 0;      //总时间
                MarkerOptions centerMarkerOption;

                initData();     //初始化一些数据

                List<WalkPath> pathList = walkRouteResult.getPaths();
                List<LatLng> walkPaths = new ArrayList<>();
                for (WalkPath dp : pathList) {                  //多条路径信息循环

                    //这里只获得一条路径信息

                    List<WalkStep> stepList = dp.getSteps();
                    for (WalkStep ds : stepList) {
                        List<LatLonPoint> points = ds.getPolyline();
                        for (LatLonPoint llp : points) {
                            walkPaths.add(new LatLng(llp.getLatitude(), llp.getLongitude()));
                        }

                        //设置路口图标
                        centerMarkerOption = new MarkerOptions().position(new LatLng(points.get(points.size() - 1).getLatitude(), points.get(points.size() - 1).getLongitude())).icon(walkDescripter);
                        aMap.addMarker(centerMarkerOption);

                    }

                    sumTime += dp.getDuration();     //计算路程所需时间
                    tvRoadCount.setText(getString(R.string.find_nearby_pass_road) + stepList.size() + "个");   //设置路口个数

                    break;

                }

                tvCostTime.setText(getString(R.string.find_nearby_cost_time) + Math.round(sumTime / 60) + "分钟");   //路程所需时间


                //路线绘制
                polyline = aMap.addPolyline(new PolylineOptions()
                        .addAll(walkPaths)
                        .width(40)
                        .setUseTexture(true)  //是否开启纹理贴图
                        //绘制成大地线
                        .geodesic(false)
                        //设置纹理样式
                        .setCustomTexture(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_road_arrow)))
                        //设置画线的颜色
                        .color(R.color.bright_green));

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));   //到达当前定位地址

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }

            @SuppressLint("SetTextI18n")
            private void initData() {
                //清空图标
                aMap.clear(true);

                pois.clear();
                roadListAdapter.notifyDataSetChanged();

                tvRoadCount.setText(getString(R.string.find_nearby_pass_road) + 0 + "个");     //路口个数

                //位置地址
                currentPosition.setText(currenAddress);  //当前
                movePosition.setText(moveAddress.toString());          //目标

                AddPosition(movelatLng.latitude, movelatLng.longitude, "", null);     //添加终点marker
            }
        });

        //布局的显隐
        showOrExitRoad(false);
    }


    //查看详细
    @OnClick(R.id.detailButton)
    public void detailButton(View view) {
        setRouteSearch();
        roadDetail();
    }

    private void roadDetail() {
        if (change) {
            if (vehicleType == 0)
                routeSearch.calculateWalkRouteAsyn(walkQueryData());                //走路
            else
                routeSearch.calculateDriveRouteAsyn(driveQueryData());              //驾车
            routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
                @Override
                public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

                }

                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                @Override
                public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                    if (null == driveRouteResult) {
                        ToastUtil.show(getContext(), getString(R.string.data_lose));
                        return;
                    }
                    //距离
                    float distance = AMapUtils.calculateLineDistance(new LatLng(latitude, longitude), movelatLng);      //计算距离
                    float sumTime = 0;      //总时间

                    initData();

                    //详细数据
                    List<DrivePath> pathList = driveRouteResult.getPaths();
                    for (DrivePath dp : pathList) {      //多条路径信息循环

                        //这里只获得一条路径信息
                        List<DriveStep> stepList = dp.getSteps();
                        RoadDetailBean roadDetailBean;
                        for (DriveStep ds : stepList) {
                            roadDetailBean = new RoadDetailBean();
                            roadDetailBean.setAction(ds.getAction());
                            roadDetailBean.setAssistantAction(ds.getAssistantAction());
                            roadDetailBean.setInstruction(ds.getInstruction());
                            roadDetails.add(roadDetailBean);
                        }
                        sumTime += dp.getDuration();
                        tvDetailDistant.setText(String.format("%.2f", distance / 1000) + " 千米" + "  经过" + dp.getSteps().size() + "个路口");
                        break;
                    }

                    roadDetailAdapter.notifyItemRangeInserted(0, roadDetails.size());
                    tvDetailCostTime.setText(Math.round(sumTime / 60) + "分钟");
                    change = false;
                }

                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                    if (null == walkRouteResult) {
                        ToastUtil.show(getContext(), getString(R.string.data_lose));
                        return;
                    }
                    //距离
                    float distance = AMapUtils.calculateLineDistance(new LatLng(latitude, longitude), movelatLng);      //计算距离
                    float sumTime = 0;      //总时间

                    initData();

                    //详细数据
                    List<WalkPath> pathList = walkRouteResult.getPaths();
                    for (WalkPath dp : pathList) {      //多条路径信息循环

                        //这里只获得一条路径信息
                        List<WalkStep> stepList = dp.getSteps();
                        RoadDetailBean roadDetailBean;
                        for (WalkStep ds : stepList) {
                            roadDetailBean = new RoadDetailBean();
                            roadDetailBean.setAction(ds.getAction());
                            roadDetailBean.setAssistantAction(ds.getAssistantAction());
                            roadDetailBean.setInstruction(ds.getInstruction());
                            roadDetails.add(roadDetailBean);
                        }

                        sumTime += dp.getDuration();
                        tvDetailDistant.setText(String.format("%.2f", distance / 1000) + " 千米" + "  经过" + dp.getSteps().size() + "个路口");
                        break;
                    }

                    roadDetailAdapter.notifyDataSetChanged();
                    tvDetailCostTime.setText(Math.round(sumTime / 60) + "分钟");
                    change = false;
                }

                @Override
                public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

                }

                private void initData() {

                    //起始元素
                    RoadDetailBean roadDetailBean = new RoadDetailBean();
                    roadDetailBean.setInstruction("从我的位置 向正东出发");
                    roadDetailBean.setAction("向正东出发");
                    roadDetails.add(roadDetailBean);
                }

            });
        }
        //路线详细
        showOrExitDetailRoad(true);
    }


    //退出路线状态
    @OnClick(R.id.ivFunction)
    public void ivFunction(View view) {
        showOrExitRoad(true);
    }

    //切换到列表
    @OnClick(R.id.ivList)
    public void ivList(View view) {
        showOrExitList(true);
    }

    //切换到底部
    @OnClick(R.id.ivMap)
    public void ivMap(View view) {
        showOrExitList(false);
    }

    //显隐列表
    private void showOrExitList(boolean val) {
        if (val) {
            //底部是否为显示
            if (roadLayout.isShown())
                showRoadLayout = true;
            else
                showRoadLayout = false;
            roadLayout.setVisibility(GONE);
            rvList.setVisibility(VISIBLE);
            mMapView.setVisibility(GONE);
            ivList.setVisibility(GONE);
            ivMap.setVisibility(VISIBLE);
        } else {
            rvList.setVisibility(GONE);
            mMapView.setVisibility(VISIBLE);
            ivList.setVisibility(VISIBLE);
            ivMap.setVisibility(GONE);
            if (showRoadLayout)
                roadLayout.setVisibility(VISIBLE);
        }
    }

    //显示地图
    @OnClick(R.id.showMapButton)
    public void showMapButton(View view) {
        rvDetail.scrollToPosition(0);               //路径详细滑动顶部
        showOrExitDetailRoad(false);
    }

    //驾车路线
    @OnClick(R.id.btnCar)
    public void btnCar(View view) {
        vehicleType = 1;
        roadDetailAdapter.setType(1);
        change = true;
        detailRoadShow();
        btnCar.setEnabled(false);
        btnCar.setTextColor(getResources().getColor(R.color.text_level_3));

        btnWalk.setEnabled(true);
        btnWalk.setTextColor(getResources().getColor(R.color.black));

        GlideUtils.displayMapImage(ivWay, R.drawable.map_road_driver);
        GlideUtils.displayMapImage(ivTitleWay, R.drawable.map_road_driver);

        if (roadDetails.size() > 0) {
            roadDetails.clear();
            roadDetailAdapter.notifyDataSetChanged();
        }

    }

    //走路路线
    @OnClick(R.id.btnWalk)
    public void btnWalk(View view) {
        vehicleType = 0;
        roadDetailAdapter.setType(0);
        change = true;

        detailRoadShow();

        btnWalk.setEnabled(false);
        btnWalk.setTextColor(getResources().getColor(R.color.text_level_3));

        btnCar.setEnabled(true);
        btnCar.setTextColor(getResources().getColor(R.color.black));

        GlideUtils.displayMapImage(ivWay, R.drawable.map_road_walk);
        GlideUtils.displayMapImage(ivTitleWay, R.drawable.map_road_walk);

        if (roadDetails.size() > 0) {
            roadDetails.clear();
            roadDetailAdapter.notifyDataSetChanged();
        }

    }

    //切换选择（医院/药店）
    @OnClick(R.id.tvSwitch)
    public void tvSwitch(View view) {
        showRoadLayout = false;     //不显示底部布局

        //重新初始化页数
        row = 1;                    //起始页数
        roadListAdapter.setRow(row);
        roadListAdapter.setIsLoad(true);

        if (roadDetails.size() > 0) {
            roadDetails.clear();
            roadDetailAdapter.notifyDataSetChanged();
        }

        if (0 == type) {            //医院
            search = "药店";
            //标题数据
            tvTitle.setText(R.string.find_nearby_pharmacy);
            tvSwitch.setText(R.string.find_nearby_switch_hospital);
            type = 1;
            ivEnd.setImageResource(R.drawable.map_pharmacy_location);       //终点图标
        } else {                   //药店
            search = "医院";
            //标题数据
            tvTitle.setText(R.string.find_nearby_hospital);
            tvSwitch.setText(R.string.find_nearby_switch_pharmacy);
            type = 0;
            ivEnd.setImageResource(R.drawable.map_hospital_location);        //终点图标
        }
        if (null != polyline) {
            polyline.remove();             //移除路线
        }

        //标题数据
        ivFunction.setVisibility(GONE);
        if (mMapView.isShown())
            ivList.setVisibility(VISIBLE);
        else
            ivMap.setVisibility(VISIBLE);

        //切换数据
        change = true;
        if (movelatLng != null)
            movelatLng = null;

        PositionLayout.setVisibility(GONE);

        //底部布局
        roadLayout.setVisibility(GONE);
        roadButton.setVisibility(VISIBLE);
        tvCostTitle.setVisibility(GONE);
        CostLayout.setVisibility(GONE);
        movePositionLayout.setVisibility(VISIBLE);

        //图标
        aMap.clear(true);       //清除原有图标，除了地位图标
        poiSearch();                //查询

        ToastUtil.show(getContext(), getString(R.string.find_nearby_switch_success));

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));        //定位到当前图标
    }

    //调用高德地图
    @OnClick(R.id.callButton)
    public void callButton(View view) {
        FunctionUtils.callGaudMap(getContext(), moveAddress.toString(), "" + movelatLng.latitude, "" + movelatLng.longitude);
    }

    //显隐地图的路径
    private void showOrExitRoad(boolean val) {
        if (val) {
            movePositionLayout.setVisibility(VISIBLE);      //底部大概布局
            //路径信息
            CostLayout.setVisibility(GONE);
            tvCostTitle.setVisibility(GONE);

            PositionLayout.setVisibility(GONE);             //顶部位置信息

            ivFunction.setVisibility(GONE);                 //退出图标

            ivList.setVisibility(VISIBLE);                  //列表图标

            if (null != polyline) {
                polyline.remove();             //移除路线
                polyline = null;
                aMap.clear(true);           //清除图标，除了定位图标
                poiSearch();                      //查询附近医院
            }
            roadButton.setVisibility(VISIBLE);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(movelatLng, 15));
//            ToastUtil.show(this, getString(R.string.find_nearby_exit_road));
        } else {
            movePositionLayout.setVisibility(GONE);     //移动
            roadButton.setVisibility(GONE);
            CostLayout.setVisibility(VISIBLE);
            tvCostTitle.setVisibility(VISIBLE);
            PositionLayout.setVisibility(VISIBLE);
            ivFunction.setVisibility(VISIBLE);
            ivList.setVisibility(GONE);
        }
    }

    //显隐详细的路径
    private void showOrExitDetailRoad(boolean val) {
        if (val) {
            DetailTitleLayout.setVisibility(VISIBLE);

            rvDetail.setVisibility(VISIBLE);

            showMapButton.setVisibility(VISIBLE);

            mMapView.setVisibility(GONE);
            detailButton.setVisibility(GONE);
            PositionInfLayout.setVisibility(GONE);
            roadButton.setVisibility(GONE);
            PositionLayout.setVisibility(GONE);
            titleLayout.setVisibility(GONE);
        } else {
            DetailTitleLayout.setVisibility(GONE);
            rvDetail.setVisibility(GONE);

            mMapView.setVisibility(VISIBLE);
            detailButton.setVisibility(VISIBLE);
            showMapButton.setVisibility(GONE);
            PositionInfLayout.setVisibility(VISIBLE);

            if (tvCostTitle.isShown()) {
                PositionLayout.setVisibility(VISIBLE);
            }
            if (null == polyline)
                roadButton.setVisibility(VISIBLE);
            titleLayout.setVisibility(VISIBLE);
        }
    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public boolean onMarkerClick(Marker marker) {
        //获得定位数据
        PoiItem poiItem = (PoiItem) marker.getObject();

        //当前位置
        if (null == poiItem)
            return true;
        if (movelatLng != null && movelatLng.latitude == poiItem.getLatLonPoint().getLatitude() && movelatLng.longitude == poiItem.getLatLonPoint().getLongitude())
            return true;
        showDetailRoad(poiItem);    //显示Marker的位置信息
        return false;
    }


    //回调：显示详细路线
    @Override
    public void showDetailRoadCall(PoiItem poiItem) {

        showRoadLayout = true;
        showOrExitList(false);
        showDetailRoad(poiItem);
        setRouteSearch();
        detailRoadShow();
    }

    private void setRouteSearch() {
        LatLonPoint pointFrom = new LatLonPoint(latitude, longitude);        //当前经纬度
        LatLonPoint pointTo = new LatLonPoint(movelatLng.latitude, movelatLng.longitude);  //目的地的经纬度
        fromAndTo = new RouteSearch.FromAndTo(pointFrom, pointTo);
    }


    //回调：下一页
    @Override
    public void nextPage(int row) {
        this.row = row;
        poiSearch();
    }

    //回调：上一页
    @Override
    public void lastPage(int row) {
        this.row = row;
        roadListAdapter.setIsLoad(true);        //可以加载
        poiSearch();
    }

    //显示位置大概信息
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void showDetailRoad(PoiItem poiItem) {
        //移动位置数据
        movelatLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
        moveAddress.setLength(0);
        moveAddress.append(poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet() + " " + poiItem.getTitle());

        //距离
        float distance = AMapUtils.calculateLineDistance(new LatLng(latitude, longitude), movelatLng);      //计算距离
        tvDistant.setText(String.format("%.2f", distance / 1000) + " 千米");

        tvName.setText(poiItem.getTitle());          //地点名
        tvSnippet.setText(poiItem.getAdName() + poiItem.getSnippet());      //大概位置

        //显示底部布局
        roadLayout.setVisibility(VISIBLE);
        roadLayout.setAnimation(showAnimation);


        change = true;          //改变选择的marker

        if (roadDetails.size() > 0) {
            roadDetails.clear();
            roadDetailAdapter.notifyDataSetChanged();
        }
    }


}
