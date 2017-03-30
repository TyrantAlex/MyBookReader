package com.example.administrator.mybookreader.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mybookreader.R;

/**
 *  状态栏比较,关于沉浸式状态栏的实现
 * Created by Administrator on 2017/3/30 0030.
 */

public class StatusBarCompat {

    private static final int INVALID_VAL = -1;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static View compat(Activity activity, int statusColor){

        int color = ContextCompat.getColor(activity, R.color.colorPrimaryDark);
        //如果当前版本大于21 则使用传进来的color值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(statusColor != INVALID_VAL){
                color = statusColor;
            }
            activity.getWindow().setStatusBarColor(color);
            return null;
        }

        //如果当前版本在19-21之间
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){

            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if(statusColor != INVALID_VAL){
                color = statusColor;
            }
            //获取ViewGroup中最顶层的view 就是状态栏
            View statusBarView = contentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity)) {
                statusBarView.setBackgroundColor(color);
                return statusBarView;
            }
            //走到这里就是重新添加一个状态栏
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
            return statusBarView;
        }

        return null;
    }

    /**
     * 一个参数的比较方法,使用默认值
     * @param activity
     */
    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }

    /**
     * 根据上下文对象获取状态栏的高
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        //返回给定资源名称的资源标识符。 传入的参数分别为 defName,defType,Package
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0){
            //检索要使用的特定资源ID的维度作为原始像素的偏移量取整
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }
}
