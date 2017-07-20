package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.FAHRENHEIT;

public class SettingsFragment extends BaseFragment implements SettingsView {

    private Unbinder unbinder;

    @BindView(R.id.rgTempMetric)
    RadioGroup rgTempMetric;
    @BindView(R.id.celsius)
    RadioButton rbCelsius;
    @BindView(R.id.fahrenheit)
    RadioButton rbFahrenheit;
    @BindView(R.id.rbUpdateInterval)
    RadioGroup rgUpdateInterval;

    @Inject
    SettingsFragmentPresenter presenter;

    private static final long INTERVAL_MULTIPLEXOR = 1 * 60 * 1000;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker) getActivity()).setDrawerEnable(false);
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        presenter.showSettings();

        rgTempMetric.setOnCheckedChangeListener((radioGroup, checkedId) -> saveTemperatureMetric(radioGroup));
        rgUpdateInterval.setOnCheckedChangeListener((radioGroup, checkedId) -> saveUpdateInterval(radioGroup));
    }

    private void saveTemperatureMetric(RadioGroup radioGroup) {
        TemperatureMetric metric;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.celsius:
                metric = CELSIUS;
                break;
            case R.id.fahrenheit:
                metric = FAHRENHEIT;
                break;
            default:
                metric = CELSIUS;
                break;
        }
        presenter.saveTemperatureMetric(metric);
    }

    private void saveUpdateInterval(RadioGroup radioGroup) {
        long interval = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbMin15:
                interval = 15 * INTERVAL_MULTIPLEXOR;
                break;
            case R.id.rbMin30:
                interval = 30 * INTERVAL_MULTIPLEXOR;
                break;
            case R.id.rbMin60:
                interval = 60 * INTERVAL_MULTIPLEXOR;
                break;
            case R.id.rbMin180:
                interval = 180 * INTERVAL_MULTIPLEXOR;
                break;
            default:
                interval = 60 * INTERVAL_MULTIPLEXOR;
                break;
        }
        presenter.saveUpdateInterval(interval);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void setSettings(SettingsModel settingsModel) {
        setTemperatureMetric(settingsModel.getMetric());
        setUpdateInterval(settingsModel.getUpdateWeatherInterval());
    }

    private void setTemperatureMetric(TemperatureMetric metric) {
        if (metric == CELSIUS)
            rgTempMetric.check(R.id.celsius);
        else
            rgTempMetric.check(R.id.fahrenheit);
    }

    private void setUpdateInterval(long interval) {
        if (interval == 15 * INTERVAL_MULTIPLEXOR)
            rgUpdateInterval.check(R.id.rbMin15);
        else if (interval == 30 * INTERVAL_MULTIPLEXOR)
            rgUpdateInterval.check(R.id.rbMin30);
        else if (interval == 60 * INTERVAL_MULTIPLEXOR)
            rgUpdateInterval.check(R.id.rbMin60);
        else
            rgUpdateInterval.check(R.id.rbMin180);
    }
}
