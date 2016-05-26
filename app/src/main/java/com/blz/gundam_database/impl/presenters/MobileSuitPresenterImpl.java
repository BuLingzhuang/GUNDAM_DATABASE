package com.blz.gundam_database.impl.presenters;

import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.interactors.MobileSuitInteractorImpl;
import com.blz.gundam_database.interfaces.interactors.MobileSuitInteractor;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public class MobileSuitPresenterImpl implements MobileSuitPresenter, MobileSuitInteractor.CallResponseListener {
    private MobileSuitView mView;
    private MobileSuitInteractor mInteractor;

    public MobileSuitPresenterImpl(MobileSuitView view) {
        mView = view;
        mInteractor = new MobileSuitInteractorImpl();
    }

    @Override
    public void gatMobileSuitEntityData(String workId) {
        mView.uploading(true);
        mInteractor.getData(this, workId);
    }

    @Override
    public void myResponse(List<MobileSuitEntity> mList) {
        mView.updateData(mList);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
    }
}
