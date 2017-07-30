package com.example.toor.yamblzweather.common;

import android.content.Context;

import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.components.DaggerAppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 7/30/17.
 */

@PrepareForTest(App.class)
public class AppMock {
    @Mock Context context;
    @Mock AppModule mockedAppModule;
    @Mock App mockedApp;
    AppComponent component;

    public static AppMock newInstance() {
        AppMock appMock = new AppMock();
        appMock.context = mock(Context.class);
        appMock.mockedAppModule = mock(AppModule.class);
        appMock.mockedApp = mock(App.class);
        when(appMock.mockedAppModule.getContext()).thenReturn(appMock.context);
        PowerMockito.mockStatic(App.class);
        appMock.component = DaggerAppComponent.builder()
                .appModule(appMock.mockedAppModule).build();
        when(App.getInstance()).thenReturn(appMock.mockedApp);
        when(appMock.mockedApp.getAppComponent()).thenReturn(appMock.component);
        return appMock;
    }

    public Context getContext() {
        return context;
    }

    public AppModule getAppModule() {
        return mockedAppModule;
    }

    public App getApp() {
        return mockedApp;
    }

    public AppComponent getComponent() {
        return component;
    }
}
