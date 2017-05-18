package com.example.administrator.mybookreader.ui.adapter;

import android.content.Context;

import com.example.administrator.mybookreader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * 搜索栏下方动态显示条适配器
 * Created by Administrator on 2017/5/16 0016.
 */

public class AutoCompleteAdapter extends EasyLVAdapter<String>{

    public AutoCompleteAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_auto_complete_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String s) {
        holder.setText(R.id.tvAutoCompleteItem, s);
    }
}
