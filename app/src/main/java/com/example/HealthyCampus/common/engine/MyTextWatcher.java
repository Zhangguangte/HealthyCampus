package com.example.HealthyCampus.common.engine;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {

    private OnComplete onComplete;

    public MyTextWatcher(OnComplete onComplete) {
        this.onComplete = onComplete;
    }

    public interface OnComplete {
        void onComplete();

        void onEdit();

        void onFinish();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString().trim();
        if (!TextUtils.isEmpty(str)) {
            onComplete.onComplete();
        } else {
            onComplete.onEdit();
        }
        onComplete.onFinish();
    }
}
