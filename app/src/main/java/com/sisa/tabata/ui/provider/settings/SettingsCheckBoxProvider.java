package com.sisa.tabata.ui.provider.settings;

import java.util.Map;

import com.google.inject.Inject;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.ui.listener.settings.SettingsCheckBoxClickListener;

import android.widget.CheckBox;

import roboguice.inject.ContextSingleton;

/**
 * Preferences checkbox state provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SettingsCheckBoxProvider {

    @Inject
    private PreferencesSource preferencesSource;
    @Inject
    private SettingsCheckBoxMapProvider settingsCheckBoxMapProvider;
    @Inject
    private SettingsCheckBoxClickListener settingsCheckBoxClickListener;

    /**
     * Sets checkbox states to reflect currently saved preferences.
     */
    public void setUpCheckBoxes() {
        for (Map.Entry<PreferenceKeys, CheckBox> currentCheckBoxEntry : settingsCheckBoxMapProvider.getCheckBoxMapping().entrySet()) {
            boolean checked = preferencesSource.is(currentCheckBoxEntry.getKey());
            CheckBox checkBox = currentCheckBoxEntry.getValue();
            checkBox.setChecked(checked);
            checkBox.setOnClickListener(settingsCheckBoxClickListener);
        }
    }

}
