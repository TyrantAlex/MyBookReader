package com.example.administrator.mybookreader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mybookreader.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * App启动页
 * 跳转主页
 * Created by Administrator on 2017/3/11 0011.
 */

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.tvSkip)
    TextView tvSkip;

    private boolean flag = false;
    private Runnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        };

        tvSkip.postDelayed(runnable,2000);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * synchronized
     * 防止该方法并发同时执行
     * 保证在同一时刻最多只有一个线程执行该段代码
     */
    private synchronized void goHome(){
        if (!flag){
            flag = true;
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
        //移除消息队列中的runnable
        tvSkip.removeCallbacks(runnable);
        ButterKnife.unbind(this);
    }
}
