package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.InfoFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.InfoView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InfoFragment extends BaseFragment implements InfoView {

    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;

    @Inject
    InfoFragmentPresenter presenter;

    private Unbinder unbinder;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_info);
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
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        presenter.showAppVersion();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void showAppVersion(String version) {
        String appVersion = getString(R.string.app_version) + " " + version;
        tvAppVersion.setText(appVersion);
    }
}
