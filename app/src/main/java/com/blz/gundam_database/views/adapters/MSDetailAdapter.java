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
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MSDetailImageEntity;
import com.blz.gundam_database.views.activitys.ImageBrowseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by BuLingzhuang
 * on 2016/5/31
 * E-mail bulingzhuang@foxmail.com
 */
public class MSDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MSDetailImageEntity> mList;
    private Context mContext;
    private String mOriginalName;
    private String mImages;
    private View mHeader;

    public MSDetailAdapter(Context context, View header, String originalName, String images) {
        mContext = context;
        mHeader = header;
        mList = new ArrayList<>();
        mOriginalName = originalName;
        mImages = images;
    }

    public void add(MSDetailImageEntity entity) {
        mList.add(entity);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends MSDetailImageEntity> entities) {
        mList.clear();
        mList.addAll(entities);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_msdetail, parent, false);
        return new MSDetailAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MSDetailImageEntity entity = mList.get(position);
        final MSDetailAdapterViewHolder viewHolder = (MSDetailAdapterViewHolder) holder;

        Glide.with(mContext).load(entity.getImageUri()).placeholder(R.mipmap.default_placeholder).crossFade().error(R.mipmap.default_placeholder).into(viewHolder.mImage);

        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
        params.height = entity.getHeight();
        params.width = entity.getWidth();
        viewHolder.itemView.setLayoutParams(params);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageBrowseActivity.class);
                intent.putExtra("originalName", mOriginalName);
                intent.putExtra("images", mImages);
                intent.putExtra("position", holder.getAdapterPosition());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) mContext, new Pair<>(mHeader, "Image_Header"), new Pair<View, String>(viewHolder.mImage, "Image_Browse")).toBundle();
                    mContext.startActivity(intent, bundle);
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MSDetailAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImage;

        public MSDetailAdapterViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.item_msdetial_image);
        }
    }
}
