package com.blz.gundam_database.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    private MobileSuitPresenter mPresenter;
    private MobileSuitAdapter mAdapter;

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
        mPresenter.gatMobileSuitEntityData(mainListByWorkEntity.getWorkId());
        mCtl.setTitle(mainListByWorkEntity.getOriginalName());
        Picasso.with(this).load(mainListByWorkEntity.getIcon()).into(mToolbarImg);
    }

    private void init() {
        mPresenter = new MobileSuitPresenterImpl(this);

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
        Tools.showSnackBar(this, eText, mParent);
    }

    @Override
    public void uploading(boolean b) {
        if (b){
            mProgressBar.setVisibility(View.VISIBLE);
        }else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
