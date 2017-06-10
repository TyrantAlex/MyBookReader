package com.example.administrator.mybookreader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.base.BaseActivity;
import com.example.administrator.mybookreader.bean.BookDetail;
import com.example.administrator.mybookreader.bean.HotReview;
import com.example.administrator.mybookreader.bean.RecommendBookList;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.ui.contract.BookDetailContract;

import java.util.List;

public class BookDetailActivity extends BaseActivity implements BookDetailContract.View{

    public static String INTENT_BOOK_ID = "bookId";

    public static void startActivity(Context context, String bookId) {
        context.startActivity(new Intent(context,
                BookDetailActivity.class).putExtra(INTENT_BOOK_ID, bookId));
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
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showBookDetail(BookDetail data) {

    }

    @Override
    public void showHotReview(List<HotReview.Reviews> list) {

    }

    @Override
    public void showRecommendBookList(List<RecommendBookList.RecommendBook> list) {

    }
}
