package com.example.HealthyCampus.module.HomePage.Consultation;


import android.text.TextUtils;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.ToastUtil;

import java.util.List;

public class ConsultationPresenter extends ConsultationContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getDoctorRoom() {
        MessageRepository.getInstance().getDoctorRoom(new MessageDataSource.MessageGetRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> listVos) {
                if (null != getView()) {
                    SPHelper.setString(SPHelper.MEIQIA_NAME_1, listVos.get(0).getAnother_name());
                    SPHelper.setString(SPHelper.MEIQIA_ROOM_1, listVos.get(0).getRoom_id());
                    SPHelper.setInt(SPHelper.MEIQIA_COOUNT, listVos.size());
                    SPHelper.setString(SPHelper.MERQIA_DATE, DateUtils.getStringDate());
                    if (listVos.size() == 2) {
                        getView().showtDoctorSuccess(listVos.get(0).getAnother_name(), listVos.get(0).getRoom_id(), listVos.get(1).getAnother_name(), listVos.get(1).getRoom_id());
                        SPHelper.setString(SPHelper.MEIQIA_NAME_2, listVos.get(1).getAnother_name());
                        SPHelper.setString(SPHelper.MEIQIA_ROOM_2, listVos.get(1).getRoom_id());
                    } else {
                        getView().showtDoctorSuccess(listVos.get(0).getAnother_name(), listVos.get(0).getRoom_id(), null, null);
                    }
                }
            }
        });
    }

    @Override
    protected void createRoom(String name) {
        RequestForm requestForm = new RequestForm("", name);
        MessageRepository.getInstance().createRoom(requestForm, new MessageDataSource.MessageCreateRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(String roomId) {
                if (null != getView()) {
                    if (TextUtils.isEmpty(SPHelper.getString(SPHelper.MEIQIA_NAME_1)) || TextUtils.isEmpty(SPHelper.getString(SPHelper.MEIQIA_ROOM_1))) {
                        SPHelper.setString(SPHelper.MEIQIA_NAME_1, name);
                        SPHelper.setString(SPHelper.MEIQIA_ROOM_1, roomId);
                    } else {
                        SPHelper.setString(SPHelper.MEIQIA_NAME_2, name);
                        SPHelper.setString(SPHelper.MEIQIA_ROOM_2, roomId);
                    }
                    getView().showSuccess(roomId, name);
                }
            }
        });
    }

    @Override
    protected void deleteRoomId(String roomId, int number) {
        RequestForm requestForm = new RequestForm("", roomId);
        MessageRepository.getInstance().deleteRoomId(requestForm, new MessageDataSource.MessageDeleteRoom() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                if (null != getView()) {
                    if (1 == number) {
                        SPHelper.setString(SPHelper.MEIQIA_NAME_1, null);
                        SPHelper.setString(SPHelper.MEIQIA_ROOM_1, null);
                    } else {
                        SPHelper.setString(SPHelper.MEIQIA_NAME_2, null);
                        SPHelper.setString(SPHelper.MEIQIA_ROOM_2, null);
                    }
                    SPHelper.setInt(SPHelper.MEIQIA_COOUNT, SPHelper.getInt(SPHelper.MEIQIA_COOUNT, 0) - 1);
                    ToastUtil.show(getView().getContext(), "删除成功");
                }
            }
        });
    }

}
