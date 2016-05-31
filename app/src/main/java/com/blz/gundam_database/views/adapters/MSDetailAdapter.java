package com.blz.gundam_database.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MSDetailImageEntity;
import com.blz.gundam_database.utils.Tools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by BuLingzhuang
 * on 2016/5/31
 * E-mail bulingzhuang@foxmail.com
 */
public class MSDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MSDetailImageEntity> mList;
    private Context mContext;

    public MSDetailAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void addAll(Collection<? extends MSDetailImageEntity> collection){
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void add(MSDetailImageEntity entity){
        mList.add(entity);
        notifyItemInserted(mList.size()-1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_msdetail, parent, false);
        return new MSDetailAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        MSDetailImageEntity entity = mList.get(position);
        MSDetailAdapterViewHolder viewHolder = (MSDetailAdapterViewHolder) holder;
        Picasso.with(mContext).load(entity.getImageUri()).placeholder(R.mipmap.menu_icon).error(R.mipmap.menu_icon).into(viewHolder.mImage);

        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
        params.height = entity.getHeight();
        params.width = entity.getWidth();
        viewHolder.itemView.setLayoutParams(params);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MSDetailAdapterViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mImage;

        public MSDetailAdapterViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.item_msdetial_image);
        }
    }
}
