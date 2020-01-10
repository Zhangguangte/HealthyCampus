package com.example.HealthyCampus.module.Find.Recipes.Customization.activity;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;

import java.util.List;

public class CustomizationPresenter extends CustomizationContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getRecipesByThreeMeals(int dayTitle, String type, int weekId) {
        RequestForm requestForm = new RequestForm(type, "%(" + weekId + ")%", dayTitle);
        RecipesRepository.getInstance().getRecipesByThreeMeals(requestForm, new RecipesDataSource.RecipesThreesMeals() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())     getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<FoodMenuVo> foodMenuVos) throws Exception {
                if (null != getView())     getView().showRecipesSuccess(foodMenuVos);
            }
        });
    }

}
