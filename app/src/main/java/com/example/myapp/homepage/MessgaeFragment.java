package com.example.myapp.homepage;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.example.myapp.R;
import com.example.myapp.widget.CustomView;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.nucarf.base.ui.BaseLazyFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MessgaeFragment extends BaseLazyFragment implements View.OnFocusChangeListener, View.OnClickListener, OnKeyboardListener {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.custom_view)
    CustomView customView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_card)
    EditText etCard;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    public MessgaeFragment() {
    }

    public static MessgaeFragment newInstance() {
        return new MessgaeFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.messgae_page;
    }

    @Override
    protected void initData() {
        ImmersionBar immersionBar = ImmersionBar.with(this).setOnKeyboardListener(this);
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });

    }

    @Override
    protected void initView() {
        etName.setOnFocusChangeListener(this);
        etPhone.setOnFocusChangeListener(this);
        etCard.setOnFocusChangeListener(this);
        etName.setOnClickListener(this);
        etPhone.setOnClickListener(this);
        etCard.setOnClickListener(this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.et_name:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etName.getTop());
                        }
                    });
                    break;
                case R.id.et_phone:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etPhone.getTop());
                        }
                    });

                    break;
                case R.id.et_card:
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, etCard.getTop());
                        }
                    });

                    break;
            }
        }
    }

    @OnClick(R.id.tvTitle)
    public void onViewClicked() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_name:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etName.getTop());
                    }
                });
                break;
            case R.id.et_phone:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etPhone.getTop());
                    }
                });

                break;
            case R.id.et_card:
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etCard.getTop());
                    }
                });

                break;
        }
    }

    @Override
    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
        if(!isPopup) {
            scrollView.smoothScrollTo(0,0);
        }
    }
}
