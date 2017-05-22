package com.example.administrator.mybookreader.moudle;

import com.example.administrator.mybookreader.api.BookApi;
import com.example.administrator.mybookreader.api.support.HeaderInterceptor;
import com.example.administrator.mybookreader.api.support.Logger;
import com.example.administrator.mybookreader.api.support.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * 提供BookApi实例的Module
 * Created by Administrator on 2017/3/11 0011.
 */
@Module
public class BookApiMoudle {

    @Provides
    public OkHttpClient provideOkHttpClient(){
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor(new Logger());
        loggingInterceptor.setLevel(LoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) //失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor);

        return builder.build();
    }

    /**
     * 提供BookApi的依赖
     * @param okHttpClient
     * @return BookApi的实例
     */
    @Provides
    protected BookApi provideBookService(OkHttpClient okHttpClient){
        return BookApi.getsInstance(okHttpClient);
    }
}
