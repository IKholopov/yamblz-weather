package com.example.toor.yamblzweather.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.toor.yamblzweather.data.models.places.Location;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.components.DaggerAppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;
import com.example.toor.yamblzweather.presentation.di.modules.UtilsModule;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 7/30/17.
 */

@PrepareForTest({ App.class, Locale.class, UtilsModule.class })
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

        Locale locale = new Locale("en");
        Resources resources = mock(Resources.class);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        when(resources.getConfiguration()).thenReturn(configuration);
        when(appMock.context.getResources()).thenReturn(resources);

        PowerMockito.mockStatic(App.class);
        PowerMockito.mockStatic(Locale.class);
        PowerMockito.mockStatic(AppModule.class);
        appMock.component = DaggerAppComponent.builder()
                .appModule(appMock.mockedAppModule).utilsModule(new UtilsModule()).build();
        PowerMockito.when(App.getInstance()).thenReturn(appMock.mockedApp);
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
    public void setComponent(AppComponent component) {
        this.component = component;
        when(mockedApp.getAppComponent()).thenReturn(component);
    }
}
