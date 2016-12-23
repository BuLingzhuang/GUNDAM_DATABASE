package com.blz.gundam_database.impl.presenters;

import com.blz.gundam_database.impl.interactors.MSTypeIneactorImpl;
import com.blz.gundam_database.interfaces.interactors.MSTypeInteractor;
import com.blz.gundam_database.interfaces.presenters.MSTypePresenter;
import com.blz.gundam_database.interfaces.views.MSTypeView;
import com.blz.gundam_database.utils.Constants;
import com.blz.gundam_database.views.activitys.MSTypeActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BuLingzhuang
 * on 2016/6/27
 * E-mail bulingzhuang@foxmail.com
 */
public class MSTypePresenterImpl implements MSTypePresenter, MSTypeInteractor.ResponseListener {
    private MSTypeView mView;
    private MSTypeInteractor mInteractor;

    public MSTypePresenterImpl(MSTypeActivity view) {
        mView = view;
        mInteractor = new MSTypeIneactorImpl(this,view);
    }

    @Override
    public void getDataMap(HashMap<String, Boolean> map,String workId) {
        for (String str : map.keySet()) {
            mInteractor.getDataMap(str,workId);
        }
    }

    @Override
    public void myResponse(String modelSeries, int count) {
        mView.updateData(modelSeries, count);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
    }
}
