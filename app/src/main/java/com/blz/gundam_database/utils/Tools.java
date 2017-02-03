package com.blz.gundam_database.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.blz.gundam_database.R;
import com.blz.gundam_database.views.activitys.UserActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 白底绿字SnackBar
     *
     * @param context
     * @param text
     * @param genView
     */
    public static void showSnackBar(Context context, String text, View genView) {
        Snackbar snackbar = Snackbar.make(genView, text, Snackbar.LENGTH_LONG);
        //白底的SnackBar样式的方法
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        ((TextView) snackBarLayout.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * 短时长Toast
     *
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    /**
     * 自定义时长Toast
     *
     * @param context
     * @param str
     * @param time
     */
    public static void showToast(Context context, String str, int time) {
        Toast.makeText(context, str, time).show();
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取默认保存文件夹(待完善)
     *
     * @return
     */
    public static File getDefaultFileDir() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/GUNDAM_DataBase/");
        if (!dir.exists()) {
            showLogE("没有文件夹，创建");
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 图片保存本地方法
     *
     * @param context
     * @param handler
     * @param imageUrl
     * @param originalName
     * @param currentItem
     */
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
                        File file = new File(getDefaultFileDir(), originalName + "_" + (currentItem + 1) + ".jpg");
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
                            message.obj = context.getString(R.string.tools_save_success) + file.getAbsolutePath();
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

    /**
     * 修改字体
     *
     * @param context
     * @param views
     */
    public static void changeFont(Context context, View... views) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "PingFangRegular.ttf");
        for (View view : views) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(tf);
            }
        }
    }

    /**
     * 遍历整个View树，修改文字(对外暴露方法)
     *
     * @param act
     */
    public static void changeFonts(Activity act) {
        ViewGroup viewGroup = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);

        changeFonts(viewGroup, act);
    }

    /**
     * 遍历整个View树，修改文字(递归向下查找)
     *
     * @param root
     * @param context
     */
    private static void changeFonts(ViewGroup root, Context context) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "PingFangRegular.ttf");

        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, context);
            }
        }

    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据Uri获取图片实际地址
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获取本机机器码
     *
     * @param context
     * @return
     */
    public static String getAndroidIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "未知机器码";
        }
        return deviceId;
    }

    /**
     * 把bitmap对象 进行jpeg格式压缩 并且输出到具体路径
     *
     * @param bitmap
     * @param path
     */
    public static String saveBitmap(Bitmap bitmap, String path) {
//		Log.i("info", "saveFile:" + path);
        File file = new File(path);
        // 若父目录不存在 则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                // 把bitmap输出到该文件中
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // 把bitmap输出到该文件中
        try {
            boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
            if (compress) {
                return path;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据返回错误码，弹对应提示
     *
     * @param context
     */
    public static void leanCloudExceptionHadling(Context context, AVException e) {
        int eCode = e.getCode();
        String eStr;
        switch (eCode) {
            case 202:
                eStr = context.getResources().getString(R.string.e_code_202);
                break;
            case 203:
                eStr = context.getResources().getString(R.string.e_code_203);
                break;
            case 205:
                eStr = context.getResources().getString(R.string.e_code_205);
                break;
            case 210:
                eStr = context.getResources().getString(R.string.e_code_210);
                break;
            case 211:
                eStr = context.getResources().getString(R.string.e_code_211);
                break;
            case 219:
                eStr = context.getResources().getString(R.string.e_code_219);
                break;
            default:
                eStr = e.getMessage();
                break;
        }
        Tools.showToast(context, eStr);
    }
}
