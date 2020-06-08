package com.example.myapp.homepage.homedemo.dialogshow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.example.myapp.login.view.LoginActivity;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.utils.DialogTools;
import com.nucarf.base.utils.DialogUtils;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.utils.ToastUtils;
import com.nucarf.base.utils.UiGoto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DialogShowActivity extends BaseActivity {

    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;
    private static Thread sRunnable;
    private Disposable mDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
//            sRunnable = new Thread() {
//                @Override
//                public void run() {
//                }
//            };
//        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        if (aLong == 2) {
//                            if (mDisposable != null && !mDisposable.isDisposed()) {
//                                mDisposable.dispose();
//                                if (!SharePreUtils.getjwt_token(mContext).equals("")) {
//                                    UiGoto.startAty(mContext, MainActivity.class);
//                                    //UiGoto.startAty(mContext, DBActivity.class);
//                                    finish();
//                                } else {
//                                    UiGoto.startAty(mContext, LoginActivity.class);
//                                    finish();
//                                }
//                            }
//                        }
//                    }
//                });
    }

    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:// 1btn
                DialogTools.showOneBtnDialog(mContext, "一个按钮","展示一个按钮的button" ,"确定" , new DialogTools.DialogClickListener() {
                    @Override
                    public void confirm() {
                        ToastUtils.show_middle(mContext,"点击确认",0);

                    }

                    @Override
                    public void cancel() {
                        ToastUtils.show_middle(mContext,"点击取消",0);

                    }

                    @Override
                    public void centerBtn() {
                        ToastUtils.show_middle(mContext,"点击中间",0);

                    }
                });
                break;
            case R.id.tv_2:// 2btn
                DialogTools.showTwoBtnDialog(mContext, "两个按钮","展示两个按钮的button" ,"确定" ,"取消", new DialogTools.DialogClickListener() {
                    @Override
                    public void confirm() {
                        ToastUtils.show_middle(mContext,"点击确认",0);

                    }

                    @Override
                    public void cancel() {
                        ToastUtils.show_middle(mContext,"点击取消",0);

                    }

                    @Override
                    public void centerBtn() {
                        ToastUtils.show_middle(mContext,"点击中间",0);

                    }
                });
                break;
            case R.id.tv_3: // 3btn
                DialogTools.showThreeBtnDialog(mContext, "3个按钮","展示3个按钮的button" ,"确定" ,"中间按钮" ,"取消" , new DialogTools.DialogClickListener() {
                    @Override
                    public void confirm() {
                        ToastUtils.show_middle(mContext,"点击确认",0);

                    }

                    @Override
                    public void cancel() {
                        ToastUtils.show_middle(mContext,"点击取消",0);

                    }

                    @Override
                    public void centerBtn() {
                        ToastUtils.show_middle(mContext,"点击中间",0);

                    }
                });
                break;
            case R.id.tv_4: //单选列表
                ArrayList<String> objects = new ArrayList<>();
                objects.add("北京");
                objects.add("上海");
                objects.add("广州");
                objects.add("深圳");
                objects.add("香港自治区");
                objects.add("澳门自治区");
                objects.add("武汉");
                objects.add("成都");
                DialogTools.showListDialog(mContext, "请选择城市", objects, new DialogTools.DialogItemClickListener() {
                    @Override
                    public void confirm(String result) {
                        ToastUtils.show_middle(mContext,result,0);
                    }
                });

                break;
            case R.id.tv_5: //自定义view
                View inflate = View.inflate(mContext, R.layout.dialog_custom, null);
                DialogTools.showCustomCenterDialog(mContext,inflate,true).show();
                break;
            case R.id.tv_6:
                break;
        }
    }
}
