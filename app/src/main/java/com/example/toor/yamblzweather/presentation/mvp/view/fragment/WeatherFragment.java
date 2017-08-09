package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.domain.utils.ViewUtils;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.ForecastAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WeatherFragment extends BaseFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivCurrent)
    ImageView ivCurrentWeatherImage;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvForecast)
    RecyclerView forecastList;

    private Unbinder unbinder;

    private PlaceModel placeModel;
    private ForecastAdapter forecastAdapter;
    private int position;

    private static final String IMAGE_RESOURCES_SUFFIX = "icon_";
    private static final String IMAGE_RESOURCES_FOLDER = "drawable";
    private static final String POSITION_KEY = "position_key";

    @Inject
    WeatherPresenter presenter;

    public static WeatherFragment newInstance(PlaceModel place, int position)
    {
        WeatherFragment fragment = new WeatherFragment();
        fragment.placeModel = place;
        fragment.position = position;
        return fragment;
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_weather);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker) getActivity()).setDrawerEnable(true);
    }

    @Override
    protected void inject() {
        App.getInstance().plusActivityComponent().inject(this);

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        forecastList.setLayoutManager(llm);
        forecastAdapter = new ForecastAdapter(null,
                App.getInstance().plusActivityComponent());
        forecastList.setAdapter(forecastAdapter);
        if(savedInstanceState != null) {
            position = savedInstanceState.getInt(POSITION_KEY);
        }
        presenter.getPlace(position).subscribe(place -> {
            if(place == null) {
                return;
            }
            placeModel = place;
            presenter.getWeather(place, this);
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        outBundle.putInt(POSITION_KEY, position);
    }

    @Override
    public void showWeather(DailyWeather weather, String placeName) {
        if(weather.getList().size() == 0) {
            setWeatherString("No downloaded weather :)", placeName, weather);
        }
        else {
            forecastAdapter.updateForecast(weather.getList());
            presenter.getCurrentTemperatureString(weather.getList().get(0))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(temperatureStr -> {
                        setWeatherString(temperatureStr, placeName, weather);
                    });
        }
    }

    @Override
    public void showErrorFragment() {
        createNetworkErrorFragment();
    }

    private void setWeatherString(@NonNull String temperatureStr, String placeName,
                                      DailyWeather weather) {
        if (tvTemp == null) {
            return;
        }
        tvTemp.setText(temperatureStr);
        tvCity.setText(placeName);
        if(weather.getList().size() > 0) {
            tvDescription.setText(weather.getList().get(0).getWeather().get(0).getDescription());
            setImageFromName(weather.getList().get(0).getWeather().get(0).getIcon());
        }
    }

    private void createNetworkErrorFragment() {
        Fragment fragment = ErrorFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment, ErrorFragment.class.getSimpleName())
                .commit();
    }

    private void setImageFromName(String name) {
        int resID = ViewUtils.getIconResourceFromName(name, getContext());
        Drawable drawable = getResources().getDrawable(resID);
        ivCurrentWeatherImage.setImageDrawable(drawable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.onDetach();
    }

    @Override
    public void onRefresh() {
        if(placeModel == null) {
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        presenter.updateWeather(placeModel, this);
        swipeRefreshLayout.setRefreshing(false);

    }
}
