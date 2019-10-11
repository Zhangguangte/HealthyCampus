package com.example.HealthyCampus.common.network.ErrorHandler;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class DefaultErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        return new RequestFailError(cause);
    }
}
