package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.WeatherRepository.WeatherRepository;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherScreenModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeatherFragment extends BaseFragment implements WeatherView {


    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvTemp)
    TextView tvTemp;

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

        App.getInstance().getAppComponent().plus(new WeatherModule()).plus(new WeatherScreenModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.bindView(this);
        unbinder = ButterKnife.bind(this, view);

        presenter.updateCurrentWeather();
    }

    @Override
    public void showCurrentWeather(WeatherRepository weather) {

        Log.v(WeatherFragment.class.getSimpleName(), "temp = " + weather.getTemperature() + "  city = " + weather.getCity());
//        tvCity.setText(weather.getCity());
//        tvTemp.setText(weather.getTemperature());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();

        tvCity = null;
        tvTemp = null;
    }
}
