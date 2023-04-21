package com.nucarf.base.retrofit;

import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.nucarf.base.utils.LogUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {

    private static Map<String, String> commonParams = new HashMap<>();

    public synchronized static void setCommonParam(Map<String, String> commonParams) {
        if (commonParams != null) {
            if (CommonInterceptor.commonParams != null) {
                CommonInterceptor.commonParams.clear();
            } else {
                CommonInterceptor.commonParams = new HashMap<>();
            }
            for (String paramKey : commonParams.keySet()) {
                String paramValue = commonParams.get(paramKey);
                if (!TextUtils.isEmpty(paramValue)) {
                    CommonInterceptor.commonParams.put(paramKey, paramValue);
                }
            }
        }
    }

    public synchronized static void updateOrInsertCommonParam(@NonNull String paramKey, @NonNull String paramValue) {
        if (commonParams == null) {
            commonParams = new HashMap<>();
        }
        commonParams.put(paramKey, paramValue);
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request request = rebuildRequest(chain.request());
//        request.newBuilder().addHeader("Cookie", "BAIDUID=FC91827F0FCBB5B3B92D53DCBA1E8E15:FG=1; BIDUPSID=FC91827F0FCBB5B35CB84F455F0B5988; PSTM=1681106886; BDUSS=2JiQlpNQTJaQnd3UDE0UHROd21HUGVadW1OSVpPdFFhOS03OTZ5QURNWjdubHhrRVFBQUFBJCQAAAAAAAAAAAEAAACi9LgszOyyxbTMv80AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHsRNWR7ETVkR; MCITY=-%3A; ZFY=Tgcmn:AhTO7GTZpLQ:BbLQ0v:Bm3wi1hB1pYT7aqbhyEdM:C; H_PS_PSSID=38515_36560_38469_38355_38468_36807_38486_37927_37709_38356_26350_38186; BDSFRCVID=o3kOJeC629rEWN6fbDrjhFuFY-zkwE7TH6aok1bxQR9bg0kDYkcsEG0PKf8g0KAbpnO3ogKK0mOTH6KF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJ-J_DPMfIL3fP36q4vfhtCLqxby26nytK39aJ5nJD_Bql55X6jt-x0XMx7l-x5kH6QWLhK5QpP-HJ7cDt5W0qj--xtjaM3k2K7eKl0MLpvtbb0xyUQDK-CgDUnMBMPjamOnaIQc3fAKftnOM46JehL3346-35543bRTLnLy5KJYMDcnK4-Xejj-jx5; delPer=1; PSINO=2; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BA_HECTOR=242h2ga404852laga020ak1t1i3v76g1n; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDRCVFR[gltLrB7qNCt]=mk3SLVN4HKm; RT=\"z=1&dm=baidu.com&si=44635159-01ac-47b1-9e8d-38cc9c054db9&ss=lgopt4hs&sl=5&tt=6ui&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&ld=xcs\"; reptileData=%7B%22data%22%3A%2234069879eb19090f116fe3036cba010e89558f354760bdad6933c91031078194ff10e838f8ae2a552aee7bad1e0a6994b8b2c53155fcd0baa5af2aa933df71f6b4a936bb0e2fd8ae0d52a64abc333298508bbf90e53ad4ae29fd7f8ded0e5b0a%22%2C%22key_id%22%3A%2230%22%2C%22sign%22%3A%2221024c8d%22%7D; ab_sr=1.0.1_NjRkODk1ZjJjNmE4ZWY2YTIxYWU5MDVjYmE3ZTM3YTc1YWQ3Y2QyMzMxNzM0NjEwNDkxOGJkODczYmY0NzdmZTUwNTExOTQ4MTViOGM3Yzg3YmMxY2E3MTBkMGEwNDU3YjM1NTRiOTIxZTYxMmM4MzgxZDE4ZjFhZDRmYjVmNTAxOWVmZDQ1OWIzMTIxOTlkZmM1MTc5YTQ2M2FmNjhmZA==; ariaDefaultTheme=undefined")
        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
            LogUtils.d("response: " + sbJson.toString());
        } catch (Exception e) {
            String msg = String.format("request error for %s", request.url().toString());
            LogUtils.e(msg, e);
        }
        return response;
    }


    public static byte[] toByteArray(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        InputStream inputStream = buffer.inputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bufferWrite = new byte[4096];
        int n;
        while (-1 != (n = inputStream.read(bufferWrite))) {
            output.write(bufferWrite, 0, n);
        }
        return output.toByteArray();
    }

    private Request rebuildRequest(Request request) throws IOException {
        LogUtils.d("conmont  params======: " + request.url().toString());
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            if (body!=null&&body.contentType()!=null&&body.contentType().type()!=null&&!body.contentType().type().contains("application/json")) {
                updateOrInsertCommonParam("signType", "1");
            }

        } else if (body instanceof MultipartBody) {
            updateOrInsertCommonParam("signType", "0");
            return request;
        } else {
            if (body!=null&&body.contentType()!=null&&body.contentType().type()!=null&&!body.contentType().type().contains("application/json")) {
                updateOrInsertCommonParam("signType", "1");
            }
        }
        if (commonParams.size() == 0) {
            return request;
        }
        if ("POST".equals(request.method())) {
            return rebuildPostRequest(request);
        } else if ("GET".equals(request.method())) {
            return rebuildGetRequest(request);
        } else {
            return request;
        }
    }

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * 对post请求添加统一参数
     */
    private Request rebuildPostRequest(Request request) {
        Map<String, String> signParams = new HashMap<>(); // 假设你的项目需要对参数进行签名
        RequestBody originalRequestBody = request.body();
        assert originalRequestBody != null;
        RequestBody newRequestBody;
        String timeStamp = String.valueOf(System.currentTimeMillis());
        if (originalRequestBody instanceof FormBody) { // 传统表单
            FormBody.Builder builder = new FormBody.Builder(UTF_8);
            FormBody requestBody = (FormBody) request.body();
            int fieldSize = requestBody == null ? 0 : requestBody.size();
            for (int i = 0; i < fieldSize; i++) {
                builder.add(requestBody.name(i), requestBody.value(i));
//                builder.addEncoded(requestBody.name(i), requestBody.value(i));
                signParams.put(requestBody.name(i), requestBody.value(i));
            }


            if (commonParams != null && commonParams.size() > 0) {
                for (String paramKey : commonParams.keySet()) {
                    String paramValue = commonParams.get(paramKey);
                    if (!TextUtils.isEmpty(paramValue)) {
                        signParams.put(paramKey, paramValue);
                        builder.add(paramKey, paramValue);
                    }
                }
            }
            // ToDo 此处可对参数做签名处理 signParams
            /*
             * String sign = SignUtil.sign(signParams);
             * builder.add("sign", sign);
             */
//            if ("1".equals(commonParams != null ? commonParams.get("signType") : null)) {
//                String sign = MD5Utils.getSign2(signParams, timeStamp);
//                builder.add("sign", sign);
//            }
//            if( signParams.isEmpty()) {
//                Map<String, String> signParams2 = new HashMap<>(); // 假设你的项目需要对参数进行签名
//                signParams2.put("userId", KvUtil.INSTANCE.decodeString(KvUtil.USER_ID));
//                signParams2   .put("tokenId", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID));
//                signParams2  .put("refreshToken", KvUtil.INSTANCE.decodeString(KvUtil.REFRESH_TOKEN));
//                signParams2.put("clientType", "2");
//                signParams2.put("devId", AndroidUtil.getDeviceId(BaseAppCache.getContext()));
//                signParams2.put("platform", "android");;
//                signParams2.put("sysVersion", AndroidUtil.getSystemVersion());
//                signParams2.put("version", String.valueOf(AndroidUtil.getVerName(BaseAppCache.getContext())));
//                signParams2.put("timeStamp", timeStamp);
//                signParams2.put("Content-Type", "application/json;text/json;text/html;application/x-www-form-urlencoded;charset=utf-8");
//                String sign2 = MD5Utils.getSign2(signParams2, timeStamp);
//                builder.add("signType","1");
//                builder.add("sign",sign2);
//            }
            newRequestBody = builder.build();
        } else if (originalRequestBody instanceof MultipartBody) { // 文件
            MultipartBody requestBody = (MultipartBody) request.body();
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder();
            if (requestBody != null) {
                for (int i = 0; i < requestBody.size(); i++) {
                    MultipartBody.Part part = requestBody.part(i);
                    multipartBodybuilder.addPart(part);

                    /*
                     上传文件时，请求方法接收的参数类型为RequestBody或MultipartBody.Part参见ApiService文件中uploadFile方法
                     RequestBody作为普通参数载体，封装了普通参数的value; MultipartBody.Part即可作为普通参数载体也可作为文件参数载体
                     当RequestBody作为参数传入时，框架内部仍然会做相关处理，进一步封装成MultipartBody.Part，因此在拦截器内部，
                     拦截的参数都是MultipartBody.Part类型
                     */

                    /*
                     1.若MultipartBody.Part作为文件参数载体传入，则构造MultipartBody.Part实例时，
                     需使用MultipartBody.Part.createFormData(String name, @Nullable String filename, RequestBody body)方法，
                     其中name参数可作为key使用(因为你可能一次上传多个文件，服务端可以此作为区分)且不能为null，
                     body参数封装了包括MimeType在内的文件信息，其实例创建方法为RequestBody.create(final @Nullable MediaType contentType, final File file)
                     MediaType获取方式如下：
                     String fileType = FileUtil.getMimeType(file.getAbsolutePath());
                     MediaType mediaType = MediaType.parse(fileType);
                     2.若MultipartBody.Part作为普通参数载体，建议使用MultipartBody.Part.createFormData(String name, String value)方法创建Part实例
                       name可作为key使用，name不能为null,通过这种方式创建的实例，其RequestBody属性的MediaType为null；当然也可以使用其他方法创建
                     */

                    /*
                      提取非文件参数时,以RequestBody的MediaType为判断依据.
                      此处提取方式简单暴力。默认part实例的RequestBody成员变量的MediaType为null时，part为非文件参数
                      前提是:
                      a.构造RequestBody实例参数时，将MediaType设置为null
                      b.构造MultipartBody.Part实例参数时,推荐使用MultipartBody.Part.createFormData(String name, String value)方法，或使用以下方法
                        b1.MultipartBody.Part.create(RequestBody body)
                        b2.MultipartBody.Part.create(@Nullable Headers headers, RequestBody body)
                        若使用方法b1或b2，则要求
                      备注：
                      您也可根据需求修改RequestBody的MediaType，但尽量保持外部传入参数的MediaType与拦截器内部添加参数的MediaType一致，方便统一处理
                     */

                    MediaType mediaType = part.body().contentType();
                    if (mediaType == null) {
                        String normalParamKey;
                        String normalParamValue;
                        try {
                            normalParamValue = getParamContent(requestBody.part(i).body());
                            Headers headers = part.headers();
                            if (!TextUtils.isEmpty(normalParamValue) && headers != null) {
                                for (String name : headers.names()) {
                                    String headerContent = headers.get(name);
                                    if (!TextUtils.isEmpty(headerContent)) {
                                        String[] normalParamKeyContainer = headerContent.split("name=\"");
                                        if (normalParamKeyContainer.length == 2) {
                                            normalParamKey = normalParamKeyContainer[1].split("\"")[0];
                                            signParams.put(normalParamKey, normalParamValue);
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            signParams.putAll(commonParams);
            for (String paramKey : commonParams.keySet()) {
                String paramValue = commonParams.get(paramKey);
                if (!TextUtils.isEmpty(paramValue)) {
                    // 两种方式添加公共参数
                    // method 1
                    multipartBodybuilder.addFormDataPart(paramKey, paramValue);
                    // method 2
//                    MultipartBody.Part part = MultipartBody.Part.createFormData(paramKey, commonParams.get(paramKey));
//                    multipartBodybuilder.addPart(part);
                }
            }
            // ToDo 此处可对参数做签名处理 signParams
            /*
             * String sign = SignUtil.sign(signParams);
             * multipartBodybuilder.addFormDataPart("sign", sign);
             */
            newRequestBody = multipartBodybuilder.build();
        } else {
            try {
                JSONObject jsonObject;
                if (originalRequestBody.contentLength() == 0) {
                    jsonObject = new JSONObject();
                } else {
                    jsonObject = new JSONObject(getParamContent(originalRequestBody));
                }
                for (String paramKey : commonParams.keySet()) {
                    String paramValue = commonParams.get(paramKey);
                    if (!TextUtils.isEmpty(paramValue)) {
                        jsonObject.put(paramKey, paramValue);
                    }
                }
                // ToDo 此处可对参数做签名处理
                /*
                 * String sign = SignUtil.sign(signParams);
                 * jsonObject.put("sign", sign);
                 */
//                if (!signParams.isEmpty()&&"1".equals(commonParams != null ? commonParams.get("signType") : null)) {
////                    String sign = MD5Utils.getSign2(signParams, timeStamp);
//                    String sign = MD5Utils.getSign2(signParams, timeStamp);
//                    jsonObject.put("sign", sign);
//                }
//                if( signParams.isEmpty()) {
//                    Map<String, String> signParams2 = new HashMap<>(); // 假设你的项目需要对参数进行签名
//                    signParams2.put("userId", KvUtil.INSTANCE.decodeString(KvUtil.USER_ID));
//                    signParams2   .put("tokenId", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID));
//                    signParams2  .put("refreshToken", KvUtil.INSTANCE.decodeString(KvUtil.REFRESH_TOKEN));
//                    signParams2.put("clientType", "2");
//                    signParams2.put("devId", AndroidUtil.getDeviceId(BaseAppCache.getContext()));
//                    signParams2.put("platform", "android");;
//                    signParams2.put("sysVersion", AndroidUtil.getSystemVersion());
//                    signParams2.put("version", String.valueOf(AndroidUtil.getVerName(BaseAppCache.getContext())));
//                    signParams2.put("timeStamp", timeStamp);
//                    signParams2.put("Content-Type", "application/json;text/json;text/html;application/x-www-form-urlencoded;charset=utf-8");
//                    String sign2 = MD5Utils.getSign2(signParams2, timeStamp);
//                    jsonObject.put("sign", sign2);
//                    jsonObject.put("signType", "1");
//                }
                newRequestBody = RequestBody.create( originalRequestBody.contentType(),jsonObject.toString());
//                newRequestBody = RequestBody.create(originalRequestBody.contentType(), jsonObject.toString());
                LogUtils.d(getParamContent(newRequestBody));

            } catch (Exception e) {
                newRequestBody = originalRequestBody;
                e.printStackTrace();
            }
        }

        Request.Builder builder = request.newBuilder();
        builder
//                .addHeader("userId", "1")//todo delete
//                .addHeader("userId", KvUtil.INSTANCE.decodeString(KvUtil.USER_ID))
//                .addHeader("tokenId", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID))
//                .addHeader("Authorization", KvUtil.INSTANCE.decodeString(KvUtil.ACCESS_TOKEN))
//                .addHeader("loginToken", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID))
//                .addHeader("refreshToken", KvUtil.INSTANCE.decodeString(KvUtil.REFRESH_TOKEN))
//                .addHeader("clientType", "2")
//                .addHeader("devId", AndroidUtil.getDeviceId(BaseAppCache.getContext()))
                .addHeader("platform", "android")
                .addHeader("Cookie", "BAIDUID=FC91827F0FCBB5B3B92D53DCBA1E8E15:FG=1; BIDUPSID=FC91827F0FCBB5B35CB84F455F0B5988; PSTM=1681106886; BDUSS=2JiQlpNQTJaQnd3UDE0UHROd21HUGVadW1OSVpPdFFhOS03OTZ5QURNWjdubHhrRVFBQUFBJCQAAAAAAAAAAAEAAACi9LgszOyyxbTMv80AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHsRNWR7ETVkR; MCITY=-%3A; ZFY=Tgcmn:AhTO7GTZpLQ:BbLQ0v:Bm3wi1hB1pYT7aqbhyEdM:C; H_PS_PSSID=38515_36560_38469_38355_38468_36807_38486_37927_37709_38356_26350_38186; BDSFRCVID=o3kOJeC629rEWN6fbDrjhFuFY-zkwE7TH6aok1bxQR9bg0kDYkcsEG0PKf8g0KAbpnO3ogKK0mOTH6KF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJ-J_DPMfIL3fP36q4vfhtCLqxby26nytK39aJ5nJD_Bql55X6jt-x0XMx7l-x5kH6QWLhK5QpP-HJ7cDt5W0qj--xtjaM3k2K7eKl0MLpvtbb0xyUQDK-CgDUnMBMPjamOnaIQc3fAKftnOM46JehL3346-35543bRTLnLy5KJYMDcnK4-Xejj-jx5; delPer=1; PSINO=2; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BA_HECTOR=242h2ga404852laga020ak1t1i3v76g1n; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDRCVFR[gltLrB7qNCt]=mk3SLVN4HKm; RT=\"z=1&dm=baidu.com&si=44635159-01ac-47b1-9e8d-38cc9c054db9&ss=lgopt4hs&sl=5&tt=6ui&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&ld=xcs\"; reptileData=%7B%22data%22%3A%2234069879eb19090f116fe3036cba010e89558f354760bdad6933c91031078194ff10e838f8ae2a552aee7bad1e0a6994b8b2c53155fcd0baa5af2aa933df71f6b4a936bb0e2fd8ae0d52a64abc333298508bbf90e53ad4ae29fd7f8ded0e5b0a%22%2C%22key_id%22%3A%2230%22%2C%22sign%22%3A%2221024c8d%22%7D; ab_sr=1.0.1_NjRkODk1ZjJjNmE4ZWY2YTIxYWU5MDVjYmE3ZTM3YTc1YWQ3Y2QyMzMxNzM0NjEwNDkxOGJkODczYmY0NzdmZTUwNTExOTQ4MTViOGM3Yzg3YmMxY2E3MTBkMGEwNDU3YjM1NTRiOTIxZTYxMmM4MzgxZDE4ZjFhZDRmYjVmNTAxOWVmZDQ1OWIzMTIxOTlkZmM1MTc5YTQ2M2FmNjhmZA==; ariaDefaultTheme=undefined")
//                .addHeader("sysVersion", AndroidUtil.getSystemVersion())
//                .addHeader("version", String.valueOf(AndroidUtil.getVerName(BaseAppCache.getContext())))
//                .addHeader("timeStamp", timeStamp)
                .addHeader("Content-Type", "application/json;text/json;text/html;application/x-www-form-urlencoded;charset=utf-8");
//
//        if( signParams.isEmpty()) {
//            Map<String, String> signParams2 = new HashMap<>(); // 假设你的项目需要对参数进行签名
//            signParams2.put("userId", KvUtil.INSTANCE.decodeString(KvUtil.USER_ID));
//            signParams2   .put("tokenId", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID));
//            signParams2  .put("refreshToken", KvUtil.INSTANCE.decodeString(KvUtil.REFRESH_TOKEN));
//            signParams2.put("clientType", "2");
//            signParams2.put("devId", AndroidUtil.getDeviceId(BaseAppCache.getContext()));
//            signParams2.put("platform", "android");;
//            signParams2.put("sysVersion", AndroidUtil.getSystemVersion());
//            signParams2.put("version", String.valueOf(AndroidUtil.getVerName(BaseAppCache.getContext())));
//            signParams2.put("timeStamp", timeStamp);
//            signParams2.put("Content-Type", "application/json;text/json;text/html;application/x-www-form-urlencoded;charset=utf-8");
//            String sign2 = MD5Utils.getSign2(signParams2, timeStamp);
//            builder.addHeader("signType","1");
//            builder.addHeader("sign",sign2);
//        }

//        可根据需求添加或修改header,此处制作示意
        return builder.method(request.method(), newRequestBody)
                .build();
//        return request.newBuilder().method(request.method(), newRequestBody).build();
    }

    /**
     * 获取常规post请求参数
     */
    private String getParamContent(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    /**
     * 对get请求做统一参数处理
     */
    private Request rebuildGetRequest(Request request) {
        String url = request.url().toString();
        int separatorIndex = url.lastIndexOf("?");
        StringBuilder sb = new StringBuilder(url);
        if (separatorIndex == -1) {
            sb.append("?");
        }
        for (String commonParamKey : commonParams.keySet()) {
            sb.append("&").append(commonParamKey).append("=").append(commonParams.get(commonParamKey));
        }
        Request.Builder requestBuilder = request.newBuilder();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        requestBuilder
//                .addHeader("userId", "1")//todo delete
//                .addHeader("userId", KvUtil.INSTANCE.decodeString(KvUtil.USER_ID))
//                .addHeader("tokenId", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID))
//                .addHeader("Authorization", KvUtil.INSTANCE.decodeString(KvUtil.ACCESS_TOKEN))
//                .addHeader("loginToken", KvUtil.INSTANCE.decodeString(KvUtil.TOKEN_ID))
//                .addHeader("refreshToken", KvUtil.INSTANCE.decodeString(KvUtil.REFRESH_TOKEN))
//                .addHeader("clientType", "2")
//                .addHeader("devId", AndroidUtil.getDeviceId(BaseAppCache.getContext()))
                .addHeader("platform", "android")
                .addHeader("Cookie", "BAIDUID=FC91827F0FCBB5B3B92D53DCBA1E8E15:FG=1; BIDUPSID=FC91827F0FCBB5B35CB84F455F0B5988; PSTM=1681106886; BDUSS=2JiQlpNQTJaQnd3UDE0UHROd21HUGVadW1OSVpPdFFhOS03OTZ5QURNWjdubHhrRVFBQUFBJCQAAAAAAAAAAAEAAACi9LgszOyyxbTMv80AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHsRNWR7ETVkR; MCITY=-%3A; ZFY=Tgcmn:AhTO7GTZpLQ:BbLQ0v:Bm3wi1hB1pYT7aqbhyEdM:C; H_PS_PSSID=38515_36560_38469_38355_38468_36807_38486_37927_37709_38356_26350_38186; BDSFRCVID=o3kOJeC629rEWN6fbDrjhFuFY-zkwE7TH6aok1bxQR9bg0kDYkcsEG0PKf8g0KAbpnO3ogKK0mOTH6KF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJ-J_DPMfIL3fP36q4vfhtCLqxby26nytK39aJ5nJD_Bql55X6jt-x0XMx7l-x5kH6QWLhK5QpP-HJ7cDt5W0qj--xtjaM3k2K7eKl0MLpvtbb0xyUQDK-CgDUnMBMPjamOnaIQc3fAKftnOM46JehL3346-35543bRTLnLy5KJYMDcnK4-Xejj-jx5; delPer=1; PSINO=2; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BA_HECTOR=242h2ga404852laga020ak1t1i3v76g1n; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDRCVFR[gltLrB7qNCt]=mk3SLVN4HKm; RT=\"z=1&dm=baidu.com&si=44635159-01ac-47b1-9e8d-38cc9c054db9&ss=lgopt4hs&sl=5&tt=6ui&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&ld=xcs\"; reptileData=%7B%22data%22%3A%2234069879eb19090f116fe3036cba010e89558f354760bdad6933c91031078194ff10e838f8ae2a552aee7bad1e0a6994b8b2c53155fcd0baa5af2aa933df71f6b4a936bb0e2fd8ae0d52a64abc333298508bbf90e53ad4ae29fd7f8ded0e5b0a%22%2C%22key_id%22%3A%2230%22%2C%22sign%22%3A%2221024c8d%22%7D; ab_sr=1.0.1_NjRkODk1ZjJjNmE4ZWY2YTIxYWU5MDVjYmE3ZTM3YTc1YWQ3Y2QyMzMxNzM0NjEwNDkxOGJkODczYmY0NzdmZTUwNTExOTQ4MTViOGM3Yzg3YmMxY2E3MTBkMGEwNDU3YjM1NTRiOTIxZTYxMmM4MzgxZDE4ZjFhZDRmYjVmNTAxOWVmZDQ1OWIzMTIxOTlkZmM1MTc5YTQ2M2FmNjhmZA==; ariaDefaultTheme=undefined")

//                .addHeader("sysVersion", AndroidUtil.getSystemVersion())
//                .addHeader("version", String.valueOf(AndroidUtil.getVerName(BaseAppCache.getContext())))
//                .addHeader("timeStamp", timeStamp)
                .addHeader("Content-Type", "application/json;text/json;text/html;application/x-www-form-urlencoded;charset=utf-8");
//
        return requestBuilder.url(sb.toString()).build();
    }
}