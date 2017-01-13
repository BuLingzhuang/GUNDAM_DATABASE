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

    public ImageBrowseAdapter(Context context, ArrayList<String> list) {
        mPhotoViewList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PhotoView photoView = new PhotoView(context);
            photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(context).load(list.get(i)).error(R.mipmap.default_placeholder).crossFade().into(photoView);
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
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = mPhotoViewList.get(position);
//        PhotoViewAttacher.OnViewTapListener tapListener = photoView.getOnViewTapListener();
//        if (tapListener != null) {
//            Log.e("卜令壮","listener不为空,"+position);
//        }else {
//            Log.e("卜令壮","listener为空,"+position);
//        }
//        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//            @Override
//            public void onViewTap(View view, float x, float y) {
//                Log.e("卜令壮","点击了ViewTap");
//                if (mActivity != null) {
//                    mActivity.hideOShowHF();
//                }
//            }
//        });
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
