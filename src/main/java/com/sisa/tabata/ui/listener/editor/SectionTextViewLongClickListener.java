package com.sisa.tabata.ui.listener.editor;

import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.dialog.DeleteWorkoutSectionDialog;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.18..
 */
@ContextSingleton
public class SectionTextViewLongClickListener implements View.OnLongClickListener {

    @Inject
    private DeleteWorkoutSectionDialog deleteWorkoutSectionDialog;
    @InjectView(R.id.existingSectionsLayout)
    private LinearLayout existingSectionsLayout;

    @Override
    public boolean onLongClick(View view) {
        WorkoutEditActivity workoutEditActivity = getCheckedContext(view);
        deleteWorkoutSectionDialog.showDeleteWorkoutsectionDialog(workoutEditActivity, workoutEditActivity.getEditedWorkout(), view,
                existingSectionsLayout);
        return true;
    }

    private WorkoutEditActivity getCheckedContext(final View view) {
        //TODO: assert
        if (!(view.getContext() instanceof WorkoutEditActivity)) {
            throw new IllegalArgumentException("View context is not a WorkoutEditActivity");
        }
        return (WorkoutEditActivity) view.getContext();
    }

}
