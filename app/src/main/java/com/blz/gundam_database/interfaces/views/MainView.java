package com.blz.gundam_database.interfaces.views;

import com.blz.gundam_database.entities.MainListByWorkEntity;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public interface MainView {
    void updateMainList(List<MainListByWorkEntity> mList);
    void updateError(String eText);
    void showRefresh(boolean b);
}
