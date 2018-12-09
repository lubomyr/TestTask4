package com.testapp.testtask;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.testapp.testtask.activities.MapsActivity;
import com.testapp.testtask.activities.SelectPlaceFromHistory;
import com.testapp.testtask.activities.ShowDataFromHistory;
import com.testapp.testtask.api.RetrofitUtil;
import com.testapp.testtask.api.api.WeatherAPI;
import com.testapp.testtask.databinding.ActivityMainBinding;
import com.testapp.testtask.entities.Weather;
import com.testapp.testtask.repository.WeatherRepository;
import com.testapp.testtask.utils.GsonUtils;
import com.testapp.testtask.utils.UserPreferences;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private WeatherAPI weatherAPI;
    private String locationName;
    private LatLng latlng;
    private final int SELECT_PLACE_FROM_MAP_REQUEST = 1;
    private final int SELECT_PLACE_FROM_HISTORY_REQUEST = 2;
    private final String RESPONSE_KEY = "testApp";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LATLNG = "latlng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initRetrofit();
        bindButton();
        setSavedLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PLACE_FROM_MAP_REQUEST
                    || requestCode == SELECT_PLACE_FROM_HISTORY_REQUEST) {
                this.locationName = data.getStringExtra(KEY_LOCATION);
                this.latlng = data.getParcelableExtra(KEY_LATLNG);
                UserPreferences.setLocation(this.locationName, this.latlng);
                updateLocation(this.locationName);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePlaceButton:
                selectLocation();
                break;
            case R.id.selectPlaceFromHistory:
                selectLocationFromHistory();
                break;
            case R.id.showWeatherDataFromHistory:
                showWeatherDataFromHistory();
                break;
        }
    }

    private void initRetrofit() {
        Retrofit retrofit = RetrofitUtil.getSimpleRetrofit();
        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    private void bindButton() {
        binding.changePlaceButton.setOnClickListener(this);
        binding.selectPlaceFromHistory.setOnClickListener(this);
        binding.showWeatherDataFromHistory.setOnClickListener(this);
    }

    private void setSavedLocation() {
        this.locationName = UserPreferences.getLocation();
        this.latlng = UserPreferences.getLatLng();
        if (this.locationName != null) {
            updateLocation(this.locationName);
        } else {
            if (BaseApplication.isConnect())
                selectLocation();
            else
                showConnectionAlert();

        }
    }

    private void selectLocation() {
        Intent intent = new Intent(this, MapsActivity.class);
        if (this.locationName != null && this.latlng != null) {
            intent.putExtra(KEY_LOCATION, this.locationName);
            intent.putExtra(KEY_LATLNG, this.latlng);
        }
        startActivityForResult(intent, SELECT_PLACE_FROM_MAP_REQUEST);
    }

    private void selectLocationFromHistory() {
        Intent intent = new Intent(this, SelectPlaceFromHistory.class);
        startActivityForResult(intent, SELECT_PLACE_FROM_HISTORY_REQUEST);
    }

    private void showWeatherDataFromHistory() {
        Intent intent = new Intent(this, ShowDataFromHistory.class);
        startActivity(intent);
    }

    private void updateLocation(String name) {
        binding.selectedPlace.setText(name);
        getWeatherFromDB(name);
        getWeatherFromApi(name);
    }

    private void getWeatherFromApi(String name) {
        binding.info.setText("Retrieving weather data from api...");
        Call<JsonElement> call;
        call = weatherAPI.getWeather(name, getString(R.string.weather_api_key));
        call.enqueue(weatherCallback);
    }

    private void getWeatherFromDB(String name) {
        Weather weather = WeatherRepository.getById(name);
        if (weather != null) {
            refreshWeatherView(weather);
        }
    }

    private void showConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Getting weather data failed")
                .setCancelable(false)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveWeatherToDb(Weather weather) {
        weather.setId(this.locationName);
        weather.setLatitude(this.latlng.latitude);
        weather.setLongitude(this.latlng.longitude);
        weather.setDescription(weather.getAddition().getDescription());
        WeatherRepository.save(weather);
        refreshWeatherView(weather);
    }

    private void refreshWeatherView(Weather weather) {
        if (weather != null) {
            binding.setWeather(weather);
            binding.temp.setText(String.format("%s ℃", weather.getTemp()));
            binding.description.setText(weather.getDescription());
            binding.date.setText(weather.getObTime());
        } else {
            binding.temp.setText("Sorry, No data for this location");
            binding.description.setText("Sorry, No data for this location");
            binding.date.setText("Sorry, No data for this location");
        }
    }

    Callback<JsonElement> weatherCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            Log.d(RESPONSE_KEY, "onResponse");
            if (response.body() != null) {
                JsonElement json = response.body();
                if (json.isJsonObject() && json.getAsJsonObject().has("data")) {
                    JsonElement dataObj = json.getAsJsonObject().get("data");
                    if (dataObj != null && dataObj.isJsonArray()) {
                        JsonElement obj = dataObj.getAsJsonArray().get(0);
                        Weather weather = GsonUtils.getGson().fromJson(obj, new TypeToken<Weather>() {
                        }.getType());
                        saveWeatherToDb(weather);
                        binding.info.setText("weather data succesfully updated");
                    }
                }
            } else {
                refreshWeatherView(null);
                binding.info.setText("Sorry, no weather data for this location");
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            Log.d(RESPONSE_KEY, "onFailure");
            binding.info.setText("retrieving weather data from api - failed");
        }
    };
}
