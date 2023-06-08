package com.example.myapp.homepage.homedemo.zxing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.moonlight.flyvideo.R;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.instagram.InsGallery;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.GlideEngine;
import com.nucarf.base.utils.ImageUtil;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.ScreenUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

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
    @BindView(R.id.bt_go_lauch)
    Button btGoLauch;
    @BindView(R.id.iv_show)
    ImageView ivShow;

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
    public void onEvent(Object event) {
        if (event instanceof QRCodeEvent) {
            QRCodeEvent qrevent = (QRCodeEvent) event;
            if (qrevent.getText().startsWith("QRCODE:")) {
                String replaceFirst = qrevent.getText().replaceFirst("QRCODE:", "");
                etName.setText(replaceFirst);
                ivShow.setImageBitmap(qrevent.getBitmap());
                ImageUtil.saveBitmapToAlbum(mContext, qrevent.getBitmap(), 60, true);


            }
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

    @OnClick({R.id.bt_scanner, R.id.bt_go_lauch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_go_lauch:
//                WebActivity.lauch(mContext, "访问链接", etName.getText().toString());
//                跳转浏览器访问
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(etName.getText().toString() + "");
                    intent.setData(content_url);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
                break;
            case R.id.bt_scanner:
                checkPermissions();

                break;
        }

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
                            //insgallery  相册
//                            InsGallery.openGallery(mContext, GlideEngine.createGlideEngine(), new OnResultCallbackListenerImpl(mAdapter));
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

    private static final String TAG = "image--";
    private static class OnResultCallbackListenerImpl implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapter;

        public OnResultCallbackListenerImpl(GridImageAdapter adapter) {
            mAdapter = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(TAG, "Size: " + media.getSize());
            }
            GridImageAdapter adapter = mAdapter.get();
            if (adapter != null) {
                adapter.setList(result);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }
    /**
     * @author：luck
     * @date：2020-01-13 17:58
     * @describe：长按事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v);
    }
}
