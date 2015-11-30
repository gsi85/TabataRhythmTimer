package com.sisa.tabata.preferences;

import java.math.BigInteger;
import java.security.SecureRandom;
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
    private static final int NUMBER_OF_BITS = 130;
    private static final int RADIX = 32;

    static {
        SecureRandom random = new SecureRandom();
        DEFAULT_VALUES_MAP = new HashMap<>();
        DEFAULT_VALUES_MAP.put(PreferenceKeys.AUTO_PAUSE_ON_CALL, true);
        DEFAULT_VALUES_MAP.put(PreferenceKeys.FIRST_TIME_OPENED, true);
        DEFAULT_VALUES_MAP.put(PreferenceKeys.INSTALLATION_ID, new BigInteger(NUMBER_OF_BITS, random).toString(RADIX));
    }

    private PreferencesDefaultValues() {

    }

    public static Map<PreferenceKeys, Object> getDefaultValuesMap() {
        return DEFAULT_VALUES_MAP;
    }
}
