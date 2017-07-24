package com.example.toor.yamblzweather.domain.utils;

import android.support.annotation.NonNull;

/**
 * Created by igor on 7/24/17.
 */

public enum GooglePlacesSupportedLanguages {
    ENGLISH("en"),
    RUSSIAN("ru"),
    ITALIAN("it"),
    SPANISH("es"),
    ROMANIAN("ro"),
    POLISH("pl"),
    FINNISH("fi"),
    DUTCH("nl"),
    FRENCH("fr"),
    BULGARIAN("bg"),
    SWEDISH("sv"),
    CHINESE_T("zh-TW"),
    CHINESE_S("zh-CN"),
    TURKISH("tr"),
    CROATIAN("hr"),
    CATALAN("ca");

    String language;

    GooglePlacesSupportedLanguages(String languageLocale) {
        this.language = languageLocale;
    }

    public @NonNull
    String getLanguageLocale() {
        return language;
    }
}
