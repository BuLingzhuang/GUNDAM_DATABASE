package com.blz.gundam_database.impl.interactors;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.blz.gundam_database.dao.SplashScreenEntityDao;
import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.interfaces.interactors.SplashScreenInteractor;
import com.blz.gundam_database.utils.DBUtils;
import com.blz.gundam_database.utils.Tools;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class SplashScreenInteractorImpl extends FindCallback<AVObject> implements SplashScreenInteractor {
    Context mContext;
    View mGenView;

    @Override
    public void setData(Context context, View genView) {
        mContext = context;
        mGenView = genView;
        AVQuery<AVObject> query = new AVQuery<>("SplashScreenEntity");
        query.findInBackground(this);
    }

    @Override
    public SplashScreenEntity getData() {
        List<SplashScreenEntity> entityList = DBUtils.getDaoSession().getSplashScreenEntityDao().loadAll();
        SplashScreenEntity entity = null;
        if (entityList.size() >= 1) {
            entity = entityList.get(0);
        }
        if (entity == null){
            entity = new SplashScreenEntity("http://tu.webps.cn/tb/img/4/TB1d.rBGpXXXXa9XpXXXXXXXXXX_%21%210-item_pic.jpg");
        }
        return entity;
    }

    @Override
    public void startThread(final OnAnimFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.finish();
            }
        }, 3000);
    }

    @Override
    public void done(List<AVObject> list, AVException e) {
        if (list.size() >= 1) {
            AVObject avObject = list.get(0);
            String imgUrl = avObject.getString("imgUrl");
            SplashScreenEntityDao dao = DBUtils.getDaoSession().getSplashScreenEntityDao();
            dao.deleteAll();
            dao.insertOrReplaceInTx(new SplashScreenEntity(imgUrl));
            Tools.showLogE(this, "网络请求成功，图片地址：" + imgUrl);
        } else {
            Tools.showSnackBar(mContext, "网络访问失败", mGenView);
        }
    }
}
