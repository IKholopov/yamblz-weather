package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;
import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.scheduler.ScheduleJobCreator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context appContext) {
        this.context = appContext;

        initScheduleJob();
    }

    private void initScheduleJob() {
        JobManager.create(context).addJobCreator(new ScheduleJobCreator());
    }

    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }
}
