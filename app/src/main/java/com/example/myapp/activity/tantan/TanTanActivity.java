package com.example.myapp.activity.tantan;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.moonlight.flyvideo.R;
import com.example.myapp.widget.swipecard.OverLayCardLayoutManager;
import com.example.myapp.widget.swipecard.RenRenCallback;
import com.jakewharton.rxbinding2.view.RxView;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TanTanActivity extends BaseActivityWithTitle {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_dislike)
    ImageView ivDislike;
    @BindView(R.id.rl_dislike)
    RelativeLayout rlDislike;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.tv_dislike_str)
    TextView tvDislikeStr;
    @BindView(R.id.tv_like_str)
    TextView tvLikeStr;
    private RenRenCallback callback;
    private List<String> mDatas;
    private TanTanAdapter tanTanAdapter;
    //    private List<TanTanDataBean.SeniorTaskBean> senior_task;
    private String senior_task_pic_url;
    private String common_description;
    private int luck_user_number;
    private List<String> data;
    private int status;
    private String senior_task_description;
    //    private ICallBack shareCallback;
//    private SharePopup sharePop;
    private int shareViewType;
    private String senior_task_title;
    private boolean isOnSwipe = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TanTanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tan);
        titlelayout.setTitleText("tan tan");
        titlelayout.setLeftClickListener((v)->{
            finish();
        });
        RxView.clicks(rlLike)
                .throttleFirst(600, TimeUnit.MILLISECONDS) // 过滤掉发射频率小于2秒的发射事件
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<Object>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(Object o) throws Exception {
                        if (isOnSwipe) {
                            return;
                        }
//                        if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 0) {
//                            showAlert();
//                            return;
//                        }
//                        if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 1) {
//                            showAlert();
//                            return;
//                        }
                        callback.toRight(recyclerView);
//                        commitLikeStatus(data.get((data.size() - 1)).getUser_id(), true);

                    }
                });
        RxView.clicks(rlDislike)
                .throttleFirst(600, TimeUnit.MILLISECONDS) // 过滤掉发射频率小于2秒的发射事件
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (isOnSwipe) {
                            return;
                        }
//                        if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 0) {
//                            showAlert();
//                            return;
//                        }
//                        if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 1) {
//                            showAlert();
//                            return;
//                        }
                        callback.toLeft(recyclerView);
//                        commitLikeStatus(data.get((data.size() - 1)).getUser_id(), false);

                    }
                });
    }

    @Override
    protected void initData() {
        initView();

        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("111" + i);
        }
        tanTanAdapter.setNewData(data);

    }


    @SuppressLint("CheckResult")
    protected void readIntent() {
//        setTitleTxt("来电");
//        setRightPic(R.mipmap.icon_tantan_user);
//        setRightVisiablity(View.VISIBLE);
//        setLeftEvent(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        setRightEvent(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UiGoto.startAty(com.jiufugame.qupei.module.home.TanTanActivity.this, PersonalInfoEditActivity.class);
//            }
//        });
//        BurPoint.addPoint("QP_YW_LDX", "page", MySelfInfo.getInstance().getUserId());


    }

    private void initView() {
        tanTanAdapter = new TanTanAdapter(R.layout.item_tantan_layout);
        tanTanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RxView.clicks(view)
                        .throttleFirst(2, TimeUnit.SECONDS) // 过滤掉发射频率小于2秒的发射事件
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new io.reactivex.functions.Consumer<Object>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void accept(Object o) throws Exception {
//                                LogUtils.e("----跳个人----" + position + "---id=" + data.get((data.size() - 1)).getUser_id());
//                                IMUserProfileActivity.start(com.jiufugame.qupei.module.home.TanTanActivity.this, data.get((data.size() - 1)).getUser_id() + "");
                            }
                        });
            }
        });

        OverLayCardLayoutManager overLayCardLayoutManager = new OverLayCardLayoutManager(this);
        overLayCardLayoutManager.setTopOffset(20);
        recyclerView.setLayoutManager(overLayCardLayoutManager);
        recyclerView.setAdapter(tanTanAdapter);

        callback = new RenRenCallback();
        callback.setSwipeListener(new RenRenCallback.OnSwipeListener() {
            @Override
            public void onSwiped(int adapterPosition, int direction) {
                isOnSwipe = false;
                if (direction == ItemTouchHelper.DOWN || direction == ItemTouchHelper.UP) {
                    LogUtils.e("----direction--UP--DOWN" + direction);
                } else {
                    if (direction == ItemTouchHelper.LEFT) {
                        LogUtils.e("----direction--LEFT" + direction);
//                        commitLikeStatus(data.get((data.size() - 1)).getUser_id(), false);

                    } else if (direction == ItemTouchHelper.RIGHT) {
                        LogUtils.e("----direction--RIGHT" + direction);
//                        commitLikeStatus(data.get((data.size() - 1)).getUser_id(), true);

                    }
                    data.remove(adapterPosition);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 1 || tanTanAdapter.getItemCount() == 0) {
//                    showAlert();
                }
            }

            @Override
            public void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset) {
                isOnSwipe = true;
                if (offset < -50) {
                    viewHolder.itemView.findViewById(R.id.iv_like_top).setVisibility(View.INVISIBLE);
                    viewHolder.itemView.findViewById(R.id.iv_dislike_top).setVisibility(View.VISIBLE);
                    tvLikeStr.setVisibility(View.GONE);
                    tvDislikeStr.setVisibility(View.VISIBLE);
                    rlDislike.setScaleX(1.25f);
                    rlDislike.setScaleY(1.25f);
                } else if (offset > 50) {
                    viewHolder.itemView.findViewById(R.id.iv_like_top).setVisibility(View.VISIBLE);
                    viewHolder.itemView.findViewById(R.id.iv_dislike_top).setVisibility(View.INVISIBLE);
                    tvLikeStr.setVisibility(View.VISIBLE);
                    tvDislikeStr.setVisibility(View.GONE);
                    rlLike.setScaleX(1.25f);
                    rlLike.setScaleY(1.25f);
                } else {
                    viewHolder.itemView.findViewById(R.id.iv_like_top).setVisibility(View.INVISIBLE);
                    viewHolder.itemView.findViewById(R.id.iv_dislike_top).setVisibility(View.INVISIBLE);
                    tvLikeStr.setVisibility(View.GONE);
                    tvDislikeStr.setVisibility(View.GONE);
                    rlDislike.setScaleX(1.0f);
                    rlDislike.setScaleY(1.0f);
                    rlLike.setScaleX(1.0f);
                    rlLike.setScaleY(1.0f);
                }
            }
        });
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
    }

//    private void showAlert() {
//        if (null != dialog) {
//            dialog.dismiss();
//        }
//        DialogPopView dialogPopView = DialogPopView.newInstance(com.jiufugame.qupei.module.home.TanTanActivity.this);
//        View view = dialogPopView.create();
//        dialogPopView.setBtnTips("" + senior_task.get(0).getTask_name(), "" + senior_task.get(1).getTask_name());
//        dialogPopView.setTitle("" + senior_task_title, "" + senior_task_description);
//        dialogPopView.setImgPic(senior_task_pic_url);
//        dialogPopView.setNegativeClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != dialog) {
//                    dialog.dismiss();
//                }
//                btnClickEvent(senior_task.get(1));
//
//            }
//        });
//        dialogPopView.setOnPositiveClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != dialog) {
//                    dialog.dismiss();
//                }
//                btnClickEvent(senior_task.get(0));
//            }
//        });
//        dialogPopView.setCloseClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != dialog) {
//                    dialog.dismiss();
//                }
//                if (null != tanTanAdapter && tanTanAdapter.getItemCount() == 0) {
//                    showAlert();
//                }
//            }
//        });
//        dialog = DialogUtils.showCustomDialog(TanTanActivity.this, view, false);
//        dialog.show();
//
//    }

//    private void btnClickEvent(TanTanDataBean.SeniorTaskBean data) {
//        switch (data.getTask_go_type()) {
//            case 1:
//                ContentWebViewActivity.startActivity(com.jiufugame.qupei.module.home.TanTanActivity.this, "", data.getTask_go_url(), "2");
//                break;
//            case 2:
//                UiGoto.startAtyASAction(com.jiufugame.qupei.module.home.TanTanActivity.this, data.getTask_go_url());
//                finish();
//                break;
//            case 3:
////                share(data.getTask_go_url());
//                break;
//            case 4://公众号
//
//                break;
//            case 5:// 关闭弹框
//
//                break;
//        }
//    }


    //分享
//    private void share(String share_source) {
//        shareCallback = new ICallBack() {
//            @Override
//            public void handle(Object object) {
//                if (sharePop != null) {
//                    sharePop.dismiss();
//                }
//                boolean isSuccess = (boolean) object;
//                if (isSuccess) {
//                    getLuckNumber();
//                    BurPoint.addPoint(ShareView.sharePointStr + "_SUCCESS", "share", "");
//                } else {
//                    BurPoint.addPoint(ShareView.sharePointStr + "_FAIL", "share", "");
//                }
//            }
//
//            @Override
//            public void shareType(String type) {
//                if (sharePop != null) {
//                    sharePop.dismiss();
//                }
//            }
//        };
//        shareViewType = ShareView.SHARE_TYPE_NONE;
//        sharePop = new SharePopup(this, shareViewType, ShareView.def, shareCallback);
//        sharePop.getData(share_source);
//        sharePop.showAtBottom(findViewById(R.id.draw));
//    }

//    private void getLuckNumber() {
//
//        HttpMethods.getInstance().getShareCallback(new ProgressSubscriber<>(new SubscriberOnNextListener() {
//            @Override
//            public void onNext(Object o) {
//                initData();
//            }
//        }, this, false));
//
//    }

    private Dialog dialog;

//    private void commitLikeStatus(int user_id, boolean likeStatus) {
//        LogUtils.e("---" + user_id, "--" + likeStatus);
//        String like = "";
//        like = likeStatus ? "luck" : "unluck";
//        HttpMethods.getInstance().setLuckyUser(new ProgressSubscriber<HttpResult<LikeUserResult>>(new SubscriberOnNextListener<LikeUserResult>() {
//            @Override
//            public void onNext(LikeUserResult likeUserResult) {
//                if (likeUserResult.getStatus() == 1) {
//                    LogUtils.e("---显示页面--");
//                    DialogPopView dialogPopView = DialogPopView.newInstance(com.jiufugame.qupei.module.home.TanTanActivity.this);
//                    View view = dialogPopView.create();
//                    dialogPopView.setBtnTips("立即接通", "错失缘分");
//                    dialogPopView.setTitle("缘分来了", "");
//                    dialogPopView.setMatch(MySelfInfo.getInstance().getUserHeadUrl(), likeUserResult.getComrade_header_url());
//                    dialogPopView.setNegativeClick(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (null != dialog) {
//                                dialog.dismiss();
//                            }
//
//                        }
//                    });
//                    dialogPopView.setOnPositiveClick(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (null != dialog) {
//                                dialog.dismiss();
//                            }
//                            IMMessageActivity.start(com.jiufugame.qupei.module.home.TanTanActivity.this, user_id + "");
//                            finish();
//                        }
//                    });
//                    dialogPopView.setCloseClick(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (null != dialog) {
//                                dialog.dismiss();
//                            }
//                        }
//                    });
//                    dialog = DialogUtils.showCustomDialog(com.jiufugame.qupei.module.home.TanTanActivity.this, view, false);
//                    dialog.show();
//
//                }
//
//            }
//        }, this, false), user_id + "", like);
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
