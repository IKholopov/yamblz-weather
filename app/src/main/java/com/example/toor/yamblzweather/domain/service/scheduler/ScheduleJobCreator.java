package com.example.toor.yamblzweather.domain.service.scheduler;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class ScheduleJobCreator implements JobCreator {

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
