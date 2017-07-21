package com.example.toor.yamblzweather.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Single;

public class DataBase {

    private Context context;
    private WeatherModelDao weatherModelDao;
    private static final String DB_NAME = "weather_db";

    public DataBase(Context context) {
        this.context = context;
        this.weatherModelDao = setupDB();
    }

    private WeatherModelDao setupDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession.getWeatherModelDao();
    }

    public void saveOrUpdateWeather(FullWeatherModel fullWeatherModel) {
        WeatherModel weather = getDataBaseWeatherModelFromFullWeatherModel(fullWeatherModel);
        List<WeatherModel> weatherModels = loadAllRecords();
        for (WeatherModel weatherModel : weatherModels) {
            CurrentWeather currentWeather = getCurrentWeatherFromString(weatherModel.getCurrentWeather());
            String cityName = currentWeather.getName();
            if (fullWeatherModel.getCurrentWeather().getName().equals(cityName)) {
                weather.setId(weatherModel.getId());
                weatherModelDao.update(weather);
                return;
            }
        }
        weatherModelDao.insert(weather);
    }

    private WeatherModel getDataBaseWeatherModelFromFullWeatherModel(FullWeatherModel fullWeatherModel) {
        WeatherModel weather = new WeatherModel();
        Gson gson = new Gson();
        weather.setCityId(fullWeatherModel.getCurrentWeather().getId());
        weather.setCurrentWeather(gson.toJson(fullWeatherModel.getCurrentWeather()));
        weather.setForecastWeather(gson.toJson(fullWeatherModel.getWeatherForecast()));
        return weather;
    }

    private CurrentWeather getCurrentWeatherFromString(String weatherStr) {
        Gson gson = new Gson();
        return gson.fromJson(weatherStr, CurrentWeather.class);
    }

    private List<WeatherModel> loadAllRecords() {
        return weatherModelDao.queryBuilder().list();
    }

    public Single<CurrentWeather> loadCurrentWeather(int cityId) {
        WeatherModel weather = weatherModelDao.queryBuilder()
                .where(WeatherModelDao.Properties.CityId.eq(cityId)).unique();
        Gson gson = new Gson();
        return Single.fromCallable(() -> gson.fromJson(weather.getCurrentWeather(), CurrentWeather.class));
    }

    public Single<ExtendedWeather> loadWeatherForecast(int cityId) {
        WeatherModel weather = weatherModelDao.queryBuilder()
                .where(WeatherModelDao.Properties.CityId.eq(cityId)).unique();
        Gson gson = new Gson();
        return Single.fromCallable(() -> gson.fromJson(weather.getForecastWeather(), ExtendedWeather.class));
    }
}
