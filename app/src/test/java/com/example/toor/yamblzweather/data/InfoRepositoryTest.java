package com.example.toor.yamblzweather.data;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.toor.yamblzweather.common.AppMock;
import com.example.toor.yamblzweather.data.repositories.info.InfoRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 7/30/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(App.class)
public class InfoRepositoryTest {

    private AppMock mockedApp;
    @Mock PackageManager mockedPackageManager;
    @Mock PackageInfo mockedPackageInfo;

    @Before
    public void prepare() throws PackageManager.NameNotFoundException {
        mockedApp = AppMock.newInstance();
        when(mockedApp.getContext().getPackageManager()).thenReturn(mockedPackageManager);
        when(mockedApp.getContext().getPackageName()).thenReturn("");
        when(mockedPackageManager.getPackageInfo("", 0)).thenReturn(mockedPackageInfo);
        mockedPackageInfo.versionName = "TestVersion";
    }

    @Test
    public void getAppVerisonTest() throws InterruptedException {
        new InfoRepositoryImpl().getAppVersion().subscribe(version -> {
            assertThat(version, equalTo(mockedPackageInfo.versionName));
        });
    }
}
