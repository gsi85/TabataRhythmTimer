package com.sisa.tabata.report.domain;

import static com.sisa.tabata.validation.Assert.notNull;

import java.util.Collections;
import java.util.Map;

/**
 * Object describing an analytics entity.
 *
 * @author Laszlo Sisa
 */
public final class AnalyticsEntity {

    private final String eventName;
    private final Map<String, String> dimensions;
    private final String installationId;
    private final String operatingSystemVersion;
    private final int appBuildVersion;
    private final String appDisplayVersion;

    private AnalyticsEntity(final Builder builder) {
        eventName = builder.eventName;
        dimensions = Collections.unmodifiableMap(builder.dimensions);
        installationId = builder.installationId;
        operatingSystemVersion = builder.operatingSystemVersion;
        appBuildVersion = builder.appBuildVersion;
        appDisplayVersion = builder.appDisplayVersion;
    }

    public String getEventName() {
        return eventName;
    }

    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public String getInstallationId() {
        return installationId;
    }

    public String getOperatingSystemVersion() {
        return operatingSystemVersion;
    }

    public int getAppBuildVersion() {
        return appBuildVersion;
    }

    public String getAppDisplayVersion() {
        return appDisplayVersion;
    }

    /**
     * Builder for {@link AnalyticsEntity}.
     */
    public static class Builder {

        private String eventName;
        private Map<String, String> dimensions;
        private String installationId;
        private String operatingSystemVersion;
        private int appBuildVersion;
        private String appDisplayVersion;

        /**
         * Sets the event name.
         *
         * @param trackingEvents {@link TrackingEvents}
         * @return {@link Builder}
         */
        public Builder withTrackingEvent(final TrackingEvents trackingEvents) {
            this.eventName = trackingEvents.getName();
            return this;
        }

        /**
         * Sets the dimensions.
         *
         * @param dimensions map of dimensions
         * @return {@link Builder}
         */
        public Builder withDimensions(final Map<String, String> dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        /**
         * Sets the installation id.
         *
         * @param installationId installation id
         * @return {@link Builder}
         */
        public Builder withInstallationId(final String installationId) {
            this.installationId = installationId;
            return this;
        }

        /**
         * Sets the operating system version.
         *
         * @param operatingSystemVersion operating system version
         * @return {@link Builder}
         */
        public Builder withOperatingSystemVersion(final String operatingSystemVersion) {
            this.operatingSystemVersion = operatingSystemVersion;
            return this;
        }

        /**
         * Sets the app build version.
         *
         * @param appBuildVersion app build version
         * @return {@link Builder}
         */
        public Builder withAppBuildVersion(final int appBuildVersion) {
            this.appBuildVersion = appBuildVersion;
            return this;
        }

        /**
         * Sets the app display version.
         *
         * @param appDisplayVersion app display version
         * @return {@link Builder}
         */
        public Builder withAppDisplayVersion(final String appDisplayVersion) {
            this.appDisplayVersion = appDisplayVersion;
            return this;
        }


        /**
         * Builds a new {@link AnalyticsEntity} instance.
         *
         * @return {@link AnalyticsEntity}
         */
        public AnalyticsEntity build() {
            validate();
            return new AnalyticsEntity(this);
        }

        private void validate() {
            notNull(eventName, "eventName must not be null in AnalyticsEntity");
            notNull(dimensions, "dimensions must not be null in AnalyticsEntity");
        }
    }

}
