package com.example.HealthyCampus.module.Find.Recipes.Functionality;

import android.view.View;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.module.Find.Recipes.Functionality.RecipesList.activity.RecipesListActivity;

import butterknife.OnClick;

public class FunctionalityFragment extends BaseFragment<FunctionalityContract.View, FunctionalityContract.Presenter> implements FunctionalityContract.View {

    @Override
    public int setContentLayout() {
        return R.layout.find_recipes_functionality_fragment;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected FunctionalityContract.Presenter setPresenter() {
        return new FunctionalityPresenter();
    }

    public static BaseFragment getInstance() {
        return new FunctionalityFragment();
    }

    @OnClick({R.id.btnCosmetology, R.id.btnPreservation, R.id.btnSlimming, R.id.btnMore})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.btnCosmetology:
                RecipesListActivity.start(getContext(), 1);
                break;
            case R.id.btnPreservation:
                RecipesListActivity.start(getContext(), 2);
                break;
            case R.id.btnSlimming:
                RecipesListActivity.start(getContext(), 3);
                break;
            case R.id.btnMore:
                RecipesListActivity.start(getContext(), 4);
                break;
            default:
                break;
        }
    }


}
