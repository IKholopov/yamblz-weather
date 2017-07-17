package com.example.toor.yamblzweather.domain.service.scheduler;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WeatherScheduleJob extends Job {

    static final String TAG = "show_notification_job_tag";
    private static final double FLEX_TIME_PERCENT = 0.5; //flex time is 50% of full period interval

    @Inject
    OWService service;
    @Inject
    SettingsPreference preference;
    @Inject
    Context context;

    private Coord coordinates;

    public WeatherScheduleJob() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        if (NetworkConectionChecker.isNetworkAvailable(context))
            serializeCurrentWeather();
        return Result.SUCCESS;
    }

    private void serializeCurrentWeather() {
        Gson gson = new Gson();
        service.getCurrentDayForecast(coordinates).subscribe(currentWeather -> preference.saveCurrentWeather(gson.toJson(currentWeather)));
    }

    public void startJob(Coord coordinates) {
        this.coordinates = coordinates;
        long interval = preference.loadUpdateWeatherInterval();
        double flexTime = (double) interval * FLEX_TIME_PERCENT;
        new JobRequest.Builder(WeatherScheduleJob.TAG)
                .setPeriodic(TimeUnit.MILLISECONDS.toMillis(interval), TimeUnit.MILLISECONDS.toMillis((long) flexTime))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}
