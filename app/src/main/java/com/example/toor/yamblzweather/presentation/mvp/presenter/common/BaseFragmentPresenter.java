package com.example.toor.yamblzweather.presentation.mvp.presenter.common;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragmentPresenter<V extends BaseView> {

    private V view;

    private CompositeDisposable disposable = new CompositeDisposable();

    public void onAttach(V view) {
        this.view = view;
    }

    public void unSubcribeOnDetach(Disposable... disposables) {
        disposable.addAll(disposables);
    }

    public void onDetach() {
        this.view = null;
        disposable.clear();
    }

    protected
    @Nullable
    V getView() {
        return view;
    }

}
