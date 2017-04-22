package com.example.administrator.mybookreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.base.BaseActivity;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.component.DaggerMainComponent;
import com.example.administrator.mybookreader.ui.fragment.CommunityFragment;
import com.example.administrator.mybookreader.ui.fragment.FindFragment;
import com.example.administrator.mybookreader.ui.fragment.RecommendFragment;
import com.example.administrator.mybookreader.view.RVPIndicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.indicator)
    RVPIndicator indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    //主页3个tab标签名称list
    private List<String> mDatas;

    //3个Fragment的list
    private List<Fragment> mTabContents;

    //ViewPager管理Fragment的适配器
    private FragmentPagerAdapter mAdapter;

    /**
     * BaseActivity方法的具体实现
     *
     * @return 返回MainActivity XML文件的id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     *
     * 获取Application的Component实例后建立连接关系的具体实现
     * @param appComponent
     */
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
        //设置标签栏的title
        indicator.setTabItemTitles(mDatas);
        viewpager.setAdapter(mAdapter);
        //设置延迟加载的页面上限为3
        viewpager.setOffscreenPageLimit(3);
        indicator.setViewPager(viewpager, 0);
    }

    @Override
    protected void initDatas() {
        //装载标签栏名称数据
        mDatas = Arrays.asList(getResources().getStringArray(R.array.home_tabs));
        //开始装载Fragment
        mTabContents = new ArrayList<Fragment>();
        mTabContents.add(new RecommendFragment());
        mTabContents.add(new CommunityFragment());
        mTabContents.add(new FindFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) { return mTabContents.get(position); }

            @Override
            public int getCount() { return mTabContents.size(); }
        };
    }

    @Override
    protected void initToolBar() {
        mCommonToolbar.setLogo(R.mipmap.logo);
        setTitle("life is short");
    }

    /**
     * 创建菜单栏:包含search按钮
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

