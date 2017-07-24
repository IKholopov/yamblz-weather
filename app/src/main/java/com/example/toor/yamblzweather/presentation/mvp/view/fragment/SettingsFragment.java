package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.CityNameAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.FAHRENHEIT;

public class SettingsFragment extends BaseFragment implements SettingsView {

    private Unbinder unbinder;

    @BindView(R.id.clSettings)
    ConstraintLayout clSettings;
    @BindView(R.id.rgTempMetric)
    RadioGroup rgTempMetric;
    @BindView(R.id.celsius)
    RadioButton rbCelsius;
    @BindView(R.id.fahrenheit)
    RadioButton rbFahrenheit;
    @BindView(R.id.rbUpdateInterval)
    RadioGroup rgUpdateInterval;
    @BindView(R.id.etSearchCity)
    EditText etSearchCity;
    @BindView(R.id.rvCityAutocomplete)
    RecyclerView rvCityAutocomplete;

    @Inject
    SettingsFragmentPresenter presenter;

    private ConstraintSet normalSet;
    private ConstraintSet searchCitySet;

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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        presenter.showSettings();

        rgTempMetric.setOnCheckedChangeListener((radioGroup, checkedId) -> saveTemperatureMetric(radioGroup));
        rgUpdateInterval.setOnCheckedChangeListener((radioGroup, checkedId) -> saveUpdateInterval(radioGroup));
        etSearchCity.setOnFocusChangeListener((editText, hasFocus) -> onSearchCityEditTextSelected(hasFocus));
        //DEBUG
        etSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.clearFocus();
            }
        });

        setUpConstraintSets();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCityAutocomplete.setLayoutManager(llm);
        rvCityAutocomplete.setAdapter(new CityNameAdapter(null));
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.onDetach();
    }

    @Override
    public void setSettings(SettingsModel settingsModel) {
        setTemperatureMetric(settingsModel.getMetric());
        setUpdateInterval(settingsModel.getUpdateWeatherInterval());
    }

    @Override
    public Observable<CharSequence> getSelectedCityObservable() {
        return RxTextView.textChanges(etSearchCity);
    }

    @Override
    public void updateCitiesSuggestionList(PlacesAutocompleteModel places) {
        rvCityAutocomplete.swapAdapter(new CityNameAdapter(places), false);
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

    private void setUpConstraintSets() {
        normalSet = new ConstraintSet();
        normalSet.clone(clSettings);
        searchCitySet = new ConstraintSet();
        searchCitySet.clone(normalSet);
        searchCitySet.setVisibility(R.id.rgTempMetric, ConstraintSet.INVISIBLE);
        searchCitySet.setVisibility(R.id.rbUpdateInterval, ConstraintSet.INVISIBLE);
        searchCitySet.setVisibility(R.id.tvTemperature, ConstraintSet.INVISIBLE);
        searchCitySet.setVisibility(R.id.tvUpdateInterval, ConstraintSet.INVISIBLE);
        searchCitySet.connect(R.id.etSearchCity, ConstraintSet.TOP, R.id.clSettings, ConstraintSet.TOP);
        searchCitySet.setVerticalBias(R.id.etSearchCity, 0.0f);
    }

    private void onSearchCityEditTextSelected(boolean hasFocus) {
        if(clSettings == null) {
            return;
        }
        TransitionManager.beginDelayedTransition(clSettings);
        ConstraintSet set;
        if(hasFocus) {
            set = searchCitySet;
        } else {
            set = normalSet;
        }
        set.applyTo(clSettings);
    }
}
