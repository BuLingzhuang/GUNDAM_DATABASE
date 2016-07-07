package com.blz.gundam_database.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by BuLingzhuang
 * on 2016/6/1
 * E-mail bulingzhuang@foxmail.com
 */
public class ImageBrowseAdapter extends PagerAdapter {

    private ArrayList<String> mList;

    public ImageBrowseAdapter(ArrayList<String> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Context context = container.getContext();
        PhotoView photoView = new PhotoView(context);
        photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(context).load(mList.get(position)).placeholder(R.mipmap.default_placeholder).error(R.mipmap.default_placeholder).into(photoView);
        container.addView(photoView);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showToast(context,position+"");
            }
        });
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }
}
