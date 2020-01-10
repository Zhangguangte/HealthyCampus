package com.example.HealthyCampus.module.Mine.Feedback;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.BookVo;

import java.util.List;

public class FeedbackPresenter extends FeedbackContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void sendFeed(String advice, String contract) {
        RequestForm requestForm = new RequestForm(contract,advice);
        ServiceRepository.getInstance().sendFeed(requestForm);
    }
}
