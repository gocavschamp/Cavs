package com.example.myapp.homepage;

import android.widget.TextView;

import com.example.myapp.BuildConfig;
import com.example.myapp.R;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.GlideUtils;
import com.nucarf.base.widget.RoundImageView;
import com.nucarf.base.widget.StarBar;

import butterknife.BindView;

public class MineFragment extends BaseLazyFragment {


    @BindView(R.id.star_bar)
    StarBar starBar;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.round_iv_head)
    RoundImageView roundIvHead;

    public MineFragment() {
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.mine_page;
    }

    @Override
    protected void initData() {
//        ImmersionBar immersionBar = ImmersionBar.with(this).setOnKeyboardListener(this);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("卡卡罗特\n");
        stringBuffer.append(BuildConfig.APPLICATION_ID + "  id\n");
        stringBuffer.append(BuildConfig.BUILD_TYPE + "  buildtype\n");
        stringBuffer.append(BuildConfig.FLAVOR + "  flavor\n");
        stringBuffer.append(BuildConfig.VERSION_CODE + "  code\n");
        stringBuffer.append(BuildConfig.VERSION_NAME + "  name\n");
        stringBuffer.append(getString(R.string.app_name )+ "  app name\n");
        stringBuffer.append(getString(R.string.app_welcome) + "  app welcome\n");
        stringBuffer.append(getString(R.string.age) + "  age\n");
        tvInfo.setText(stringBuffer);
        GlideUtils.load(mActivity, getString(R.string.kakaluot), roundIvHead);

    }

    @Override
    protected void initView() {

    }

}
