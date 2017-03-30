package com.example.administrator.mybookreader.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.base.BaseActivity;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.component.DaggerMainComponent;
import com.example.administrator.mybookreader.view.RVPIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.indicator)
    RVPIndicator indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    /**
     * BaseActivity方法的具体实现
     *
     * @return 返回MainActivity XML文件的id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolBar() {
        mCommonToolbar.setLogo(R.mipmap.logo);
        setTitle("Python");
    }

}

