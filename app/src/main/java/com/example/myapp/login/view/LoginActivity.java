package com.example.myapp.login.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.google.android.material.tabs.TabLayout;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.utils.KeyboardUtil;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.utils.UiGoto;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.switch_context)
    TextView switchContext;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.iv_pwd_phone_icon)
    ImageView ivPwdPhoneIcon;
    @BindView(R.id.et_pwd_phone)
    EditText etPwdPhone;
    @BindView(R.id.iv_clear_text_pwd_phone)
    ImageView ivClearTextPwdPhone;
    @BindView(R.id.iv_pwd_icon)
    ImageView ivPwdIcon;
    @BindView(R.id.et_pass_word)
    EditText etPassWord;
    @BindView(R.id.iv_clear_text_pwd_code)
    ImageView ivClearTextPwdCode;
    @BindView(R.id.iv_pwd_eye_icon)
    ImageView ivPwdEyeIcon;
    @BindView(R.id.ll_pwd_login)
    LinearLayout llPwdLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_go_rigist)
    TextView tvGoRigist;
    @BindView(R.id.ll_go_rigist)
    RelativeLayout llGoRigist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // v1.1.2
    }
    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtil.hideSoftInput(etPwdPhone.getWindowToken(), this);

    }
    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //用户同意使用权限
                        } else {
                            //用户不同意使用权限
                            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                            Toast.makeText(LoginActivity.this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);
                        }
                    }
                });
    }

    @OnClick({R.id.tv_login, R.id.tv_go_rigist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if(!TextUtils.isEmpty(etPwdPhone.getText().toString())&&!TextUtils.isEmpty(etPassWord.getText().toString())) {
                    SharePreUtils.setjwt_token(this,etPassWord.getText().toString());
                    UiGoto.startAty(mContext, MainActivity.class);
                    finish();
                }

                break;
            case R.id.tv_go_rigist:
                break;
        }
    }
}
