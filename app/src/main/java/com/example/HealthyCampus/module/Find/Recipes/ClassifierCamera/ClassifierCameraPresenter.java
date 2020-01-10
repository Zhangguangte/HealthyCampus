package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;

public class ClassifierCameraPresenter extends ClassifierCameraContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getDishResult(String data) {
//        RequestForm requestForm = new RequestForm("ID", id);
        RecipesRepository.getInstance().getDishResult(data, new RecipesDataSource.RecipesGetDish() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DishVo dishVo) throws Exception {
                if (null != getView()) getView().showDishSuccess(dishVo);
            }

            @Override
            public void onFinish() throws Exception {
                if (null != getView()) getView().enableClick(false);
            }
        });
    }

    @Override
    protected void getIngredientResult(String data) {
//        RequestForm requestForm = new RequestForm("ID", id);
        RecipesRepository.getInstance().getIngredientResult(data, new RecipesDataSource.RecipesGetIngredient() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(IngredientVo ingredientVo) throws Exception {
                if (null != getView()) getView().showIngredientSuccess(ingredientVo);
            }

            @Override
            public void onFinish() throws Exception {
                if (null != getView()) getView().enableClick(false);
            }
        });
    }


}
