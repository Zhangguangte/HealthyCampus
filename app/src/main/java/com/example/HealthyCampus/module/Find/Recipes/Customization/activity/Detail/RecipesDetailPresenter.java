package com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail;


import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;
import com.example.HealthyCampus.common.network.vo.FoodVo;
import com.example.HealthyCampus.common.utils.StringUtil;

import java.util.List;

public class RecipesDetailPresenter extends RecipesDetailContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getRecipeDetail(int type, String id) {
        RequestForm requestForm = new RequestForm(type, id);
        RecipesRepository.getInstance().getRecipeDetail(requestForm, new RecipesDataSource.RecipesGetDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(FoodVo foodVo) throws Exception {
                changedData(foodVo);
            }
        });
    }


    private void changedData(FoodVo foodVo) {
        getView().showIconSuccess(foodVo.getPictureUrl());      //菜肴图片
        getView().showTitle(foodVo.getDishName());      //标题
        getView().showGeneralSuccess(foodVo.getFlavor(), foodVo.getProductionTime(), foodVo.getMainProcess());  //大概


        //需要通过Message机制更新数据，或者初始化数据时直接赋值，不然Item部分不显示
        getView().showMaterialSuccess(StringUtil.getIngredientsArray(foodVo.getIngredients())); //食材
        getView().showDetailSuccess(StringUtil.getStepArray(foodVo.getPractice())); //做法
        getView().showElementSuccess(foodVo.getComponents());  //元素
    }


}
