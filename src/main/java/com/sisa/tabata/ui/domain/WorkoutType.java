package com.sisa.tabata.ui.domain;

import android.graphics.Color;

/**
 * Created by Laca on 2015.02.27..
 */
public enum WorkoutType {

    WARM_UP("WARM UP", Color.DKGRAY), WORK("WORK", Color.RED), REST("REST", Color.parseColor("#005200")), COOL_DOWN("COOL DOWN", Color.DKGRAY), FINISHED("FINISHED", Color.BLACK);

    private String displayText;
    private int backGroundColor;

    private WorkoutType(String displayText, int backGroundColor) {
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
