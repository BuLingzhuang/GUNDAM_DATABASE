package com.blz.gundam_database.interfaces.interactors;

import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.interfaces.CallResponseListener;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public interface MobileSuitInteractor {
    void getData(String workId,String modelSeries, boolean isRefresh, int skip);

//    interface CallResponseListener {
//        void myResponse(List<MobileSuitEntity> mList);
//
//        void myError(String eText);
//    }
}
