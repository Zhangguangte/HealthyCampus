package com.example.HealthyCampus.module.Message.Chat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.widgets.custom_image.ScaleImageView;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;
import java.util.List;

interface ChatContract {
    interface View extends BaseView {
        Context getContext();

        void finish();

        void showError(Throwable throwable);

        void showRecyclerView(List<ChatItemBean> messageListVos);

        void setRoomId(String roomId);

        void loadPictureSuccess(ImageView sivPicture, String result);

        void loadPictureFail(ImageView sivPicture);

        void addCardItem(UserVo userVo);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void allChatByRoomId(RequestForm requestForm);

        protected abstract void searchRoomid(RequestForm requestForm);

        protected abstract void allChatByUid(RequestForm requestForm);

        protected abstract List<ChatItemBean> changeItemBean(List<MessageListVo> messageListVos);

        protected abstract void insertText(String content, String roomId);

        protected abstract void insertRecord(String content, String roomId, String filePath);

        protected abstract void insertPicture(String content, String roomId, String filePath);

        protected abstract void insertMap(String content, String roomId, String filePath);

        protected abstract void insertCard(String account, String roomId);

        protected abstract void upPicture(String path, String name, String account);

        protected abstract void loadPicture(String belongId, String filename, ImageView sivPicture);

    }
}
