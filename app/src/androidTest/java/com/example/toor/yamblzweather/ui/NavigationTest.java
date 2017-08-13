package com.example.toor.yamblzweather.ui;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.MainActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by igor on 7/30/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    private String getString(int res) {
        return App.getInstance().getString(res);
    }

    @Test
    public void navigateToSettingsTest(){
        onView(allOf(withContentDescription(getString(R.string.navigation_drawer_open)),
                withParent(withId(R.id.toolbar)), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.design_menu_item_text), withText(getString(R.string.title_settings)), isDisplayed()))
                .perform(click());
        onView(withId(R.id.tvTemperature)).check(matches(isDisplayed()));
        //Back to main screen
        onView(allOf(isClickable(), withParent(withId(R.id.toolbar))))
                .perform(click());
        onView(withId(R.id.tvCity)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToInfoTest(){
        onView(allOf(withContentDescription(getString(R.string.navigation_drawer_open)),
                withParent(withId(R.id.toolbar)), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.design_menu_item_text), withText(getString(R.string.title_info)), isDisplayed()))
                .perform(click());
        onView(withId(R.id.tvAppVersion)).check(matches(isDisplayed()));
        //Back to main screen
        onView(allOf(isClickable(), withParent(withId(R.id.toolbar))))
                .perform(click());
        onView(withId(R.id.tvCity)).check(matches(isDisplayed()));
    }


    @Test
    public void navigateToCitySelectionTest(){
        onView(allOf(withContentDescription(getString(R.string.navigation_drawer_open)),
                withParent(withId(R.id.toolbar)), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.design_menu_item_text), withText(getString(R.string.title_settings)), isDisplayed()))
                .perform(click());
        onView(withId(R.id.bPlaces)).perform(click());
        onView(withId(R.id.bAddPlace)).perform(click());
        onView(withId(R.id.etSearchPlace)).perform(click());
    }
}
