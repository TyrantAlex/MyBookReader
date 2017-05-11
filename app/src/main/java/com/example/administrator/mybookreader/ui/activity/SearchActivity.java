package com.example.administrator.mybookreader.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.base.BaseActivity;
import com.example.administrator.mybookreader.base.BaseContract;
import com.example.administrator.mybookreader.base.BaseRVActivity;
import com.example.administrator.mybookreader.bean.SearchDetail;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.ui.contract.SearchContract;

import java.util.List;

/**
 * 搜索Activity
 */
public class SearchActivity extends BaseRVActivity<SearchDetail.SearchBooks> implements SearchContract.View{


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void showHotWordList(List<String> list) {

    }

    @Override
    public void showAutoCompleteList(List<String> list) {

    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
