package com.sisa.tabata.ui.provider;

import android.content.Intent;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;

/**
 * Created by Laca on 2015.03.17..
 */
@Singleton
public class WorkoutSectionsUpdateProvider {

    public static final int NEW_SECTION_INDEX = -1;

    public void updateWorkoutWithEditedSection(Workout workout, Intent workoutEditIntent) {
        if (updateAvailable(workoutEditIntent)) {
            updateWorkoutSections(workout, workoutEditIntent);
        }
    }

    private boolean updateAvailable(Intent workoutEditIntent) {
        return getWorkoutSectionFromIntent(workoutEditIntent) != null;
    }

    private void updateWorkoutSections(Workout workout, Intent workoutEditIntent) {
        WorkoutSection editedWorkoutSection = getWorkoutSectionFromIntent(workoutEditIntent);
        int sectionIndex = getWorkoutSectionIdFromIntent(workoutEditIntent);
        if (sectionIndex == NEW_SECTION_INDEX)
            addNewSection(workout, editedWorkoutSection);
        else
            updateSection(workout, editedWorkoutSection, sectionIndex);
    }

    private void addNewSection(Workout workout, WorkoutSection editedWorkoutSection) {
        workout.getWorkoutSections().add(editedWorkoutSection);
    }

    private void updateSection(Workout workout, WorkoutSection editedWorkoutSection, int sectionIndex) {
        workout.getWorkoutSections().set(sectionIndex, editedWorkoutSection);
    }

    private WorkoutSection getWorkoutSectionFromIntent(Intent workoutEditIntent) {
        return workoutEditIntent.getParcelableExtra("workoutSection");
    }

    private int getWorkoutSectionIdFromIntent(Intent workoutEditIntent) {
        return workoutEditIntent.getIntExtra("workoutSectionId", -1);
    }

}
