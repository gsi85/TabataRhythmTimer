package com.sisa.tabata.ui.provider.settings;

import static com.sisa.tabata.validation.Validation.notEmpty;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sisa.tabata.R;
import com.sisa.tabata.preferences.PreferenceKeys;

import android.widget.CheckBox;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Settings check box map provider for settings activity.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SettingsCheckBoxMapProvider {

    private BiMap<PreferenceKeys, CheckBox> checkBoxMapping;

    @InjectView(R.id.lowRefreshRateCheckbox)
    private CheckBox lowRefreshRate;
    @InjectView(R.id.wakeLockDisabledCheckbox)
    private CheckBox wakeLockDisabled;

    /**
     * Returns checkbox mapping where {@link PreferenceKeys} are association with their representative {@link CheckBox}.
     *
     * @return map of checkboxes
     */
    public Map<PreferenceKeys, CheckBox> getCheckBoxMapping() {
        return notEmpty(checkBoxMapping) ? checkBoxMapping : initCheckBoxMapping();
    }

    /**
     * Returns checkbox mapping where {@link CheckBox} are association with their representative {@link PreferenceKeys}.
     *
     * @return map of preference keys
     */
    public Map<CheckBox, PreferenceKeys> getInvertedCheckBoxMapping() {
        return notEmpty(checkBoxMapping) ? checkBoxMapping.inverse() : initCheckBoxMapping().inverse();
    }

    private BiMap<PreferenceKeys, CheckBox> initCheckBoxMapping() {
        checkBoxMapping = HashBiMap.create();
        checkBoxMapping.put(PreferenceKeys.WORKOUT_LOW_REFRESH_RATE, lowRefreshRate);
        checkBoxMapping.put(PreferenceKeys.WAKE_LOCK_DISABLED, wakeLockDisabled);
        return checkBoxMapping;
    }

}
