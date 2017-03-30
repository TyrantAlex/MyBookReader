package com.example.administrator.mybookreader.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.ReaderApplication;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.utils.SharedPreferencesUtil;
import com.example.administrator.mybookreader.utils.StatusBarCompat;
import com.example.administrator.mybookreader.view.loading.CustomDialog;

import butterknife.ButterKnife;

/**
 * Activity的基类
 * Created by Administrator on 2017/3/11 0011.
 */
public abstract class BaseActivity extends AppCompatActivity{

    public Toolbar mCommonToolbar;

    protected Context mContext;

    protected int statusBarColor = 0;

    protected View statusBarView = null;

    private boolean mNowMode;

    //自定义dialog
    private CustomDialog dialog; //进度条

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if(statusBarColor == 0){
            //根据SDK版本设置状态栏的颜色
            statusBarView = StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }else if(statusBarColor != -1){
            statusBarView = StatusBarCompat.compat(this, statusBarColor);
        }
        //Android sdk版本如果是19~20则设置为透明
        transparent19and20();

        mContext = this;

        ButterKnife.bind(this);

        setupActivityComponent(ReaderApplication.getsInstance().getAppComponent());

        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (mCommonToolbar != null){
            initToolBar();
            setSupportActionBar(mCommonToolbar);
        }

        initDatas();

        configViews();

        mNowMode = SharedPreferencesUtil.getInstance().getBoolean("11");
    }

    /**
     * 抽象方法
     * 对各种控件进行设置, 适配, 填充数据
     */
    protected abstract void configViews();

    /**
     * 抽象方法
     * 初始化数据
     */
    protected abstract void initDatas();

    /**
     * 抽象方法
     * 初始化ToolBar
     */
    protected abstract void initToolBar();

    /**
     * 抽象方法 设置Dagger2 Component
     * @param appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent);

    /**
     * 抽象方法 获取layout
     * @return
     */
    public abstract int getLayoutId();

    /**
     * Android sdk版本如果是19~20则设置为透明
     */
    protected void transparent19and20(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置ToolBar的高度
     * 需求SDK 4.4以上
     * @param elevation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCommonToolbar.setElevation(elevation);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //标志位不相符证明Activity可能被回收了,重新设置模式
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false) != mNowMode) {
            //根据标志位设置夜间模式开启或者关闭
            if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            //重新创建Activity实例
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        dismissDialog();
    }

    /**
     * 隐藏View
     * ...表示不定数量实参,编译器实际会将其作为数组传递进来
     * @param views
     */
    protected void gone(View... views){
        if(views != null && views.length > 0){
            for(View view : views){
                if(view != null) view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示一个或多个view
     * @param views
     */
    protected void visible(View... views){
        if(views != null && views.length > 0){
            for(View view : views){
                if(view != null) view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 判断view是否是显示状态
     * @param view
     * @return
     */
    protected boolean isVisible(View view) { return view.getVisibility() == View.VISIBLE; }

    /**
     * 创建一个模板dialog
     * @return
     */
    public CustomDialog getDialog(){
        if (dialog == null){
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    /**
     * 隐藏dialog
     */
    public void hideDialog(){
        if (dialog != null) dialog.hide();
    }

    /**
     * 干掉一个dialog
     */
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 使dialog从隐藏状态变为可见状态
     */
    public void showDialog(){ getDialog().show(); }

    /**
     * 在此监听左返回按钮的事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if(statusBarView != null){
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    /**
     * 显示状态栏
     */
    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if(statusBarView != null){
            statusBarView.setBackgroundColor(statusBarColor);
        }
    }
}
