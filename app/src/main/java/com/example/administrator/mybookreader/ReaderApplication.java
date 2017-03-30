package com.example.administrator.mybookreader;

import android.app.Application;
import android.content.Context;

import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.component.DaggerAppComponent;
import com.example.administrator.mybookreader.moudle.AppMoudle;
import com.example.administrator.mybookreader.moudle.BookApiMoudle;
import com.example.administrator.mybookreader.utils.SharedPreferencesUtil;

/**
 * APP的全局Application类
 * Created by Administrator on 2017/3/10 0010.
 */

public class ReaderApplication extends Application{

    //单例对象
    private static ReaderApplication sInstance;
    //提供依赖的中间组件实例
    private AppComponent appComponent;

    //单例对外提供实例的方法
    public static ReaderApplication getsInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initComponent();
        initPrefs();
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    private void initComponent(){
        appComponent = DaggerAppComponent.builder()
                .bookApiMoudle(new BookApiMoudle())
                .appMoudle(new AppMoudle(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
