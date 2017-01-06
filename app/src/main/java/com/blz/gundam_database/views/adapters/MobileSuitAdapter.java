package com.blz.gundam_database.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.views.activitys.MSDetailActivity;
import com.bumptech.glide.Glide;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by BuLingzhuang
 * on 2016/5/26
 * E-mail bulingzhuang@foxmail.com
 */
public class MobileSuitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Object> mList;
    private Map<Type, Integer> mMap;

    public MobileSuitAdapter(Context context, Map<Type, Integer> map) {
        mContext = context;
        mMap = map;
        mList = new ArrayList<>();
    }

    public void addAll(Collection<?> collection, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(collection);
        mList.add(new EmptyEntity());
        notifyDataSetChanged();
    }

    public void addFoot() {
        mList.add(new EmptyEntity());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.adapter_mobile_suit:
                return new MobileSuitAdapterDefaultViewHolder(inflate);
            case R.layout.adapter_main_empty:
                return new MainListByWorksAdapter.MainEmptyViewHolder(inflate);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.adapter_mobile_suit:
                final MobileSuitEntity entity = (MobileSuitEntity) mList.get(position);
                MobileSuitAdapterDefaultViewHolder viewHolder = (MobileSuitAdapterDefaultViewHolder) holder;
                Glide.with(mContext).load(entity.getHeadImage()).error(R.mipmap.default_placeholder).placeholder(R.mipmap.default_placeholder).crossFade().into(viewHolder.mIv);
                viewHolder.mTvOriginalName.setText(mContext.getString(R.string.mobile_type) + entity.getOriginalName());
                viewHolder.mTvModelSeries.setText(mContext.getString(R.string.mobile_model_series) + entity.getModelSeries());
                viewHolder.mTvPrice.setText(mContext.getString(R.string.mobile_price) + entity.getPrice());
                viewHolder.mTvLaunchDate.setText(mContext.getString(R.string.mobile_launch_date) + entity.getLaunchDate());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AVAnalytics.onEvent(mContext, mContext.getString(R.string.mobile_see_belong) + entity.getWorkId());
                        Intent intent = new Intent(mContext, MSDetailActivity.class);
                        intent.putExtra("MobileSuitEntity", entity);
                        mContext.startActivity(intent);
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

    private class MobileSuitAdapterDefaultViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mIv;
        private final TextView mTvOriginalName;
        private final TextView mTvModelSeries;
        private final TextView mTvPrice;
        private final TextView mTvLaunchDate;

        public MobileSuitAdapterDefaultViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.item_mobile_suit_iv);
            mTvOriginalName = (TextView) itemView.findViewById(R.id.item_mobile_suit_tvOriginalName);
            mTvModelSeries = (TextView) itemView.findViewById(R.id.item_mobile_suit_tvModelSeries);
            mTvPrice = (TextView) itemView.findViewById(R.id.item_mobile_suit_tvPrice);
            mTvLaunchDate = (TextView) itemView.findViewById(R.id.item_mobile_suit_tvLaunchDate);
        }
    }
}
