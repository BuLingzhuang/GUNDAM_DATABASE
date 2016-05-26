package com.blz.gundam_database.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.presenters.MobileSuitPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

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
    private MobileSuitPresenter mPresenter;

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

    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(0, R.anim.finish_activity_alpha);
//    }

    @Override
    public void updateData(List<MobileSuitEntity> mList) {

    }

    @Override
    public void updateError(String eText) {

    }

    @Override
    public void uploading(boolean b) {

    }
}
