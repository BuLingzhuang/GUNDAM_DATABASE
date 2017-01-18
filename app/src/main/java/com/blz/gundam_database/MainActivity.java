package com.blz.gundam_database;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.impl.presenters.MainPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.MainPresenter;
import com.blz.gundam_database.interfaces.views.MainView;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.activitys.AboutActivity;
import com.blz.gundam_database.views.activitys.UserActivity;
import com.blz.gundam_database.views.adapters.MainListByWorksAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.main_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.menu_header_icon)
    ImageView mHeaderIcon;
    @Bind(R.id.menu_header_favorites)
    LinearLayout mHeaderFavorites;
    @Bind(R.id.menu_header_download)
    LinearLayout mHeaderDownload;
    @Bind(R.id.main_nv_listView)
    ListView mNvListView;
    @Bind(R.id.main_navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.main_drawerLayout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private long firstTime = 0L;
    private MainListByWorksAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void initData() {
        mPresenter.getMainListByWorks();
    }

    private void init() {
        mPresenter = new MainPresenterImpl(this);

        //设置左侧按钮拉出侧拉栏
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        HashMap<Type, Integer> map = new HashMap<>();
        map.put(MainListByWorkEntity.class,R.layout.adapter_main_grid);
        map.put(EmptyEntity.class,R.layout.adapter_main_empty);
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position+1 == mAdapter.getItemCount()){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(layout);
        mAdapter = new MainListByWorksAdapter(this, map);
        mRecyclerView.setAdapter(mAdapter);

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(this);

        // TODO: 2016/5/9 侧拉显示假数据
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add("Item-" + i);
        }
        mNvListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
    }

    @OnClick({R.id.menu_header_icon, R.id.menu_header_favorites, R.id.menu_header_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_header_icon:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.menu_header_favorites:
                break;
            case R.id.menu_header_download:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_default_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean b = super.onOptionsItemSelected(item);
        if (mToggle.onOptionsItemSelected(item)) {
            b = true;
        }
        switch (item.getItemId()){
            case R.id.menu_message://消息

                break;
            case R.id.menu_about://关于
                AVAnalytics.onEvent(this, getString(R.string.main_see_about));
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return b;
    }

    //连点两次后退退出应用
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if ((paramKeyEvent.getAction() == 0) && (4 == paramInt)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - firstTime >= 2000L) {
                invalidateOptionsMenu();
                Toast.makeText(this, getString(R.string.main_again_touch), Toast.LENGTH_SHORT).show();
                firstTime = currentTime;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    @Override
    public void updateMainList(List<MainListByWorkEntity> mList) {
        if (mList != null) {
            mAdapter.addAll(mList);
        }
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
            Tools.showSnackBar(this,getString(R.string.main_refresh_succeed),mDrawerLayout);
        }
    }

    @Override
    public void updateError(String eText) {
        Tools.showSnackBar(this,eText,mDrawerLayout);
    }

    @Override
    public void showRefresh(boolean b) {
        if (!mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getMainListByWorks();
    }
}
