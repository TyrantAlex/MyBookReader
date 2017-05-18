package com.example.administrator.mybookreader.ui.presenter;

import com.example.administrator.mybookreader.api.BookApi;
import com.example.administrator.mybookreader.base.RxPresenter;
import com.example.administrator.mybookreader.ui.contract.SearchContract;

import javax.inject.Inject;

/**
 * 搜索中间层
 * Created by Administrator on 2017/5/15 0015.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter<SearchContract.View>{

    private BookApi bookApi;

    @Inject
    public SearchPresenter(BookApi bookApi){
        this.bookApi = bookApi;
    }

    public void getHotWordList() {

    }

    @Override
    public void getAutoCompleteList(String query) {

    }

    @Override
    public void getSearchResultList(String query) {

    }
}
