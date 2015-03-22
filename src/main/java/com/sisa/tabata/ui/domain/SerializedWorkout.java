package com.sisa.tabata.ui.domain;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Laca on 2015.02.27..
 */
public class SerializedWorkout {

    private String workoutName;
    private TimeUnit timeUnit;
    private List<SerializedWorkoutSection> workoutSections;
    private long workoutDuration;
    private int sectionCount;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public List<SerializedWorkoutSection> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<SerializedWorkoutSection> workoutSections) {
        this.workoutSections = workoutSections;
    }

    public long getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(long workoutDuration) {
        this.workoutDuration = workoutDuration;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }
}
