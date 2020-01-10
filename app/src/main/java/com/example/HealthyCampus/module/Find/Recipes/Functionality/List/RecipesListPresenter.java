package com.example.HealthyCampus.module.Find.Recipes.Functionality.List;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.RecipesListVo;

import java.util.List;

public class RecipesListPresenter extends RecipesListContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getRecipesList(int type, int row) {
        RequestForm requestForm = new RequestForm(type, row);
        RecipesRepository.getInstance().getRecipesList(requestForm, new RecipesDataSource.RecipesGetList() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(RecipesListVo recipesListVo) throws Exception {
                if (null != getView()) {
                    getView().showRecipesListSuccess(recipesListVo.getClassList());
                    getView().showRecipesSuccess(recipesListVo.getFoodList());
                }
            }
        });
    }

    @Override
    protected void getFoodList(String name, int row) {
        RequestForm requestForm = new RequestForm("", name, row);
        RecipesRepository.getInstance().getFoodList(requestForm, new RecipesDataSource.RecipesGetFood() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView() && getView().isList(name))
                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<FoodMenuVo> foodMenuVos) throws Exception {
                if (null != getView()&& getView().isList(name)) getView().showRecipesSuccess(foodMenuVos);
            }
        });
    }

}
