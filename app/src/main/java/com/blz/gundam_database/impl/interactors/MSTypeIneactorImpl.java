package com.blz.gundam_database.impl.interactors;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.blz.gundam_database.R;
import com.blz.gundam_database.interfaces.interactors.MSTypeInteractor;
import com.blz.gundam_database.utils.Constants;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by BuLingzhuang
 * on 2016/6/27
 * E-mail bulingzhuang@foxmail.com
 */
public class MSTypeIneactorImpl implements MSTypeInteractor {
    private ResponseListener mListener;
    private Context mContext;

    public MSTypeIneactorImpl(ResponseListener listener,Context context) {
        mListener = listener;
        mContext = context;
    }

    @Override
    public void getDataMap(final String modelSeries,String workId) {
        AVQuery<AVObject> query1 = new AVQuery<>("MobileSuitEntity");
        query1.whereEqualTo("modelSeries", modelSeries);
        AVQuery<AVObject> query2 = new AVQuery<>("MobileSuitEntity");
        query2.whereEqualTo("workId", workId);
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1,query2));
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                if (e == null) {
                    mListener.myResponse(modelSeries,i);
                }else {
                    mListener.myError(mContext.getString(R.string.mst_failed_get_data)+modelSeries);
                }
            }
        });

    }
}
