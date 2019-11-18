package com.example.myapp.homepage.homedemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.nucarf.base.ui.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxjavaDemoActivity extends BaseActivity {

    @BindView(R.id.tv_rx_creat)
    TextView tvRxCreat;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_rx_just)
    TextView tvRxJust;
    @BindView(R.id.tv_rx_from_filter)
    TextView tvRxFromFilter;
    @BindView(R.id.tv_rx_flowable)
    TextView tvRxFlowable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_demo);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }


    @SuppressLint("CheckResult")
    private void creatRxjava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();

            }
        }).subscribe(integer -> {
            tvResult.append("\n creat " + integer);

        }, throwable -> {
            tvResult.append("\n error");

        }, () -> {
            tvResult.append("\n onComplete");

        });
    }

    @OnClick({R.id.tv_rx_creat, R.id.tv_rx_just, R.id.tv_rx_from_filter,R.id.tv_rx_flowable, R.id.tv_rx_flowable_syan})
    public void onViewClicked(View view) {
        tvResult.setText("结果：");
        switch (view.getId()) {
            case R.id.tv_rx_creat:
                creatRxjava();
                break;
            case R.id.tv_rx_just:
                justRxjava();
                break;
            case R.id.tv_rx_from_filter:
                formRxjava();
                break;
            case R.id.tv_rx_flowable:
                flowableRxjava();
                break;
            case R.id.tv_rx_flowable_syan:
                flowableSyanRxjava();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void justRxjava() {
        Observable.just(1.45, 234, 345, 222, 6566)
                .subscribe(new Consumer<Number>() {
                    @Override
                    public void accept(Number number) throws Exception {
                        tvResult.append("\n" + number);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        tvResult.append("\n" + throwable);

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        tvResult.append("\n just " + "onComplete");

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void formRxjava() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Observable.fromIterable(list)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvResult.append("\n from " + integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void flowableRxjava() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Flowable<Integer> integerFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                tvResult.append("\n flowable " + 1);
                emitter.onNext(34);
                tvResult.append("\n flowable " + 2);
                emitter.onNext(5678);
                tvResult.append("\n flowable " + 3);
                emitter.onNext(9349);
                tvResult.append("\n flowable " + 4);
                emitter.onNext(3434);
                emitter.onComplete();

            }

        }, BackpressureStrategy.ERROR);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);

            }

            @Override
            public void onNext(Integer integer) {
                tvResult.append("\n flowable " + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                tvResult.append("\n flowable " + "onComplete");

            }
        };
        tvResult.append("\n 同步");
        integerFlowable.subscribe(subscriber);
    }
    @SuppressLint("CheckResult")
    private void flowableSyanRxjava() {
        Flowable<Integer> integerFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                tvResult.append("\n flowable " + 1);
                emitter.onNext(34);
                tvResult.append("\n flowable " + 2);
                emitter.onNext(5678);
                tvResult.append("\n flowable " + 3);
                emitter.onNext(9349);
                tvResult.append("\n flowable " + 4);
                emitter.onNext(3434);
                emitter.onComplete();

            }

        }, BackpressureStrategy.ERROR);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);

            }

            @Override
            public void onNext(Integer integer) {
                tvResult.append("\n flowable " + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                tvResult.append("\n flowable " + "onComplete");

            }
        };
//        tvResult.append("\n 同步");
//        integerFlowable.subscribe(subscriber);

        tvResult.append("\n 异步");
        integerFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
