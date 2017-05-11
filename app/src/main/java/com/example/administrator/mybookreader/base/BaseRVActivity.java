package com.example.administrator.mybookreader.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.utils.NetworkUtils;
import com.example.administrator.mybookreader.view.recyclerview.EasyRecyclerView;
import com.example.administrator.mybookreader.view.recyclerview.adapter.OnLoadMoreListener;
import com.example.administrator.mybookreader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.example.administrator.mybookreader.view.recyclerview.swipe.OnRefreshListener;

import java.lang.reflect.Constructor;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public abstract class BaseRVActivity<T> extends BaseActivity implements OnLoadMoreListener,OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @Bind(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;

    protected RecyclerArrayAdapter<T> mAdapter;

    protected int start = 0;

    protected int limit = 20;

    /**
     * 初始化RecyclerView Adapter方法
     * 两个参数
     * @param refreshable 是否可以刷新
     * @param loadmoreable 是否可以加载更多
     */
    protected void initAdapter(boolean refreshable, boolean loadmoreable){

        if(mAdapter != null){
            //设置ItemClick监听
            mAdapter.setOnItemClickListener(this);
            //设置Error页面和其点击事件
            mAdapter.setError(R.layout.common_error_view).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //重新调用加载更多
                    mAdapter.resumeMore();
                }
            });

            //如果支持加载更多,则为Adapter对象设置加载更多的页面
            if (loadmoreable){

                mAdapter.setMore(R.layout.common_more_view, this);

                mAdapter.setNoMore(R.layout.common_nomore_view);
            }

            //如果支持下拉刷新则设置刷新监听
            if (refreshable && mRecyclerView != null){
                mRecyclerView.setRefreshListener(this);
            }
        }

        if (mRecyclerView != null){
            //为RecyclerView设置布局管理器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            //为RecyclerView设置ItemDecoration
            mRecyclerView.setItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0);
            //设置 RecyclerView 的 Adapter
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }
    }

    /**
     * 利用反射获取对象实例
     * @param cls
     * @return
     */
    public Object createInstance(Class<?> cls){
        Object obj;
        try{
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            /**
             * setAccessible(true) 并不是将方法的访问权限改成了public，而是取消java的权限控制检查。所以即使是public方法，其accessible 属相默认也是false
             */
            c1.setAccessible(true);
            obj = c1.newInstance(mContext);
        }catch (Exception e){
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isConnected(getApplicationContext())){
            //停止加载
            mAdapter.pauseMore();
            return;
        }
    }

    @Override
    public void onRefresh() {
        start = 0;
        if(!NetworkUtils.isConnected(getApplicationContext())){
            mAdapter.pauseMore();
            return;
        }
    }

    /**
     * 加载出错调用此方法
     */
    protected void loaddingError(){
        mAdapter.clear();
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
    }
}
