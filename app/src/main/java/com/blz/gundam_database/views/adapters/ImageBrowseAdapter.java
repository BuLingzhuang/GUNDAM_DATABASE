package com.blz.gundam_database.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.LogUtil;
import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.activitys.ImageBrowseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by BuLingzhuang
 * on 2016/6/1
 * E-mail bulingzhuang@foxmail.com
 */
public class ImageBrowseAdapter extends PagerAdapter {

    private ArrayList<PhotoView> mPhotoViewList;
    private ImageBrowseActivity mActivity;

    public ImageBrowseAdapter(ImageBrowseActivity activity,ArrayList<String> list) {
        mActivity = activity;
        mPhotoViewList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PhotoView photoView = new PhotoView(activity);
            photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Log.e("blz","点击了");
                    if (mActivity != null) {
                        mActivity.hideOShowHF();
                    }
                }
            });
            Glide.with(activity).load(list.get(i)).placeholder(R.mipmap.default_placeholder).error(R.mipmap.default_placeholder).crossFade().into(photoView);
            mPhotoViewList.add(photoView);
        }
    }

    @Override
    public int getCount() {
        return mPhotoViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        PhotoView photoView = mPhotoViewList.get(position);
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }
}
