package com.example.administrator.mybookreader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mybookreader.R;
import com.example.administrator.mybookreader.base.BaseRVActivity;
import com.example.administrator.mybookreader.bean.SearchDetail;
import com.example.administrator.mybookreader.component.AppComponent;
import com.example.administrator.mybookreader.component.DaggerBookComponent;
import com.example.administrator.mybookreader.manager.CacheManager;
import com.example.administrator.mybookreader.ui.adapter.AutoCompleteAdapter;
import com.example.administrator.mybookreader.ui.adapter.SearchHistoryAdapter;
import com.example.administrator.mybookreader.ui.contract.SearchContract;
import com.example.administrator.mybookreader.ui.easyadapter.SearchAdapter;
import com.example.administrator.mybookreader.ui.presenter.SearchPresenter;
import com.example.administrator.mybookreader.view.TagColor;
import com.example.administrator.mybookreader.view.TagGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * 搜索Activity
 */
public class SearchActivity extends BaseRVActivity<SearchDetail.SearchBooks> implements SearchContract.View{

    public static final String INTENT_QUERY = "query";

    /**
     * 跳转到搜索Activity并传递Query数据
     * @param context
     * @param query
     */
    public static void startActivity(Context context, String query){
        context.startActivity(new Intent(context, SearchActivity.class)
                .putExtra(INTENT_QUERY,query));
    }

    /**
     * Bind View
     */
    @Bind(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.rootLayout)
    LinearLayout mRootLayout;
    @Bind(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;
    @Bind(R.id.rlHistory)
    RelativeLayout rlHistory;
    @Bind(R.id.tvClear)
    TextView tvClear;
    @Bind(R.id.lvSearchHistory)
    ListView lvSearchHistory;

    /**
     * 注入依赖
     */
    @Inject
    SearchPresenter mPresenter;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    private AutoCompleteAdapter mAutoAdapter;
    private List<String> mAutoList = new ArrayList<>();

    private SearchHistoryAdapter mHisAdapter;
    private List<String> mHisList = new ArrayList<>();
    private String key;
    private MenuItem searchMenuItem;
    private SearchView searchView;

    private ListPopupWindow mListPopupWindow;

    int hotIndex = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    protected void initDatas() {
        key = getIntent().getStringExtra(INTENT_QUERY);

        mHisAdapter = new SearchHistoryAdapter(this, mHisList);
        lvSearchHistory.setAdapter(mHisAdapter);
        lvSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //搜索对应的内容
                search(mHisList.get(position));
            }
        });
        initSearchHistory();
    }

    @Override
    protected void configViews() {
        initAdapter(SearchAdapter.class, false, false);

        initAutoList();

        //热词具体监听
        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener(){
            @Override
            public void onTagClick(String tag) {
                //开始搜索
                search(tag);
            }
        });

        //换一换按钮监听
        mTvChangeWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示热词
                showHotWord();
            }
        });

        mPresenter.attachView(this);
        mPresenter.getHotWordList();
    }

    /**
     * 每次显示8个热词
     */
    private synchronized void showHotWord() {
        int tagSize = 8;
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < tagList.size(); hotIndex++, j++) {
            tags[j] = tagList.get(hotIndex % tagList.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        mTagGroup.setTags(colors, tags);
    }

    @Override
    public synchronized void showHotWordList(List<String> list) {
        visible(mTvChangeWords);
        tagList.clear();
        tagList.addAll(list);
        times = 0;
        showHotWord();
    }

    /**
     * 初始化动态搜索结果ListView
     */
    private void initAutoList() {
        mAutoAdapter = new AutoCompleteAdapter(this, mAutoList);
        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setAdapter(mAutoAdapter);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹出窗口的锚点视图
        mListPopupWindow.setAnchorView(mCommonToolbar);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListPopupWindow.dismiss();
                TextView tv = (TextView) view.findViewById(R.id.tvAutoCompleteItem);
                String str = tv.getText().toString();
                search(str);
            }
        });
    }

    /**
     * 动态搜索栏更新数据回调
     * @param list
     */
    @Override
    public void showAutoCompleteList(List<String> list) {
        mAutoList.clear();
        mAutoList.addAll(list);

        if (!mListPopupWindow.isShowing()){
            //始终需要输入法唤出
            mListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            //设置弹出键盘挤压窗口
            mListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mListPopupWindow.show();
        }

        mAutoAdapter.notifyDataSetChanged();
    }

    /**
     * 显示搜索结果
     * @param list
     */
    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        initSearchResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        searchMenuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                mPresenter.getSearchResultList(query);
                saveSearchHistory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    if (mListPopupWindow.isShowing()){
                        mListPopupWindow.dismiss();
                    }
                    initTagGroup();
                }else{
                    mPresenter.getAutoCompleteList(newText);
                }
                return false;
            }
        });

        search(key); //外部调用搜索，则打开页面立即进行搜索

        //searchview 伸缩监听
        MenuItemCompat.setOnActionExpandListener(searchMenuItem
                , new MenuItemCompat.OnActionExpandListener() { //设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                initTagGroup();
                return true;
            }
        });

        return true;
    }

    /**
     * 保存搜索历史记录
     * 最多20条
     * @param query
     */
    private void saveSearchHistory(String query) {
        //取出缓存历史记录list
        List<String> list = CacheManager.getInstance().getSearchHistory();
        if (list == null){
            list = new ArrayList<>();
        }else{ //list不为空则判断是否重复,重复则去掉以前的并把当次查询结果加到第一个位置
            Iterator<String> iterator = list.iterator();
            while(iterator.hasNext()){
                String item = iterator.next();
                if (TextUtils.equals(query, item)){
                    iterator.remove();
                }
            }
            list.add(0, query);
        }
        //判断list是否大于20个元素,大于则去掉末尾的元素
        int size = list.size();
        if (size > 20){
            for (int i = size - 1; i >= 20; i--){
                list.remove(i);
            }
        }
        //保存list
        CacheManager.getInstance().saveSearchHistory(list);
        //重新初始化搜索历史
        initSearchHistory();
    }

    /**
     * 初始化搜索历史
     */
    private void initSearchHistory() {
        List<String> list = CacheManager.getInstance().getSearchHistory();
        mHisAdapter.clear();
        if (list != null && list.size() > 0){
            tvClear.setEnabled(true);
            mHisAdapter.addAll(list);
        }else{
            tvClear.setEnabled(false);
        }
        mHisAdapter.notifyDataSetChanged();
    }

    /**
     * 展开SearchView查询
     * @param key
     */
    private void search(String key) {
        MenuItemCompat.expandActionView(searchMenuItem);
        if (!TextUtils.isEmpty(key)){
            searchView.setQuery(key, true);
            saveSearchHistory(key);
        }
    }

    /**
     * 初始化搜索结果
     */
    private void initSearchResult() {
        gone(mTagGroup, mLayoutHotWord, rlHistory);
        visible(mRecyclerView);
        if (mListPopupWindow.isShowing()){
            mListPopupWindow.dismiss();
        }
    }

    private void initTagGroup() {
        visible(mTagGroup, mLayoutHotWord, rlHistory);
        gone(mRecyclerView);
        if (mListPopupWindow.isShowing()){
            mListPopupWindow.dismiss();
        }
    }

    @Override
    public void onItemClick(int position) {
        SearchDetail.SearchBooks data = mAdapter.getItem(position);
        BookDetailActivity.startActivity(this, data._id);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
