package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;

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

    private static final String IMAGE_RESOURCES_SUFFIX = "icon_";
    private static final String IMAGE_RESOURCES_FOLDER = "drawable";

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

        App.getInstance().plusActivityComponent().inject(this);
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

        Coord coord = new Coord();
        coord.setLat(55.751244);
        coord.setLon(37.618423);

        City city = new City();
        city.setCoord(coord);

        presenter.updateCurrentWeather(city);
    }

    @Override
    public void showCurrentWeather(FullWeatherModel fullWeatherModel) {
        String temperatureStr = getCurrentTemperatureString(fullWeatherModel);
        tvTemp.setText(temperatureStr);
        tvCity.setText(fullWeatherModel.getCurrentWeather().getName());
        tvDescription.setText(fullWeatherModel.getCurrentWeather().getWeather().get(0).getDescription());
        setImageFromName(fullWeatherModel.getCurrentWeather().getWeather().get(0).getIcon());
    }

    private String getCurrentTemperatureString(FullWeatherModel fullWeatherModel) {
        String metricStr = convertMetricToString(fullWeatherModel.getTemperatureMetric());
        int temperature = (int) Math.round(fullWeatherModel.getCurrentWeather().getMain().getTemp());
        String temperatureStr = String.valueOf(temperature);
        return temperatureStr + " " + metricStr;
    }

    @Override
    public void showErrorFragment() {
        createNetworkErrorFragment();
    }

    private void createNetworkErrorFragment() {
        Fragment fragment = ErrorFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment, ErrorFragment.class.getSimpleName())
                .commit();
    }

    private String convertMetricToString(TemperatureMetric metric) {
        if (metric == CELSIUS)
            return getString(R.string.celsius);
        else
            return getString(R.string.fahrenheit);
    }

    private void setImageFromName(String name) {
        String mDrawableName = IMAGE_RESOURCES_SUFFIX + name;
        int resID = getResources().getIdentifier(mDrawableName, IMAGE_RESOURCES_FOLDER, getContext().getPackageName());
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
}
