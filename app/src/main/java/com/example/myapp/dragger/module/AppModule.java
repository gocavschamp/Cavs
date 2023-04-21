package com.example.myapp.dragger.module;
import com.example.myapp.MyApplication1;
import com.example.myapp.dragger.ContextLife;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class AppModule {
    private final MyApplication1 application;

    public AppModule(com.example.myapp.MyApplication1 application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    MyApplication1 provideApplicationContext() {
        return application;
    }

}
