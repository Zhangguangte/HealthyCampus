package com.example.HealthyCampus.module;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.example.HealthyCampus.R;

/**
 * OK
 */
public class MainPresenter extends MainContract.Presenter {

    private long currentTime;

    @Override
    public void onStart() {
        //在用户打开App时，通知用户新的反馈回复
        FeedbackAgent agent = new FeedbackAgent(getView().getContext());
        agent.sync();
    }

    @Override
    public void exitApp()   {
        if (System.currentTimeMillis() - currentTime < 2 * 1000) {
            getView().finishView();
        } else {
            currentTime = System.currentTimeMillis();
            getView().showSnackBar(R.string.main_exit_app);
        }
    }


}
