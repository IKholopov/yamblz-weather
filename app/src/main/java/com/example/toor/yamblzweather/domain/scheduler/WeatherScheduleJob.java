package com.example.toor.yamblzweather.domain.scheduler;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import timber.log.Timber;

public class WeatherScheduleJob extends Job {

    static final String TAG = "show_notification_job_tag";
    private static final double FLEX_TIME_PERCENT = 0.5; //flex time is 50% of full period interval

    @Inject
    WeatherInteractor weatherInteractor;

    @Inject
    SettingsInteractor settingsInteractor;

    private City city;

    public WeatherScheduleJob() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        return Result.SUCCESS;
    }

//    private void serializeCurrentWeather() {
//        interactor.getCurrentWeather(coordinates).subscribe(currentWeather -> interactor.saveCurrentWeatherToCache(currentWeather));
//    }

    public void startJob(City city) {
        Timber.v("startJob");
        this.city = city;
        settingsInteractor.getUserSettings().subscribe((settings, throwable) -> new JobRequest.Builder(WeatherScheduleJob.TAG)
                .setPeriodic(TimeUnit.MILLISECONDS.toMillis(settings.getUpdateWeatherInterval())
                        , TimeUnit.MILLISECONDS.toMillis((long) ((double) settings.getUpdateWeatherInterval() * FLEX_TIME_PERCENT)))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule());
    }
}
