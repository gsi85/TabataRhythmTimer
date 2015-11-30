package com.sisa.tabata.preferences;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provider for settings preferences default values enlisted in {@link PreferencesDefaultValues}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class DefaultPreferenceValuesProvider {

    @Inject
    private PreferencesSource preferencesSource;

    /**
     * Sets default values for preferences if not set already.
     */
    public void checkSetDefaultValues() {
        for (Map.Entry<PreferenceKeys, Object> preferenceEntry : PreferencesDefaultValues.getDefaultValuesMap().entrySet()) {
            checkSetPreference(preferenceEntry);
        }
    }

    private void checkSetPreference(final Map.Entry<PreferenceKeys, Object> preferenceEntry) {
        if (notSet(preferenceEntry.getKey())) {
            setPreference(preferenceEntry);
        }
    }

    private boolean notSet(final PreferenceKeys key) {
        return !preferencesSource.isSet(key);
    }

    private void setPreference(final Map.Entry<PreferenceKeys, Object> preference) {
        Object value = preference.getValue();
        PreferenceKeys key = preference.getKey();
        if (value instanceof Boolean) {
            preferencesSource.setBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            preferencesSource.setLong(key, (Long) value);
        } else if (value instanceof String) {
            preferencesSource.setString(key, (String) value);
        }
    }
}
