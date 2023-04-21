package com.example.myapp.api;

import com.example.myapp.bean.ArticleBean;
import com.example.myapp.bean.ArticleListBean;
import com.example.myapp.bean.StringBean;
import com.example.myapp.homepage.homedemo.videolist.VideoListData;
import com.nucarf.base.retrofit.logiclayer.BaseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
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

    @GET("article/list/{page}/json")
//玩安卓api
    Observable<BaseResult<ArticleBean>> getArticleList(@Path("page") int page);

    //https://haokan.baidu.com/videoui/api/videorec?tab=yingshi&act=pcFeed&pd=pc&num=5&shuaxin_id=1577413362081
    @GET
//好看视频
    Observable<BaseResult<VideoListData>> getVideoList(@Url String url);

//    https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=9933924977310a55c471d6f287756f91/314e251f95cad1c8c983f49d713e6709c93d514b.jpg

    //使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    @GET
    @Streaming
    Flowable<ResponseBody> downloadFile(@Url String url);  //返回值使用 ResponseBody 之后会对ResponseBody 进行读取


}
