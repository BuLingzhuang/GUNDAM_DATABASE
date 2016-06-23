package com.blz.gundam_database.base;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.blz.gundam_database.interfaces.CallResponseListener;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/30
 * E-mail bulingzhuang@foxmail.com
 */
public abstract class BaseFindCallback extends FindCallback<AVObject> {
    private CallResponseListener mListener;
    private boolean b = true;

    public boolean isRefresh() {
        return b;
    }

    public void setRefresh(boolean refresh) {
        b = refresh;
    }

    public BaseFindCallback(CallResponseListener listener) {
        mListener = listener;
    }


    @Override
    public void done(List<AVObject> list, AVException e) {
        if (list != null) {

            if (list.size() >= 1){
                mListener.myResponse(list,b);
            } else {
                mListener.myError("无数据");
            }
        } else {
            mListener.myError("网络错误");
        }
    }
}
