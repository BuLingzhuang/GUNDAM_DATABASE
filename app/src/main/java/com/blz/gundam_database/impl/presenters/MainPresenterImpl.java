package com.blz.gundam_database.impl.presenters;

import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.impl.interactors.MainInteractorImpl;
import com.blz.gundam_database.interfaces.interactors.MainInteractor;
import com.blz.gundam_database.interfaces.presenters.MainPresenter;
import com.blz.gundam_database.interfaces.views.MainView;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public class MainPresenterImpl implements MainPresenter, MainInteractor.CallResponseListener {
    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterImpl(MainView view) {
        mView = view;
        mInteractor = new MainInteractorImpl();
    }

    @Override
    public void getMainListByWorks() {
        mView.showRefresh(true);
        mInteractor.getData(this);
    }

    @Override
    public void myResponse(List<MainListByWorkEntity> mList) {
        mView.updateMainList(mList);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
    }
}
