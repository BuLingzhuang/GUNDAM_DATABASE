package com.blz.gundam_database.views.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.activitys.MSTypeActivity;
import com.bumptech.glide.Glide;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by BuLingzhuang
 * on 2016/5/24
 * E-mail bulingzhuang@foxmail.com
 */
public class MainListByWorksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Object> mList;
    private Map<Type, Integer> mMap;

    public MainListByWorksAdapter(Context context, Map<Type, Integer> map) {
        mContext = context;
        mMap = map;
        mList = new ArrayList<>();
    }

    public void addAll(Collection<?> collection) {
        mList.clear();
        mList.addAll(collection);
        mList.add(new EmptyEntity());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.adapter_main_grid:
                return new MainListByWorksAdapterViewHolder(inflate);
            case R.layout.adapter_main_empty:
                return new MainEmptyViewHolder(inflate);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.adapter_main_grid:
                final MainListByWorkEntity entity = (MainListByWorkEntity) mList.get(position);
                final MainListByWorksAdapterViewHolder viewHolder = (MainListByWorksAdapterViewHolder) holder;
                Glide.with(mContext).load(entity.getIcon()).error(R.mipmap.default_placeholder).placeholder(R.mipmap.default_placeholder).into(viewHolder.mIvIcon);
                viewHolder.mTvOriginalName.setText(entity.getOriginalName());
                viewHolder.mTvStoryYear.setText(entity.getStoryYear());
                Tools.changeFont(mContext, viewHolder.mTvOriginalName, viewHolder.mTvStoryYear);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AVAnalytics.onEvent(mContext, mContext.getString(R.string.main_see_series) + entity.getOriginalName());
                        Intent intent = new Intent(mContext, MSTypeActivity.class);
                        intent.putExtra(MainListByWorkEntity.class.getName(), entity);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) mContext, new Pair<View, String>(viewHolder.mIvIcon, "Image_MSType_Header"), new Pair<View, String>(viewHolder.mLl, "LinearLayout_MSType_bottom")).toBundle();
                            mContext.startActivity(intent, bundle);
                        } else {
                            mContext.startActivity(intent);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMap.get(mList.get(position).getClass());
    }

    public static class MainListByWorksAdapterViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout mLl;
        private final ImageView mIvIcon;
        private final TextView mTvOriginalName;
        private final TextView mTvStoryYear;

        public MainListByWorksAdapterViewHolder(View itemView) {
            super(itemView);
            mLl = (LinearLayout) itemView.findViewById(R.id.item_main_ll);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_main_iv);
            mTvOriginalName = (TextView) itemView.findViewById(R.id.item_main_tvOriginalName);
            mTvStoryYear = (TextView) itemView.findViewById(R.id.item_main_tvStoryYear);
        }
    }

    public static class MainEmptyViewHolder extends RecyclerView.ViewHolder {

        public MainEmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
