package com.blz.gundam_database.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSDetailActivity extends SwipeBackActivity {

    @Bind(R.id.msdetail_boxImage)
    ImageView mMsdetailBoxImage;
    @Bind(R.id.msdetail_tvOriginalName)
    TextView mMsdetailTvOriginalName;
    @Bind(R.id.msdetail_tvVersion)
    TextView mMsdetailTvVersion;
    @Bind(R.id.msdetail_tvModelSeries)
    TextView mMsdetailTvModelSeries;
    @Bind(R.id.msdetail_tvScale)
    TextView mMsdetailTvScale;
    @Bind(R.id.msdetail_tvPrice)
    TextView mMsdetailTvPrice;
    @Bind(R.id.msdetail_tvManufacturer)
    TextView mMsdetailTvManufacturer;
    @Bind(R.id.msdetail_tvPrototypeMaster)
    TextView mMsdetailTvPrototypeMaster;
    @Bind(R.id.msdetail_tvItemNo)
    TextView mMsdetailTvItemNo;
    @Bind(R.id.msdetail_tvLaunchDate)
    TextView mMsdetailTvLaunchDate;
    @Bind(R.id.head_toolbar_back)
    RelativeLayout mHeadToolbarBack;
    @Bind(R.id.head_toolbar_title)
    TextView mHeadToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdetail);
        ButterKnife.bind(this);
        init();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        MobileSuitEntity data = (MobileSuitEntity) intent.getSerializableExtra("MobileSuitEntity");
        mMsdetailTvOriginalName.setText("型号："+data.getOriginalName());
        mMsdetailTvVersion.setText("版本："+data.getVersion());
        mMsdetailTvModelSeries.setText("系列："+data.getModelSeries());
        mMsdetailTvScale.setText("比例："+data.getScale());
        mMsdetailTvPrice.setText("价格："+data.getPrice());
        mMsdetailTvManufacturer.setText("厂商："+data.getManufacturer());
        mMsdetailTvPrototypeMaster.setText("原型师："+data.getPrototypeMaster());
        mMsdetailTvItemNo.setText("编号："+data.getItemNo());
        mMsdetailTvLaunchDate.setText("发售时间："+data.getLaunchDate());
        Picasso.with(this).load(data.getBoxImage()).placeholder(R.mipmap.menu_icon).error(R.mipmap.menu_icon).into(mMsdetailBoxImage);
    }

    private void init() {
        mHeadToolbarTitle.setText("机体详情");
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    @OnClick(R.id.head_toolbar_back)
    public void onClick() {
        onBackPressed();
    }
}
