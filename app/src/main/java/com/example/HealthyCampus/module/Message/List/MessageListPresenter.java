package com.example.HealthyCampus.module.Message.List;

import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

public class MessageListPresenter extends MessageListContract.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    protected void lastMessage() {
        MessageRepository.getInstance().lastMessage( new MessageDataSource.MessageSearchMessage() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().noChatItem(true);
                getView().MessageError(throwable);
                getView().loadComplete();
            }

            @Override
            public void onDataAvailable(List<MessageListVo> messageListVos) throws Exception {
                getView().noChatItem(false);
                getView().refreshList(messageListVos);
                getView().loadComplete();
            }
        });
    }

}
