package com.blz.gundam_database.interfaces.views;

import java.util.HashMap;

/**
 * Created by BuLingzhuang
 * on 2016/6/27
 * E-mail bulingzhuang@foxmail.com
 */
public interface MSTypeView {
    void updateData(String modelSeries, int count);
    void updateError(String eText);
}
