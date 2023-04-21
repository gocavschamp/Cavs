package com.example.myapp.dragger.component;

import android.app.Activity;

import com.example.myapp.dragger.FragmentScope;
import com.example.myapp.dragger.module.FragmentModule;

import dagger.Component;

/**
 * @author Administrator
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

//    void inject(HomeFragment homeFragment);
//
//    void inject(ProjectFragment projectFragment);
//
//    void inject(SystemFragment systemFragment);
//
//    void inject(NaviFragment naviFragment);
//
//    void inject(PublicAddrFragment publicAddrFragment);
//
//    void inject(PubListFragment pubListFragment);
//
//    void inject(ProjectListFragment projectListFragment);
//
//    void inject(SubSystemFragment subSystemFragment);
//
//    void inject(ToDoFragment toDoFragment);
//
//    void inject(DoneFragment doneFragment);
}
