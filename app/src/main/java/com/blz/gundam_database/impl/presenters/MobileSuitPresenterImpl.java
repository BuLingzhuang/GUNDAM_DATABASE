package com.blz.gundam_database.impl.presenters;

import com.avos.avoscloud.AVObject;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.interactors.MobileSuitInteractorImpl;
import com.blz.gundam_database.interfaces.CallResponseListener;
import com.blz.gundam_database.interfaces.interactors.MobileSuitInteractor;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public class MobileSuitPresenterImpl implements MobileSuitPresenter, CallResponseListener {
    private MobileSuitView mView;
    private MobileSuitInteractor mInteractor;

    public MobileSuitPresenterImpl(MobileSuitView view) {
        mView = view;
        mInteractor = new MobileSuitInteractorImpl(this);
    }

    @Override
    public void gatMobileSuitEntityData(String workId) {
        mView.uploading(true);
        mInteractor.getData(this, workId);
    }

    @Override
    public void myResponse(List<AVObject> list) {
        ArrayList<MobileSuitEntity> entityList = new ArrayList<>();
        for (AVObject obj : list) {
            MobileSuitEntity entity = new MobileSuitEntity();
            entity.setObjectId(obj.getObjectId());
            entity.setWorkId(obj.getString("workId"));
            entity.setOriginalName(obj.getString("originalName"));
            entity.setModelSeries(obj.getString("modelSeries"));
            entity.setScale(obj.getString("scale"));
            entity.setItemNo(obj.getString("itemNo"));
            entity.setLaunchDate(obj.getString("launchDate"));
            entity.setPrice(obj.getString("price"));
            entity.setImages(obj.getString("images"));
            entity.setHeadImage(obj.getString("headImage"));

            entityList.add(entity);
        }
        mView.updateData(entityList);
        mView.uploading(false);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
        mView.uploading(false);
    }
}
