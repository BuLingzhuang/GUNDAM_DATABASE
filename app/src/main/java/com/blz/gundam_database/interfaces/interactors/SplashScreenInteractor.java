package com.blz.gundam_database.interfaces.interactors;

import android.content.Context;
import android.view.View;

import com.blz.gundam_database.entities.SplashScreenEntity;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public interface SplashScreenInteractor {
    void setData(Context context, View genView);

    SplashScreenEntity getData();

    interface OnAnimFinishedListener {
        void finish();
    }

    void startThread(OnAnimFinishedListener listener);
}
