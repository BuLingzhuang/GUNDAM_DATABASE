package com.blz.gundam_database;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.impl.presenters.SplashScreenPresenterImpl;
import com.blz.gundam_database.interfaces.presenters.SplashScreenPresenter;
import com.blz.gundam_database.interfaces.views.SplashScreenView;
import com.blz.gundam_database.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    private static final int MY_PERMISSIONS_REQUEST_GROUP_STORAGE = 33;

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
            // TODO: 2016/12/22 暂时不做网络动态图片
//            Glide.with(this).load(entity.getImgUrl()).placeholder(R.mipmap.splash_bg).error(R.mipmap.splash_bg).into(mImgBG);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_GROUP_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_GROUP_STORAGE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_GROUP_STORAGE);
            } else {
                intent2Main();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Tools.showLogE(this, grantResults.length + "" + Arrays.toString(permissions));
        if (requestCode == MY_PERMISSIONS_REQUEST_GROUP_STORAGE) {
            if (!(grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Tools.showToast(this, "请在下次启动时允许权限", Toast.LENGTH_LONG);
            }
            intent2Main();
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void intent2Main() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
