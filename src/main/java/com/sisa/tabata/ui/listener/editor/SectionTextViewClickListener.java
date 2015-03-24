package com.sisa.tabata.ui.listener.editor;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutSectionFactory;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.08..
 */
@Singleton
public class SectionTextViewClickListener implements View.OnClickListener {

    private static final int NEW_SECTION_INDEX = -1;
    private static final String NEW_WORKOUT_NAME = "newWorkout";

    @Inject
    private WorkoutSectionFactory workoutSectionFactory;
    private WorkoutEditActivity workoutEditActivity;

    public SectionTextViewClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onClick(View view) {
        Intent sectionEditIntent = new Intent(workoutEditActivity, SectionEditActivity.class);
        Integer sectionIndex = (Integer) view.getTag();
        WorkoutSection workoutSection = getWorkoutSection(sectionIndex);
        sectionEditIntent.putExtra("workoutSection", workoutSection);
        sectionEditIntent.putExtra("workoutSectionId", sectionIndex);
        sectionEditIntent.putExtra(NEW_WORKOUT_NAME, workoutEditActivity.getIntent().getBooleanExtra(NEW_WORKOUT_NAME, false));
        workoutEditActivity.startActivity(sectionEditIntent);
    }

    private WorkoutSection getWorkoutSection(Integer sectionIndex) {
        return isNewWorkoutSection(sectionIndex) ? getNewWorkoutSection() : getSelectedWorkoutSection(sectionIndex);
    }

    private boolean isNewWorkoutSection(Integer sectionIndex) {
        return NEW_SECTION_INDEX == sectionIndex;
    }

    private WorkoutSection getNewWorkoutSection() {
        return workoutSectionFactory.create();
    }

    private WorkoutSection getSelectedWorkoutSection(Integer sectionIndex) {
        return workoutEditActivity.getEditedWorkout().getWorkoutSections().get(sectionIndex);
    }

    public void setWorkoutEditActivity(WorkoutEditActivity workoutEditActivity) {
        this.workoutEditActivity = workoutEditActivity;
    }

}
