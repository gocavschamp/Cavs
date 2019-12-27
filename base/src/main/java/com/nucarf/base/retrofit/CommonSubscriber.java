package com.nucarf.base.retrofit;

import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.JsonParseException;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.retrofit.logiclayer.BaseResult;
import com.nucarf.base.utils.LogUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import retrofit2.HttpException;


/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> implements Observer<T> {
    /**
     * 对应HTTP的状态码
     */
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int CONFLICT = 409;
    private static final int PRECONDITION_FAILED = 412;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    /**
     * 服务器定义的状态吗
     * 比如：登录过期，提醒用户重新登录；
     *      添加商品，但是服务端发现库存不足，这个时候接口请求成功，服务端定义业务层失败，服务端给出提示语，客户端进行吐司
     *      请求接口，参数异常或者类型错误，请求code为200成功状态，不过给出提示，这个时候客户端用log打印服务端给出的提示语，方便快递查找问题
     *      其他情况，接口请求成功，但是服务端定义业务层需要吐司服务端返回的对应提示语
     */
    /**
     * 完全成功
     */
    private static final int CODE_SUCCESS = 0;
    /**
     * Token 失效
     */
    public static final int CODE_TOKEN_INVALID = 401;
    /**
     * 缺少参数
     */
    public static final int CODE_NO_MISSING_PARAMETER = 400400;
    /**
     * 其他情况
     */
    public static final int CODE_NO_OTHER = 403;
    /**
     * 统一提示
     */
    public static final int CODE_SHOW_TOAST = 400000;


    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = false;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        Log.d("tag", "apply: " + e);
        if (mView == null) {
            return;
        }
        BaseResult ex = new BaseResult();
        //HTTP错误   网络请求异常 比如常见404 500之类的等
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case CODE_TOKEN_INVALID:
                    ex.setErrorMsg("重新登陆");
                    break;
                case CODE_NO_OTHER:
                    ex.setErrorMsg("其他情况");
                    break;
                case CODE_SHOW_TOAST:
                    ex.setErrorMsg("吐司");
                    break;
                case CODE_NO_MISSING_PARAMETER:
                    ex.setErrorMsg("缺少参数");
                    break;
                case BAD_REQUEST:
                case NOT_FOUND:
                case METHOD_NOT_ALLOWED:
                case REQUEST_TIMEOUT:
                case CONFLICT:
                case PRECONDITION_FAILED:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    //均视为网络错误
                default:
                    ex.setErrorMsg("网络错误" + httpException.code());
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            ex.setErrorMsg("解析错误");
        } else if (e instanceof ConnectException) {
            //均视为网络错误
            ex.setErrorMsg("连接失败");
        } else if (e instanceof java.net.UnknownHostException) {
            //网络未连接
            ex.setErrorMsg("网络未连接");
        } else if (e instanceof SocketTimeoutException) {
            //网络未连接
            ex.setErrorMsg("服务器响应超时");
        } else {
            //未知错误
            ex.setErrorMsg("未知错误");
            String displayMessage = ex.getErrorMsg();
            Log.d("<----TAG---->", "onError: " + e.getMessage());
        }
//        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
//            mView.onNetError(1, mErrorMsg);
//        } else if (e instanceof ApiException) {
//            mView.onNetError(1, ex.getErrorMsg());
//        } else if (e instanceof HttpException) {
//            mView.onNetError(1, ex.getErrorMsg());
//        } else {
//            mView.onNetError(1, ex.getErrorMsg());
//        }

        if (isShowErrorState) {
            mView.onNetError(0, ex.getErrorMsg());
        }
    }
}
