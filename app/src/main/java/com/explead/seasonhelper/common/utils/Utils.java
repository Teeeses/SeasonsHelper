package com.explead.seasonhelper.common.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by Александр on 09.07.2017.
 */

public class Utils {

    public final static String SUMMER_CURRENT_LEVEL = "summer_current_level";
    public final static String WINTER_CURRENT_LEVEL = "winter_current_level";

    public static final String APP_PREFERENCES = "mysettings";

    public static Typeface getTypeFaceLevel(AssetManager manager) {
        return Typeface.createFromAsset(manager,"fonts/level_personal.ttf");
    }

    public static Typeface getTypeGecko(AssetManager manager) {
        return Typeface.createFromAsset(manager,"fonts/gecko.ttf");
    }

}
