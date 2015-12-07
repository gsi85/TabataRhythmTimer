package com.sisa.tabata.report.domain;

/**
 * Enumeration of tracking events.
 *
 * @author Laszlo Sisa
 */
public enum TrackingEvents {

    APP_OPENED_FIRST_TIME("First Time Opened"),
    APP_OPENED("Application Opened"),
    APP_CLOSED("Application Closed"),
    APP_CRASHED("Application Crashed"),
    APP_UPDATED("Application Updated"),
    CAUGHT_EXCEPTION("Caught Exception"),
    FACEBOOK_SHARE_SUCCESS("Facebook Share Success"),
    FACEBOOK_SHARE_CANCELLED("Facebook Share Cancelled"),
    FACEBOOK_SHARE_ERROR("Facebook Share Error"),
    TWITTER_WORKOUT_TWEET_OPENED("Twitter Workout Tweet Opened"),
    TWITTER_GENERIC_TWEET_OPENED("Twitter Generic Tweet Opened"),
    WORKOUT_STARTED("Workout Started"),
    WORKOUT_FINISHED("Workout Finished");

    private final String name;

    TrackingEvents(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
