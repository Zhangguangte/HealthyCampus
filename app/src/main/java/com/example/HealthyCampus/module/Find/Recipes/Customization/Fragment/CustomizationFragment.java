package com.example.HealthyCampus.module.Find.Recipes.Customization.Fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CardAdapter;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.widgets.card.CardConfig;
import com.example.HealthyCampus.common.widgets.card.CardItemTouchCallBack;
import com.example.HealthyCampus.common.widgets.card.DailyCard;
import com.example.HealthyCampus.common.widgets.card.SwipeCardLayoutManager;
import com.example.HealthyCampus.framework.BaseFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class CustomizationFragment extends BaseFragment<CustomizationContract.View, CustomizationContract.Presenter> implements CustomizationContract.View {

    @BindView(R.id.card_recycler_view)
    RecyclerView rvCard;

    private List<DailyCard> mDataList = new LinkedList<>();

    private final static int[] picList = new int[]{
            R.drawable.sunday,
            R.drawable.monday,
            R.drawable.tuesday,
            R.drawable.wednesday,
            R.drawable.thursday,
            R.drawable.friday,
            R.drawable.saturday,
    };

    @Override
    public int setContentLayout() {
        return R.layout.find_recipes_customization_fragment;
    }

    @Override
    public void setUpView(View view) {
        initCardRecyclerView();
        loadData();
    }

    public static BaseFragment getInstance() {
        return new CustomizationFragment();
    }


    @Override
    protected CustomizationContract.Presenter setPresenter() {
        return new CustomizationPresenter();
    }

    private void loadData() {
        CharSequence[] weeks = mActivity.getResources().getStringArray(R.array.recipes_week);
        for (int i = DateUtils.getWeek(); i < 7 + DateUtils.getWeek(); i++) {
            DailyCard dailyCard = new DailyCard(
                    "周" + DateUtils.arab2Chinese(i) + "美食谱",
                    weeks[i%7].toString(),
                    picList[i%7]
            );
            mDataList.add(dailyCard);
        }
    }

    private void initCardRecyclerView() {
        CardConfig.initConfig(mActivity);
        rvCard.setLayoutManager(new SwipeCardLayoutManager());
        CardAdapter cardAdapter = new CardAdapter(mActivity, mDataList);
        rvCard.setHasFixedSize(true);
        rvCard.setItemViewCacheSize(4);
        rvCard.setAdapter(cardAdapter);

        //添加Item滑动监听
        CardItemTouchCallBack callBack = new CardItemTouchCallBack(rvCard, cardAdapter, mDataList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);     //2.创建ItemTouchHelper并把callBack传进去
        itemTouchHelper.attachToRecyclerView(rvCard);   //3.与RecyclerView关联起来
    }


}
