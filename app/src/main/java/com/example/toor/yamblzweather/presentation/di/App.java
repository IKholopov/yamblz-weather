package com.example.toor.yamblzweather.presentation.di;

import android.app.Application;

import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.components.DaggerAppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

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

}
