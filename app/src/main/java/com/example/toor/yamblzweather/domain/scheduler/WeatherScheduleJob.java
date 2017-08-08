package com.example.toor.yamblzweather.domain.scheduler;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.App;

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

    @Inject
    PlacesInteractor placesInteractor;

    public WeatherScheduleJob() {
        App.getInstance().plusActivityComponent().inject(this);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Timber.v("onRunJob");
        serializeCurrentWeather();

        return Result.SUCCESS;
    }

    private void serializeCurrentWeather() {
        placesInteractor.getAllPlaces()
                .subscribe(place -> weatherInteractor.updateWeather(place)
                        .subscribe(weather -> {}));
    }

    public void startJob() {
        Timber.v("startJob");
        if (BuildConfig.DEBUG)
            settingsInteractor.getUserSettings().subscribe((settings, throwable) ->
                    new JobRequest.Builder(TAG)
                            .setPeriodic(TimeUnit.MILLISECONDS.toMillis(900000)
                                    , TimeUnit.MILLISECONDS.toMillis(900000))
                            .setUpdateCurrent(true)
                            .setPersisted(true)
                            .build()
                            .schedule());
        else
            settingsInteractor.getUserSettings().subscribe((settings, throwable) ->
                    new JobRequest.Builder(TAG)
                            .setPeriodic(TimeUnit.MILLISECONDS.toMillis(settings.getUpdateWeatherInterval())
                                    , TimeUnit.MILLISECONDS.toMillis((long) ((double) settings.getUpdateWeatherInterval() * FLEX_TIME_PERCENT)))
                            .setUpdateCurrent(true)
                            .setPersisted(true)
                            .build()
                            .schedule());

    }
}
