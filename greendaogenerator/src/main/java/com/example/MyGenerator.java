package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.example.toor.yamblzweather.data.database");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            /* Use forward slashes if you're on macOS or Unix, i.e. "/app/src/main/java"  */
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addWeather(schema);
    }

    private static Entity addWeather(final Schema schema) {
        Entity weather = schema.addEntity("WeatherModel");
        weather.addIdProperty().primaryKey().autoincrement();
        weather.addIntProperty("cityId").notNull();
        weather.addStringProperty("currentWeather");
        weather.addStringProperty("forecastWeather");
        return weather;
    }

}
