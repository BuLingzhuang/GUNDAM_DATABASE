package com.blz.gundam_database.impl.interactors;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.blz.gundam_database.base.BaseFindCallback;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.interfaces.CallResponseListener;
import com.blz.gundam_database.interfaces.interactors.MobileSuitInteractor;
import com.blz.gundam_database.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public class MobileSuitInteractorImpl extends BaseFindCallback implements MobileSuitInteractor {


    public MobileSuitInteractorImpl(CallResponseListener listener) {
        super(listener);
    }

    @Override
    public void getData(CallResponseListener listener, String workId) {
        getData(listener,workId,true,0);
    }

    @Override
    public void getData(CallResponseListener listener, String workId, int skip) {
        getData(listener, workId,false, skip);
    }

    @Override
    public void getData(CallResponseListener listener, String workId, boolean isRefresh, int skip) {
        setRefresh(isRefresh);
        AVQuery<AVObject> query = new AVQuery<>("MobileSuitEntity");
        query.whereEqualTo("workId", workId);
        query.orderByDescending("launchDate");
        query.limit(10);
        query.skip(skip);
        query.findInBackground(this);
    }
//
//    @Override
//    public List<MobileSuitEntity> doneSuccessful(List<AVObject> list) {
//        ArrayList<MobileSuitEntity> entityList = new ArrayList<>();
//        for (AVObject obj : list) {
//            MobileSuitEntity entity = new MobileSuitEntity();
//            entity.setObjectId(obj.getObjectId());
//            entity.setWorkId(obj.getString("workId"));
//            entity.setOriginalName(obj.getString("originalName"));
//            entity.setModelSeries(obj.getString("modelSeries"));
//            entity.setScale(obj.getString("scale"));
//            entity.setItemNo(obj.getString("itemNo"));
//            entity.setLaunchDate(obj.getString("launchDate"));
//            entity.setPrice(obj.getString("price"));
//            entity.setImages(obj.getString("images"));
//            entity.setHeadImage(obj.getString("headImage"));
//
//            entityList.add(entity);
//        }
//        return entityList;
//    }

//    @Override
//    public void done(List<AVObject> list, AVException e) {
//        if (list != null) {
//
//            if (list.size() >= 1) {
//                ArrayList<MobileSuitEntity> entityList = new ArrayList<>();
//                for (AVObject obj : list) {
//                    MobileSuitEntity entity = new MobileSuitEntity();
//                    entity.setObjectId(obj.getObjectId());
//                    entity.setWorkId(obj.getString("workId"));
//                    entity.setOriginalName(obj.getString("originalName"));
//                    entity.setModelSeries(obj.getString("modelSeries"));
//                    entity.setScale(obj.getString("scale"));
//                    entity.setItemNo(obj.getString("itemNo"));
//                    entity.setLaunchDate(obj.getString("launchDate"));
//                    entity.setPrice(obj.getString("price"));
//                    entity.setImages(obj.getString("images"));
//                    entity.setHeadImage(obj.getString("headImage"));
//
//                    entityList.add(entity);
//                }
//                mListener.myResponse(entityList);
//            } else {
//                mListener.myError("无数据");
//            }
//        } else {
//            mListener.myError("网络错误");
//        }
//    }
}
