package com.nucarf.base.retrofit;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public class RetrofitConfig {

    public static final String ERROR_NETWORK = "net_error";
    public static final String ERROR_PARSE = "parse_error";
    public static final String STATUS_SUCCESS = "200";
    public static final String STATUS_NCARF_SUCCESS = "0";
    public static final String STATUS_NCARF_ERROR = "1";
    //授权过期，需要登录
    public static final String STATUS_GOTOLOGIN = "401";
    public static final String STATUS_NO_EXITS = "404";


    public final static int HTTP_CONNECT_TIMEOUT = 1000 * 10;
    public final static int HTTP_READ_TIMEOUT = 1000 * 10;
    public final static int HTTP_WRITE_TIMEOUT = 1000 * 10;
    public final static int RESPONSE_CACHE_SIZE = 1024 * 1024 * 12;

    //服务器
    //测试 http://123.57.0.118:5000/
    //正式 http://api.startvshow.com/
    public static final String TEST_HOST_URL = "http://tapi.nucarf.cn//";
    /**
     * 測試：'https://twechat.nucarf.cn'
     * 正式：'https://wechat.nucarf.com';
     */
    public static final String TEST_WECHAT_URL = "https://twechat.nucarf.cn";
    public static final String WECHAT_URL = "https://wechat.nucarf.com";
//    public static final String TEST_HOST_URL = AppUtils.getApplicationInfoMateData(BaseAppCache.getContext(), "TEST_HOST_URL");

    public static final String TEMP_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/starshow/";


//    public static final boolean isTest = AppUtils.getApplicationInfoMateDataBoolean(BaseAppCache.getContext(), "APP_IS_TEST");
    public static final boolean isTest =true;
    public static final String CODE_LOGIN_STR = isTest ? "2dd5183143e74b1a" : "2dd5183143e74b1a";
    public static final String PWD_LOGIN_STR = isTest ? "880e96ce5cda3ea607bf11184825ce6d" : "vB8SAqViGxYtUomFZpfF3NnQAe43Kt";

    //极光推送 自己的flyvideo  app_key 502b8e17dc24899f6d66105a
    public static final String CODE_JPUSH_STR = "502b8e17dc24899f6d66105a";

    /**
    //微信 apiSecret 995f6ee44a0b28ef026f0bfca0069c97
     *
     微信 appid wx8d519b866f73fcbc
     */
    public static final String WX_APPID = "wx8d519b866f73fcbc111";
    public static final String WX_APISECRET = "995f6ee44a0b28ef026f0bfca0069c97111";

    //友盟 AppKey：
    //5c19c610b465f56e03000259
    public static final String UM_APPKEY = "5c19c610b465f56e03000259111";

}
