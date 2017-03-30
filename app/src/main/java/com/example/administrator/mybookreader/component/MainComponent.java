package com.example.administrator.mybookreader.component;

import com.example.administrator.mybookreader.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Component(dependencies = AppComponent.class)
public interface MainComponent {

    MainActivity inject (MainActivity activity);
}
