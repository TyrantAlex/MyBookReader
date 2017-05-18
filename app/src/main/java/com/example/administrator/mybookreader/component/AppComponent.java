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

    // ★前面说过这里的这个方法是可以不写的，
    // 但是，如果你想让别的Component依赖这个Component，
    // 就必须写，不写这个方法，就意味着没有向外界，暴露这个依赖
    BookApi getReaderApi();

}
