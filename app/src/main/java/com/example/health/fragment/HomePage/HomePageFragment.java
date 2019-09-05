package com.example.health.fragment.HomePage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.health.BaseFragment;
import com.example.health.Entity.Information;
import com.example.health.ITabFragment;
import com.example.health.R;
import com.example.health.carousel.ImageCarousel;
import com.example.health.carousel.ImageInfo;
import com.example.health.widget.XListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomePageFragment extends BaseFragment implements View.OnClickListener, ITabFragment, XListView.IXListViewListener {

    //总视图
    private View view;
    //图片轮播图
    private ViewPager mviewPager;
    private TextView mTvPagerTitle;
    private LinearLayout mLinearLayoutDot;
    private ImageCarousel imageCarousel;
    private List<View> dots; //小点
    private boolean isBottomShow = true;

    // 图片数据，包括图片标题、图片链接、数据、点击要打开的网站（点击打开的网页或一些提示指令）
    private List<ImageInfo> imageInfoList;

    private SimpleDraweeView simpleDraweeView;
    private ListView health_information_listView;
    private ScrollView myScrollView;

    private ArrayList<Information> informationList = new ArrayList<>();

    private ArrayList<String> items = new ArrayList<String>();
    private int mIndex = 0;
    private Handler mHandler;
    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private int mRefreshIndex = 0;
    private HomePageAdapter adapter;


    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        initView();
        initEvent();
        imageStart();
        //initInformation();
        hideBottomUIMenu();


        geneItems();
        initItemView();
        initScrollView();


        //  设置头像图片
        /*RoundImageView iv1= view.findViewById(R.id.head_portrait);
        iv1.setBorderWidth(3)
                .setBorderColor(Color.RED)
                .setType(RoundImageView.TYPE_ROUND)
                .setLeftTopCornerRadius(0)
                .setRightTopCornerRadius(10)
                .setRightBottomCornerRadius(30)
                .setLeftBottomCornerRadius(50);

        Picasso.with(getActivity())
                .load("http://ww2.sinaimg.cn/large/610dc034jw1fa42ktmjh4j20u011hn8g.jpg")
                .into(iv1);*/


        // Inflate the layout for this fragment
        myScrollView.smoothScrollTo(0, 0);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {

        myScrollView.setFocusable(true);
        myScrollView.setFocusableInTouchMode(true);
        myScrollView.requestFocus();
        myScrollView.scrollTo(0, 0);
        myScrollView.fullScroll(View.FOCUS_UP);
        myScrollView.setOnScrollChangeListener(new ScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
             /* *//*  Log.v("HomePageFragment123","scrollY:"+scrollY);
                Log.v("HomePageFragment123","oldScrollY:"+oldScrollY);
                Log.v("HomePageFragment123","scrollY - oldScrollY:"+(scrollY - oldScrollY));
                Log.v("HomePageFragment123","isBottomShow:"+isBottomShow);
                Log.v("HomePageFragment123","myScrollView.getMeasuredHeight():"+myScrollView.getMeasuredHeight());
                Log.v("HomePageFragment123","---------------------------------");*//*

                if (scrollY - oldScrollY > 0 && isBottomShow) {         //下滑 并且 正在隐藏底部栏
                    if (oldScrollY < 0)
                        return;
                    isBottomShow = false;
                    getActivity().findViewById(R.id.bottom_navigation_bar).setVisibility(View.GONE);
                    //将Y属性变为底部栏高度  (相当于隐藏了)
                    // getActivity().findViewById(R.id.bottom_navigation_bar).animate().translationY( getActivity().findViewById(R.id.bottom_navigation_bar).getHeight());
                } else if (scrollY - oldScrollY < 0 && !isBottomShow) {   //上滑 并且 正在显示底部栏
                    isBottomShow = true;
                    getActivity().findViewById(R.id.bottom_navigation_bar).setVisibility(View.VISIBLE);
                    // getActivity().findViewById(R.id.bottom_navigation_bar).animate().translationY(0);
                }*/
            }

        });

        /*myScrollView.setOnTouchListener(new ScrollView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getActivity().findViewById(R.id.bottom_navigation_bar).setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_UP:
                        getActivity().findViewById(R.id.bottom_navigation_bar).setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });*/


    }


    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("Test XListView item " + (++mIndex));
        }


    /*    for (int i = 0; i < 6; i++) {
            Information information = new Information();
            information.setContent("一连几个小时甚至几天，通过电视机、电脑或ＤＶＤ观看自己喜爱的某个电视剧，试图通过这种方式把负面情绪消化掉。");
            information.setDate("2019-8-12");
            information.setTitle("这种做法对人的心理健康会产生负面影响");
            informationList.add(information);
        }*/
    }

    private void initItemView() {
        mHandler = new Handler();

        mListView = view.findViewById(R.id.list_view1);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vw_list_item, items);
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);

    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIndex = ++mRefreshIndex;
                items.clear();
                geneItems();
                mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vw_list_item,
                        items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    /**
     * getTime初始化控件
     */
    private void initView() {
        mviewPager = view.findViewById(R.id.mviewPager);
        mTvPagerTitle = view.findViewById(R.id.mTvPagerTitle);
        mLinearLayoutDot = view.findViewById(R.id.mLinearLayoutDot);
        health_information_listView = view.findViewById(R.id.health_information_listView);
        myScrollView = view.findViewById(R.id.myScrollView);
        mListView = view.findViewById(R.id.list_view1);
    }


    private void initData() {
        informationList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Information information = new Information();
            information.setContent("一连几个小时甚至几天，通过电视机、电脑或ＤＶＤ观看自己喜爱的某个电视剧，试图通过这种方式把负面情绪消化掉。");
            information.setDate("2019-8-12|||" + i);
            information.setTitle("这种做法对人的心理健康会产生负面影响");
            informationList.add(information);
        }

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        imageInfoList = new ArrayList<>();
        imageInfoList.add(new ImageInfo(1, "图片1，公告1啦啦啦啦", "", "http://d.hiphotos.baidu.com/image/pic/item/6159252dd42a2834a75bb01156b5c9ea15cebf2f.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片2，公告2嘻嘻嘻嘻", "", "http://c.hiphotos.baidu.com/image/h%3D300/sign=cfce96dfa251f3dedcb2bf64a4eff0ec/4610b912c8fcc3ce912597269f45d688d43f2039.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片3，公告3哈哈哈哈", "", "http://e.hiphotos.baidu.com/image/pic/item/6a600c338744ebf85ed0ab2bd4f9d72a6059a705.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片4，公告4呵呵呵呵", "仅展示", "http://b.hiphotos.baidu.com/image/h%3D300/sign=8ad802f3801001e9513c120f880e7b06/a71ea8d3fd1f4134be1e4e64281f95cad1c85efa.jpg", ""));
        imageInfoList.add(new ImageInfo(1, "图片5，公告5嘿嘿嘿嘿", "仅展示", "http://e.hiphotos.baidu.com/image/h%3D300/sign=73443062281f95cab9f594b6f9177fc5/72f082025aafa40fafb5fbc1a664034f78f019be.jpg", ""));
    }

    /**
     * 隐藏虚拟按键，并且全屏
     * 沉浸式状态栏
     */
    @SuppressLint("NewApi")
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = this.getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            //顶部状态栏设置为透明
            this.getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            /*

            1.View.SYSTEM_UI_FLAG_FULLSCREEN;为全屏

            2.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //底部导航栏设置为透明
            this.getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //顶部状态栏设置为透明
            this.getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
             */


        }
    }


    /**
     * 图片轮播
     */
    private void imageStart() {
        int[] imgaeIds = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3, R.id.pager_image4,
                R.id.pager_image5, R.id.pager_image6, R.id.pager_image7, R.id.pager_image8};
        String[] titles = new String[imageInfoList.size()];
        List<SimpleDraweeView> simpleDraweeViews = new ArrayList<>();

        for (int i = 0; i < imageInfoList.size(); i++) {
            titles[i] = imageInfoList.get(i).getTitle();
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getActivity());
            simpleDraweeView.setAspectRatio(1.78f);

            //设置一张默认图片
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getActivity().getResources())
                    .setPlaceholderImage(ContextCompat.getDrawable(getActivity(), R.drawable.defult_load), ScalingUtils.ScaleType.CENTER_CROP).build();
            simpleDraweeView.setHierarchy(hierarchy);
            simpleDraweeView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

            //加载高分辨率图片
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageInfoList.get(i).getImage()))
                    .setResizeOptions(new ResizeOptions(1280, 720))
                    .build();


            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    //.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(listItemBean.test_pic_low))) //在加载高分辨率图片之前加载低分辨率图片
                    .setImageRequest(imageRequest)
                    .setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(controller);

            simpleDraweeView.setId(imgaeIds[i]); //给view设置id
            simpleDraweeView.setTag(imageInfoList.get(i));
            simpleDraweeView.setOnClickListener(this);
            titles[i] = imageInfoList.get(i).getTitle();
            simpleDraweeViews.add(simpleDraweeView);
        }

        dots = addDots(mLinearLayoutDot, fromResToDrawable(getActivity(), R.drawable.ic_dot_focused), simpleDraweeViews.size());
        imageCarousel = new ImageCarousel(getActivity(), mviewPager, mTvPagerTitle, dots, 5000);
        imageCarousel.init(simpleDraweeViews, titles).startAutoPlay();
        imageCarousel.start();
    }


    /**
     * 健康资讯
     */
    private void initInformation() {
        List<Information> informationList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Information information = new Information();
            information.setContent("一连几个小时甚至几天，通过电视机、电脑或ＤＶＤ观看自己喜爱的某个电视剧，试图通过这种方式把负面情绪消化掉。");
            information.setDate("2019-8-12");
            information.setTitle("这种做法对人的心理健康会产生负面影响");
            informationList.add(information);
        }

        HomePageAdapter adapter = new HomePageAdapter(getActivity(), informationList);
        health_information_listView.setAdapter(adapter);


        health_information_listView.setFocusable(false);

        myScrollView.smoothScrollTo(0, 0);
        myScrollView.setFocusable(true);
        myScrollView.setFocusableInTouchMode(true);
        myScrollView.requestFocus();
    }


    /**
     * 资源图片转Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return 返回Drawable图像
     */
    private static Drawable fromResToDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    /**
     * 动态添加一个点
     *
     * @param linearLayout 添加到LinearLayout布局
     * @param backgount    设置
     * @return 小点的Id
     */
    private int addDot(final LinearLayout linearLayout, Drawable backgount) {
        final View dot = new View(getActivity());
        LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.width = 16;
        dotParams.height = 16;
        dotParams.setMargins(4, 0, 4, 0);
        dot.setLayoutParams(dotParams);
        dot.setBackground(backgount);
        dot.setId(View.generateViewId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayout.addView(dot);
            }
        });
        return dot.getId();
    }

    /**
     * 添加多个轮播小点到横向线性布局
     *
     * @param linearLayout 线性横向布局
     * @param backgount    小点资源图标
     * @param number       数量
     * @return 返回小点View集合
     */
    private List<View> addDots(final LinearLayout linearLayout, Drawable backgount, int number) {
        List<View> dots = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int dotId = addDot(linearLayout, backgount);
            dots.add(view.findViewById(dotId));
        }
        return dots;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Fragment getFragment() {
        return this;
    }

}
