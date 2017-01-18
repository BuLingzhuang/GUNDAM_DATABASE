package com.blz.gundam_database.views.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSBase;
import com.avos.sns.SNSCallback;
import com.avos.sns.SNSException;
import com.avos.sns.SNSType;
import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;
import com.blz.gundam_database.views.ui.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends SwipeBackActivity {

    @Bind(R.id.btn_pageType)
    RelativeLayout btnPageType;
    @Bind(R.id.tv_pageType)
    TextView tvPageType;
    @Bind(R.id.btn_login_weibo)
    ImageView btnLoginWeibo;
    @Bind(R.id.btn_back)
    RelativeLayout btnBack;
    @Bind(R.id.civ_icon)
    CircleImageView civIcon;
    @Bind(R.id.iv_icon_type)
    ImageView ivIconType;
    @Bind(R.id.et_login_phone)
    EditText etLoginPhone;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.et_register_userName)
    EditText etRegisterUserName;
    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.ll_register)
    LinearLayout llRegister;
    @Bind(R.id.btn_submit)
    TextView btnSubmit;
    @Bind(R.id.activity_login)
    RelativeLayout activity;

    private boolean mIsRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        changePageType(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
    }

    public void btnWeiboLogin(View view) {
        try {
            SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo, "https://leancloud.cn/1.1/sns/goto/yrb88s03u2emd8ye");
            SNS.loginWithCallback(this, SNSType.AVOSCloudSNSSinaWeibo, new SNSCallback() {
                @Override
                public void done(SNSBase snsBase, SNSException e) {
                    if (e == null) {
                        JSONObject data = snsBase.authorizedData();
                        if (data != null) {
                            Tools.showLogE(data.toString());
                        }
                        SNS.loginWithAuthData(snsBase.userInfo(), new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                            }
                        });
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.civ_icon, R.id.btn_submit, R.id.btn_pageType, R.id.btn_login_weibo, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_icon://头像(注册时可上传，登录时不可上传隐藏右上角图标)
                break;
            case R.id.btn_submit://提交表单(登录或注册)
                break;
            case R.id.btn_pageType://跳转注册or登录
                changePageType(!mIsRegister);
                break;
            case R.id.btn_login_weibo://第三方登陆
                break;
            case R.id.btn_back://回退
                finish();
                break;
        }
    }

    private void changePageType(boolean isRegister) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(activity, new Fade());
        }
        if (mIsRegister = isRegister) {//注册页面
            ivIconType.setVisibility(View.VISIBLE);
            btnSubmit.setText("注册");
            llRegister.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
            tvPageType.setText(getResources().getString(R.string.user_page_type_register));
        } else {//登录页面
            ivIconType.setVisibility(View.GONE);
            btnSubmit.setText("登录");
            llRegister.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
            tvPageType.setText(getResources().getString(R.string.user_page_type_login));
        }
    }
}
