package com.example.toor.yamblzweather.presentation.di;

import android.app.Application;

import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.presentation.di.components.ActivityComponent;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.components.DaggerAppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;

import timber.log.Timber;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;
    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setInstance(this);
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private static void setInstance(App instance) {
        App.instance = instance;
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ActivityComponent plusActivityComponent() {
        if (activityComponent == null) {
            activityComponent = appComponent.plus(new ActivityModule());
        }

        return activityComponent;
    }

    public void releaseActivityComponent() {
        activityComponent = null;
    }
}
