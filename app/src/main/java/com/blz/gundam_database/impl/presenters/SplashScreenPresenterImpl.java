package com.blz.gundam_database.impl.presenters;

import android.view.View;

import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.impl.interactors.SplashScreenInteractorImpl;
import com.blz.gundam_database.interfaces.interactors.SplashScreenInteractor;
import com.blz.gundam_database.interfaces.presenters.SplashScreenPresenter;
import com.blz.gundam_database.interfaces.views.SplashScreenView;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class SplashScreenPresenterImpl implements SplashScreenPresenter,SplashScreenInteractor.OnAnimFinishedListener {
    private SplashScreenView mView;
    private SplashScreenInteractor mInteractor;
    private View mGenView;

    public SplashScreenPresenterImpl(SplashScreenView view, View genView) {
        mView = view;
        mGenView = genView;
        mInteractor = new SplashScreenInteractorImpl();
    }

    @Override
    public void start() {
        SplashScreenEntity data = mInteractor.getData();
        mView.startAnim(data);
        mInteractor.startThread(this);
        mInteractor.setData(mView.getContext(),mGenView);
    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void finish() {
        mView.navigateToMain();
    }
}
