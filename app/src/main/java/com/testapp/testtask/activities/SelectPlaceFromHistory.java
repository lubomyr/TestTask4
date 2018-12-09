package com.testapp.testtask.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.testapp.testtask.R;
import com.testapp.testtask.databinding.ActivitySelectPlaceFromHistoryBinding;
import com.testapp.testtask.repository.WeatherRepository;

import java.util.List;

import static com.testapp.testtask.MainActivity.KEY_LATLNG;
import static com.testapp.testtask.MainActivity.KEY_LOCATION;

public class SelectPlaceFromHistory extends AppCompatActivity {
    private ActivitySelectPlaceFromHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_place_from_history);

        bindAdapter();
    }

    public void bindAdapter() {
        List<String> list = WeatherRepository.getLocations();
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,
                list);
        binding.placesListView.setAdapter(adapter);
        binding.placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                String location = (String) p1.getItemAtPosition(p3);
                LatLng latlng = WeatherRepository.getLatLngById(location);
                finishWithResult(location, latlng);
            }

        });
    }

    private void finishWithResult(String location, LatLng latlng) {
        Bundle conData = new Bundle();
        conData.putString(KEY_LOCATION, location);
        conData.putParcelable(KEY_LATLNG, latlng);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

}
