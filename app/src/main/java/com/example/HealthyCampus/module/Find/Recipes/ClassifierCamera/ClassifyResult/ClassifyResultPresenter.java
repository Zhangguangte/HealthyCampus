package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera.ClassifyResult;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;
import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;
import com.example.HealthyCampus.common.network.vo.IngredientResultVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;

public class ClassifyResultPresenter extends ClassifyResultContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getIngredientResult(String name) {
        RequestForm requestForm = new RequestForm("", "%(" + name + ")%");
        RecipesRepository.getInstance().getIngredientResult(requestForm, new RecipesDataSource.RecipesGetSearch() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(IngredientResultVo ingredientResultVo) throws Exception {
                if (null != getView()) getView().showIngredientSuccess(ingredientResultVo);
            }

        });
    }

}
