package com.example.administrator.mybookreader.ui.contract;

import com.example.administrator.mybookreader.base.BaseContract;
import com.example.administrator.mybookreader.bean.BookDetail;
import com.example.administrator.mybookreader.bean.HotReview;
import com.example.administrator.mybookreader.bean.RecommendBookList;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/6/6 0006.
 */

public interface BookDetailContract {

    interface View extends BaseContract.BaseView{
        void showBookDetail(BookDetail data);

        void showHotReview(List<HotReview.Reviews> list);

        void showRecommendBookList(List<RecommendBookList.RecommendBook> list);

    }

    interface Presenter<T> extends BaseContract.BasePresenter {
        void getBookDetail(String bookId);

        void getHotReview(String book);

        void getRecommendBookList(String bookId, String limit);
    }
}
