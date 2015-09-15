package com.sisa.tabata.ui.listener.editor;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import android.view.View;
import android.widget.LinearLayout;
import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.dialog.DeleteWorkoutSectionDialog;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Section text view long click listener.
 *
 * @author Laszlo Sisa
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
        deleteWorkoutSectionDialog.showDeleteWorkoutSectionDialog(workoutEditActivity, workoutEditActivity.getEditedWorkout(), view,
                existingSectionsLayout);
        return true;
    }

    private WorkoutEditActivity getCheckedContext(final View view) {
        isInstanceOf(WorkoutEditActivity.class, view.getContentDescription(), "View context is not a WorkoutEditActivity");
        return (WorkoutEditActivity) view.getContext();
    }

}
