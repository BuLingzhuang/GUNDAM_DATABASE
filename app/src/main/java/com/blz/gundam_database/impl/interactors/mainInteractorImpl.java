package com.blz.gundam_database.impl.interactors;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.interfaces.interactors.MainInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public class MainInteractorImpl extends FindCallback<AVObject> implements MainInteractor{

    private CallResponseListener mListener;

    @Override
    public void getData(CallResponseListener listener) {
        mListener = listener;
        AVQuery<AVObject> query = new AVQuery<>("MainListByWorks");
        query.orderByAscending("workId");
        query.findInBackground(this);
    }

    @Override
    public void done(List<AVObject> list, AVException e) {
        if (list.size() >=1) {
            ArrayList<MainListByWorkEntity> mEntityList = new ArrayList<>();
            for (AVObject obj : list) {
                MainListByWorkEntity entity = new MainListByWorkEntity();
                entity.setName(obj.getString("name"));
                entity.setEnglishName(obj.getString("englishName"));
                entity.setOriginalName(obj.getString("originalName"));
                entity.setIcon(obj.getString("icon"));
                entity.setWorkId(obj.getString("workId"));
                entity.setStoryYear(obj.getString("storyYear"));
                mEntityList.add(entity);
            }
            mListener.myResponse(mEntityList);
        }else {
            mListener.myError("加载数据失败");
        }
    }
}
