package com.sisa.tabata.ui.provider.editor;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;

import android.content.Intent;

/**
 * Workout section update provider.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutSectionsUpdateProvider {

    public static final int NEW_SECTION_INDEX = -1;
    private static final String WORKOUT_SECTION = "workoutSection";
    private static final String WORKOUT_SECTION_ID = "workoutSectionId";
    private static final int DEFAULT_VALUE = -1;

    /**
     * Updates workout with edited section.
     *
     * @param workout {@link Workout}
     * @param workoutEditIntent {@link Intent}
     */
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
        if (sectionIndex == NEW_SECTION_INDEX) {
            addNewSection(workout, editedWorkoutSection);
        } else {
            updateSection(workout, editedWorkoutSection, sectionIndex);
        }
    }

    private void addNewSection(Workout workout, WorkoutSection editedWorkoutSection) {
        workout.getWorkoutSections().add(editedWorkoutSection);
    }

    private void updateSection(Workout workout, WorkoutSection editedWorkoutSection, int sectionIndex) {
        workout.getWorkoutSections().set(sectionIndex, editedWorkoutSection);
    }

    private WorkoutSection getWorkoutSectionFromIntent(Intent workoutEditIntent) {
        return workoutEditIntent.getParcelableExtra(WORKOUT_SECTION);
    }

    private int getWorkoutSectionIdFromIntent(Intent workoutEditIntent) {
        return workoutEditIntent.getIntExtra(WORKOUT_SECTION_ID, DEFAULT_VALUE);
    }

}
