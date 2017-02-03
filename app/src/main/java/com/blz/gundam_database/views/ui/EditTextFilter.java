package com.blz.gundam_database.views.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 创建日期：2016-10-17 15:46
 * 描    述：只能输入字母、数字
 * 修订历史：
 * ================================================
 */
public class EditTextFilter extends EditText {


    public EditTextFilter(Context context) {
        super(context);
        initFilter();
    }

    public EditTextFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFilter();
    }

    public EditTextFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFilter();
    }

    private void initFilter() {
        final EditText et = this;

        TextWatcher tw = new TextWatcher() {
            private int cou = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editable = et.getText().toString();
                String str = stringFilter(editable);
                if (!editable.equals(str)) {
                    et.setText(str);
                    et.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        this.addTextChangedListener(tw);
    }


    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

}
