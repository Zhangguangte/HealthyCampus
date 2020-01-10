package com.example.HealthyCampus.Broadcast;

import android.content.Context;
import android.content.Intent;

import com.example.HealthyCampus.application.HealthApp;
import com.example.HealthyCampus.module.Loading.LoadingActivity;
import com.orhanobut.logger.Logger;
import com.today.step.lib.BaseClickBroadcast;

/**
 * 计步消息广播监听
 */
public class StepStarter extends BaseClickBroadcast {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        HealthApp healthApp = (HealthApp) context.getApplicationContext();
        if (!healthApp.isForeground()) {
            Intent mainIntent = new Intent(context, LoadingActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        } else {
            Logger.e("123456:已经在计步了");
        }
    }
}