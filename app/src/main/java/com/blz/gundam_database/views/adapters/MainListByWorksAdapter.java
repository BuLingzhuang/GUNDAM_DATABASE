package com.blz.gundam_database.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.EmptyEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.views.activitys.MobileSuitActivity;
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
                MainListByWorksAdapterViewHolder viewHolder = (MainListByWorksAdapterViewHolder) holder;
                Picasso.with(mContext).load(entity.getIcon()).error(R.mipmap.menu_icon).placeholder(R.mipmap.menu_icon).into(viewHolder.mIvIcon);
                viewHolder.mTvOriginalName.setText(entity.getOriginalName());
                viewHolder.mTvStoryYear.setText(entity.getStoryYear());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, MobileSuitActivity.class);
                        intent.putExtra("MainListByWorkEntity",entity);
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

    public static class MainListByWorksAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mIvIcon;
        private final TextView mTvOriginalName;
        private final TextView mTvStoryYear;

        public MainListByWorksAdapterViewHolder(View itemView) {
            super(itemView);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_main_iv);
            mTvOriginalName = (TextView) itemView.findViewById(R.id.item_main_tvOriginalName);
            mTvStoryYear = (TextView) itemView.findViewById(R.id.item_main_tvStoryYear);
        }
    }

    public static class MainEmptyViewHolder extends RecyclerView.ViewHolder{

        public MainEmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}