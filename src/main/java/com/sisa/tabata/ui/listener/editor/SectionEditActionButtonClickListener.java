package com.sisa.tabata.ui.listener.editor;

import android.content.Intent;
import android.view.View;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.17..
 */
@ContextSingleton
public class SectionEditActionButtonClickListener implements View.OnClickListener {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final String SAVE_ACTION = "save_action";

    @Override
    public void onClick(View view) {
        SectionEditActivity sectionEditActivity = getCheckedContext(view);
        Intent workoutEditIntent = createIntent(sectionEditActivity);
        if (isSaveAction(view)) {
            addWorkoutSectionToIntent(sectionEditActivity, workoutEditIntent);
        }
        sectionEditActivity.startActivity(workoutEditIntent);
        sectionEditActivity.finish();
    }

    private SectionEditActivity getCheckedContext(final View view) {
        //TODO: Assert
        if (!(view.getContext() instanceof SectionEditActivity)) {
            throw new IllegalArgumentException("View is not a SectionEditActivity");
        }
        return (SectionEditActivity) view.getContext();
    }

    private Intent createIntent(final SectionEditActivity sectionEditActivity) {
        return new Intent(sectionEditActivity, WorkoutEditActivity.class);
    }

    private boolean isSaveAction(View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private void addWorkoutSectionToIntent(final SectionEditActivity sectionEditActivity, Intent workoutEditIntent) {
        workoutEditIntent.putExtra("workoutSection", sectionEditActivity.getWorkoutSection());
        workoutEditIntent.putExtra("workoutSectionId", sectionEditActivity.getIntent().getIntExtra("workoutSectionId", -1));
        workoutEditIntent.putExtra(NEW_WORKOUT_NAME, sectionEditActivity.getIntent().getBooleanExtra(NEW_WORKOUT_NAME, false));
    }

}
