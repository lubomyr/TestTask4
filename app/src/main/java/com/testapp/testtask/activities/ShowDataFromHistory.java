package com.testapp.testtask.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.testapp.testtask.R;
import com.testapp.testtask.adapters.WeatherAdapter;
import com.testapp.testtask.databinding.ActivityShowDataFromHistoryBinding;
import com.testapp.testtask.entities.Weather;
import com.testapp.testtask.repository.WeatherRepository;

import java.util.List;

public class ShowDataFromHistory extends AppCompatActivity {
    private ActivityShowDataFromHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_data_from_history);

        bindAdapter();
    }

    private void bindAdapter() {
        List<Weather> list = WeatherRepository.getAll();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        WeatherAdapter adapter = new WeatherAdapter(this, R.layout.item_weather);
        adapter.setList(list);
        binding.recycler.setAdapter(adapter);
    }
}
