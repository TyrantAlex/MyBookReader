package com.example.administrator.mybookreader.api;

import com.example.administrator.mybookreader.bean.AutoComplete;
import com.example.administrator.mybookreader.bean.HotWord;
import com.example.administrator.mybookreader.bean.SearchDetail;

import retrofit2.http.GET;
import retrofit2.http.Query;
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

    /**
     * 关键字自动补全
     * @param query
     * @return
     */
    @GET("/book/auto-complete")
    Observable<AutoComplete> autoComplete(@Query("query") String query);

    /**
     * 书籍查询
     * @param query
     * @return
     */
    @GET("/book/fuzzy-search")
    Observable<SearchDetail> searchBooks(@Query("query") String query);
}
