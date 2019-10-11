package com.example.HealthyCampus.common.network.ErrorHandler;

import android.text.TextUtils;

import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class RequestFailError extends Throwable {
    private DefaultResponseVo response;
    private RetrofitError error;

    public RequestFailError(RetrofitError cause) {
        super(cause);
        this.error = cause;
        Response _response = cause.getResponse();
        try {
            String jsonStr = StringUtil.formatInputStream(_response.getBody().in());
            if (!TextUtils.isEmpty(jsonStr)) {
                response = JsonUtil.format(jsonStr, DefaultResponseVo.class);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response = new DefaultResponseVo(1000, "Bad Server");
        // print error log
        Logger.e(String.format("Request %s Fail, Code:%s, Message:%s", cause.getUrl(), response.code, response.message));
    }


    public DefaultResponseVo getResponse() {
        return response;
    }

    public void setResponse(DefaultResponseVo response) {
        this.response = response;
    }

    public RetrofitError getError() {
        return error;
    }

    public void setError(RetrofitError error) {
        this.error = error;
    }
}
