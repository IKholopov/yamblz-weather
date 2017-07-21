package com.example.toor.yamblzweather.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class DataBase {

    private Context context;
    private WeatherDao weatherDao;
    private static final String DB_NAME = "weather_db";

    public DataBase(Context context) {
        this.context = context;
        this.weatherDao = setupDB();
    }

    private WeatherDao setupDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession.getWeatherDao();
    }

    public void saveWeather(FullWeatherModel fullWeatherModel) {
        Weather weather = new Weather();
        Gson gson = new Gson();
        weather.setCurrentWeather(gson.toJson(fullWeatherModel.getCurrentWeather()));
        weather.setForecastWeather(gson.toJson(fullWeatherModel.getWeatherForecast()));
        weatherDao.insert(weather);
    }

    public Single<List<Coord>> loadAllCities() {
        List<Weather> weathers = weatherDao.queryBuilder().build().list();
        List<Coord> coords = new ArrayList<>();
        for (Weather weather : weathers) {
            Gson gson = new Gson();
            CurrentWeather currentWeather = gson.fromJson(weather.getCurrentWeather(), CurrentWeather.class);
            coords.add(currentWeather.getCoord());
        }
        return Single.fromCallable(() -> coords);
    }

    public Single<CurrentWeather> loadCurrentWeather(Coord coordinates) {
        Weather weather = weatherDao.queryBuilder()
                .where(WeatherDao.Properties.Latitude.eq(coordinates.getLat())
                        , WeatherDao.Properties.Longitude.eq(coordinates.getLon())).unique();
        String currentWeatherStr = weather.getCurrentWeather();
        Gson gson = new Gson();
        return Single.fromCallable(() -> gson.fromJson(currentWeatherStr, CurrentWeather.class));
    }

    public Single<ExtendedWeather> loadWeatherForecast(Coord coord) {
        Weather weather = weatherDao.queryBuilder()
                .where(WeatherDao.Properties.Latitude.eq(coord.getLat())
                        , WeatherDao.Properties.Longitude.eq(coord.getLon())).unique();
        String weatherForecastStr = weather.getForecastWeather();
        Gson gson = new Gson();
        return Single.fromCallable(() -> gson.fromJson(weatherForecastStr, ExtendedWeather.class));
    }
}
