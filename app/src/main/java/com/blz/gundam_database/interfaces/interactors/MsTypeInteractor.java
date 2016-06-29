package com.blz.gundam_database.interfaces.interactors;

import java.util.HashMap;

/**
 * Created by BuLingzhuang
 * on 2016/6/27
 * E-mail bulingzhuang@foxmail.com
 */
public interface MSTypeInteractor {
    void getDataMap(String modelSeries,String workId);
    interface ResponseListener{
        void myResponse(String modelSeries,int count);
        void myError(String eText);
    }
}
