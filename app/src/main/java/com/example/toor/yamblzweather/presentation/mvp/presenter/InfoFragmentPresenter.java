package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.InfoInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.InfoView;

import javax.inject.Inject;

public class InfoFragmentPresenter extends BaseFragmentPresenter<InfoView> {

    private InfoInteractor interactor;

    @Inject
    public InfoFragmentPresenter(InfoInteractor interactor) {
        this.interactor = interactor;
    }

    public void showAppVersion() {
        if (getView() != null)
            unSubcribeOnDetach(interactor.getAppVersion().subscribe((version, throwable) -> getView().showAppVersion(version)));
    }
}
