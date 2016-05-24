package com.blz.gundam_database.views.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends SwipeBackActivity {

    @Bind(R.id.about_head_back)
    RelativeLayout mHeadBack;
    @Bind(R.id.about_tvEmail)
    TextView mAboutTvEmail;
    @Bind(R.id.about_tvSina)
    TextView mAboutTvSina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.about_head_back)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        //Genymotion上alpha动画好象有显示问题
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }
}
