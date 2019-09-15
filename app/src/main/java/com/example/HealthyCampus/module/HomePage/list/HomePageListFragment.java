package com.example.HealthyCampus.module.HomePage.list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.common.data.model.StoriesBean;
import com.example.HealthyCampus.common.data.model.TopStoriesBean;
import com.example.HealthyCampus.common.utils.ActivityUtils;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.PullRecycler;
import com.example.HealthyCampus.framework.BaseListFragment;
import com.example.HealthyCampus.module.HomePage.article.HomePageArticleFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * OK
 */
public class HomePageListFragment extends BaseListFragment<HomePageListContract.View, HomePageListContract.Presenter, StoriesBean> implements HomePageListContract.View {

    private List<TopStoriesBean> topStories = new ArrayList();
    private String currentDate;

    public static HomePageListFragment newInstance() {
        HomePageListFragment fragment = new HomePageListFragment();
        return fragment;
    }

    @Override
    protected void setUpData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullRecycler.setRefreshing();
            }
        }, 200);
    }

    @Override
    protected int getItemType(int position) {

        if (mDataList.get(position).getType() == ConstantValues.VIEW_HEALTH_BANNER) {
            return ConstantValues.VIEW_HEALTH_BANNER;
        } else if (mDataList.get(position).getType() == ConstantValues.VIEW_HEALTH_DOCOTOR_DISPLAY) {
            return ConstantValues.VIEW_HEALTH_DOCOTOR_DISPLAY;
        } else if (mDataList.get(position).getType() == ConstantValues.VIEW_HEALTH_DATE) {
            return ConstantValues.VIEW_HEALTH_DATE;
        } else {
            return ConstantValues.VIEW_HEALTH_SUMMARY;
        }


    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {

//        LogUtil.logE("HomePageListFragment" + "123456", "viewType" + viewType);

        if (viewType == ConstantValues.VIEW_HEALTH_BANNER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_health_banner, parent, false);
            return new HomePageListFragment.BannerHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_HEALTH_DOCOTOR_DISPLAY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_docotor_display, parent, false);
            return new HomePageListFragment.DisplayHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_HEALTH_DATE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_health_date, parent, false);
            return new HomePageListFragment.DateHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_HEALTH_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_health_summary, parent, false);
            return new HomePageListFragment.SummaryHolder(itemView);
        }


        return null;
    }

    @Override
    protected HomePageListContract.Presenter setPresenter() {
        return new HomePageListPresenter();
    }

    @Override
    public void onRefresh(int action) {

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            mPresenter.getLatestNews();
        } else {
            mPresenter.getBeforeNews(currentDate);
        }
    }

    @Override
    public void refreshList(LatestNewsBean newsBean) {

        currentDate = newsBean.getDate();

        topStories.clear();
        topStories.addAll(newsBean.getTop_stories());

        StoriesBean bannerBean = new StoriesBean();
        bannerBean.setType(ConstantValues.VIEW_HEALTH_BANNER);

        StoriesBean displayBean = new StoriesBean();
        displayBean.setType(ConstantValues.VIEW_HEALTH_DOCOTOR_DISPLAY);

        StoriesBean dateBean = new StoriesBean();
        dateBean.setType(ConstantValues.VIEW_HEALTH_DATE);
        dateBean.setTitle(getString(R.string.health_item_today));

        mDataList.clear();
        mDataList.add(bannerBean);
        mDataList.add(displayBean);
        mDataList.add(dateBean);
        mDataList.addAll(newsBean.getStories());

        adapter.notifyDataSetChanged();
    }

    @Override
    public void addList(BeforeNewsBean beforeNewsBean) {

        currentDate = beforeNewsBean.getDate();

        StoriesBean dateBean = new StoriesBean();
        dateBean.setType(ConstantValues.VIEW_HEALTH_DATE);
        dateBean.setTitle(DateUtils.string2DateString3(beforeNewsBean.getDate(), DateUtils.FORMAT_HP_yyyyMMdd));

        mDataList.add(dateBean);
        mDataList.addAll(beforeNewsBean.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    class BannerHolder extends BaseViewHolder {

        @BindView(R.id.bannerView)
        Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
//            LogUtil.logE("HomePageListFragment" + "123456", "1");
            List<String> images = new ArrayList<>();
            List<String> titles = new ArrayList<>();

            for (TopStoriesBean topStory : topStories) {
                images.add(topStory.getImage());
                titles.add(topStory.getTitle());
            }

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(images);
            banner.setBannerTitles(titles);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    try {
                        String title = topStories.get(position).getTitle();
                        String url = topStories.get(position).getImage();
                        int id = topStories.get(position).getId();

                        ActivityUtils.startActivity(mActivity, HomePageArticleFragment.newInstance(title, id, url));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            banner.start();
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class DateHolder extends BaseViewHolder {

        @BindView(R.id.summary_date_tv)
        TextView mItemDate;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
//            LogUtil.logE("HomePageListFragment" + "123456", "2");
            StoriesBean bean = mDataList.get(position);
            if (bean != null) {
                mItemDate.setText(bean.getTitle());
            }
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }


    class SummaryHolder extends BaseViewHolder {

        @BindView(R.id.summary_item_title)
        TextView mItemTitle;
        @BindView(R.id.summary_item_img)
        ImageView mItemImg;

        public SummaryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
//            LogUtil.logE("HomePageListFragment" + "123456", "3");
            StoriesBean bean = mDataList.get(position);
            if (bean != null) {
                mItemTitle.setText(bean.getTitle());
//                LogUtil.logE("HomePageListFragment"+"123456","mItemImg:"+mItemImg);
//                LogUtil.logE("HomePageListFragment"+"123456","position:"+position);
//                LogUtil.logE("HomePageListFragment"+"123456","bean:"+bean);
//                LogUtil.logE("HomePageListFragment"+"123456"," bean.getImages():"+ bean.getImages());
//                LogUtil.logE("HomePageListFragment"+"123456"," bean.getImages().get(0:"+ bean.getImages().get(0));
                GlideUtils.display(mItemImg, bean.getImages().get(0));
            }
        }

        @Override
        public void onItemClick(View view, int position) {

            try {
                String title = mDataList.get(position).getTitle();
                String url = mDataList.get(position).getImages().get(0);
                int id = mDataList.get(position).getId();

                ActivityUtils.startActivity(mActivity, HomePageArticleFragment.newInstance(title, id, url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //******************************************************************************************//

    class DisplayHolder extends BaseViewHolder {

        public DisplayHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
//            LogUtil.logE("HomePageListFragment" + "123456", "4");
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }


}
