package com.example.myapp.dragger.module;
import com.example.myapp.MyApplication;
import com.example.myapp.dragger.ContextLife;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class AppModule {
    private final MyApplication application;

    public AppModule(com.example.myapp.MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    MyApplication provideApplicationContext() {
        return application;
    }

}
