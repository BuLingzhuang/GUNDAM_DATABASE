package com.blz.gundam_database.interfaces.views;

import com.blz.gundam_database.entities.MobileSuitEntity;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public interface MobileSuitView {
    void updateData(List<MobileSuitEntity> mList,boolean isRefresh);

    void updateError(String eText);

    void uploading(boolean b);
}
