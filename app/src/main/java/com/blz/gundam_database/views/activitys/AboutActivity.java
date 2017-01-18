package com.blz.gundam_database.views.activitys;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends SwipeBackActivity {

    @Bind(R.id.about_head_back)
    RelativeLayout mHeadBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Tools.changeFonts(this);
    }

    @OnClick(R.id.about_head_back)
    public void onClick() {
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(0, R.anim.finish_activity_alpha);
//    }
}
