package com.example.toor.yamblzweather.domain.service.scheduler;

import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherFragment;

public class ScheduleJobCreator implements JobCreator {

    public ScheduleJobCreator() {
        Log.v(WeatherFragment.class.getSimpleName(), "initJOB");
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case WeatherScheduleJob.TAG:
                return new WeatherScheduleJob();
            default:
                return null;
        }
    }
}
