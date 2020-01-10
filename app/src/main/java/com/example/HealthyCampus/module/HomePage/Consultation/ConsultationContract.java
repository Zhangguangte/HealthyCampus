package com.example.HealthyCampus.module.HomePage.Consultation;

import android.content.Context;

import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface ConsultationContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(String room_id,String name);

        void showtDoctorSuccess(String nickName1, String room1, String nickName2, String room2);

    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getDoctorRoom();

        protected abstract void createRoom(String name);

        protected abstract void deleteRoomId(String name, int number);
    }
}
