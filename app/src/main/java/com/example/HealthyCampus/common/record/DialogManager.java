package com.example.HealthyCampus.common.record;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogManager {


    @BindView(R.id.dlg_main)
    ImageView mIcon;

    @BindView(R.id.dlg_sec)
    ImageView mVoice;

    @BindView(R.id.dlg_text)
    TextView mLable;

    private Context mContext;
    private AlertDialog.Builder builder;
    private AlertDialog mDialog;
    private View mDialogView;

    public DialogManager(Context context) {
        this.mContext = context;
    }

    public void show() {
        builder = new AlertDialog.Builder(mContext, R.style.NobackDialog);
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.chats_dialog_record, null);    //录音框
        ButterKnife.bind(this, mDialogView);
        builder.setView(mDialogView);
        mDialog = builder.create();
        mDialog.show();
        recording();
    }


    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void recording() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.chatting_recording);
            mLable.setText("手指上滑，取消发送");
        }
    }

    // 显示想取消的对话框
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.chatting_record_cancel);
            mLable.setText("松开手指，取消发送");
        }
    }

    // 显示时间过短的对话框
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.chatting_record_cancel);
            mLable.setText("说话时间太短");
        }
        Log.d("DEBUG", "说话时间太短");
    }

    // 显示更新音量级别的对话框
    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {

            //设置图片的id
//            int resId = mContext.getResources().getIdentifier(v+level, drawable, mContext.getPackageName());
//            mVoice.setImageResource(resId);
        }
    }

}
