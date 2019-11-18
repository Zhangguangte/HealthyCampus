package com.example.HealthyCampus.module.Message.Chat.Map;

import android.content.Context;

import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.widgets.custom_image.ScaleImageView;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface MapContract {
    interface View extends BaseView {
        Context getContext();

    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
