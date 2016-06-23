package com.blz.gundam_database.interfaces.presenters;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public interface MobileSuitPresenter {
    void getData(String workId);
    void getData(String workId, int skip);
    void getData(String workId, boolean isRefresh, int skip);
}
