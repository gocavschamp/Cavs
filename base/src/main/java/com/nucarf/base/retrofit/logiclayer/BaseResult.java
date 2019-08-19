package com.nucarf.base.retrofit.logiclayer;


import com.nucarf.base.R;
import com.nucarf.base.retrofit.LoginEvent;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public class BaseResult<T> {

    private String errorCode;
    private String errorMsg;
    private String code;
    private T result;
    private Object post;
    private Object message;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Object getPost() {
        return post;
    }

    public void setPost(Object post) {
        this.post = post;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessed() {
        try {
            if (code.equals(RetrofitConfig.STATUS_NCARF_SUCCESS)) {
                if (getMessage() instanceof MessageBean) {
                    //第一次成功登陆时返回数据
                    MessageBean messageBean = (MessageBean) getMessage();
                    SharePreUtils.setIsNewUser(BaseAppCache.getContext(), !messageBean.getNew_user().equals("0"));
                }
                return true;
            } else if (code.equals(RetrofitConfig.STATUS_GOTOLOGIN)) {
                SharePreUtils.setjwt_token(BaseAppCache.getContext(), "");
                EventBus.getDefault().post(new LoginEvent());
                return false;
            } else if (code.equals(RetrofitConfig.STATUS_NO_EXITS)) {
                return false;
            }else if (code.equals(RetrofitConfig.STATUS_COMPANY_OR_ID_ERROR)) {
                return false;
            } else {
                ToastUtils.show_middle_pic(R.mipmap.ic_launcher, getMessage() instanceof String ? getMessage() + "" : "", 0);

                if (code.equals("1")) {
                    if (getMessage() instanceof String) {
                        String message = (String) getMessage();
                        if (message.equals("token_invalid")) {
                            LogUtils.e("base result" + "token_invalid");
                            SharePreUtils.setjwt_token(BaseAppCache.getContext(), "");
                            EventBus.getDefault().post(new LoginEvent());
                        }
                    }
                }
                return false;
            }
        } catch (Exception e) {
            ToastUtils.show_middle_pic(R.mipmap.ic_launcher, "网络错误", 0);

            return false;
        }
    }


}
