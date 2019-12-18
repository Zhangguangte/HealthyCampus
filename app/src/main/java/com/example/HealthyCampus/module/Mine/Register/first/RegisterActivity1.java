package com.example.HealthyCampus.module.Mine.Register.first;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.engine.MyTextWatcher;
import com.example.HealthyCampus.common.utils.CodeUtils;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Mine.Login.LoginActivity;
import com.example.HealthyCampus.module.Mine.Register.second.RegisterActivity2;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity1 extends BaseActivity<RegisterContract1.View, RegisterContract1.Presenter> implements RegisterContract1.View {

    @BindView(R.id.picture_code_image)
    ImageView picture_code_image;
    @BindView(R.id.sendSms)
    TextView sendSms;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.smsCode)
    EditText smsCode;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.pictureCode)
    EditText pictureCode;

    private CodeUtils codeUtils;
    private EventHandler eventHandler;

    private boolean isExistPhone = false;
    private boolean isExistPictureCode = false;
    private boolean isExistSmsCode = false;

    private int countDown = 60;//倒计时
    private String RegisterHint = "";
    private MaterialDialog progressDialog;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_register1);
    }

    @Override
    protected RegisterContract1.Presenter createPresenter() {
        return new RegisterPresenter1();
    }

    @Override
    protected void initView() {
        generatePictureCode();
        initProgressView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initSMS();
        try {
            mPresenter.listenRegisterEditText();
        mPresenter.foucusRegisterEditText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void initSMS() {
        SMSSDK.initSDK(this, ConstantValues.MOBSMSSDK_ID, ConstantValues.MOBSMSSDK_SECRET);
        //initSDK方法是短信SDK的入口，需要传递您从MOB应用管理后台中注册的SMSSDK的应用AppKey和AppSecrete，如果填写错误，后续的操作都将不能进行
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }


    //获取验证码
    @Override
    public void generatePictureCode() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        picture_code_image.setImageBitmap(bitmap);
    }

    @OnClick(R.id.sendSms)
    public void btnSmsRespond(View view) {
        String phoneNum = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.show(this, R.string.register1_phone_not_empty);
            return;
        }
        SMSSDK.getVerificationCode(ConstantValues.COUNTRYCODE, phoneNum);
        sendSms.setClickable(false);
        //开始倒计时
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; countDown > 0; countDown--) {
                    handler.sendEmptyMessage(-1);
                    if (countDown <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-2);
            }
        }).start();
    }

    @OnClick(R.id.btnNext)
    public void btnNextRespond(View view) {
        String phoneNum = etPhone.getText().toString().trim();
        RegisterFrom registerFrom = new RegisterFrom();
        registerFrom.setPhone(phoneNum);
        try {
            mPresenter.searchPhone(registerFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void phoneNotExist() {
        String phoneNum = etPhone.getText().toString().trim();
        String sms_code = smsCode.getText().toString().trim();
        String picture_code = pictureCode.getText().toString().trim();
        Log.e("RegisterActivity1" + "123456", "picture_code: " + picture_code);
        Log.e("RegisterActivity1" + "123456", "codeUtils.getCode: " + codeUtils.getCode());
        if (!codeUtils.getCode().equals(picture_code.toLowerCase())) {
            ToastUtil.show(this, R.string.register1_picture_code_error);
            return;
        } else {
            SMSSDK.submitVerificationCode(ConstantValues.COUNTRYCODE, phoneNum, sms_code);
        }
    }

    @Override
    public void phoneExist() {
        ToastUtil.show(this, R.string.register1_alread_register);
    }

    @Override
    public void showProgressView() {
        btnNext.setEnabled(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressView() {
        btnNext.setEnabled(true);
        progressDialog.dismiss();
    }

    @Override
    public void initProgressView() {
        MaterialDialog.Builder builder = DialogUtil.ProgressView(this);
        builder.title(R.string.network_request)// 标题
                .content(R.string.netwotk_registering);
        progressDialog = builder.build();// 创建对话框
    }

    @Override
    public void setSMSTextViewEnable() {
        if (isExistPhone && isExistPictureCode) {
            sendSms.setEnabled(true);
            sendSms.setTextColor(getResources().getColor(R.color.clickable_color));
        } else {
            sendSms.setEnabled(false);
            sendSms.setTextColor(getResources().getColor(R.color.not_clickable_color));
        }
    }

    @Override
    public void setNextButtonEnable() {
        if (isExistPhone && isExistPictureCode && isExistSmsCode) {
            btnNext.setEnabled(true);
            btnNext.setBackgroundColor(getResources().getColor(R.color.clickable_color));
        } else {
        //    btnNext.setEnabled(false);
            btnNext.setBackgroundColor(getResources().getColor(R.color.not_clickable_color));
        }

    }

    @Override
    public void setRegisterHint(final EditText editText) {
        if (!TextUtils.isEmpty(editText.getHint().toString().trim())) {
            RegisterHint = editText.getHint().toString().trim();
            editText.setTag(RegisterHint);
            editText.setHint("");
        } else {
            RegisterHint = editText.getTag().toString().trim();
            editText.setHint(RegisterHint);
        }
    }

    @Override
    public void listenRegisterEditTextStatus() {
        etPhone.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                isExistPhone = StringUtil.isMobileNO(etPhone.getText().toString().trim());
            }

            @Override
            public void onEdit() {
                isExistPhone = false;
            }

            @Override
            public void onFinish() {
                setSMSTextViewEnable();
                setNextButtonEnable();
            }
        }));

        pictureCode.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                isExistPictureCode = pictureCode.getText().toString().trim().length() == 5;
            }

            @Override
            public void onEdit() {
                isExistPictureCode = false;
            }

            @Override
            public void onFinish() {
                setSMSTextViewEnable();
                setNextButtonEnable();
            }
        }));

        smsCode.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                isExistSmsCode = true;
            }

            @Override
            public void onEdit() {
                isExistSmsCode = false;
            }

            @Override
            public void onFinish() {
                setNextButtonEnable();
                setNextButtonEnable();
            }
        }));


    }

    @Override
    public void focusRegisterEditTextStatus() {
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setRegisterHint(etPhone);
            }
        });

        pictureCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setRegisterHint(pictureCode);
            }
        });

        smsCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setRegisterHint(smsCode);
            }
        });
    }

    @Override
    public void jumpToNext() {
        Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", etPhone.getText().toString().trim() + "");
        intent.putExtras(bundle);
        startActivity(intent);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void jumpToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }



    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                sendSms.setText(countDown + " s");
            } else if (msg.what == -2) {
                sendSms.setText(R.string.register1_code_already_sent);
                sendSms.setClickable(true);
                countDown = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("RegisterActivity1" + "123456", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口
                        jumpToNext();
//                        Toast.makeText(getApplicationContext(), "短信验证成功",
//                                Toast.LENGTH_SHORT).show();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("RegisterActivity1" + "123456", "des: " + des);
                            Toast.makeText(RegisterActivity1.this, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    @OnClick(R.id.picture_code_image)
    public void pictureCode(View view) {
        generatePictureCode();
    }

    @OnClick(R.id.returnBack)
    public void btnBackRespond(View view) {
        jumpToLogin();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
