package com.sisa.tabata.report.parse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.parse.ParseObject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.SessionIdProvider;
import com.sisa.tabata.report.domain.AnalyticsDimensions;
import com.sisa.tabata.report.domain.AnalyticsEntity;
import com.sisa.tabata.report.domain.TrackingEvents;
import com.sisa.tabata.report.factory.AnalyticsEntityFactory;
import com.sisa.tabata.validation.Validation;

import android.util.DisplayMetrics;

/**
 * Adapter for reporting events towards Parse.
 *
 * @author Laca
 */
@Singleton
public class ParseAnalyticsAdapter {

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private AnalyticsEntityFactory analyticsEntityFactory;
    @Inject
    private AnalyticsEntityTransformer analyticsEntityTransformer;
    private boolean appOpenedReported;

    /**
     * Tracks application opened event.
     */
    public void trackAppOpenedEvent() {
        if (!appOpenedReported) {
            appOpenedReported = true;
            trackEvent(TrackingEvents.APP_OPENED, null);
        }
    }

    /**
     * Track application closed event.
     */
    public void trackAppClosedEvent() {
        trackEvent(TrackingEvents.APP_CLOSED, null);
    }

    /**
     * Tracks application crashed event.
     *
     * @param dimensions map of values to be reported.
     */
    public void trackAppCrashedEvent(final Map<String, String> dimensions) {
        trackEvent(TrackingEvents.APP_CRASHED, dimensions);
    }

    /**
     * Tracks caught exception.
     *
     * @param exception the {@link Exception} caught
     */
    public void trackException(final Exception exception) {
        Map<String, String> dimensions = new HashMap<>();
        String stackTrace = getStackTrace(exception);
        dimensions.put(AnalyticsDimensions.STACK_TRACE.getName(), stackTrace);
        trackEvent(TrackingEvents.CAUGHT_EXCEPTION, dimensions);
    }

    /**
     * Tracks a custom event.
     *
     * @param trackingEvent {@link TrackingEvents} the event to track
     * @param dimensions map of custom values to be reported.
     */
    public void trackEvent(final TrackingEvents trackingEvent, final Map<String, String> dimensions) {
        AnalyticsEntity analyticsEntity = analyticsEntityFactory.createEntity(trackingEvent, augmentCommonData(dimensions));
        ParseObject parseObject = analyticsEntityTransformer.transform(analyticsEntity);
        parseObject.saveInBackground();
    }

    private Map<String, String> augmentCommonData(final Map<String, String> dimensions) {
        return Validation.notEmpty(dimensions) ? addCommonData(dimensions) : addCommonData(new HashMap<String, String>());
    }

    private Map<String, String> addCommonData(final Map<String, String> dimensions) {
        DisplayMetrics displayMetrics = applicationContextProvider.getContext().getResources().getDisplayMetrics();
        dimensions.put(AnalyticsDimensions.SESSION_ID.getName(), SessionIdProvider.SESSION_ID);
        dimensions.put(AnalyticsDimensions.DENSITY.getName(), String.valueOf(displayMetrics.densityDpi));
        dimensions.put(AnalyticsDimensions.SCREEN_HEIGHT.getName(), String.valueOf(displayMetrics.heightPixels));
        dimensions.put(AnalyticsDimensions.SCREEN_WIDTH.getName(), String.valueOf(displayMetrics.widthPixels));
        return dimensions;
    }

    private String getStackTrace(final Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
