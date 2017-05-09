package com.example.administrator.mybookreader.base;

import com.example.administrator.mybookreader.view.recyclerview.adapter.OnLoadMoreListener;
import com.example.administrator.mybookreader.view.recyclerview.swipe.OnRefreshListener;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public abstract class BaseRVActivity<T> extends BaseActivity implements OnLoadMoreListener,OnRefreshListener {


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
