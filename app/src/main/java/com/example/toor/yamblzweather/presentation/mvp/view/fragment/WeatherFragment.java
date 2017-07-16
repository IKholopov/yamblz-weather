package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;

public class WeatherFragment extends BaseFragment implements WeatherView {

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvMetric)
    TextView tvMetric;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private Unbinder unbinder;

    @Inject
    WeatherFragmentPresenter presenter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_weather);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        App.getInstance().getAppComponent().plus(new WeatherModule()).plus(new ScreenModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.onAttach(this);
        unbinder = ButterKnife.bind(this, view);

        Coord coord = new Coord();
        coord.setLat(55.751244);
        coord.setLon(37.618423);
        presenter.updateCurrentWeather(coord);
    }

    @Override
    public void showCurrentWeather(CurrentWeather weather) {
        tvTemp.setText(String.valueOf(weather.getMain().getTemp()));
        tvCity.setText(weather.getName());
        tvDescription.setText(weather.getWeather().get(0).getDescription());
    }

    @Override
    public void showTenperatureMetric(OWSupportedUnits metric) {
        if (metric == CELSIUS)
            tvMetric.setText(getString(R.string.celsius));
        else
            tvMetric.setText(getString(R.string.fahrenheit));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
