package com.example.administrator.mybookreader.api;

import com.example.administrator.mybookreader.bean.HotWord;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public interface BookApiService {

    /**
     * 直接使用Rxjava中的Observable对象
     * 需要在Retrofit对象初始化的时候添加RxjavaCallAdapterFactory
     * 否则会报 java.lang.IllegalArgumentException:
     * Unable to create call adapter for rx.Observable异常
     * @return
     */
    @GET("/book/hot-word")
    Observable<HotWord> getHotWord();
}
