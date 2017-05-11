package com.example.administrator.mybookreader.ui.contract;

import com.example.administrator.mybookreader.base.BaseContract;
import com.example.administrator.mybookreader.bean.SearchDetail;

import java.util.List;

/**
 * 搜索接口定义
 * Created by Administrator on 2017/5/11 0011.
 */

public interface SearchContract {

    interface View extends BaseContract.BaseView {

        void showHotWordList(List<String> list);

        void showAutoCompleteList(List<String> list);

        void showSearchResultList(List<SearchDetail.SearchBooks> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getHotWordList();

        void getAutoCompleteList(String query);

        void getSearchResultList(String query);
    }
}
