package com.example.myapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @auther yuwenming
 * @createTime：2023/5/17 18:11
 * @desc:
 **/
public class MoneyTextWatcher implements TextWatcher {
    private EditText editText;
    private int digits = 2;

    public MoneyTextWatcher(EditText et) {
        editText = et;
    }
    public MoneyTextWatcher setDigits(int d) {
        digits = d;
        return this;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + digits+1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}