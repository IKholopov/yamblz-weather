package com.example.toor.yamblzweather.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.toor.yamblzweather.data.weather.common.City;

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

    public void saveWeather(Weather weather) {
        weatherDao.insert(weather);
    }

    public Single<Weather> loadWeather(City city) {
        Weather weather = weatherDao.queryBuilder().where(WeatherDao.Properties.Latitude.eq(city.getCoord().getLat())
                , WeatherDao.Properties.Longitude.eq(city.getCoord().getLon())).unique();
        return Single.fromCallable(() -> weather);
    }
}
