package com.blz.gundam_database.interfaces.views;

import android.content.Context;

import com.blz.gundam_database.entities.SplashScreenEntity;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public interface SplashScreenView {
    void startAnim(SplashScreenEntity entity);

    Context getContext();

    void navigateToMain();
}
