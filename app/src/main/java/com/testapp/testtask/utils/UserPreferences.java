package com.testapp.testtask.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import static com.testapp.testtask.MainActivity.KEY_LOCATION;

public class UserPreferences {
    private static final String KEY = "shared_settings";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private static SharedPreferences prefs = null;

    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        }
    }

    public static void setLocation(String name, LatLng latlng) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LOCATION, name);
        editor.putString(KEY_LATITUDE, String.valueOf(latlng.latitude));
        editor.putString(KEY_LONGITUDE, String.valueOf(latlng.longitude));
        editor.apply();
    }

    public static String getLocation() {
        return prefs.getString(KEY_LOCATION, null);
    }

    public static LatLng getLatLng() {
        double latitude = Double.parseDouble(prefs.getString(KEY_LATITUDE, "0"));
        double longitude = Double.parseDouble(prefs.getString(KEY_LONGITUDE, "0"));
        return new LatLng(latitude, longitude);
    }
}
