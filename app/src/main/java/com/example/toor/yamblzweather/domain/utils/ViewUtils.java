package com.example.toor.yamblzweather.domain.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by igor on 8/9/17.
 */

public class ViewUtils {

    private static final String ICON_PREFIX = "icon_";
    private static final String ICON_RESOURCES_FOLDER = "drawable";

    public static int getIconResourceFromName(String name, @NonNull Context context) {
        return context.getResources().getIdentifier(ICON_PREFIX + name, ICON_RESOURCES_FOLDER,
                context.getPackageName());
    }
}
