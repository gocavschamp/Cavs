package com.nucarf.base.retrofit.logiclayer;

import com.nucarf.base.utils.ToastUtils;

/**
 * Creator: kakaluote.
 * Email  : kakaluote.com
 */
public class BaseResultOld<T> {




    private T info;
    private String result;
    private String error;




    public T getData() {
        return info;
    }

    public void setData(T data) {
        this.info = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccessed() {
        try {
            if (result.equals("succ")) {
                return true;
            } else {
                ToastUtils.showShort( getError() + "");
                return false;
            }
        } catch (Exception e) {
            ToastUtils.showShort( "网络错误");
            return false;
        }
    }



}
