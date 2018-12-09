package com.testapp.testtask.repository;

import com.google.android.gms.maps.model.LatLng;
import com.testapp.testtask.BaseApplication;
import com.testapp.testtask.entities.Weather;
import com.testapp.testtask.entities.WeatherDao;

import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {

    private static WeatherDao getDao() {
        return BaseApplication.getDaoSession().getWeatherDao();
    }

    public static void save(Weather item) {
        getDao().insertOrReplace(item);
    }

    public static Weather getById(String id) {
        return getDao().queryBuilder().where(WeatherDao.Properties.Id.eq(id)).unique();
    }

    public static List<String> getLocations() {
       List<Weather> all = getDao().loadAll();
       List<String> list = new ArrayList<>();
       for (Weather item : all) {
           String name = item.getId();
           list.add(name);
       }
       return list;
    }

    public static LatLng getLatLngById(String id) {
        Weather weather = getById(id);
        return new LatLng(weather.getLatitude(), weather.getLongitude());
    }

    public static List<Weather> getAll() {
        return getDao().loadAll();
    }

}
