package com.blz.gundam_database.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.squareup.picasso.Picasso;

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
    private List<MainListByWorkEntity> mList;
    private Map<Type, Integer> mMap;

    public MainListByWorksAdapter(Context context, Map<Type, Integer> map) {
        mContext = context;
        mMap = map;
        mList = new ArrayList<>();
    }

    public void addAll(Collection<? extends MainListByWorkEntity> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.adapter_main_grid:
                return new MainListByWorksAdapterViewHolder(inflate);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.adapter_main_grid:
                MainListByWorkEntity entity = mList.get(position);
                MainListByWorksAdapterViewHolder viewHolder = (MainListByWorksAdapterViewHolder) holder;
                Picasso.with(mContext).load(entity.getIcon()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(viewHolder.mIvIcon);
                viewHolder.mTvOriginalName.setText(entity.getOriginalName());
                viewHolder.mTvStoryYear.setText(entity.getStoryYear());
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

        private final ImageView mIvIcon;
        private final TextView mTvOriginalName;
        private final TextView mTvStoryYear;
        private final CardView mCv;

        public MainListByWorksAdapterViewHolder(View itemView) {
            super(itemView);
            mCv = (CardView) itemView.findViewById(R.id.item_main_cv);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_main_iv);
            mTvOriginalName = (TextView) itemView.findViewById(R.id.item_main_tvOriginalName);
            mTvStoryYear = (TextView) itemView.findViewById(R.id.item_main_tvStoryYear);
        }
    }
}
