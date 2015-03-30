package com.sisa.tabata.ui.listener.editor;

import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.dialog.DeleteWorkoutSectionDialog;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.18..
 */
@Singleton
public class SectionTextViewLongClickListener implements View.OnLongClickListener {

    @Inject
    private DeleteWorkoutSectionDialog deleteWorkoutSectionDialog;
    private Workout workout;
    private LinearLayout existingSectionsLayout;
    private WorkoutEditActivity workoutEditActivity;

    public SectionTextViewLongClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public boolean onLongClick(View view) {
        deleteWorkoutSectionDialog.showDeleteWorkoutsectionDialog(workoutEditActivity, workout, view, existingSectionsLayout);
        return true;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void setExistingSectionsLayout(LinearLayout existingSectionsLayout) {
        this.existingSectionsLayout = existingSectionsLayout;
    }

    public void setWorkoutEditActivity(WorkoutEditActivity workoutEditActivity) {
        this.workoutEditActivity = workoutEditActivity;
    }

}
