package com.example.administrator.mybookreader.api.support;

import com.example.administrator.mybookreader.utils.LogUtils;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class Logger implements LoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        LogUtils.i("http : " + message);
    }
}
