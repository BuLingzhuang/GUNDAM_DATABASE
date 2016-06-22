package com.blz.gundam_database.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blz.gundam_database.R;

/**
 * Created by BuLingzhuang
 * on 2016/5/23
 * E-mail bulingzhuang@foxmail.com
 */
public class Tools {

    public static void showLogE(Object obj, String str) {
        if (Constants.DEBUG) {
            Log.e(obj.getClass().getSimpleName(), str);
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

    public static void showToast(Context context,String str){
        showToast(context,str,Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context,String str,int time){
        Toast.makeText(context,str,time).show();
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
}
