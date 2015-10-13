package com.sisa.tabata.report.parse;

import static com.parse.ParseAnalytics.trackEventInBackground;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import com.sisa.tabata.SessionIdProvider;
import com.sisa.tabata.report.TrackingEvents;
import com.sisa.tabata.validation.Validation;

/**
 * Adapter for reporting events towards Parse.
 *
 * @author Laca
 */
@Singleton
public class ParseAnalyticsAdapter {

    private static final String SESSION_ID_KEY = "Session Id";
    private static final Map<String, String> SESSION_ID_MAP = Collections.singletonMap(SESSION_ID_KEY, SessionIdProvider.SESSION_ID);
    private static final String STACK_TRACE_KEY = "Stack trace";
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
     * Track application closed event;
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
        dimensions.put(STACK_TRACE_KEY, stackTrace);
        trackEvent(TrackingEvents.CAUGHT_EXCEPTION, dimensions);
    }

    /**
     * Tracks a custom event.
     *
     * @param trackingEvent {@link TrackingEvents} the event to track
     * @param dimensions map of custom values to be reported.
     */
    public void trackEvent(final TrackingEvents trackingEvent, final Map<String, String> dimensions) {
        trackEventInBackground(trackingEvent.getName(), augmentSessionId(dimensions));
    }

    private Map<String, String> augmentSessionId(final Map<String, String> dimensions) {
        return Validation.notEmpty(dimensions) ? addSessionId(dimensions) : SESSION_ID_MAP;
    }

    private Map<String, String> addSessionId(final Map<String, String> dimensions) {
        dimensions.put(SESSION_ID_KEY, SessionIdProvider.SESSION_ID);
        return dimensions;
    }

    private String getStackTrace(final Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
