package com.blz.gundam_database.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.presenters.MobileSuitPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.adapters.MobileSuitAdapter;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_suit);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        MainListByWorkEntity mainListByWorkEntity = (MainListByWorkEntity) intent.getSerializableExtra("MainListByWorkEntity");
        mWebUrl = mainListByWorkEntity.getWebUrl();
        mPresenter.gatMobileSuitEntityData(mainListByWorkEntity.getWorkId());
        mCtl.setTitle(mainListByWorkEntity.getOriginalName());
        Picasso.with(this).load(mainListByWorkEntity.getIcon()).into(mToolbarImg);
    }

    private void init() {
        mPresenter = new MobileSuitPresenterImpl(this);
        mProgressBar.setDrawingCacheBackgroundColor(Tools.getColor(this, R.color.colorPrimary));

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

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    @Override
    public void updateData(List<MobileSuitEntity> mList) {
        mAdapter.addAll(mList);
    }

    @Override
    public void updateError(String eText) {
//        Tools.showSnackBar(this, eText, mParent);
        Tools.showToast(this,eText);
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
//        TextView tvContent = (TextView) inflate.findViewById(R.id.popup_window_mobile_suit_tvContent);
//        tvContent.setText("最新剧场版是00的完结篇，正式命名为《机动战士高达00：A wakening of the Trailblazer（先驱者的觉醒）》。最新剧场版内容是：变革者事件结束后，世界又恢复了和平。但是，这样的和平并没有持续多久。地球联邦发现的外星未知金属【联邦政府将其命名为：地外变异性金属生命体(Extraterrestrial Living-metal Shapeshifter)，简称ELS】开始向地球发起进攻，并且目标都是变革者。在最终决战时，刹那终于又醒了过来，并在洛克昂、阿雷路亚等人的帮助下成功进入大型ELS内部，开始对话，也阻止了ELS的进攻......50年后，经过量子跳跃的量子型00回到地球，刹那也再次与玛丽娜相见，达成相互理解的共识。西历2307年，由于化石燃料的枯竭，人类开始寻找全新形式的能源——由3条巨大的轨道式升降梯所组成的巨型太阳能发电系统。然而，能够分享这一能源的，只有世界上的一部分大国及其同盟国。拥有3条巨大的轨道式升降梯的3个超大国及其联盟：以「美利坚合众国」为中央政府领导的的「UNION（世界经济联合）」，以「中国」、「俄罗斯」、「印度」为中心的「人类革新联盟」，以及以欧洲为中心的「AEU（新欧盟共同体）」。各大国家联盟为了各自的威信和繁荣，开展了庞大的争霸游戏。没错，即使历史的滚轮驶入了24世纪，人类依然保持着四分五裂的状态。在这个战争终日不绝的世界，一个宣称“以武力根绝战争”的私人武装组织出现了。拥有机动战士「高达」的他们，名为「天人（Celestial Being）」。凭借高达强大的性能，他们开始对各国纷争进行武力介入，最终三大阵营在天人和另外三台非天人（THRONE）高达的威胁下被迫联合成为国联，世界第一次走向联合，此时天人开始怀疑自己在伊奥利亚计划中应以被消灭为结局，最终在决战中天人遭重创。");
        WebView webView = (WebView) inflate.findViewById(R.id.popup_window_mobile_suit_webView);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.popup_window_mobile_suit_iv);
        if (mWebUrl.endsWith(".jpg")) {
            imageView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Picasso.with(this).load(mWebUrl).error(R.mipmap.ic_launcher_black).placeholder(R.mipmap.ic_launcher_black).into(imageView);
        }else {
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
