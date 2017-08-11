package com.example.toor.yamblzweather.presentation.mvp.view.fragment.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.OnBackBehaviour;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        inject();
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        setDrawableEnabled();
        setTitle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = getActivity();
        if(activity != null && activity instanceof OnBackBehaviour) {
            ((OnBackBehaviour)activity).setOnBackBehaviour(null);
        }
        App.getInstance().releaseActivityComponent();
    }

    public void unSubcribeOnDetach(Disposable... disposables) {
        disposable.addAll(disposables);
    }

    public void onDetach() {
        super.onDetach();
        disposable.clear();
    }

    protected abstract void setTitle();

    protected abstract void setDrawableEnabled();

    protected abstract void inject();
}