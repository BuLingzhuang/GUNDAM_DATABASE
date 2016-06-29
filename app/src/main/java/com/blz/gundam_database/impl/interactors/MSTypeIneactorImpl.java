package com.blz.gundam_database.impl.interactors;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.blz.gundam_database.interfaces.interactors.MSTypeInteractor;
import com.blz.gundam_database.utils.Constants;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by BuLingzhuang
 * on 2016/6/27
 * E-mail bulingzhuang@foxmail.com
 */
public class MSTypeIneactorImpl implements MSTypeInteractor {
    private ResponseListener mListener;

    public MSTypeIneactorImpl(ResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void getDataMap(final String modelSeries,String workId) {
        AVQuery<AVObject> query1 = new AVQuery<>("MobileSuitEntity");
        query1.whereEqualTo("modelSeries", modelSeries);
        AVQuery<AVObject> query2 = new AVQuery<>("MobileSuitEntity");
        query2.whereEqualTo("workId", workId);
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1,query2));
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                if (e == null) {
                    mListener.myResponse(modelSeries,i);
                }else {
                    mListener.myError("获取数据失败："+modelSeries);
                }
            }
        });

    }
}
