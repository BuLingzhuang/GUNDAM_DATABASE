package com.blz.gundam_database.base;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.blz.gundam_database.R;
import com.blz.gundam_database.interfaces.CallResponseListener;

import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/30
 * E-mail bulingzhuang@foxmail.com
 */
public abstract class BaseFindCallback extends FindCallback<AVObject> {
    private CallResponseListener mListener;
    private Context mContext;
    private boolean b = true;

    public boolean isRefresh() {
        return b;
    }

    public void setRefresh(boolean refresh) {
        b = refresh;
    }

    public BaseFindCallback(CallResponseListener listener, Context context) {
        mListener = listener;
        mContext = context;
    }


    @Override
    public void done(List<AVObject> list, AVException e) {
        if (list != null) {

            if (list.size() >= 1){
                mListener.myResponse(list,b);
            } else {
                mListener.myError(mContext.getString(R.string.base_no_data));
            }
        } else {
            mListener.myError(mContext.getString(R.string.base_network_error));
        }
    }
}
