package com.example.myapp.dragger.component;

import android.app.Activity;


import com.example.myapp.dragger.ActivityScope;
import com.example.myapp.dragger.module.ActivityModule;
import com.example.myapp.homepage.homedemo.BottomSheetBihaverActivity;
import com.example.myapp.login.view.LoginActivity;

import dagger.Component;

/**
 * @author Administrator
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity loginActivity);

//    void inject(BottomSheetBihaverActivity bottomSheetBihaverActivity);
//
//    void inject(SearchActivity searchActivity);
//
//    void inject(ArticleSearchActivity articleSearchActivity);
//
//    void inject(FreqWebActivity freqWebActivity);
//
//    void inject(MyCollectActivity myCollectActivity);
//
//    void inject(AddToDoActivity addToDoActivity);
//
//    void inject(AddCollectActivity addCollectActivity);
}
