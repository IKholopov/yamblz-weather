package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.ConnectionErrorFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.ConnectionErrorView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ErrorFragment extends BaseFragment implements ConnectionErrorView {

    @BindView(R.id.btnRetry)
    Button btnRetry;

    private Unbinder unbinder;

    @Inject
    ConnectionErrorFragmentPresenter presenter;

    public static ErrorFragment newInstance() {
        return new ErrorFragment();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        App.getInstance().plusActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connection_error, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.onAttach(this);
        unbinder = ButterKnife.bind(this, view);

        btnRetry.setOnClickListener(btnView -> presenter.retryConnection());
    }

    @Override
    public void showWeatherFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, WeatherFragment.newInstance(), ErrorFragment.class.getSimpleName())
                .commit();
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
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
