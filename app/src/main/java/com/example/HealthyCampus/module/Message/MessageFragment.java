package com.example.HealthyCampus.module.Message;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;
import com.example.HealthyCampus.module.Message.Address_list.AddressListActivity;
import com.example.HealthyCampus.module.Message.New_friend.NewFriendActivity;
import com.example.HealthyCampus.module.Message.List.MessageListFragment;
import com.example.HealthyCampus.module.Message.Notice.NoticeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageFragment extends BaseFragment<MessageContract.View, MessageContract.Presenter> implements MessageContract.View, ITabFragment {

    @BindView(R.id.noChatItem)
    LinearLayout noChatItem;
    @BindView(R.id.mailList)
    ImageView mailList;
    @BindView(R.id.newFriend)
    ImageView newFriend;
    @BindView(R.id.ivNotice)
    ImageView ivNotice;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvFunction)
    TextView tvInitiateChat;

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public void setUpView(View view) {
        MessageListFragment fragment = MessageListFragment.newInstance();
        if (fragment != null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.mContainerLayout, fragment, MessageListFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.tab_message);
        tvInitiateChat.setVisibility(View.VISIBLE);
        tvInitiateChat.setText(R.string.message_initiate_chat);
    }


    @Override
    protected MessageContract.Presenter setPresenter() {
        return new MessagePresenter();
    }


    @Override
    public void noChatItemVisible(boolean visible) {
        noChatItem.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.mailList)
    public void mailList(View view) {
        Intent intent = new Intent(mActivity, AddressListActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.newFriend)
    public void newFriend(View view) {
        Intent intent = new Intent(mActivity, NewFriendActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.ivNotice)
    public void ivNotice(View view) {
        Intent intent = new Intent(mActivity, NoticeActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
