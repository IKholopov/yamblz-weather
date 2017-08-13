package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

/**
 * Created by igor on 8/11/17.
 */

public interface NavigateView extends BaseView{

    void showNetworkError();
    void navigateTo(int position);
}
