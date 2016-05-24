package com.blz.gundam_database;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.blz.gundam_database.views.activitys.AboutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //设置左侧按钮拉出侧拉栏
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }

    @OnClick({R.id.menu_header_icon, R.id.menu_header_favorites, R.id.menu_header_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_header_icon:
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
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = currentTime;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }
}
