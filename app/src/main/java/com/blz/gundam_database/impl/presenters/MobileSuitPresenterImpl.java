package com.blz.gundam_database.impl.presenters;

import com.avos.avoscloud.AVObject;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.impl.interactors.MobileSuitInteractorImpl;
import com.blz.gundam_database.interfaces.CallResponseListener;
import com.blz.gundam_database.interfaces.interactors.MobileSuitInteractor;
import com.blz.gundam_database.interfaces.presenters.MobileSuitPresenter;
import com.blz.gundam_database.interfaces.views.MobileSuitView;
import com.blz.gundam_database.views.activitys.MobileSuitActivity;

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

    public MobileSuitPresenterImpl(MobileSuitActivity view) {
        mView = view;
        mInteractor = new MobileSuitInteractorImpl(this,view);
    }

    @Override
    public void getData(String workId, String modelSeries) {
        getData(workId, modelSeries, true, 0);
    }

    @Override
    public void getData(String workId, String modelSeries, int skip) {
        getData(workId, modelSeries, false, skip);
    }

    @Override
    public void getData(String workId, String modelSeries, boolean isRefresh, int skip) {
        mView.uploading(true);
        mInteractor.getData(workId, modelSeries, isRefresh, skip);
    }

    @Override
    public void myResponse(List<AVObject> list, boolean isRefresh) {
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
            entity.setVersion(obj.getString("version"));
            entity.setManufacturer(obj.getString("manufacturer"));
            entity.setPrototypeMaster(obj.getString("prototypeMaster"));
            entity.setBoxImage(obj.getString("boxImage"));
            entity.setLlType(obj.getString("llType"));

            entityList.add(entity);
        }
        mView.updateData(entityList, isRefresh);
        mView.uploading(false);
    }

    @Override
    public void myError(String eText) {
        mView.updateError(eText);
        mView.uploading(false);
    }
}
