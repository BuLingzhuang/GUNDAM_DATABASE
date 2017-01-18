package com.blz.gundam_database.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.blz.gundam_database.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class Tools {

    public static void showLogE(String str) {
        if (Constants.DEBUG) {
            Log.e("blz", str);
        }
    }

    public static void showSnackBar(Context context, String text, View genView) {
        Snackbar snackbar = Snackbar.make(genView, text, Snackbar.LENGTH_LONG);
        //白底的SnackBar样式的方法
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(getColor(context, R.color.colorPrimary));
        ((TextView) snackBarLayout.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String str, int time) {
        Toast.makeText(context, str, time).show();
    }

    public static int getColor(Context context, int RColor) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(RColor, null);
        } else {
            color = context.getResources().getColor(RColor);
        }
        return color;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void save2SDCard(final Context context, final Handler handler, final String imageUrl, final String originalName, final int currentItem) {
        if (imageUrl != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    try {
                        URL m_url = new URL(imageUrl);
                        HttpURLConnection con = (HttpURLConnection) m_url.openConnection();
                        InputStream is = con.getInputStream();
                        File directory = Environment.getExternalStorageDirectory();
                        File file = new File(directory, originalName + "_" + (currentItem + 1) + ".jpg");
                        boolean b = file.createNewFile();
                        FileOutputStream fos;
                        if (b) {
                            fos = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len;
                            while ((len = is.read(bytes)) != -1) {
                                fos.write(bytes, 0, len);
                            }
                            fos.close();
                            is.close();
                            message.obj =  context.getString(R.string.tools_save_success)+ file.getAbsolutePath();
                            AVAnalytics.onEvent(context, context.getString(R.string.tools_download) + originalName);
                        } else {
                            message.obj = context.getString(R.string.tools_saved) + file.getAbsolutePath();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        message.obj = context.getString(R.string.tools_save_failed);
                    }
                    handler.sendMessage(message);
                }
            }).start();
        }
    }
}
