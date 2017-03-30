package com.example.administrator.mybookreader.moudle;

import com.example.administrator.mybookreader.api.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * 提供BookApi实例的Module
 * Created by Administrator on 2017/3/11 0011.
 */
@Module
public class BookApiMoudle {

    /**
     * 提供BookApi的依赖
     * @return BookApi的实例
     */
    @Provides
    protected BookApi provideBookService(){
        return BookApi.getsInstance();
    }
}
