package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.animation.Animator;
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
import com.example.toor.yamblzweather.data.models.weather.common.Temp;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.domain.utils.TimeUtils;
import com.example.toor.yamblzweather.domain.utils.ViewUtils;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;
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

@ActivityScope
public class WeatherFragment extends BaseFragment implements WeatherView, WeatherDetailsView,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.pressureLabel)
    TextView pressureLabel;
    @BindView(R.id.tvPressure)
    TextView tvPressure;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.humidityLabel)
    TextView humidityLabel;
    @BindView(R.id.tvTempMax)
    TextView tvTempMax;
    @BindView(R.id.tvTempMin)
    TextView tvTempMin;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivCurrent)
    ImageView ivCurrentWeatherImage;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvForecast)
    RecyclerView forecastList;
    @BindView(R.id.tvNoConnection)
    TextView noConnection;


    private Unbinder unbinder;

    private PlaceModel placeModel;
    private ForecastAdapter forecastAdapter;
    private int position;
    private int dayPosition;

    private static final String POSITION_KEY = "position_key";
    private static final String DAY_KEY = "day_key";

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

        presenter.onChildAttach(this);
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
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        forecastList.setLayoutManager(llm);
        forecastAdapter = new ForecastAdapter(null, this,
                App.getInstance().plusActivityComponent());
        forecastList.setAdapter(forecastAdapter);
        if(savedInstanceState != null) {
            position = savedInstanceState.getInt(POSITION_KEY);
            dayPosition = savedInstanceState.getInt(DAY_KEY);
        } else {
            dayPosition = 0;
        }
        presenter.getPlace(position).subscribe(place -> {
            if(place == null) {
                return;
            }
            placeModel = place;
            unSubcribeOnDetach(presenter.getWeather(place, this));
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        outBundle.putInt(POSITION_KEY, position);
        outBundle.putInt(DAY_KEY, forecastAdapter.getPosition());
    }

    @Override
    public void showWeather(DailyWeather weather, String placeName) {
        if(forecastList == null) {
            return;
        }
        if(weather.getList().size() == 0) {
            noConnection.setVisibility(View.VISIBLE);
            presenter.updateAllWeather();
        }
        else {
            if(noConnection.getVisibility() == View.VISIBLE) {
                noConnection.animate().alpha(0f);
                noConnection.setVisibility(View.INVISIBLE);
            }
            forecastAdapter.updateForecast(weather.getList());
            forecastList.addItemDecoration(forecastAdapter.getDecoration());
            if(weather.getList() != null && weather.getList().size() > 0) {
                forecastAdapter.setSelected(dayPosition);
                forecastList.getLayoutManager().scrollToPosition(dayPosition);
            }
            Temp temp = weather.getList().get(dayPosition).getTemp();
            unSubcribeOnDetach(presenter.getCurrentTemperatureString(temp.getMax())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(temperatureMaxStr -> {
                        unSubcribeOnDetach(presenter.getCurrentTemperatureString(temp.getMin())
                                .subscribe(temperatureMinStr ->
                                        setSelectedWeather(temperatureMaxStr, temperatureMinStr,
                                                placeName, weather.getList().get(dayPosition))));
                    }));
        }
    }

    @Override
    public void showWeather(DailyWeather weather) {
        showWeather(weather, placeModel.getName());
    }

    @Override
    public long getPlaceId() {
        return placeModel.getLocalId();
    }


    private void setSelectedWeather(@NonNull String temperatureMaxStr,
                                    @NonNull String temperatureMinStr,
                                    String placeName,
                                    DailyForecastElement weather) {
        if (tvTempMax == null) {
            return;
        }
        animateFade(tvTempMax);
        tvTempMax.setText(temperatureMaxStr);
        animateFade(tvTempMin);
        tvTempMin.setText(temperatureMinStr);
        if(weather != null) {
            tvCity.setText(placeName);
            animateFade(tvDate);
            tvDate.setText(TimeUtils.formatDayAndDateShort(weather.getDt()));
            humidityLabel.setText(getString(R.string.humidity));
            animateFade(tvHumidity);
            tvHumidity.setText(String.format(getString(R.string.percents_format), weather.getHumidity()));
            pressureLabel.setText(getString(R.string.pressure));
            animateFade(tvPressure);
            tvPressure.setText(String.format(getString(R.string.pressure_format), weather.getPressure()));
            animateFade(tvDescription);
            tvDescription.setText(weather.getWeather().get(0).getDescription());
            setImageFromName(weather.getWeather().get(0).getIcon());
        }
    }

    private void setImageFromName(String name) {
        int resID = ViewUtils.getIconResourceFromName(name, getContext());
        Drawable drawable = getResources().getDrawable(resID);;
        ivCurrentWeatherImage.setImageDrawable(drawable);
        animateFade(ivCurrentWeatherImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onChildDetach(this);
    }

    @Override
    public void onRefresh() {
        if(placeModel == null) {
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        dayPosition = forecastAdapter.getPosition();
        presenter.updateAllWeather();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showWeatherDetails(DailyForecastElement element) {
        Temp temp = element.getTemp();
        unSubcribeOnDetach(presenter.getCurrentTemperatureString(temp.getMax())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(temperatureMaxStr -> {
                    unSubcribeOnDetach(presenter.getCurrentTemperatureString(temp.getMin())
                            .subscribe(temperatureMinStr ->
                                    setSelectedWeather(temperatureMaxStr, temperatureMinStr,
                                            placeModel.getName(), element)
                    ));
                }));
    }

    private void animateFade(View view) {
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(400);
    }
}
