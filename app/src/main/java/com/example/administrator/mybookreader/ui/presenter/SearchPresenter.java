package com.example.administrator.mybookreader.ui.presenter;

import com.example.administrator.mybookreader.api.BookApi;
import com.example.administrator.mybookreader.base.RxPresenter;
import com.example.administrator.mybookreader.bean.AutoComplete;
import com.example.administrator.mybookreader.bean.HotWord;
import com.example.administrator.mybookreader.bean.SearchDetail;
import com.example.administrator.mybookreader.ui.contract.SearchContract;
import com.example.administrator.mybookreader.utils.LogUtils;
import com.example.administrator.mybookreader.utils.RxUtil;
import com.example.administrator.mybookreader.utils.StringUtils;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @SuppressWarnings("unchecked")
    public void getHotWordList() {
        String key = StringUtils.creatAcacheKey("hot-word-list");
        /**
         *  将通过网络获取的数据保存到缓存中
         */
        Observable<HotWord> fromNetWork = bookApi.getHotWord().compose(RxUtil.<HotWord>rxCacheListHelper(key));

        //依次检查disk，network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, HotWord.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotWord>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                    }

                    @Override
                    public void onNext(HotWord hotWord) {
                        List<String> list = hotWord.hotWords;
                        if (list != null && !list.isEmpty() && mView != null){
                            mView.showHotWordList(list);
                        }
                    }
                });
        //添加到整体的订阅管理中
        addSubscribe(rxSubscription);
    }

    @Override
    public void getAutoCompleteList(String query) {
        Subscription rxSubscription = bookApi.getAutoComplete(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoComplete>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onNext(AutoComplete autoComplete) {
                        LogUtils.d("getAutoCompleteList" + autoComplete.keywords);
                        List<String> list = autoComplete.keywords;
                        if (list != null && !list.isEmpty() && mView != null){
                            mView.showAutoCompleteList(list);
                        }
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getSearchResultList(String query) {
        Subscription rxSubscription = bookApi.getSearchResult(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDetail>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onNext(SearchDetail searchDetail) {
                        List<SearchDetail.SearchBooks> list = searchDetail.books;
                        if (list != null && !list.isEmpty() && mView != null){
                            mView.showSearchResultList(list);
                        }
                    }
                });
        addSubscribe(rxSubscription);
    }
}
