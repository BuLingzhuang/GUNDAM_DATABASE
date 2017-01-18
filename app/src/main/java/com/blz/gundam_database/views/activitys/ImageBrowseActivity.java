package com.blz.gundam_database.views.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.LogUtil;
import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.adapters.ImageBrowseAdapter;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;

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
    ImageView mBack;
    @Bind(R.id.image_browse_download)
    ImageView mDownload;
    @Bind(R.id.image_browse_tvOriginalName)
    TextView mTvOriginalName;
    @Bind(R.id.image_browse_tvPageNumber)
    TextView mTvPageNumber;
    @Bind(R.id.rl_header)
    RelativeLayout rlHeader;
    @Bind(R.id.rl_footer)
    RelativeLayout rlFooter;
    @Bind(R.id.fl_content)
    RelativeLayout flContent;
    private ArrayList<String> mImageList;
    private String mOriginalName;
    private String mImageUrl;
    private int mCurrentItem;
    private boolean isVis = true;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Tools.showToast(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG);
        }
    };

    /*private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isVis) {
                rlHeader.setVisibility(View.VISIBLE);
                rlFooter.setVisibility(View.VISIBLE);
                mBack.setVisibility(View.VISIBLE);
                mDownload.setVisibility(View.VISIBLE);
            } else {
                rlHeader.setVisibility(View.GONE);
                rlFooter.setVisibility(View.GONE);
                mBack.setVisibility(View.GONE);
                mDownload.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_browse);
        ButterKnife.bind(this);
        Tools.changeFonts(this);
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
        ImageBrowseAdapter adapter = new ImageBrowseAdapter(this, mImageList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        mTvPageNumber.setText((position + 1) + "/" + mImageList.size());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewPager.setTransitionGroup(true);
            mViewPager.setTransitionName("Image_Browse");
        }
    }

    private void init() {
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 显隐Header和Footer的方法
     *
     * @param view
     */
    /*public void hideOShowHF() {
        if (isVis) {
            isVis = false;
            AnimationSet headerSet = new AnimationSet(true);
            AnimationSet footerSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            ScaleAnimation headerAnim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
            ScaleAnimation footerAnim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
            headerAnim.setDuration(200);
            headerAnim.setFillAfter(true);
            footerAnim.setDuration(200);
            footerAnim.setFillAfter(true);
            alphaAnimation.setDuration(200);
            alphaAnimation.setFillAfter(true);
            headerAnim.setInterpolator(new OvershootInterpolator());
            footerAnim.setInterpolator(new OvershootInterpolator());
            alphaAnimation.setInterpolator(new OvershootInterpolator());
            headerSet.setAnimationListener(mAnimationListener);
            footerSet.setAnimationListener(mAnimationListener);
            headerSet.addAnimation(headerAnim);
            headerSet.addAnimation(alphaAnimation);
            footerSet.addAnimation(footerAnim);
            footerSet.addAnimation(alphaAnimation);
            rlHeader.startAnimation(headerSet);
            rlFooter.startAnimation(footerSet);
        } else {
            isVis = true;
            AnimationSet headerSet = new AnimationSet(true);
            AnimationSet footerSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            ScaleAnimation headerAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
            ScaleAnimation footerAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
            headerAnim.setDuration(200);
            headerAnim.setFillAfter(true);
            footerAnim.setDuration(200);
            footerAnim.setFillAfter(true);
            alphaAnimation.setDuration(200);
            alphaAnimation.setFillAfter(true);
            headerAnim.setInterpolator(new OvershootInterpolator());
            footerAnim.setInterpolator(new OvershootInterpolator());
            alphaAnimation.setInterpolator(new OvershootInterpolator());
            headerSet.setAnimationListener(mAnimationListener);
            footerSet.setAnimationListener(mAnimationListener);
            headerSet.addAnimation(headerAnim);
            headerSet.addAnimation(alphaAnimation);
            footerSet.addAnimation(footerAnim);
            footerSet.addAnimation(alphaAnimation);
            rlHeader.startAnimation(headerSet);
            rlFooter.startAnimation(footerSet);
        }
    }*/
    @OnClick({R.id.image_browse_back, R.id.image_browse_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_browse_back:
                finish();
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
            Tools.save2SDCard(this, mHandler, mImageUrl, mOriginalName, mCurrentItem);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Tools.showLogE(grantResults.length + "" + Arrays.toString(permissions));
        if (requestCode == MY_PERMISSIONS_REQUEST_GROUP_STORAGE) {
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Tools.save2SDCard(this, mHandler, mImageUrl, mOriginalName, mCurrentItem);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(0, R.anim.finish_activity_alpha);
//    }

    //ViewPager的滑动监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        mTvPageNumber.setText((position + 1) + "/" + mImageList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
