package com.example.administrator.mybookreader.manager;

import com.example.administrator.mybookreader.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * 缓存管理
 * Created by Administrator on 2017/5/19 0019.
 */

public class CacheManager {

    private static CacheManager manager;

    /**
     * singleton
     * @return
     */
    public static CacheManager getInstance(){
        return manager == null ? (manager = new CacheManager()) : manager;
    }

    /**
     * 获取搜索历史
     * SharedPreference保存
     * @return
     */
    public List<String> getSearchHistory(){
        return SharedPreferencesUtil.getInstance().getObject(getSearchHistoryKey(), List.class);
    }

    /**
     * 保存搜索历史
     * SharedPreference保存
     */
    public void saveSearchHistory(Object obj){
        SharedPreferencesUtil.getInstance().putObject(getSearchHistoryKey(), obj);
    }

    /**
     * 获取搜索历史key
     * @return
     */
    private String getSearchHistoryKey(){
        return "searchHistory";
    }
}
