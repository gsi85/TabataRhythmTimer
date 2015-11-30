package com.sisa.tabata.report.factory;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.BuildConfig;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.report.domain.AnalyticsEntity;
import com.sisa.tabata.report.domain.TrackingEvents;

import android.os.Build;

/**
 * Factory for creating an {@link AnalyticsEntity}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class AnalyticsEntityFactory {

    @Inject
    private PreferencesSource preferencesSource;

    /**
     * Creates a new, configured {@link AnalyticsEntity} instance.
     *
     * @param trackingEvent {@link TrackingEvents}
     * @param dimensions map of custom tracking parameters
     * @return {@link AnalyticsEntity}
     */
    public AnalyticsEntity createEntity(final TrackingEvents trackingEvent, final Map<String, String> dimensions) {
        return new AnalyticsEntity.Builder()
                .withTrackingEvent(trackingEvent)
                .withDimensions(dimensions)
                .withAppBuildVersion(getAppBuildVersion())
                .withAppDisplayVersion(getAppDisplayVersion())
                .withInstallationId(getInstallationId())
                .withOperatingSystemVersion(getOperatingSystemVersion())
                .build();
    }

    private int getAppBuildVersion() {
        return BuildConfig.VERSION_CODE;
    }

    private String getAppDisplayVersion() {
        return BuildConfig.VERSION_NAME;
    }

    private String getInstallationId() {
        return preferencesSource.getString(PreferenceKeys.INSTALLATION_ID);
    }

    private String getOperatingSystemVersion() {
        return Build.VERSION.RELEASE;
    }

}
