package com.blz.gundam_database.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.presenters.MobileSuitPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.adapters.MobileSuitAdapter;
import com.bumptech.glide.Glide;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileSuitActivity extends AppCompatActivity implements MobileSuitView {

    @Bind(R.id.mobile_suit_toolbar_img)
    ImageView mToolbarImg;
    @Bind(R.id.mobile_suit_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.mobile_suit_ctl)
    CollapsingToolbarLayout mCtl;
    @Bind(R.id.mobile_suit_parent)
    CoordinatorLayout mParent;
    @Bind(R.id.mobile_suit_rv)
    RecyclerView mRv;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.mobile_suit_btnContent)
    FloatingActionButton mMobileSuitBtnContent;
    @Bind(R.id.mobile_suit_vBG)
    View mMobileSuitVBG;
    private MobileSuitPresenter mPresenter;
    private MobileSuitAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private String mWebUrl;
    private int lastVisibleItem;
    private MainListByWorkEntity mMainListByWorkEntity;
    private int lastPullTimes = 0;
    private boolean hasNext = true;
    private String mModelSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_suit);
        ButterKnife.bind(this);
        Tools.changeFonts(this);
        init();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mMainListByWorkEntity = (MainListByWorkEntity) intent.getSerializableExtra(MainListByWorkEntity.class.getName());
        mModelSeries = intent.getStringExtra("modelSeries");
        mWebUrl = mMainListByWorkEntity.getWebUrl();
        mPresenter.getData(mMainListByWorkEntity.getWorkId(), mModelSeries);
        mCtl.setTitle(mMainListByWorkEntity.getOriginalName());
        Glide.with(this).load(mMainListByWorkEntity.getIcon()).crossFade().into(mToolbarImg);
    }

    private void init() {
        mPresenter = new MobileSuitPresenterImpl(this);
        mProgressBar.setDrawingCacheBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        //设置回退按钮
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        Map<Type, Integer> map = new HashMap<>();
        map.put(MobileSuitEntity.class, R.layout.adapter_mobile_suit);
        map.put(EmptyEntity.class, R.layout.adapter_main_empty);
        mAdapter = new MobileSuitAdapter(this, map);
        mRv.setAdapter(mAdapter);

        //RecyclerView的上拉加载
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断是不是向下滑动 且 当前显示的最下面那一项是不是adapter中最后一个
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount() && hasNext) {
                    lastPullTimes++;
                    mPresenter.getData(mMainListByWorkEntity.getWorkId(), mModelSeries, lastPullTimes * 10);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //滑动屏幕，最下面出现新的一条的时候，记录为lastVisibleItem
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    @Override
    public void updateData(List<MobileSuitEntity> mList, boolean isRefresh) {
        mAdapter.addAll(mList, isRefresh);
    }

    @Override
    public void updateError(String eText) {
//        Tools.showSnackBar(this, eText, mParent);
        if (eText.equals(getString(R.string.base_no_data)) && lastPullTimes >= 0) {
            eText = getString(R.string.mobile_load_all);
            hasNext = false;
        }
        Tools.showToast(this, eText);
    }

    @Override
    public void uploading(boolean b) {
        if (b) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.mobile_suit_btnContent)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mobile_suit_btnContent:
                if (mPopupWindow == null) {
                    showPopupWindow(view);
                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    mPopupWindow = null;
                }
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showPopupWindow(View view) {
        mMobileSuitBtnContent.setImageResource(R.mipmap.btn_hidden);
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(this).inflate(R.layout.popup_window_mobile_suit, null);
        WebView webView = (WebView) inflate.findViewById(R.id.popup_window_mobile_suit_webView);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        ImageView imageView = (ImageView) inflate.findViewById(R.id.popup_window_mobile_suit_iv);
        if (mWebUrl.endsWith(".jpg")) {
            imageView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Glide.with(this).load(mWebUrl).error(R.mipmap.ic_launcher_black).placeholder(R.mipmap.ic_launcher_black).crossFade().into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(mWebUrl);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view, handler, error);
                }
            });
        }

        int[] ints = new int[2];
        view.getLocationOnScreen(ints);

        mPopupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT, ints[1] - Tools.dp2px(this, 20), true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.popupWindow_sh_anim_style);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mMobileSuitVBG.setVisibility(View.GONE);
                mPopupWindow = null;
                mMobileSuitBtnContent.setImageResource(R.mipmap.btn_show);
            }
        });

        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, ints[0], 0);
        mMobileSuitVBG.setVisibility(View.VISIBLE);
    }
}
