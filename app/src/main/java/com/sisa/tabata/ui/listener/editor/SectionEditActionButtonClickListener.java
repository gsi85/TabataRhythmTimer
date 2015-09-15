package com.sisa.tabata.ui.listener.editor;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import android.content.Intent;
import android.view.View;

import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

import roboguice.inject.ContextSingleton;

/**
 * Section edit action button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SectionEditActionButtonClickListener implements View.OnClickListener {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final String SAVE_ACTION = "save_action";
    private static final String WORKOUT_SECTION_ID = "workoutSectionId";
    private static final String WORKOUT_SECTION = "workoutSection";

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
        isInstanceOf(SectionEditActivity.class, view.getContext(), "view is not a SectionEditActivity");
        return (SectionEditActivity) view.getContext();
    }

    private Intent createIntent(final SectionEditActivity sectionEditActivity) {
        return new Intent(sectionEditActivity, WorkoutEditActivity.class);
    }

    private boolean isSaveAction(View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private void addWorkoutSectionToIntent(final SectionEditActivity sectionEditActivity, Intent workoutEditIntent) {
        workoutEditIntent.putExtra(WORKOUT_SECTION, sectionEditActivity.getWorkoutSection());
        workoutEditIntent.putExtra(WORKOUT_SECTION_ID, sectionEditActivity.getIntent().getIntExtra(WORKOUT_SECTION_ID, -1));
        workoutEditIntent.putExtra(NEW_WORKOUT_NAME, sectionEditActivity.getIntent().getBooleanExtra(NEW_WORKOUT_NAME, false));
    }

}
