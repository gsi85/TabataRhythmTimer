package com.sisa.tabata.ui.listener.editor;

import android.content.Intent;
import android.view.View;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

/**
 * Created by Laca on 2015.03.17..
 */
@Singleton
public class SectionEditActionButtonClickListener implements View.OnClickListener {

    private static final String SAVE_ACTION = "save_action";
    private SectionEditActivity sectionEditActivity;

    @Override
    public void onClick(View view) {
        Intent workoutEditIntent = createIntent();
        if (isSaveAction(view)) {
            addWorkoutSectionToIntent(workoutEditIntent);
        }
        sectionEditActivity.startActivity(workoutEditIntent);
    }

    private Intent createIntent() {
        return new Intent(sectionEditActivity, WorkoutEditActivity.class);
    }

    private boolean isSaveAction(View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private void addWorkoutSectionToIntent(Intent workoutEditIntent) {
        workoutEditIntent.putExtra("workoutSection", sectionEditActivity.getWorkoutSection());
        workoutEditIntent.putExtra("workoutSectionId", sectionEditActivity.getIntent().getIntExtra("workoutSectionId", -1));
    }

    public void setSectionEditActivity(SectionEditActivity sectionEditActivity) {
        this.sectionEditActivity = sectionEditActivity;
    }
}
