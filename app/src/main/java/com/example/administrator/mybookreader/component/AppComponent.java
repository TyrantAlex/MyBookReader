package com.example.administrator.mybookreader.component;

import android.content.Context;

import com.example.administrator.mybookreader.api.BookApi;
import com.example.administrator.mybookreader.moudle.AppMoudle;
import com.example.administrator.mybookreader.moudle.BookApiMoudle;

import dagger.Component;
import dagger.Module;

/**
 * 提供依赖注入的中间件
 * Created by Administrator on 2017/3/10 0010.
 */
@Component(modules = {BookApiMoudle.class, AppMoudle.class})
public interface AppComponent {

    Context getContext();

    BookApi getReaderApi();

}
