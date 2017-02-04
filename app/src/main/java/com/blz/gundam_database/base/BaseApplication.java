package com.blz.gundam_database.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.blz.gundam_database.utils.DBUtils;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class BaseApplication extends Application {

    public static BaseApplication sBaseApplication;
    private Typeface mTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = (BaseApplication) getApplicationContext();
        DBUtils.initialize(this);
        AVOSCloud.initialize(this, "f3kcniWbbAvyxysaypyeCkjV-gzGzoHsz", "LAx4i7JNDiv7kcNxTP4pypMO");
        AVAnalytics.enableCrashReport(this, true);
        mTypeface = Typeface.createFromAsset(sBaseApplication.getAssets(), "PingFangRegular.ttf");
    }

    public static BaseApplication getInstance() {
        return sBaseApplication;
    }

    public Typeface getCustomTypeface() {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "PingFangRegular.ttf");
        }
        return mTypeface;
    }

    public void modifyCustomTypeface(Typeface typeface) {
        mTypeface = typeface;
    }
}
