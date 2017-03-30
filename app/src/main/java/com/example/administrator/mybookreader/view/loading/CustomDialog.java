package com.example.administrator.mybookreader.view.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mybookreader.R;

/**
 * 定制dialog模板
 * Created by Administrator on 2017/3/28 0028.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context,int themeResId){
        super(context, themeResId);
    }

    public static CustomDialog instance(Activity activity){
        LoadingView v = (LoadingView) View.inflate(activity, R.layout.common_progress_view, null);
        v.setColor(ContextCompat.getColor(activity, R.color.reader_menu_bg_color));
        CustomDialog customDialog = new CustomDialog(activity, R.style.loading_dialog);
        customDialog.setContentView(v,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return customDialog;
    }
}
