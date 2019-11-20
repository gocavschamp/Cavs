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
import java.util.concurrent.TimeUnit;

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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxjavaDemoActivity extends BaseActivity {

    private static final String TAG = "rxjava ：";
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
    @BindView(R.id.tv_rx_flowable_syan)
    TextView tvRxFlowableSyan;
    @BindView(R.id.tv_rx_interval)
    TextView tvRxInterval;
    @BindView(R.id.tv_concat)
    TextView tvConcat;
    @BindView(R.id.tv_merge)
    TextView tvMerge;
    @BindView(R.id.tv_rx_zip)
    TextView tvRxZip;
    private Disposable didpose;

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

        }, disposable -> {
            Disposable d = disposable;

        });
    }

    @OnClick({R.id.tv_rx_creat, R.id.tv_rx_just, R.id.tv_rx_from_filter, R.id.tv_rx_flowable, R.id.tv_rx_flowable_syan, R.id.tv_rx_interval
            , R.id.tv_concat, R.id.tv_merge, R.id.tv_rx_zip})
    public void onViewClicked(View view) {
        tvResult.setText("结果：");
        cancelRxjava();
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
            case R.id.tv_rx_interval:
                intervalRxjava();
                break;
            case R.id.tv_concat:
                concatRxjava();
                break;
            case R.id.tv_merge:
                mergeRxjava();
                break;
            case R.id.tv_rx_zip:
                zipRxjava();
                break;
        }
    }

    private void zipRxjava() {
        Observable.zip(Observable.just(1, 2, 3), Observable.just("A", "B", "C", "D"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + "$" + s;
            }
        })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void mergeRxjava() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(3);
                emitter.onNext(3);
                emitter.onNext(3);
            }
        });
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
                emitter.onNext(7);
                emitter.onNext(7);
                emitter.onNext(7);
            }
        });
        Observable.merge(observable1, observable2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        didpose = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void concatRxjava() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6, 7))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        didpose = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }


    /**
     * 定时器
     */
    private void intervalRxjava() {

        Observable.interval(1, 2, TimeUnit.SECONDS)
//                .repeatWhen()
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        didpose = d;
                        Log.d(TAG, "onSubscribe: onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        if (aLong == 10)
                            if (!didpose.isDisposed())
                                didpose.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void justRxjava() {
        Observable.just(1.45, 234, 345, 222, 6566)
                .subscribe(new Consumer<Number>() {
                    @Override
                    public void accept(Number number) throws Exception {
                        tvResult.append("\n" + number);
                        if (number.equals(222)) {
                        }

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
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        didpose = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tvResult.append("\n from " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        ;
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
                Log.d(TAG, "subscribe: 观察者需求count" + emitter.requested());
                Log.d(TAG, "subscribe:  flowable " + 1);
                emitter.onNext(34);
                Log.d(TAG, "subscribe: flowable " + 2);
                emitter.onNext(5678);
                Log.d(TAG, "subscribe: flowable " + 3);
                emitter.onNext(9349);
                Log.d(TAG, "subscribe: flowable " + 4);
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
                Log.d(TAG, "onNext: flowable " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError: " + t);

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onNext:flowable " + "onComplete");

            }
        };
        Log.d(TAG, ": Flowable 同步");
        integerFlowable.subscribe(subscriber);
    }

    @SuppressLint("CheckResult")
    private void flowableSyanRxjava() {
        Flowable<Integer> integerFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                tvResult.append("\n emitter  " + 1);
                emitter.onNext(34);
                tvResult.append("\n emitter " + 2);
                emitter.onNext(5678);
                tvResult.append("\n emitter " + 3);
                emitter.onNext(9349);
                tvResult.append("\n emitter " + 4);
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
                tvResult.append("\n subscriber " + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                tvResult.append("\n subscriber " + "onComplete");

            }
        };
//        tvResult.append("\n 同步");
//        integerFlowable.subscribe(subscriber);

        tvResult.append("\n Flowable 异步");
        integerFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelRxjava();
    }

    private void cancelRxjava() {
        if (didpose != null && !didpose.isDisposed()) {
            didpose.dispose();
        }
    }
}
