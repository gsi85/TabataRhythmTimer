package com.sisa.tabata.ui.listener.editor;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutSectionFactory;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

import roboguice.inject.ContextSingleton;

/**
 * Section text view click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SectionTextViewClickListener implements View.OnClickListener {

    private static final int NEW_SECTION_INDEX = -1;
    private static final String NEW_WORKOUT_NAME = "newWorkout";

    @Inject
    private WorkoutSectionFactory workoutSectionFactory;

    @Override
    public void onClick(View view) {
        WorkoutEditActivity workoutEditActivity = getCheckedContext(view);
        Intent sectionEditIntent = new Intent(workoutEditActivity, SectionEditActivity.class);
        Integer sectionIndex = (Integer) view.getTag();
        WorkoutSection workoutSection = getWorkoutSection(workoutEditActivity, sectionIndex);
        sectionEditIntent.putExtra("workoutSection", workoutSection);
        sectionEditIntent.putExtra("workoutSectionId", sectionIndex);
        sectionEditIntent.putExtra(NEW_WORKOUT_NAME, workoutEditActivity.getIntent().getBooleanExtra(NEW_WORKOUT_NAME, false));
        workoutEditActivity.startActivity(sectionEditIntent);
    }

    private WorkoutSection getWorkoutSection(final WorkoutEditActivity workoutEditActivity, Integer sectionIndex) {
        return isNewWorkoutSection(sectionIndex) ? getNewWorkoutSection() : getSelectedWorkoutSection(workoutEditActivity, sectionIndex);
    }

    private boolean isNewWorkoutSection(Integer sectionIndex) {
        return NEW_SECTION_INDEX == sectionIndex;
    }

    private WorkoutSection getNewWorkoutSection() {
        return workoutSectionFactory.create();
    }

    private WorkoutSection getSelectedWorkoutSection(final WorkoutEditActivity workoutEditActivity, Integer sectionIndex) {
        return workoutEditActivity.getEditedWorkout().getWorkoutSections().get(sectionIndex);
    }

    private WorkoutEditActivity getCheckedContext(final View view) {
        isInstanceOf(WorkoutEditActivity.class, view.getContext(), "View context is not a WorkoutEditActivity");
        return (WorkoutEditActivity) view.getContext();
    }

}
