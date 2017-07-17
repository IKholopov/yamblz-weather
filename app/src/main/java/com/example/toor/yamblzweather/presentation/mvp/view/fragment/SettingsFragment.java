package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;
import com.example.toor.yamblzweather.presentation.mvp.view.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends BaseFragment implements SettingsView {

    private Unbinder unbinder;

    @BindView(R.id.temperatureSwitch)
    Switch temperatureSwitch;
    @BindView(R.id.rbUpdateInterval)
    RadioGroup rbUpdateInterval;

    @Inject
    SettingsFragmentPresenter presenter;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker)getActivity()).setDrawerEnable(false);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        App.getInstance().getAppComponent().plus(new WeatherModule()).plus(new ScreenModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.onAttach(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.showSettings();

        temperatureSwitch.setOnCheckedChangeListener((compoundButton, state) -> presenter.saveTemperatureState(state));
        rbUpdateInterval.setOnCheckedChangeListener((radioGroup, checkedId) -> saveUpdateInterval(radioGroup));
    }

    private void saveUpdateInterval(RadioGroup radioGroup) {
        long interval = 1 * 1000 * 60;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbMin15:
                interval = 15 * interval;
                break;
            case R.id.rbMin30:
                interval = 30 * interval;
                break;
            case R.id.rbMin60:
                interval = 60 * interval;
                break;
            case R.id.rbMin180:
                interval = 180 * interval;
                break;
            default:
                interval = 60 * interval;
                break;
        }
        presenter.saveUpdateInterval(interval);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void setTemperatureState(boolean state) {
        temperatureSwitch.setChecked(state);
    }

    @Override
    public void setUpdateInterval(long interval) {
        long intervalMultiplexor = 1 * 1000 * 60;
        if (interval == 15 * intervalMultiplexor)
            rbUpdateInterval.check(R.id.rbMin15);
        else if (interval == 30 * intervalMultiplexor)
            rbUpdateInterval.check(R.id.rbMin30);
        else if (interval == 60 * intervalMultiplexor)
            rbUpdateInterval.check(R.id.rbMin60);
        else
            rbUpdateInterval.check(R.id.rbMin180);
    }
}
