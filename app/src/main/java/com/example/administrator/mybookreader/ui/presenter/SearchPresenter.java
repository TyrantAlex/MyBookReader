package com.example.administrator.mybookreader.ui.presenter;

import com.example.administrator.mybookreader.api.BookApi;
import com.example.administrator.mybookreader.base.RxPresenter;
import com.example.administrator.mybookreader.bean.HotWord;
import com.example.administrator.mybookreader.ui.contract.SearchContract;
import com.example.administrator.mybookreader.utils.LogUtils;
import com.example.administrator.mybookreader.utils.RxUtil;
import com.example.administrator.mybookreader.utils.StringUtils;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

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
         *
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
        addSubscribe(rxSubscription);
    }

    @Override
    public void getAutoCompleteList(String query) {

    }

    @Override
    public void getSearchResultList(String query) {

    }
}
