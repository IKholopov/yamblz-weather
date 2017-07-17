package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedMetric.CELSIUS;

public class WeatherFragment extends BaseFragment implements WeatherView {

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivCurrent)
    ImageView ivCurrentWeatherImage;

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
    protected void setDrawableEnabled() {
        ((DrawerLocker) getActivity()).setDrawerEnable(true);
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
    public void showCurrentWeather(CurrentWeather weather, OWSupportedMetric metric) {
        String metricStr = convertMetricToString(metric);
        String temperature = String.valueOf((int) Math.round(weather.getMain().getTemp())) + " " + metricStr;
        tvTemp.setText(temperature);
        tvCity.setText(weather.getName());
        tvDescription.setText(weather.getWeather().get(0).getDescription());

        setImageFromName(weather.getWeather().get(0).getIcon());
    }

    private String convertMetricToString(OWSupportedMetric metric) {
        if (metric == CELSIUS)
            return getString(R.string.celsius);
        else
            return getString(R.string.fahrenheit);
    }

    private void setImageFromName(String name) {
        String mDrawableName = "icon_" + name;
        int resID = getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
        Drawable drawable = getResources().getDrawable(resID);
        ivCurrentWeatherImage.setImageDrawable(drawable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
