package com.example.administrator.mybookreader.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lfh on 2016/9/11.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 * Created by Administrator on 2017/5/17 0017.
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>{

    protected T mView;

    /**
     * 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅
     * 来解决 Observable持有Context导致的内存泄露问题
     */
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 取消订阅
     */
    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 添加订阅
     * @param subscription
     */
    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
