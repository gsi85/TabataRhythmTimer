package com.sisa.tabata.report.parse;

import static com.parse.ParseAnalytics.trackEventInBackground;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.SessionIdProvider;
import com.sisa.tabata.report.TrackingEvents;
import com.sisa.tabata.validation.Validation;

import android.util.DisplayMetrics;

/**
 * Adapter for reporting events towards Parse.
 *
 * @author Laca
 */
@Singleton
public class ParseAnalyticsAdapter {

    private static final String SESSION_ID_KEY = "Session Id";
    private static final String STACK_TRACE_KEY = "Stack trace";
    private static final String DENSITY_KEY = "Density dpi";
    private static final String SCREEN_WIDTH = "Screen width px";
    private static final String SCREEN_HEIGHT = "Screen height px";

    @Inject
    private ApplicationContextProvider applicationContextProvider;
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
        trackEventInBackground(trackingEvent.getName(), augmentCommonData(dimensions));
    }

    private Map<String, String> augmentCommonData(final Map<String, String> dimensions) {
        return Validation.notEmpty(dimensions) ? addCommonData(dimensions) : addCommonData(new HashMap<String, String>());
    }

    private Map<String, String> addCommonData(final Map<String, String> dimensions) {
        DisplayMetrics displayMetrics = applicationContextProvider.getContext().getResources().getDisplayMetrics();
        dimensions.put(SESSION_ID_KEY, SessionIdProvider.SESSION_ID);
        dimensions.put(DENSITY_KEY, String.valueOf(displayMetrics.densityDpi));
        dimensions.put(SCREEN_HEIGHT, String.valueOf(displayMetrics.heightPixels));
        dimensions.put(SCREEN_WIDTH, String.valueOf(displayMetrics.widthPixels));
        return dimensions;
    }

    private String getStackTrace(final Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
