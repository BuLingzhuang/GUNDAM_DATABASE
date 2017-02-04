package com.blz.gundam_database.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blz.gundam_database.R;

/**
 * Created by bulingzhuang
 * on 16/4/13
 * E-mail:bulingzhuang@foxmail.com
 */
public class SelectPopUtil {

    public interface SelectPopListener {
        void send(String flag);
    }

    private SelectPopListener listener;

    public void setListener(SelectPopListener listener) {
        this.listener = listener;
    }

    private PopupWindow popupWindow;

    private Context mContext;

    public SelectPopUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 弹窗
     */
    public void showPopupWindow(@NonNull String text0, String text1) {
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View menuView = mLayoutInflater.inflate(R.layout.popwindow_sel_pop_list, null, true);
        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //添加淡入淡出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_window);
        //必须设置
        Drawable win_bg = ContextCompat.getDrawable(mContext, R.drawable.pop_shadow);
        popupWindow.setBackgroundDrawable(win_bg);

        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());

        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //弹出窗口消失方法
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });


        LinearLayout ll_pop_main = (LinearLayout) menuView.findViewById(R.id.ll_pop_main);
        LinearLayout ll_list_1 = (LinearLayout) menuView.findViewById(R.id.ll_list_1);
        TextView list_0 = (TextView) menuView.findViewById(R.id.tv_list_0);
        TextView list_1 = (TextView) menuView.findViewById(R.id.tv_list_1);
        TextView cancel = (TextView) menuView.findViewById(R.id.tv_cancel);

        Tools.changeFont(list_0, list_1, cancel);

        list_0.setText(text0);
        if (TextUtils.isEmpty(text1)) {
            ll_list_1.setVisibility(View.GONE);
        } else {
            ll_list_1.setVisibility(View.VISIBLE);
            list_1.setText(text1);
        }

        ll_pop_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        list_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.send("0"); //0第一条事件  1第二条事件
                popupWindow.dismiss();
            }
        });

        list_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.send("1"); //0第一条事件  1第二条事件
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        View rootView = ((ViewGroup) (((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0); //设置窗口显示位置

        popupWindow.update();

    }


    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {

        }

    }
}
