package com.example.administrator.mybookreader.moudle;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * 提供上下文对象的Moudle
 * Created by Administrator on 2017/3/11 0011.
 */
@Module
public class AppMoudle {

    private Context context;

    public AppMoudle(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
