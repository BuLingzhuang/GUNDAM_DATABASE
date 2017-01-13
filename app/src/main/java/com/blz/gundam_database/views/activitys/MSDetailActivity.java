package com.blz.gundam_database.views.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MSDetailImageEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.views.adapters.MSDetailAdapter;
import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSDetailActivity extends AppCompatActivity {

    @Bind(R.id.msdetail_tb)
    Toolbar mToolbar;
    @Bind(R.id.head_toolbar_back)
    ImageButton mHeadToolbarBack;
    @Bind(R.id.head_toolbar_title)
    TextView mHeadToolbarTitle;
    @Bind(R.id.msdetail_recyclerView)
    RecyclerView mMsdetailRecyclerView;
    @Bind(R.id.msdetail_progressBar)
    ProgressBar mMsdetailProgressBar;
    @Bind(R.id.msdetail_a)
    TextView mMsdetailA;
    @Bind(R.id.msdetail_H_ll)
    LinearLayout mMsdetailHLl;
    @Bind(R.id.msdetail_V_ll)
    LinearLayout mMsdetailVLl;
    @Bind(R.id.msdetail_bgImage)
    ImageView mBgImage;
    private TextView mMsdetailTvOriginalName;
    private TextView mMsdetailTvVersion;
    private TextView mMsdetailTvModelSeries;
    private TextView mMsdetailTvScale;
    private TextView mMsdetailTvPrice;
    private TextView mMsdetailTvManufacturer;
    private TextView mMsdetailTvPrototypeMaster;
    private TextView mMsdetailTvItemNo;
    private TextView mMsdetailTvLaunchDate;
    private ImageView mMsdetailBoxImage;
    private MSDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdetail);
        ButterKnife.bind(this);
        MobileSuitEntity data = init();
        initData(data);
    }

    @SuppressLint("SetTextI18n")
    private void initData(MobileSuitEntity data) {
        mMsdetailTvOriginalName.setText("型号：" + data.getOriginalName());
        mMsdetailTvVersion.setText("版本：" + data.getVersion());
        mMsdetailTvModelSeries.setText("系列：" + data.getModelSeries());
        mMsdetailTvScale.setText("比例：" + data.getScale());
        mMsdetailTvPrice.setText("价格：" + data.getPrice());
        mMsdetailTvManufacturer.setText("厂商：" + data.getManufacturer());
        mMsdetailTvPrototypeMaster.setText("原型师：" + data.getPrototypeMaster());
        mMsdetailTvItemNo.setText("编号：" + data.getItemNo());
        mMsdetailTvLaunchDate.setText("发售时间：" + data.getLaunchDate());
        mMsdetailBoxImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(this).load(data.getBoxImage()).crossFade().placeholder(R.mipmap.default_placeholder).error(R.mipmap.default_placeholder).into(mMsdetailBoxImage);
        Glide.with(this).load(data.getBoxImage()).crossFade().into(mBgImage);
        mBgImage.setAlpha(.075f);

        mAdapter = new MSDetailAdapter(this,mToolbar, data.getOriginalName(), data.getImages());
        mMsdetailRecyclerView.setAdapter(mAdapter);

        prepareImageList(data.getImages());
    }

    private void prepareImageList(String images) {
        isUploading(true);
        MSDetailAsyncTask msDetailAsyncTask = new MSDetailAsyncTask(this);
        msDetailAsyncTask.execute(images);
    }

    private MobileSuitEntity init() {
        mHeadToolbarTitle.setText("机体详情");
        mMsdetailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //RecyclerView间隔着色
//        mMsdetailRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));

        mMsdetailA.setMovementMethod(LinkMovementMethod.getInstance());
        Intent intent = getIntent();
        MobileSuitEntity data = (MobileSuitEntity) intent.getSerializableExtra("MobileSuitEntity");
        changeLayout(data.getLlType());
        return data;
    }

    private void changeLayout(String type) {
        switch (type) {
            case "2":
                mMsdetailVLl.setVisibility(View.VISIBLE);
                mMsdetailHLl.setVisibility(View.GONE);
                mMsdetailTvOriginalName = (TextView) findViewById(R.id.msdetail_tvOriginalName);
                mMsdetailTvVersion = (TextView) findViewById(R.id.msdetail_tvVersion);
                mMsdetailTvModelSeries = (TextView) findViewById(R.id.msdetail_tvModelSeries);
                mMsdetailTvScale = (TextView) findViewById(R.id.msdetail_tvScale);
                mMsdetailTvPrice = (TextView) findViewById(R.id.msdetail_tvPrice);
                mMsdetailTvManufacturer = (TextView) findViewById(R.id.msdetail_tvManufacturer);
                mMsdetailTvPrototypeMaster = (TextView) findViewById(R.id.msdetail_tvPrototypeMaster);
                mMsdetailTvItemNo = (TextView) findViewById(R.id.msdetail_tvItemNo);
                mMsdetailTvLaunchDate = (TextView) findViewById(R.id.msdetail_tvLaunchDate);
                mMsdetailBoxImage = (ImageView) findViewById(R.id.msdetail_boxImage);
                break;
            case "1":
                mMsdetailVLl.setVisibility(View.GONE);
                mMsdetailHLl.setVisibility(View.VISIBLE);
                mMsdetailTvOriginalName = (TextView) findViewById(R.id.msdetail_H_tvOriginalName);
                mMsdetailTvVersion = (TextView) findViewById(R.id.msdetail_H_tvVersion);
                mMsdetailTvModelSeries = (TextView) findViewById(R.id.msdetail_H_tvModelSeries);
                mMsdetailTvScale = (TextView) findViewById(R.id.msdetail_H_tvScale);
                mMsdetailTvPrice = (TextView) findViewById(R.id.msdetail_H_tvPrice);
                mMsdetailTvManufacturer = (TextView) findViewById(R.id.msdetail_H_tvManufacturer);
                mMsdetailTvPrototypeMaster = (TextView) findViewById(R.id.msdetail_H_tvPrototypeMaster);
                mMsdetailTvItemNo = (TextView) findViewById(R.id.msdetail_H_tvItemNo);
                mMsdetailTvLaunchDate = (TextView) findViewById(R.id.msdetail_H_tvLaunchDate);
                mMsdetailBoxImage = (ImageView) findViewById(R.id.msdetail_H_boxImage);
                break;
        }
    }

    //返回时Activity销毁动画
//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(0, R.anim.finish_activity_alpha);
//    }

    private void isUploading(boolean b) {
        if (b) {
            mMsdetailProgressBar.setVisibility(View.VISIBLE);
        } else {
            mMsdetailProgressBar.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.head_toolbar_back, R.id.msdetail_a})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_toolbar_back:
//                onBackPressed();
                finish();
                break;
            case R.id.msdetail_a:

                break;
        }
    }

    class MSDetailAsyncTask extends AsyncTask<String, String, ArrayList<MSDetailImageEntity>> {
        private Context mContext;

        public MSDetailAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected final ArrayList<MSDetailImageEntity> doInBackground(String... params) {
            ArrayList<MSDetailImageEntity> entityList = new ArrayList<>();
            String str = params[0];
            ArrayList<String> imageList = new ArrayList<>();
            String[] split = str.split(",");
            Collections.addAll(imageList, split);
            for (String url : imageList) {
                try {
                    URL m_url = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) m_url.openConnection();
                    InputStream in = con.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, options);
                    float density = mContext.getResources().getDisplayMetrics().density;
                    int height = (int) (220 * density);
                    int width = (int) ((220 * density) / options.outHeight * options.outWidth);
                    in.close();
                    entityList.add(new MSDetailImageEntity(url, height, width));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return entityList;
        }

        @Override
        protected void onPostExecute(ArrayList<MSDetailImageEntity> msDetailImageEntities) {
            mAdapter.addAll(msDetailImageEntities);
            isUploading(false);
        }
    }
}
