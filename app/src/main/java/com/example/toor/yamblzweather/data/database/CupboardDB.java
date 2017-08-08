package com.example.toor.yamblzweather.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.common.Main;
import com.example.toor.yamblzweather.data.models.weather.common.Weather;
import com.example.toor.yamblzweather.data.models.weather.common.Wind;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.WeatherForecastElement;
import com.example.toor.yamblzweather.domain.utils.TimeUtils;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import nl.nl2312.rxcupboard2.RxCupboard;
import nl.nl2312.rxcupboard2.RxDatabase;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;

/**
 * Created by igor on 8/2/17.
 */

public class CupboardDB extends SQLiteOpenHelper implements DataBase{

    private static final String DATABASE_NAME = "CupboardWeather.db";
    private static final int DATABASE_VERSION = 7;

    private static Cupboard cupboard;

    private RxDatabase db;

    public synchronized RxDatabase getDatabase() {
        if(db == null) {
            db = RxCupboard.with(cupboard, getWritableDatabase());
        }
        return db;
    }

    public CupboardDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        cupboard = new CupboardBuilder().build();
        cupboard.register(PlaceDBModel.class);
        cupboard.register(WeatherDBModel.class);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard.withDatabase(sqLiteDatabase).createTables();

        addCompositeUniqueConstraint(sqLiteDatabase, WeatherDBModel.class, getWeatherConstraintNames());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        cupboard.withDatabase(sqLiteDatabase).upgradeTables();
        addCompositeUniqueConstraint(sqLiteDatabase, WeatherDBModel.class, getWeatherConstraintNames());
    }

    @NonNull
    @Override
    public Flowable<PlaceDetails> getPlaces() {
        return getDatabase().query(PlaceDBModel.class).subscribeOn(Schedulers.io())
                .map(this::modelToPlaceDetails);
    }

    @NonNull
    @Override
    public Flowable<WeatherForecastElement> getWeather(@NonNull PlaceDetails placeDetails) {
        long id = placeDetails.getId();
        return getDatabase().query(getDatabase().buildQuery(WeatherDBModel.class)
                .withSelection("placeId = ?", String.valueOf(id)).orderBy("date"))
                .subscribeOn(Schedulers.io()).map(this::modelToForecastElement);
    }

    @NonNull
    @Override
    public Single<Long> clearBeforeDate(@NonNull Calendar date) {
        long normalizeDate = TimeUtils.normalizeDate(date.getTimeInMillis() / 1000);
        return getDatabase().delete(WeatherDBModel.class, "date < ?", String.valueOf(normalizeDate))
                    .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<Long> deletePlace(long placeId) {
        return getDatabase().delete(PlaceDBModel.class, "_id = ?", String.valueOf(placeId))
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<Long> deleteWeatherForPlace(long placeId) {
        return getDatabase().delete(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void addOrUpdateWeather(@NonNull ExtendedWeather weatherModel, long placeId, @NonNull Action onComplete) {
        Observable.fromIterable((weatherModel.getList())).subscribeOn(Schedulers.io())
                .flatMapSingle(forecast -> getDatabase().put(forecastToDBModel(forecast, placeId)))
                .doOnComplete(onComplete)
                .subscribe(getDatabase().put());
    }

    @Override
    public void addPlace(@NonNull PlaceDetails place, @NonNull Action onComplete) {
        PlaceDBModel model = placeDetailsToModel(place);
        getDatabase().query(PlaceDBModel.class, "latitude = ? AND longitude = ?",
                String.valueOf(place.getCoords().getLat()),
                String.valueOf(place.getCoords().getLon())).subscribeOn(Schedulers.io())
                .doOnComplete(() -> Flowable.just(model).subscribeOn(Schedulers.io())
                        .flatMapSingle(details -> getDatabase().put(details))
                        .doOnComplete(onComplete)
                        .subscribe(getDatabase().put()))
                .subscribe(existing -> model._id = existing._id);
    }

    @Override
    public void addCurrentLocation(@NonNull PlaceDetails place, @NonNull Action onComplete) {
        PlaceDBModel model = placeDetailsToModel(place,true);
        getDatabase().query(PlaceDBModel.class, "currentLocation = 1").subscribeOn(Schedulers.io())
                .doOnComplete(() -> Flowable.just(model).subscribeOn(Schedulers.io())
                        .flatMapSingle(modelToInsert -> getDatabase().put(modelToInsert))
                        .doOnComplete(onComplete)
                        .subscribe(getDatabase().put()))
                .subscribe(existing -> model._id = existing._id);
    }

    private WeatherDBModel forecastToDBModel(@NonNull WeatherForecastElement forecast, long placeId) {
        Main main = forecast.getMain();
        Weather weather = forecast.getWeather().get(0);
        return new WeatherDBModel(TimeUtils.normalizeDate(forecast.getDt()), placeId,
                main.getTempMin().floatValue(),
                main.getTempMax().floatValue(),
                main.getHumidity().longValue(),
                main.getPressure().floatValue(),
                forecast.getWind().getSpeed().floatValue(),
                forecast.getWind().getDeg().floatValue(),
                weather.getDescription(), weather.getIcon());
    }

    private PlaceDBModel placeDetailsToModel(PlaceDetails details) {
        return placeDetailsToModel(details, false);
    }

    private PlaceDBModel placeDetailsToModel(PlaceDetails details, boolean current) {
        return new PlaceDBModel(details.getCoords().getLat().floatValue(),
                details.getCoords().getLon().floatValue(), details.getName(), current);
    }

    private PlaceDetails modelToPlaceDetails(PlaceDBModel model) {
        return PlaceDetails.newInstance(model._id, new Coord(model.latitude, model.longitude),
                model.name);
    }

    private WeatherForecastElement modelToForecastElement(WeatherDBModel model) {
        WeatherForecastElement element = new WeatherForecastElement();
        Main main = new Main();
        main.setTempMax(model.tempMax.doubleValue());
        main.setTempMin(model.tempMin.doubleValue());
        main.setTemp((model.tempMax.doubleValue() + model.tempMin.doubleValue()) / 2);
        main.setPressure(model.pressure.doubleValue());
        main.setHumidity(model.humidity.intValue());
        main.setTempKf(main.getTemp() + 273);
        element.setMain(main);
        Wind wind = new Wind();
        wind.setDeg(model.windDirection.doubleValue());
        wind.setSpeed(model.windSpeed.doubleValue());
        element.setWind(wind);
        Weather weather = new Weather();
        weather.setDescription(model.weatherDescription);
        weather.setIcon(model.weatherIcon);
        weather.setId(model._id.intValue());
        weather.setMain(model.weatherDescription);
        List<Weather> listWeather = new ArrayList<>();
        listWeather.add(weather);
        element.setWeather(listWeather);
        element.setDt(model.date.intValue());
        return element;
    }

    private List<String> getWeatherConstraintNames() {
        List<String> uniqueComposite = new ArrayList<>();
        uniqueComposite.add("placeId");
        uniqueComposite.add("date");
        return uniqueComposite;
    }

    private void addCompositeUniqueConstraint(SQLiteDatabase sqLiteDatabase, Class model,
                                              List<String> fieldNames) {
        Field[] allFields = (model.getFields());
        Set<String> allNames = new HashSet<>();
        for(Field field: allFields) {
            allNames.add(field.getName());
        }
        for(String name: fieldNames) {
            if(!fieldNames.contains(name)) {
                throw new InvalidParameterException("fieldNames: " + name + " is not a field of " + model.getSimpleName());
            }
        }
        String[] columns = { "sql" };
        String[] selectionArgs = { model.getSimpleName() };
        Cursor cursor = sqLiteDatabase.query("sqlite_master", columns, "name = ?", selectionArgs, null, null, null);
        cursor.moveToFirst();
        String string = cursor.getString(0);
        String command = string.substring(0, string.length() - 1) + ", UNIQUE('";
        for(int i = 0; i < fieldNames.size() - 1; ++i) {
            command += fieldNames.get(i);
            command += "', '";
        }
        command += fieldNames.get(fieldNames.size() - 1);
        command += "') ON CONFLICT REPLACE)";
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + model.getSimpleName() + "'");
        sqLiteDatabase.execSQL(command);
    }
}
