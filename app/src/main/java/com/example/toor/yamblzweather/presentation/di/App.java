package com.example.toor.yamblzweather.presentation.di;

import android.app.Application;
import android.util.Log;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobManagerCreateException;
import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.domain.scheduler.ScheduleJobCreator;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.presentation.di.components.ActivityComponent;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.components.DaggerAppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import timber.log.Timber;

public class App extends Application {

    private static final String TAG = "Application";

    private static App instance;

    private AppComponent appComponent;
    private ActivityComponent activityComponent;

    @Inject
    WeatherScheduleJob scheduleJob;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        try {
            JobManager.create(getApplicationContext()).addJobCreator(new ScheduleJobCreator());
        } catch (JobManagerCreateException e) {
            Log.e(TAG, "Failed to create a job are we running a unit test here?");
        }

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setInstance(this);
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        this.appComponent.inject(this);

        startJob();
    }

    private void startJob() {
        scheduleJob.startJob();
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
