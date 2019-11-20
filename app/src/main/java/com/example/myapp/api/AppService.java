package com.example.myapp.api;

import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.ArticleListBean;
import com.example.myapp.bean.StringBean;
import com.nucarf.base.retrofit.logiclayer.BaseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface AppService {
    @FormUrlEncoded
    @POST("Sms/sendCaptcha")
    Call<BaseResult<Object>> getCode(@QueryMap Map<String, String> baseParam, @Field("mobile") String mobile, @Field("deviceId") String deviceId);


    @FormUrlEncoded
    @POST("Member/checkCode")
    Call<BaseResult<Object>> checkCode(@QueryMap Map<String, String> baseParam, @FieldMap Map<String, String> request);

    @FormUrlEncoded
    @POST("Member/appRegister")
    Call<BaseResult<Object>> appRegister(@QueryMap Map<String, String> baseParam, @FieldMap Map<String, String> request);

    @FormUrlEncoded
    @POST("Member/login")
    Call<BaseResult<StringBean>> loginByMsg(@QueryMap Map<String, String> baseParam, @FieldMap Map<String, String> request);

    @GET("article/list/{page}/json")//玩安卓api
    Observable<BaseResult<ArticleBean>> getArticleList(@Path("page") int page);

}
