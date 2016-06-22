package com.blz.gundam_database.base;

import android.app.Application;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.blz.gundam_database.utils.DBUtils;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DBUtils.initialize(this);
        AVOSCloud.initialize(this,"f3kcniWbbAvyxysaypyeCkjV-gzGzoHsz","LAx4i7JNDiv7kcNxTP4pypMO");
        AVAnalytics.enableCrashReport(this, true);
    }
}
