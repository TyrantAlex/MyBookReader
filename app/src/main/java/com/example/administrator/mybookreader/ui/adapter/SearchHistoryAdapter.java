package com.example.administrator.mybookreader.ui.adapter;

import android.content.Context;

import com.example.administrator.mybookreader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class SearchHistoryAdapter extends EasyLVAdapter<String> {

    public SearchHistoryAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_search_history);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String s) {
        holder.setText(R.id.tvTitle, s);
    }
}
