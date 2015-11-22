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
    CAUGHT_EXCEPTION("Caught Exception"),
    FACEBOOK_SHARE_SUCCESS("Facebook Share Success"),
    FACEBOOK_SHARE_CANCELLED("Facebook Share Cancelled"),
    FACEBOOK_SHARE_ERROR("Facebook Share Error"),
    TWITTER_WORKOUT_TWEET_OPENED("Twitter Workout Tweet Opened"),
    TWITTER_GENERIC_TWEET_OPENED("Twitter Generic Tweet Opened");


    private final String name;

    TrackingEvents(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
