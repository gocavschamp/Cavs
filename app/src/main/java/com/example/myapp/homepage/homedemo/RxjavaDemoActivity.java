package com.example.myapp.homepage.homedemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;
import com.nucarf.base.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RxjavaDemoActivity extends BaseActivity {

    @BindView(R.id.tv_rx_creat)
    TextView tvRxCreat;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_rx_just)
    TextView tvRxJust;

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
            tvResult.append("\n" + integer);

        }, throwable -> {
            tvResult.append("\n error");

        }, () -> {
            tvResult.append("\n onComplete");

        });
    }

    @OnClick({R.id.tv_rx_creat, R.id.tv_rx_just})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rx_creat:
                creatRxjava();
                break;
            case R.id.tv_rx_just:
                justRxjava();
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
                        tvResult.append("\n" + "onComplete");

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
                        tvResult.append("\n" + integer);
                    }
                });
    }
}
