package com.example.administrator.mybookreader.base;

/**
 * 模板协议接口
 * Created by Administrator on 2017/5/11 0011.
 */

public interface BaseContract {

    /**
     * 模板BasePresenter接口
     * @param <T>
     */
    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    /**
     * 模板BaseView接口
     */
    interface BaseView {

        void showError();

        void complete();
    }
}
