package com.example.myapp.dragger.component;



import com.example.myapp.MyApplication;
import com.example.myapp.dragger.ContextLife;
import com.example.myapp.dragger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /**
     * 提供App的Context
     * @return
     */
    @ContextLife("Application")
    MyApplication getContext();

}
