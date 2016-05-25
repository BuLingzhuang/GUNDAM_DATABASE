package com.blz.gundam_database.interfaces.interactors;

import com.blz.gundam_database.entities.MainListByWorkEntity;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public interface MainInteractor {
    void getData(CallResponseListener listener);
    interface CallResponseListener{
        void myResponse(List<MainListByWorkEntity> mList);
        void myError(String eText);
    }
}
