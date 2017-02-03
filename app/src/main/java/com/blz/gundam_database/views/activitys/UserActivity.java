package com.blz.gundam_database.views.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSBase;
import com.avos.sns.SNSCallback;
import com.avos.sns.SNSException;
import com.avos.sns.SNSType;
import com.blz.gundam_database.R;
import com.blz.gundam_database.utils.Constants;
import com.blz.gundam_database.utils.SelectPopUtil;
import com.blz.gundam_database.utils.Tools;
import com.blz.gundam_database.views.swipebacklayout.SwipeBackActivity;
import com.blz.gundam_database.views.ui.CircleImageView;
import com.blz.gundam_database.views.ui.EditTextFilter;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

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
    EditText etLoginUserName;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.et_register_nickname)
    EditText etRegisterNickname;
    @Bind(R.id.et_register_userName)
    EditTextFilter etRegisterUserName;
    @Bind(R.id.et_register_checkPassword)
    EditText etRegisterCheckPassword;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.ll_register)
    LinearLayout llRegister;
    @Bind(R.id.btn_submit)
    TextView btnSubmit;
    @Bind(R.id.activity_login)
    RelativeLayout activity;
    @Bind(R.id.v_login_phone)
    View vLoginPhone;
    @Bind(R.id.v_login_password)
    View vLoginPassword;
    @Bind(R.id.v_register_nickname)
    View vRegisterNickname;
    @Bind(R.id.v_register_userName)
    View vRegisterUserName;
    @Bind(R.id.v_register_checkPassword)
    View vRegisterPhone;
    @Bind(R.id.v_register_password)
    View vRegisterPassword;
    @Bind(R.id.civ_icon_mask)
    View civIconMask;
    @Bind(R.id.tv_icon_progress)
    TextView tvIconProgress;
    @Bind(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @Bind(R.id.btn_user_logout)
    TextView btnUserLogout;
    @Bind(R.id.ll_login_otherTitle)
    LinearLayout llLoginOtherTitle;

    private boolean mIsRegister;
    private boolean mIsUser;

    private int mLoginPhoneLength;
    private int mLoginPasswordLength;
    private int mRegisterUserNameLength;
    private int mRegisterPhoneLength;
    private int mRegisterPasswordLength;

    private Uri mUploadImageUri;//待上传图片本地Uri
    private String mNetImageUri;//已上传图片网络Uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        Tools.changeFonts(this);
        init();
        initData();
    }

    //初始化数据
    private void initData() {
        checkCurrentStatus(true);
    }

    /**
     * 进入页面时检查本地是否有用户登录缓存，有的话进入注销页面
     */
    private void checkCurrentStatus(boolean isCreate) {
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {//代表当前有账号登录中
            if (!isCreate && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(activity, new Fade());
            }
            mIsUser = true;
            ivIconType.setVisibility(View.GONE);
            llLogin.setVisibility(View.GONE);
            llRegister.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            btnPageType.setVisibility(View.GONE);
            btnLoginWeibo.setVisibility(View.GONE);
            llLoginOtherTitle.setVisibility(View.GONE);
            tvUserNickname.setVisibility(View.VISIBLE);
            btnUserLogout.setVisibility(View.VISIBLE);

            etLoginUserName.setText("");
            etLoginPassword.setText("");
            String nickname = (String) currentUser.get("nickname");
            if (nickname != null) {
                tvUserNickname.setText(nickname);
            } else {
                tvUserNickname.setText("null");
            }
            String userIcon = (String) currentUser.get("userIcon");
            Glide.with(this).load(userIcon).error(R.mipmap.default_placeholder).crossFade().into(civIcon);
        }
    }

    //初始化各种View，监听器
    private void init() {
        btnSubmit.setEnabled(false);
        changePageType(true);

        initListener();
    }

    /**
     * 初始化相关监听器
     */
    private void initListener() {
        MyFocusChangeListener myFocusChangeListener = new MyFocusChangeListener();
        etLoginUserName.setOnFocusChangeListener(myFocusChangeListener);
        etLoginPassword.setOnFocusChangeListener(myFocusChangeListener);
        etRegisterNickname.setOnFocusChangeListener(myFocusChangeListener);
        etRegisterUserName.setOnFocusChangeListener(myFocusChangeListener);
        etRegisterCheckPassword.setOnFocusChangeListener(myFocusChangeListener);
        etRegisterPassword.setOnFocusChangeListener(myFocusChangeListener);
        MyTextWatcher myTextWatcher = new MyTextWatcher();
        etLoginUserName.addTextChangedListener(myTextWatcher);
        etLoginPassword.addTextChangedListener(myTextWatcher);
        etRegisterNickname.addTextChangedListener(myTextWatcher);
        etRegisterUserName.addTextChangedListener(myTextWatcher);
        etRegisterCheckPassword.addTextChangedListener(myTextWatcher);
        etRegisterPassword.addTextChangedListener(myTextWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            Tools.showLogE("requestCode：" + requestCode + "，resultCode：" + resultCode);
            switch (requestCode) {
                case Constants.IMAGE_REQUEST_CODE:
                    /** 相册里的相片 */
                    startPhotoZoom(data.getData());
                    break;

                case Constants.CAMERA_REQUEST_CODE:
                    /** 相机拍摄的相片 */
                    if (Tools.hasSdcard()) {
                        startPhotoZoom(mUploadImageUri);
                    } else {
                        Tools.showToast(this, "未找到存储卡，无法存储照片");
                    }
                    break;
                case Constants.RESULT_REQUEST_CODE:
                    /** 保存并设置头像 */
                    if (data != null) {
                        saveClippedImage(data);
                    }
                    break;
                default:
                    SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
                    break;
            }
        } else {
            SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
        }
    }

    private void saveClippedImage(Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            String imagePath = Tools.saveBitmap(photo, this.getFilesDir() + "clippedImage.jpeg");
            if (!TextUtils.isEmpty(imagePath)) {
                uploadIcon(imagePath, photo);
            } else {
                Tools.showToast(this, "保存剪裁图片失败");
            }
        }
    }

    public void btnWeiboLogin() {
        try {
            Tools.showLogE("到这了");
            SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo, "https://leancloud.cn/1.1/sns/goto/yrb88s03u2emd8ye");
            Tools.showLogE("到这里了");
            SNS.loginWithCallback(this, SNSType.AVOSCloudSNSSinaWeibo, new SNSCallback() {
                @Override
                public void done(SNSBase snsBase, SNSException e) {
                    if (e == null) {
                        JSONObject data = snsBase.authorizedData();
                        if (data != null) {
                            Tools.showLogE(data.toString());
                            try {
                                JSONObject user_info = data.getJSONObject("user_info");
                                final String name = user_info.getString("name");
                                final String avatar_hd = user_info.getString("avatar_hd");
                                SNS.loginWithAuthData(snsBase.userInfo(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser avUser, AVException e) {
                                        if (e == null) {
                                            if (!TextUtils.isEmpty(name)) {
                                                avUser.put("nickname",name);
                                            }else {
                                                avUser.put("nickname",avUser.getUsername());
                                            }
                                            if (!TextUtils.isEmpty(avatar_hd)) {
                                                avUser.put("userIcon",avatar_hd);
                                            }
                                            avUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e == null) {
                                                        checkCurrentStatus(true);
                                                    }else {
                                                        Tools.leanCloudExceptionHadling(getApplicationContext(),e);
                                                    }
                                                }
                                            });
                                        }else {
                                            Tools.leanCloudExceptionHadling(getApplicationContext(),e);
                                        }
                                    }
                                });
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.civ_icon, R.id.btn_submit, R.id.btn_pageType, R.id.btn_login_weibo, R.id.btn_back, R.id.btn_user_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_icon://头像(注册时可上传，登录时不可上传隐藏右上角图标)
                if (mIsRegister && !mIsUser) {
                    chooseUserIcon();
                }
                break;
            case R.id.btn_submit://提交表单(登录或注册)
                submit();
                break;
            case R.id.btn_pageType://跳转注册or登录
                changePageType(!mIsRegister);
                break;
            case R.id.btn_login_weibo://第三方登陆
                btnWeiboLogin();
                break;
            case R.id.btn_back://回退
                finish();
                break;
            case R.id.btn_user_logout://退出登录
                SNS.logout(this,SNSType.AVOSCloudSNSSinaWeibo);
                AVUser.logOut();
                if (AVUser.getCurrentUser() == null) {
                    mIsUser = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(activity, new Fade());
                    }
                    tvUserNickname.setVisibility(View.GONE);
                    btnUserLogout.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnPageType.setVisibility(View.VISIBLE);
                    btnLoginWeibo.setVisibility(View.VISIBLE);
                    llLoginOtherTitle.setVisibility(View.VISIBLE);
                    changePageType(false);
                    Glide.with(getApplicationContext()).load(R.mipmap.default_placeholder).crossFade().into(civIcon);
                } else {
                    Tools.showToast(this, "登出失败(可能是服务器炸了吧)");
                }
                break;
        }
    }

    /**
     * 根据是注册/登录，执行相关操作
     */
    private void submit() {
        if (mIsRegister) {//注册
            String nickname = etRegisterNickname.getText().toString().trim();
            if (TextUtils.isEmpty(nickname)) {
                Tools.showToast(this, "昵称为空");
                return;
            }
            String userName = etRegisterUserName.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                Tools.showToast(this, "账号为空");
                return;
            }
            String password = etRegisterPassword.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                Tools.showToast(this, "密码为空");
                return;
            }
            String checkPassword = etRegisterCheckPassword.getText().toString().trim();
            if (TextUtils.isEmpty(checkPassword)) {
                Tools.showToast(this, "确认密码为空");
                return;
            }
            if (!password.equals(checkPassword)) {
                Tools.showToast(this, "两次密码不一致");
                return;
            }

            if (TextUtils.isEmpty(mNetImageUri)) {
                Tools.showToast(this, "请上传头像");
                return;
            }

            AVUser avUser = new AVUser();
            avUser.setUsername(userName);
            avUser.setPassword(password);
            avUser.put("nickname", nickname);
            avUser.put("userIcon", mNetImageUri);
            avUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {//注册成功
                        Tools.showToast(getApplicationContext(), "注册成功");
                        checkCurrentStatus(false);
                    } else {//注册失败，根据code弹对应提示
                        Tools.leanCloudExceptionHadling(UserActivity.this, e);
                    }
                }
            });
        } else {//登录
            AVUser.logInInBackground(etLoginUserName.getText().toString(), etLoginPassword.getText().toString(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        checkCurrentStatus(false);
                    } else {
                        Tools.leanCloudExceptionHadling(getApplicationContext(), e);
                    }
                }
            });
        }
    }

    /**
     * 改变页面类型(注册/登录)
     *
     * @param isRegister
     */
    private void changePageType(boolean isRegister) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(activity, new Fade());
        }
        etLoginUserName.setText("");
        etLoginPassword.setText("");
        etRegisterNickname.setText("");
        etRegisterUserName.setText("");
        etRegisterPassword.setText("");
        etRegisterCheckPassword.setText("");
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
        checkCanSubmit();
    }

    /**
     * 选择上传头像所用图片
     */
    private void chooseUserIcon() {
        SelectPopUtil popWindow = new SelectPopUtil(this);
        popWindow.showPopupWindow("拍照", "相册选择");
        popWindow.setListener(new SelectPopUtil.SelectPopListener() {
            @Override
            public void send(String flag) {
                if (flag.equals("0")) { //拍照
                    /** 打开相机拍照 */
                    mUploadImageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir(), "GUNDAM_Database_" + System.currentTimeMillis() + ".jpg"));
                    Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUploadImageUri);
                    startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE);
                }
                if (flag.equals("1")) { //本地
                    /** 打开相册选择图片 */
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constants.IMAGE_REQUEST_CODE);
                }
            }
        });

    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("circleCrop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constants.RESULT_REQUEST_CODE);
    }

    /**
     * 上传图片
     *
     * @param filePath
     */
    private void uploadIcon(String filePath, final Bitmap photo) {
        if (filePath != null) {
            int lastIndexOf = filePath.lastIndexOf(".");
            if (lastIndexOf > 0) {
                String suffix = filePath.substring(lastIndexOf, filePath.length());
                try {
                    final AVFile avFile = AVFile.withAbsoluteLocalPath("ICON_" + Tools.getAndroidIMEI(this) + suffix, filePath);
                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            civIconMask.setVisibility(View.GONE);
                            tvIconProgress.setVisibility(View.GONE);

                            if (e == null && !TextUtils.isEmpty(avFile.getUrl())) {
                                ivIconType.setImageResource(R.mipmap.camera_true);
                                mNetImageUri = avFile.getUrl();
                                LogUtil.log.e("图片地址：" + mNetImageUri);
                                civIcon.setImageBitmap(photo);
                            } else {
                                ivIconType.setImageResource(R.mipmap.camera_false);
                                Tools.showToast(UserActivity.this, "头像上传失败");
                            }
                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer integer) {//上传进度
                            Tools.showLogE("upload进度值：" + integer);
                            if (integer > 0 && integer < 100) {
                                civIconMask.setVisibility(View.VISIBLE);
                                tvIconProgress.setVisibility(View.VISIBLE);
                                tvIconProgress.setText(integer + "%");
                            } else {
                                civIconMask.setVisibility(View.GONE);
                                tvIconProgress.setVisibility(View.GONE);
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Tools.showToast(this, "所选图片地址异常");
            }

        } else {
            Tools.showToast(this, "获取图片地址失败");
        }
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            View lineView = null;
            switch (v.getId()) {
                case R.id.et_login_phone:
                    lineView = vLoginPhone;
                    break;
                case R.id.et_login_password:
                    lineView = vLoginPassword;
                    break;
                case R.id.et_register_userName:
                    lineView = vRegisterUserName;
                    break;
                case R.id.et_register_checkPassword:
                    lineView = vRegisterPhone;
                    break;
                case R.id.et_register_password:
                    lineView = vRegisterPassword;
                    break;
                case R.id.et_register_nickname:
                    lineView = vRegisterNickname;
                    break;
            }
            if (lineView != null) {
                if (hasFocus) {//获得焦点
                    lineView.setBackgroundResource(R.color.colorPrimary);
                } else {//失去焦点
                    lineView.setBackgroundResource(R.color.login_gray_light);
                }
            }
        }
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = s.length();
            if (getCurrentFocus() != null) {
                switch (getCurrentFocus().getId()) {
                    case R.id.et_login_phone:
                        mLoginPhoneLength = length;
                        break;
                    case R.id.et_login_password:
                        mLoginPasswordLength = length;
                        break;
                    case R.id.et_register_userName:
                        mRegisterUserNameLength = length;
                        break;
                    case R.id.et_register_checkPassword:
                        mRegisterPhoneLength = length;
                        break;
                    case R.id.et_register_password:
                        mRegisterPasswordLength = length;
                        break;
                }
            }
            checkCanSubmit();

        }
    }

    /**
     * 检查提交按钮是否应该可以点击
     */
    private void checkCanSubmit() {
        if (mIsRegister) {
            if (mRegisterUserNameLength > 0 && mRegisterPhoneLength > 0 && mRegisterPasswordLength > 0) {
                btnSubmit.setEnabled(true);
            } else {
                btnSubmit.setEnabled(false);
            }
        } else {
            if (mLoginPhoneLength > 0 && mLoginPasswordLength > 0) {
                btnSubmit.setEnabled(true);
            } else {
                btnSubmit.setEnabled(false);
            }
        }
    }
}
