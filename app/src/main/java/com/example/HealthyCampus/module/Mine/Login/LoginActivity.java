package com.example.HealthyCampus.module.Mine.Login;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.engine.MyTextWatcher;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.MainActivity;
import com.example.HealthyCampus.module.Mine.Register.first.RegisterActivity1;
import com.example.HealthyCampus.service.UserService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * 2019.10.11
 */

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.showPassword)
    ImageView showPassword;
    @BindView(R.id.btnRegister)
    TextView btnRegister;
    @BindView(R.id.Username_clear)
    ImageView Username_clear;
    @BindView(R.id.Password_clear)
    ImageView Password_clear;

    private boolean PasswordVisible = false;
    private boolean isExistUsername = false;
    private boolean isExistPassword = false;
    private String loginHint = "";
    private MaterialDialog progressDialog;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_login);
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        initProgressView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        try {
            mPresenter.listenLoginEditText();
            mPresenter.foucusLoginEditText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.btnLogin)
    public void login(View view) {
        showProgressView();
        LoginForm dataForm = mPresenter.encapsulationData(etUsername.getText().toString(), etPassword.getText().toString());
        mPresenter.login(dataForm, etPassword.getText().toString());
    }


    @OnClick(R.id.btnRegister)
    public void Register(View view) {
        startActivity(new Intent(this, RegisterActivity1.class));
    }

    @OnClick(R.id.showPassword)
    public void showPassword(View view) {
        setShowPassword();
    }

    @OnClick({R.id.Username_clear, R.id.Password_clear})
    public void cleanLoginEditText(View view) {
        switch (view.getId()) {
            case R.id.Username_clear:
                cleanLoginEditText(etUsername, Username_clear);
                break;
            case R.id.Password_clear:
                cleanLoginEditText(etPassword, Password_clear);
                break;
            default:
                break;
        }

    }


    @Override
    public void setLoginButtonEnable() {
        //设置登录按钮可用并且颜色变成深绿色
        if (isExistUsername && isExistPassword) {
            btnLogin.setBackgroundColor(getResources().getColor(R.color.clickable_color));
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setBackgroundColor(getResources().getColor(R.color.not_clickable_color));
            btnLogin.setEnabled(false);
        }
    }


    @Override
    public void setSeePasswordEnable(boolean value) {
        if (value) {
            showPassword.setVisibility(View.VISIBLE);
            showPassword.setEnabled(true);
        } else {
            showPassword.setVisibility(View.INVISIBLE);
            showPassword.setEnabled(false);
        }
    }

    @Override
    public void setShowPassword() {
        if (PasswordVisible) {
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_visible));
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.password_invisible));
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        PasswordVisible = !PasswordVisible;
    }

    @Override
    public void setSeeClearEnable(boolean value, ImageView imageView) {
        if (value) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Log.e("LoginActivity" + "123456", "Login error");
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                String errorbody = httpException.errorBody().string();
                DefaultResponseVo response = JsonUtil.format(errorbody, DefaultResponseVo.class);
                Log.e("LoginActivity" + "123456", "errorbody:" + errorbody);
                Log.e("LoginActivity" + "123456", "response.toString:" + response.toString());
                if (response.code == 1001) {
                    ToastUtil.show(LoginActivity.this, "账号或者密码错误");
                } else {
                    ToastUtil.show(LoginActivity.this, "未知错误:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("LoginActivity" + "123456", "throwable.getMessage()" + throwable.getMessage());
            ToastUtil.show(LoginActivity.this, "未知错误:" + throwable.getMessage());
        }
        // Log.e("LoginActivity" + "123456", "throwable.getMessage:" + throwable.getMessage());
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        userService.registerJPush(LoginActivity.this, "1");
//        userService.persistenceUser(userVo, password);

        finish();
    }

    @Override
    public void showProgressView() {
        btnLogin.setEnabled(false);
        progressDialog.show();
    }

    @Override
    public void initProgressView() {
        MaterialDialog.Builder builder = DialogUtil.ProgressView(this);
        builder.title(R.string.network_request)// 标题
                .content(R.string.netwotk_loginig);
        progressDialog = builder.build();// 创建对话框
    }

    @Override
    public void showProgressDialog(String msg) {
        super.showProgressDialog(msg);
    }

    @Override
    public void dismissProgressView() {
        btnLogin.setEnabled(true);
        progressDialog.dismiss();
    }

    @Override
    public void setLoginHint(final EditText editText, final ImageView imageView) {
        if (!TextUtils.isEmpty(editText.getHint().toString().trim())) {
            loginHint = editText.getHint().toString().trim();
            editText.setTag(loginHint);
            editText.setHint("");
            setSeeClearEnable(editText.getText().toString().trim().length() > 0, imageView);
        } else {
            loginHint = editText.getTag().toString().trim();
            editText.setHint(loginHint);
            setSeeClearEnable(false, imageView);
        }
    }


    @Override
    public void listenLoginEditTextStatus() {
        etPassword.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                setSeePasswordEnable(true);
                setSeeClearEnable(true, Password_clear);
                isExistPassword = true;
            }

            @Override
            public void onEdit() {
                setSeeClearEnable(false, Password_clear);
                setSeePasswordEnable(false);
                isExistPassword = false;
            }

            @Override
            public void onFinish() {
                setLoginButtonEnable();
            }

        }));

        etUsername.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                isExistUsername = true;
                setSeeClearEnable(true, Username_clear);
            }

            @Override
            public void onEdit() {
                isExistUsername = false;
                setSeeClearEnable(false, Username_clear);
            }

            @Override
            public void onFinish() {
                setLoginButtonEnable();
            }

        }));
    }

    @Override
    public void focusLoginEditTextStatus() {
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setLoginHint(etUsername, Username_clear);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setLoginHint(etPassword, Password_clear);
            }
        });
    }


    @Override
    public void cleanLoginEditText(EditText editText, ImageView imageView) {
        editText.setText("");
        imageView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValues.RESULT_CODE_USERNAME && resultCode == RESULT_OK) {
            String username = data.getStringExtra(ConstantValues.KEY_USERNAME);
            etUsername.setText(username);
            etPassword.requestFocus();
        }
    }


}
