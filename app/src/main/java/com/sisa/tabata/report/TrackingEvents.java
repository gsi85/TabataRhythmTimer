package com.sisa.tabata.report;

/**
 * Enumeration of tracking events.
 *
 * @author Laca
 */
public enum TrackingEvents {

    APP_OPENED_FIRST_TIME("First Time Opened"),
    APP_OPENED("Application Opened"),
    APP_CLOSED("Application Closed"),
    APP_CRASHED("Application Crashed"),
    CAUGHT_EXCEPTION("Caught Exception");

    private final String name;

    TrackingEvents(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
