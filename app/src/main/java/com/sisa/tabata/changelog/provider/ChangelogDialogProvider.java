package com.sisa.tabata.changelog.provider;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.sisa.tabata.BuildConfig;
import com.sisa.tabata.changelog.dao.ChangeLogJsonDao;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.report.domain.AnalyticsDimensions;
import com.sisa.tabata.report.domain.TrackingEvents;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.sisa.tabata.ui.dialog.ChangeLogDialog;

import android.support.v4.app.FragmentActivity;

import roboguice.inject.ContextSingleton;

/**
 * Provider for change log dialog.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class ChangeLogDialogProvider {

    private static final String DIALOG_CHANGE_LOG = "dialog_change_log";

    @Inject
    private PreferencesSource preferencesSource;
    @Inject
    private ChangeLogJsonDao changeLogJsonDao;
    @Inject
    private ChangeLogDialog changeLogDialog;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;

    /**
     * Checks if change log has been shown already shown for current version, and displays it if not.
     *
     * @param context {@link FragmentActivity}
     */
    public void checkShowChangeLog(final FragmentActivity context) {
        if (notShowForCurrentVersion()) {
            changeLogDialog.show(context.getSupportFragmentManager(), DIALOG_CHANGE_LOG);
            reportUpdate();
        }
    }

    private boolean notShowForCurrentVersion() {
        return BuildConfig.VERSION_CODE != preferencesSource.getLong(PreferenceKeys.CHANGE_LOG_SHOWN_FOR_VERSION);
    }

    private void reportUpdate() {
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put(AnalyticsDimensions.UPDATE_TO_VERSION.getName(), BuildConfig.VERSION_NAME);
        parseAnalyticsAdapter.trackEvent(TrackingEvents.APP_UPDATED, dimensions);
    }

}
