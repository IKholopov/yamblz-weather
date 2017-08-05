package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;
import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.domain.scheduler.ScheduleJobCreator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context appContext) {
        this.context = appContext;

        //TODO initScheduleJob();
    }

    private void initScheduleJob() {
        JobManager.create(context).addJobCreator(new ScheduleJobCreator());
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
            JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true);
    }

    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }
}
