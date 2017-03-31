package com.example.administrator.mybookreader;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.example.administrator.mybookreader.base.Constant;
import com.example.administrator.mybookreader.base.CrashHandler;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.component.DaggerAppComponent;
import com.example.administrator.mybookreader.moudle.AppMoudle;
import com.example.administrator.mybookreader.moudle.BookApiMoudle;
import com.example.administrator.mybookreader.utils.AppUtils;
import com.example.administrator.mybookreader.utils.LogUtils;
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
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        initPrefs();
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    /**
     * 初始化Component
     */
    private void initComponent(){
        appComponent = DaggerAppComponent.builder()
                .bookApiMoudle(new BookApiMoudle())
                .appMoudle(new AppMoudle(this))
                .build();
    }

    /**
     * 初始化夜间模式
     */
    protected void initNightMode(){
        boolean isNight = SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT);
        LogUtils.d("isNight = " + isNight);
        if(isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 返回Component实例
     * @return
     */
    public AppComponent getAppComponent(){
        return appComponent;
    }
}
