package com.blz.gundam_database.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.impl.presenters.MSTypePresenterImpl;
import com.blz.gundam_database.interfaces.presenters.MSTypePresenter;
import com.blz.gundam_database.interfaces.views.MSTypeView;
import com.blz.gundam_database.utils.Constants;
import com.blz.gundam_database.utils.SystemStatusManager;
import com.blz.gundam_database.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSTypeActivity extends AppCompatActivity implements MSTypeView {

    @Bind(R.id.mstype_rlMG)
    RelativeLayout mRlMG;
    @Bind(R.id.mstype_rlHG)
    RelativeLayout mRlHG;
    @Bind(R.id.mstype_rlRG)
    RelativeLayout mRlPG;
    @Bind(R.id.mstype_rlPG)
    RelativeLayout mRlRG;
    @Bind(R.id.mstype_rlOther)
    RelativeLayout mRlOther;
    @Bind(R.id.mstype_btnBack)
    FloatingActionButton mBtnBack;
    @Bind(R.id.mstype_tvMG)
    TextView mTvMG;
    @Bind(R.id.mstype_tvHG)
    TextView mTvHG;
    @Bind(R.id.mstype_tvRG)
    TextView mTvRG;
    @Bind(R.id.mstype_tvPG)
    TextView mTvPG;
    @Bind(R.id.mstype_tvOther)
    TextView mTvOther;
    @Bind(R.id.mstype_progressBar)
    ProgressBar mMstypeProgressBar;
    @Bind(R.id.mstype_imHeader)
    ImageView mImHeader;
    private HashMap<String, Boolean> mBooleanMap;
    private MainListByWorkEntity mEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mstype);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemStatusManager statusManager = new SystemStatusManager(this);
            statusManager.setStatusBarTintEnabled(true);
            statusManager.setStatusBarAlpha(0);
        }
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        isUploading(true);
        MSTypePresenter presenter = new MSTypePresenterImpl(this);
        mBooleanMap = new HashMap<>();
        mBooleanMap.put(Constants.MS_MODEL_SERIES_MG, false);
        mBooleanMap.put(Constants.MS_MODEL_SERIES_RG, false);
        mBooleanMap.put(Constants.MS_MODEL_SERIES_HG, false);
        mBooleanMap.put(Constants.MS_MODEL_SERIES_PG, false);
        mBooleanMap.put(Constants.MS_MODEL_SERIES_OTHER, false);

        Intent intent = getIntent();
        mEntity = (MainListByWorkEntity) intent.getSerializableExtra(MainListByWorkEntity.class.getName());
        Glide.with(this).load(mEntity.getIcon()).error(R.mipmap.default_placeholder).into(mImHeader);
        presenter.getDataMap(mBooleanMap, mEntity.getWorkId());
    }

    @OnClick({R.id.mstype_rlMG, R.id.mstype_rlHG, R.id.mstype_rlRG, R.id.mstype_rlPG, R.id.mstype_rlOther, R.id.mstype_btnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mstype_rlMG:
                start4MSA(Constants.MS_MODEL_SERIES_MG);
                break;
            case R.id.mstype_rlHG:
                start4MSA(Constants.MS_MODEL_SERIES_HG);
                break;
            case R.id.mstype_rlRG:
                start4MSA(Constants.MS_MODEL_SERIES_RG);
                break;
            case R.id.mstype_rlPG:
                start4MSA(Constants.MS_MODEL_SERIES_PG);
                break;
            case R.id.mstype_rlOther:
                start4MSA(Constants.MS_MODEL_SERIES_OTHER);
                break;
            case R.id.mstype_btnBack:
                onBackPressed();
                break;
        }
    }

    private void start4MSA(String modelSeries) {
        Intent intent = new Intent(this, MobileSuitActivity.class);
        intent.putExtra(MainListByWorkEntity.class.getName(), mEntity);
        intent.putExtra("modelSeries", modelSeries);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
//        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateData(String modelSeries, int count) {
        String str = String.format("已收录：%d 款", count);
        switch (modelSeries) {
            case Constants.MS_MODEL_SERIES_MG:
                mTvMG.setText(str);
                break;
            case Constants.MS_MODEL_SERIES_RG:
                mTvRG.setText(str);
                break;
            case Constants.MS_MODEL_SERIES_HG:
                mTvHG.setText(str);
                break;
            case Constants.MS_MODEL_SERIES_PG:
                mTvPG.setText(str);
                break;
            case Constants.MS_MODEL_SERIES_OTHER:
                mTvOther.setText(str);
                break;
            default:
                Tools.showToast(this, "错误的查询：" + modelSeries);
                break;
        }
        mBooleanMap.put(modelSeries, true);
        boolean breakLoading = true;
        for (Map.Entry<String, Boolean> next : mBooleanMap.entrySet()) {
            if (!next.getValue()) {
                breakLoading = false;
            }
        }
        if (breakLoading) {
            isUploading(false);
        }
    }

    @Override
    public void updateError(String eText) {
        isUploading(false);
        Tools.showToast(this, eText);
    }

    private void isUploading(boolean b) {
        if (b) {
            mMstypeProgressBar.setVisibility(View.VISIBLE);
        } else {
            mMstypeProgressBar.setVisibility(View.GONE);
        }
    }
}
