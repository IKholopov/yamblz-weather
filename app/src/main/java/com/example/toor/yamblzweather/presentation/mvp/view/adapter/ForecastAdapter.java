package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.domain.utils.TimeUtils;
import com.example.toor.yamblzweather.domain.utils.ViewUtils;
import com.example.toor.yamblzweather.presentation.di.components.ActivityComponent;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherDetailsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igor on 8/9/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<DailyForecastElement> forecast;
    private int selectedItem;
    private WeatherDetailsView detailsView;

    @Inject Context context;
    @Inject WeatherPresenter presenter;

    public ForecastAdapter(@Nullable List<DailyForecastElement> forecast, WeatherDetailsView detailsView,
                           @NonNull ActivityComponent component) {
        this.forecast = forecast;
        this.detailsView = detailsView;
        selectedItem = 0;
        component.inject(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_forecast_entry,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DailyForecastElement weather = forecast.get(position);
        int resId = ViewUtils.getIconResourceFromName(weather.getWeather().get(0).getIcon(),
                context);
        holder.icon.setImageResource(resId);
        presenter.getCurrentTemperatureString(weather.getTemp().getMax()).subscribe(max ->
                        presenter.getCurrentTemperatureString(weather.getTemp().getMin())
                                .subscribe(min -> holder.temperature.setText(max + "/" + min)));
        holder.dayOfWeek.setText(TimeUtils.formatDayShort(weather.getDt()));
        holder.layout.setBackgroundColor(context.getResources().getColor(selectedItem == position ?
                        R.color.color_forecast_selected : R.color.color_forecast_notselected));
        holder.itemView.setOnClickListener(view -> switchSelect(position));
    }

    @Override
    public int getItemCount() {
        if(forecast == null) {
            return 0;
        }
        return forecast.size();
    }

    public void updateForecast(@Nullable List<DailyForecastElement> forecast) {
        this.forecast = forecast;
        notifyDataSetChanged();
    }

    public void setSelected(int position) {
        switchSelect(position);
    }

    public int getPosition() {
        return selectedItem;
    }

    private void switchSelect(int position) {
        detailsView.showWeatherDetails(forecast.get(position));
        selectedItem = position;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivForecastIcon) ImageView icon;
        @BindView(R.id.tvTemperature) TextView temperature;
        @BindView(R.id.tvDayOfWeek) TextView dayOfWeek;
        @BindView(R.id.llForecastLayout) LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
