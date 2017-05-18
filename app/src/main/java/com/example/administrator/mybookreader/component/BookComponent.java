package com.example.administrator.mybookreader.component;

import com.example.administrator.mybookreader.ui.activity.SearchActivity;

import dagger.Component;

/**
 * Component依赖
 * Created by Administrator on 2017/5/15 0015.
 */
@Component(dependencies = AppComponent.class)
public interface BookComponent {
    SearchActivity inject(SearchActivity activity);
}
