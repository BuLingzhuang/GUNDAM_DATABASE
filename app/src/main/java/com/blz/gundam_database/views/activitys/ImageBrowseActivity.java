package com.blz.gundam_database.views.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.adapters.ImageBrowseAdapter;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageBrowseActivity extends SwipeBackActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final int MY_PERMISSIONS_REQUEST_GROUP_STORAGE = 3;
    @Bind(R.id.image_browse_viewPager)
    ViewPager mViewPager;
    @Bind(R.id.image_browse_back)
    ImageButton mBack;
    @Bind(R.id.image_browse_download)
    ImageButton mDownload;
    @Bind(R.id.image_browse_tvOriginalName)
    TextView mTvOriginalName;
    @Bind(R.id.image_browse_tvPageNumber)
    TextView mTvPageNumber;
    private ArrayList<String> mImageList;
    private String mOriginalName;
    private String mImageUrl;
    private int mCurrentItem;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Tools.showToast(getApplicationContext(),(String) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mOriginalName = intent.getStringExtra("originalName");
        mTvOriginalName.setText(mOriginalName);
        String images = intent.getStringExtra("images");
        mImageList = new ArrayList<>();
        String[] split = images.split(",");
        Collections.addAll(mImageList, split);
        int position = intent.getIntExtra("position", 0);
        ImageBrowseAdapter adapter = new ImageBrowseAdapter(mImageList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
    }

    private void init() {
        mBack.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @OnClick({R.id.image_browse_back, R.id.image_browse_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_browse_back:
                onBackPressed();
                break;
            case R.id.image_browse_download:
                int currentItem = mViewPager.getCurrentItem();
                if (mImageList != null) {
                    String imageUrl = mImageList.get(currentItem);
                    loadImage(imageUrl, currentItem);
                }
                break;
        }
    }

    private void loadImage(String imageUrl, int currentItem) {
        mImageUrl = imageUrl;
        mCurrentItem = currentItem;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_GROUP_STORAGE);
        } else {
            Tools.save2SDCard(this,mHandler,mImageUrl,mOriginalName,mCurrentItem);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Tools.showLogE(this,grantResults.length+""+ Arrays.toString(permissions));
        if (requestCode == MY_PERMISSIONS_REQUEST_GROUP_STORAGE) {
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Tools.save2SDCard(this,mHandler,mImageUrl,mOriginalName,mCurrentItem);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    //ViewPager的滑动监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        mTvPageNumber.setText((position+1)+"/"+mImageList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
