package com.sisa.tabata.ui.domain;

import android.graphics.Color;

/**
 * Enumeration of possible workout section types.
 *
 * @author Laszlo Sisa
 */
public enum WorkoutType {

    WARM_UP("WARM UP", Color.DKGRAY),
    WORK("WORK", Color.RED),
    REST("REST", Color.parseColor("#005200")),
    COOL_DOWN("COOL DOWN", Color.DKGRAY),
    FINISHED("FINISHED", Color.BLACK);

    private String displayText;
    private int backGroundColor;

    WorkoutType(String displayText, int backGroundColor) {
        this.displayText = displayText;
        this.backGroundColor = backGroundColor;
    }

    public String getDisplayText() {
        return displayText;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }
}
