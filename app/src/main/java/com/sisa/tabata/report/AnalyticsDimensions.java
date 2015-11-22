package com.sisa.tabata.report;

/**
 * Enumeration of custom analytics dimensions.
 *
 * @author Laszlo Sisa
 */
public enum AnalyticsDimensions {

    SESSION_ID("Session Id"),
    STACK_TRACE("Stack trace"),
    DENSITY("Density dpi"),
    SCREEN_HEIGHT("Screen height px"),
    SCREEN_WIDTH("Screen width px"),
    POST_ID("Post Id"),
    FACEBOOK_ERROR("Facebook Error");

    private final String name;

    AnalyticsDimensions(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
