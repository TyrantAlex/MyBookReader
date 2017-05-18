package com.example.administrator.mybookreader.ui.contract;

import com.example.administrator.mybookreader.base.BaseContract;
import com.example.administrator.mybookreader.bean.SearchDetail;

import java.util.List;

/**
 * 搜索接口定义
 * Created by Administrator on 2017/5/11 0011.
 */

public interface SearchContract {

    /**
     * 搜索需要显示的方法接口
     */
    interface View extends BaseContract.BaseView {

        /**
         * 显示热词
         * @param list
         */
        void showHotWordList(List<String> list);

        /**
         * 显示AutoComplete列表
         * @param list
         */
        void showAutoCompleteList(List<String> list);

        /**
         * 显示搜索结果列表
         * @param list
         */
        void showSearchResultList(List<SearchDetail.SearchBooks> list);
    }

    /**
     * 搜索中间层接口
     * 获取数据，以及向底层传递并返回上层
     * @param <T>
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getHotWordList();

        void getAutoCompleteList(String query);

        void getSearchResultList(String query);
    }
}
