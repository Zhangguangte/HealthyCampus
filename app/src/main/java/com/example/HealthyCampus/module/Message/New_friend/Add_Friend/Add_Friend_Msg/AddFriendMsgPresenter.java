package com.example.HealthyCampus.module.Message.New_friend.Add_Friend.Add_Friend_Msg;


import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.DateUtils;

public class AddFriendMsgPresenter extends AddFriendMsgContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void sendRequestFriend(RequestForm requestForm) {
        FriendRepository.getInstance().sendRequestFriend(requestForm, new FriendRepository.SendRequestFriend() {

            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView())
                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                if (null != getView()) getView().sendSuccess();
            }

        });
    }

    @Override
    protected RequestForm encapsulationData(String quest_id, String content) {
        return new RequestForm(quest_id, content);
    }
}
