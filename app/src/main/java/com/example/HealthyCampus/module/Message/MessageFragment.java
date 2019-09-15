package com.example.HealthyCampus.module.Message;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;

public class MessageFragment extends BaseFragment<MessageContract.View,MessageContract.Presenter> implements MessageContract.View, ITabFragment {
    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected MessageContract.Presenter setPresenter() {
        return null;
    }
}
