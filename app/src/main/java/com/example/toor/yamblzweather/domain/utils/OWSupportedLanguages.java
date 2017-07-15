package com.example.toor.yamblzweather.domain.utils;

public enum OWSupportedLanguages {
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
    CHINESE_T("zh_tw"),
    CHINESE_S("zh"),
    TURKISH("tr"),
    CROATIAN("hr"),
    CATALAN("ca");

    String language;

    OWSupportedLanguages(String languageLocale) {
        this.language = languageLocale;
    }

    public String getLanguageLocale() {
        return language;
    }
}
