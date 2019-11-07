package com.example.HealthyCampus.module.Message.List;

import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

public interface MessageListContract {
    //专门来处理view的变化
    interface View extends BaseView {

        void refreshList(List<MessageListVo> messageListVos);

        void loadComplete();

        void noChatItem(boolean value);

        void MessageError(Throwable throwable);

    }

    //处理业务逻辑
    abstract class Presenter extends BasePresenter<View> {
        protected abstract void lastMessage();

    }
}
