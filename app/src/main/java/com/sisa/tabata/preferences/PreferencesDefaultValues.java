package com.sisa.tabata.preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains default values for preferences.
 * If a {@link PreferenceKeys} default differs from what {@link PreferencesSource} uses for the give type, it should be enlisted here.
 *
 * @author Laszlo Sisa
 */
public final class PreferencesDefaultValues {

    private static final Map<PreferenceKeys, Object> DEFAULT_VALUES_MAP;

    static {
        DEFAULT_VALUES_MAP = new HashMap<>();
        DEFAULT_VALUES_MAP.put(PreferenceKeys.AUTO_PAUSE_ON_CALL, true);
        DEFAULT_VALUES_MAP.put(PreferenceKeys.FIRST_TIME_OPENED, true);
    }

    private PreferencesDefaultValues() {

    }

    public static Map<PreferenceKeys, Object> getDefaultValuesMap() {
        return DEFAULT_VALUES_MAP;
    }
}
