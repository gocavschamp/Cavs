package com.nucarf.base.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.nucarf.base.R;

public class TitleLayout extends RelativeLayout {
    ImageView ivLeft;
    RelativeLayout rlLeft;
    TextView tvCenterTitle;
    TextView tvRight;
    ImageView ivRight;
    RelativeLayout rlRight;
    RelativeLayout rlDefaultTitleLayout;

    public TitleLayout(Context context) {
        super(context);
        initView(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R.layout.default_title_layout, this);
        ivLeft =   inflate.findViewById(R.id.iv_left);
        rlLeft =   inflate.findViewById(R.id.rl_left);
        tvCenterTitle =   inflate.findViewById(R.id.tv_center_title);
        tvRight =   inflate.findViewById(R.id.tv_right);
        ivRight =   inflate.findViewById(R.id.iv_right);
        rlRight =   inflate.findViewById(R.id.rl_right);
        rlDefaultTitleLayout =   inflate.findViewById(R.id.rl_default_title_layout);
        tvCenterTitle.setTextColor(getResources().getColor(R.color.color_333333));
        rlDefaultTitleLayout.setBackgroundColor(getResources().getColor(R.color.white));
//        ButterKnife.bind(this, inflate);
    }

    public TitleLayout setTitleBg(int color) {
        rlDefaultTitleLayout.setBackgroundColor(color);
        return this;
    }

    public TitleLayout setTitleText(String titleText) {
        tvCenterTitle.setText(titleText);
        return this;
    }
    public TitleLayout setRightText(String titleText) {
        tvRight.setText(titleText);
        tvRight.setTextColor(getResources().getColor(R.color.color_333333));
        return this;
    }
    public TextView getRightText() {
        return tvRight;
    }

    public TitleLayout setTitleColor(int color) {
        tvCenterTitle.setTextColor(color);
        return this;
    }
    public TitleLayout setLeftPic(int rec) {
        ivLeft.setImageResource(rec);
        return this;
    }

    public TitleLayout setLeftClickListener(OnClickListener onclicklistener) {
        rlLeft.setOnClickListener(onclicklistener);
        return this;

    }

    public TitleLayout setRightClickListener(OnClickListener onclicklistener) {
        rlRight.setOnClickListener(onclicklistener);
        return this;

    }
}
