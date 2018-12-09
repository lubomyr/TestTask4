package com.testapp.testtask.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.testapp.testtask.databinding.ItemWeatherBinding;
import com.testapp.testtask.entities.Weather;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Weather> itemList;
    private Context mContext;
    private int mResources;

    public WeatherAdapter(Context context, int resources) {
        this.mContext = context;
        this.mResources = resources;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemWeatherBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), mResources, parent, false);

        return new WeatherAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        Weather item = itemList.get(position);
        holder.setItem(item);
        holder.binding.setWeather(item);

        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setList(List<Weather> list) {
        this.itemList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWeatherBinding binding;
        private Weather item;

        public ViewHolder(ItemWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(Weather item) {
            this.item = item;
        }

    }
}
