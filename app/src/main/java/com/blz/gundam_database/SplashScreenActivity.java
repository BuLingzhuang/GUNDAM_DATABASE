package com.blz.gundam_database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.impl.presenters.SplashScreenPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.SplashScreenPresenter;
import com.blz.gundam_database.interfaces.views.SplashScreenView;
import com.blz.gundam_database.view.activity.MainActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    @Bind(R.id.splash_screen_imgBG)
    ImageView mImgBG;
    @Bind(R.id.splash_screen_genView)
    RelativeLayout mGenView;
    private SplashScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        mPresenter = new SplashScreenPresenterImpl(this, mGenView);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void startAnim(SplashScreenEntity entity) {
        if (entity != null) {
            Picasso.with(this).load(entity.getImgUrl()).into(mImgBG);
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.enlarge);
        animation.setFillAfter(true);
        mImgBG.startAnimation(animation);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
