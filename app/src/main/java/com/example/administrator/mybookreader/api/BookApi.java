package com.example.administrator.mybookreader.api;

import com.example.administrator.mybookreader.base.Constant;
import com.example.administrator.mybookreader.bean.AutoComplete;
import com.example.administrator.mybookreader.bean.HotWord;
import com.example.administrator.mybookreader.bean.SearchDetail;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class BookApi {

    private static BookApi sInstance;

    private BookApiService service;

    public BookApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //添加Rxjava适配器 使 Retrofit返回对象可以使用Observable对象
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getsInstance(OkHttpClient okHttpClient){
        if(sInstance == null){
            sInstance = new BookApi(okHttpClient);
        }
        return sInstance;
    }

    /**
     * 获取热词
     * @return 被观察者对象
     */
    public Observable<HotWord> getHotWord(){
        return service.getHotWord();
    }

    /**
     * 关键字补全
     * @param query
     * @return
     */
    public Observable<AutoComplete> getAutoComplete(String query){
        return service.autoComplete(query);
    }

    /**
     * 书籍查询
     * @param query
     * @return
     */
    public Observable<SearchDetail> getSearchResult(String query){
        return service.searchBooks(query);
    }
}
