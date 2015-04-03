package com.sisa.tabata.domain;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Domain objects describing a workout.
 *
 * @author Laszlo Sisa
 */
public class Workout {

    private int id;
    private String name;
    private List<WorkoutSection> workoutSections;
    private TimeUnit timeUnit;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutSection> getWorkoutSections() {
        return workoutSections;
    }

    public void setWorkoutSections(List<WorkoutSection> workoutSections) {
        this.workoutSections = workoutSections;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
