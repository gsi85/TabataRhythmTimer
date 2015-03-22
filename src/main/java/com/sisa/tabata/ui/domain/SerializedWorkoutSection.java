package com.sisa.tabata.ui.domain;

/**
 * Created by Laca on 2015.02.27..
 */
public class SerializedWorkoutSection {

    private int sectionCount;
    private int roundCount;
    private int totalRoundsInSection;
    private long startTime;
    private long endTime;
    private WorkoutType workoutType;

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int getTotalRoundsInSection() {
        return totalRoundsInSection;
    }

    public void setTotalRoundsInSection(int totalRoundsInSection) {
        this.totalRoundsInSection = totalRoundsInSection;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }
}
