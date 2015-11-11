package com.sisa.tabata.ui.listener.settings;

import com.google.inject.Inject;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.ui.provider.settings.SettingsCheckBoxMapProvider;

import android.view.View;
import android.widget.CheckBox;

import roboguice.inject.ContextSingleton;

/**
 * Settings checkbox click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SettingsCheckBoxClickListener implements View.OnClickListener {

    @Inject
    private PreferencesSource preferencesSource;
    @Inject
    private SettingsCheckBoxMapProvider settingsCheckBoxMapProvider;

    @Override
    public void onClick(final View checkbox) {
        PreferenceKeys modifiedPreference = getPreferenceKey(checkbox);
        updatePreferences(modifiedPreference, ((CheckBox) checkbox).isChecked());
    }

    private PreferenceKeys getPreferenceKey(final View checkbox) {
        return settingsCheckBoxMapProvider.getInvertedCheckBoxMapping().get(checkbox);
    }

    private void updatePreferences(final PreferenceKeys modifiedPreference, final boolean value) {
        preferencesSource.setBoolean(modifiedPreference, value);
    }
}
