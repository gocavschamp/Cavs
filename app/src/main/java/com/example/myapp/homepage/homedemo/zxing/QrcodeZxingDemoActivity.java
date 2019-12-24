package com.example.myapp.homepage.homedemo.zxing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.ScreenUtil;
import com.nucarf.base.widget.TitleLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class QrcodeZxingDemoActivity extends BaseActivityWithTitle {

    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_scanner)
    Button btScanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_zxing_demo);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        titlelayout.setTitleText("QRCODE");
        creatQrCode("湖人总冠军！！！");
    }

    @Subscribe
    public void onEvent(String event) {
        if (event.startsWith("QRCODE:")) {
            String replaceFirst = event.replaceFirst("QRCODE:", "");
            etName.setText(replaceFirst);

        }
    }

    private void creatQrCode(String code_str) {//only_petrol
        if (code_str != null && !"".equals(code_str.trim())) {
            //根据输入的文本生成对应的二维码并且显示出来vOi
            Bitmap mBitmap = EncodingHandler.createQRCode(code_str, ScreenUtil.dip2px(150), ScreenUtil.dip2px(150), null);
            if (mBitmap != null) {
                ivCode.setImageBitmap(mBitmap);
            }
        } else {
            Toast.makeText(mContext, "文本信息不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.bt_scanner)
    public void onViewClicked() {
        checkPermissions();

    }

    @SuppressLint("CheckResult")
    private void checkPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //用户同意使用权限
                            CaptureActivity.lauch(mContext, "1");
                        } else {
                            //用户不同意使用权限
                            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                            Toast.makeText(mContext, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                            startActivity(intent);
                        }
                    }
                });
    }
}
