package com.sisa.tabata.preferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;

import android.content.SharedPreferences;

/**
 * Controlling application wide preferences.
 *
 * @author Laca
 */
@Singleton
public class PreferencesSource {

    private static final int PRIVATE_MODE = 0;
    private static final int DEFAULT_NUMERIC_PREFERENCE_VALUE = -1;
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final String PREFERENCES_FILE_NAME = "TabataPreferences";

    private final SharedPreferences settings;
    private final SharedPreferences.Editor settingsEditor;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    @Inject
    public PreferencesSource(final ApplicationContextProvider applicationContextProvider) {
        settings = applicationContextProvider.getContext().getSharedPreferences(PREFERENCES_FILE_NAME, PRIVATE_MODE);
        settingsEditor = settings.edit();
    }

    /**
     * Returns a boolean value related to the given preference key.
     *
     * @param preferenceKeys {@link PreferenceKeys} as the key to retrieve
     * @return the preference value
     */
    public boolean is(final PreferenceKeys preferenceKeys) {
        return settings.getBoolean(preferenceKeys.name(), DEFAULT_BOOLEAN_VALUE);
    }

    /**
     * Returns a boolean value related to the given preference key.
     *
     * @param preferenceKeys {@link PreferenceKeys} as the key to retrieve
     * @param defaultValue the default value if preference does not exist
     * @return the preference value
     */
    public boolean is(final PreferenceKeys preferenceKeys, final boolean defaultValue) {
        return settings.getBoolean(preferenceKeys.name(), defaultValue);
    }

    /**
     * Returns a long value related to the given preference key.
     *
     * @param preferenceKeys {@link PreferenceKeys} as the key to retrieve
     * @return the preference value
     */
    public long getLong(final PreferenceKeys preferenceKeys) {
        return settings.getLong(preferenceKeys.name(), DEFAULT_NUMERIC_PREFERENCE_VALUE);
    }

    /**
     * Sets the boolean value of the given key.
     *
     * @param preferenceKeys {@link PreferenceKeys} the key to set
     * @param value the value of the key
     */
    public void setBoolean(final PreferenceKeys preferenceKeys, final boolean value) {
        settingsEditor.putBoolean(preferenceKeys.name(), value);
        settingsEditor.apply();
    }

    /**
     * Sets the long value of the given key.
     *
     * @param preferenceKeys {@link PreferenceKeys} the key to set
     * @param value the value of the key
     */
    public void setLong(final PreferenceKeys preferenceKeys, final long value) {
        settingsEditor.putLong(preferenceKeys.name(), value);
        settingsEditor.apply();
    }

    /**
     * Check if long type preference is set for the given key.
     *
     * @param preferenceKeys {@link PreferenceKeys} the key to check
     * @return if key is set (e.g.: current value defers from default value)
     */
    public boolean isLongSet(final PreferenceKeys preferenceKeys) {
        return getLong(preferenceKeys) != DEFAULT_NUMERIC_PREFERENCE_VALUE;
    }

}
