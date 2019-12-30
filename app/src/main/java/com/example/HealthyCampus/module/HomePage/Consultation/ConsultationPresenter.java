package com.example.HealthyCampus.module.HomePage.Consultation;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

public class ConsultationPresenter extends ConsultationContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getDoctorRoom() {
        MessageRepository.getInstance().getDoctorRoom(new MessageDataSource.MessageGetRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> listVos) throws Exception {
                getView().showtDoctorSuccess(listVos);
            }
        });
    }

    @Override
    protected void createRoom(String name) {
        RequestForm requestForm = new RequestForm("", name);
        MessageRepository.getInstance().createRoom(requestForm, new MessageDataSource.MessageCreateRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(String roomId) throws Exception {
                getView().showSuccess(roomId);
            }
        });
    }

    @Override
    protected void deleteRoomId(String roomId) {
        RequestForm requestForm = new RequestForm("", roomId);
        MessageRepository.getInstance().deleteRoomId(requestForm, new MessageDataSource.MessageDeleteRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception {
            }
        });
    }

}
