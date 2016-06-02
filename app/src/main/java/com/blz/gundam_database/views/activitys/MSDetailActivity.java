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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blz.gundam_database.R;
import com.blz.gundam_database.entities.MSDetailImageEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;
import com.blz.gundam_database.utils.DividerItemDecoration;
import com.blz.gundam_database.views.adapters.MSDetailAdapter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSDetailActivity extends AppCompatActivity {

    @Bind(R.id.msdetail_boxImage)
    ImageView mMsdetailBoxImage;
    @Bind(R.id.msdetail_tvOriginalName)
    TextView mMsdetailTvOriginalName;
    @Bind(R.id.msdetail_tvVersion)
    TextView mMsdetailTvVersion;
    @Bind(R.id.msdetail_tvModelSeries)
    TextView mMsdetailTvModelSeries;
    @Bind(R.id.msdetail_tvScale)
    TextView mMsdetailTvScale;
    @Bind(R.id.msdetail_tvPrice)
    TextView mMsdetailTvPrice;
    @Bind(R.id.msdetail_tvManufacturer)
    TextView mMsdetailTvManufacturer;
    @Bind(R.id.msdetail_tvPrototypeMaster)
    TextView mMsdetailTvPrototypeMaster;
    @Bind(R.id.msdetail_tvItemNo)
    TextView mMsdetailTvItemNo;
    @Bind(R.id.msdetail_tvLaunchDate)
    TextView mMsdetailTvLaunchDate;
    @Bind(R.id.head_toolbar_back)
    ImageButton mHeadToolbarBack;
    @Bind(R.id.head_toolbar_title)
    TextView mHeadToolbarTitle;
    @Bind(R.id.msdetail_recyclerView)
    RecyclerView mMsdetailRecyclerView;
    @Bind(R.id.msdetail_progressBar)
    ProgressBar mMsdetailProgressBar;
    private MSDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdetail);
        ButterKnife.bind(this);
        init();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        MobileSuitEntity data = (MobileSuitEntity) intent.getSerializableExtra("MobileSuitEntity");
        mMsdetailTvOriginalName.setText("型号：" + data.getOriginalName());
        mMsdetailTvVersion.setText("版本：" + data.getVersion());
        mMsdetailTvModelSeries.setText("系列：" + data.getModelSeries());
        mMsdetailTvScale.setText("比例：" + data.getScale());
        mMsdetailTvPrice.setText("价格：" + data.getPrice());
        mMsdetailTvManufacturer.setText("厂商：" + data.getManufacturer());
        mMsdetailTvPrototypeMaster.setText("原型师：" + data.getPrototypeMaster());
        mMsdetailTvItemNo.setText("编号：" + data.getItemNo());
        mMsdetailTvLaunchDate.setText("发售时间：" + data.getLaunchDate());
        Picasso.with(this).load(data.getBoxImage()).placeholder(R.mipmap.default_placeholder).error(R.mipmap.default_placeholder).into(mMsdetailBoxImage);

        mAdapter = new MSDetailAdapter(this, data.getOriginalName(), data.getImages());
        mMsdetailRecyclerView.setAdapter(mAdapter);

        prepareImageList(data.getImages());
    }

    private void prepareImageList(String images) {
        isUploading(true);
        MSDetailAsyncTask msDetailAsyncTask = new MSDetailAsyncTask(this);
        msDetailAsyncTask.execute(images);
    }

    private void init() {
        mHeadToolbarTitle.setText("机体详情");
        mMsdetailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMsdetailRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.finish_activity_alpha);
    }

    @OnClick(R.id.head_toolbar_back)
    public void onClick() {
        onBackPressed();
    }

    private void isUploading(boolean b) {
        if (b) {
            mMsdetailProgressBar.setVisibility(View.VISIBLE);
        } else {
            mMsdetailProgressBar.setVisibility(View.GONE);
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
