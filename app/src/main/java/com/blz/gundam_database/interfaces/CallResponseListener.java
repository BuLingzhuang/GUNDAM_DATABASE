package com.blz.gundam_database.interfaces;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/30
 * E-mail bulingzhuang@foxmail.com
 */
public interface CallResponseListener {
    void myResponse(List<AVObject> list,boolean isRefresh);

    void myError(String eText);
}
