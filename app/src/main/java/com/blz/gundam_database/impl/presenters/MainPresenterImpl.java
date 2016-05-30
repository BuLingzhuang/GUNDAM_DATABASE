package com.blz.gundam_database.impl.presenters;

import com.avos.avoscloud.AVObject;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.impl.interactors.MainInteractorImpl;
import com.blz.gundam_database.interfaces.CallResponseListener;
import com.blz.gundam_database.interfaces.interactors.MainInteractor;
import com.blz.gundam_database.interfaces.presenters.MainPresenter;
import com.blz.gundam_database.interfaces.views.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public class MainPresenterImpl implements MainPresenter, CallResponseListener {
    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterImpl(MainView view) {
        mView = view;
        mInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void getMainListByWorks() {
        mView.showRefresh(true);
        mInteractor.getData(this);
    }

    @Override
    public void myResponse(List<AVObject> list) {
        ArrayList<MainListByWorkEntity> mEntityList = new ArrayList<>();
        for (AVObject obj : list) {
            MainListByWorkEntity entity = new MainListByWorkEntity();
            entity.setName(obj.getString("name"));
            entity.setEnglishName(obj.getString("englishName"));
            entity.setOriginalName(obj.getString("originalName"));
            entity.setIcon(obj.getString("icon"));
            entity.setWorkId(obj.getString("workId"));
            entity.setStoryYear(obj.getString("storyYear"));
            mEntityList.add(entity);
        }
        mView.updateMainList(mEntityList);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
    }
}
