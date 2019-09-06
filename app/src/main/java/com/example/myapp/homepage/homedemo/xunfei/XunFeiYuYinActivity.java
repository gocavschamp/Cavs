package com.example.myapp.homepage.homedemo.xunfei;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.myapp.R;
import com.example.myapp.bean.ArticleListBean;
import com.example.myapp.database.greenDao.db.DaoSession;
import com.example.myapp.mvp.BaseMvpActivity;
import com.google.gson.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.nucarf.base.mvp.BaseView;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.ToastUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class XunFeiYuYinActivity extends BaseMvpActivity<XunFeiPresenter> implements XunFeiCotract.View, PoiSearch.OnPoiSearchListener {

    private String TAG = XunFeiYuYinActivity.this.getClass().getSimpleName();
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.et_result)
    EditText etResult;
    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    private int currentPage;
    private PoiSearch.Query query1;
    private PoiSearch.Query query2;
    //    private PoiSearch poiSearch;
    private List<Tip> mTipList = new ArrayList<>();
    //    private PoiResult poiResult;
    private ArrayList<PoiItem> poiItems;
    private PoiSearch poiSearch1;
    private PoiSearch poiSearch2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xun_fei_yu_yin);
        ButterKnife.bind(this);
// 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        getPemision();
    }

    private void getPemision() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                ToastUtils.showShort("初始化失败，错误码：" + code);
            }
        }
    };

    @Override
    public void onSucess() {

    }

    @Override
    public void onReLoad() {

    }

    @OnClick({R.id.tv_start, R.id.tv_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startListen();
                break;
            case R.id.tv_stop:
                stopListen();
                break;
        }
    }

    private void startListen() {
// 设置参数
        setParam();
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            LogUtils.e("创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化");
            return;
        }
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
//设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//设置结果返回语言
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
//取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
//设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
//自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
//设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");
// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
//开始识别，并设置监听器
        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
        etResult.setText("");
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "recognizer result：" + results.getResultString());

            String text = com.nucarf.base.utils.JsonParser.parseIatResult(results.getResultString());
            etResult.append("_" + text);
            etResult.setSelection(etResult.length());

            if (isLast && !TextUtils.isEmpty(etResult.getText().toString())) {
                checkLocation(etResult.getText().toString());
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtils.showShort(error.getPlainDescription(true));
        }

    };
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            ToastUtils.showShort("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            ToastUtils.showShort(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            ToastUtils.showShort("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "recognizer result：" + results.getResultString());

            String text = com.nucarf.base.utils.JsonParser.parseIatResult(results.getResultString());
            etResult.append("_" + text);
            etResult.setSelection(etResult.length());

            if (isLast && !TextUtils.isEmpty(etResult.getText().toString())) {
                checkLocation(etResult.getText().toString());
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            ToastUtils.showShort("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

    private void checkLocation(String text) {
        if (text.contains("到")) {
            doSearchQuery(text);
            LogUtils.e("搜索___路线:" + text);
        } else {
            LogUtils.e("搜索油站:" + text);
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord) {
        mTipList.clear();
        String[] strings = keyWord.split("到");
        if (strings.length == 2) {
            if (strings[0].equals(strings[1])) {
                ToastUtils.showShort("起终点不能相同！");
                return;
            }
            showLoading();
            currentPage = 0;
//        String type = "地名地址信息|汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|公共设施";
            String type = "地名地址信息";
            query1 = new PoiSearch.Query(strings[0], type, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query1.setPageSize(5);// 设置每页最多返回多少条poiitem
            query1.setPageNum(currentPage);// 设置查第一页
            poiSearch1 = new PoiSearch(this, query1);
            query2 = new PoiSearch.Query(strings[1], type, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query2.setPageSize(5);// 设置每页最多返回多少条poiitem
            query2.setPageNum(currentPage);// 设置查第一页
            poiSearch2 = new PoiSearch(this, query2);
            poiSearch1.setOnPoiSearchListener(this);
            poiSearch2.setOnPoiSearchListener(this);
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch1.searchPOIAsyn();// 异步搜索
            poiSearch2.searchPOIAsyn();// 异步搜索
        } else {
            closeLoading();
        }

    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) { // 搜索poi的结果
                if (result.getQuery().equals(query1)) { // 是否是同一条
                    ArrayList<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems.size() != 0 && poiItems.get(0).getLatLonPoint() != null) {
                        Tip tip = new Tip();
                        tip.setPostion(poiItems.get(0).getLatLonPoint());
                        tip.setName(poiItems.get(0).getTitle());
                        tip.setDistrict("" + poiItems.get(0).getTitle() + "(" + poiItems.get(0).getProvinceName() + ")");
                        mTipList.add(tip);
                        closeLoading();
                        if (mTipList.size() == 2) {
                            if (!mTipList.get(0).equals(tip)) {
                                Collections.reverse(mTipList);
                            }
                            LogUtils.e(mTipList.toString() + "");
                        }
                    } else {
                        ToastUtils.showShort("暂无相关搜索数据", 0);
                        closeLoading();
                    }
                } else if (result.getQuery().equals(query2)) {
                    ArrayList<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems.size() != 0 && poiItems.get(0).getLatLonPoint() != null) {
                        Tip tip = new Tip();
                        tip.setPostion(poiItems.get(0).getLatLonPoint());
                        tip.setName(poiItems.get(0).getTitle());
                        tip.setDistrict("" + poiItems.get(0).getTitle() + "(" + poiItems.get(0).getProvinceName() + ")");
                        mTipList.add(tip);
                        closeLoading();
                        if (mTipList.size() == 2) {
                            if (!mTipList.get(1).equals(tip)) {
                                Collections.reverse(mTipList);
                            }
                            LogUtils.e(mTipList.toString() + "");
                        }
                    } else {
                        ToastUtils.showShort("暂无相关搜索数据", 0);
                        closeLoading();
                    }
                } else {
                    ToastUtils.showShort("暂无相关搜索数据", 0);
                    closeLoading();
                }
            } else {
                ToastUtils.showShort("暂无相关搜索数据", 0);
                closeLoading();
            }
        } else {
            ToastUtils.showShort("暂无相关搜索数据", 0);
            closeLoading();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private void stopListen() {
        mIat.cancel();
    }

    @Override
    public void setData(boolean isRefresh, List<ArticleListBean> data, boolean isEnd) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isLoadingShow()) {
            closeLoading();
        }
    }
}
