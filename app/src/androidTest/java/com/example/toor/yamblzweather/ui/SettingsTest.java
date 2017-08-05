package com.example.toor.yamblzweather.ui;

import android.preference.PreferenceManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by igor on 7/30/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    private SettingsPreference preferences;

    private String getString(int res) {
        return App.getInstance().getString(res);
    }

    @Before
    public void setUp() {
        preferences = new SettingsPreference(PreferenceManager.getDefaultSharedPreferences(
                App.getInstance().getApplicationContext()));
        onView(allOf(withContentDescription(getString(R.string.navigation_drawer_open)),
                withParent(withId(R.id.toolbar)), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.design_menu_item_text), withText(getString(R.string.title_settings)), isDisplayed()))
                .perform(click());
    }

    @Test
    public void changeTemperatureMetricTest(){
        onView(withId(R.id.celsius)).perform(click());
        TemperatureMetric metric = preferences.loadTemperatureMetric();
        assertThat(metric, equalTo(TemperatureMetric.CELSIUS));
        onView(withId(R.id.fahrenheit)).perform(click());
        metric = preferences.loadTemperatureMetric();
        assertThat(metric, equalTo(TemperatureMetric.FAHRENHEIT));
    }

    @Test
    public void changeUpdateInterval() {
        onView(withId(R.id.rbMin15)).perform(click());
        long interval = preferences.loadUpdateWeatherInterval();
        assertThat(interval, equalTo(15 * 60 * 1000L));
        onView(withId(R.id.rbMin30)).perform(click());
        interval = preferences.loadUpdateWeatherInterval();
        assertThat(interval, equalTo(30 * 60 * 1000L));
        onView(withId(R.id.rbMin60)).perform(click());
        interval = preferences.loadUpdateWeatherInterval();
        assertThat(interval, equalTo(60 * 60 * 1000L));
        onView(withId(R.id.rbMin180)).perform(click());
        interval = preferences.loadUpdateWeatherInterval();
        assertThat(interval, equalTo(180 * 60 * 1000L));
    }
}
