package com.sisa.tabata.ui.listener.editor;

import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;

/**
 * Created by Laca on 2015.03.18..
 */
@Singleton
public class SectionTextViewLongClickListener implements View.OnLongClickListener {

    private Workout workout;
    private LinearLayout existingSectionsLayout;

    @Override
    public boolean onLongClick(View view) {
        removeWorkoutSection(view);
        updateTextViewTags(view);
        return true;
    }

    private void removeWorkoutSection(View view) {
        workout.getWorkoutSections().remove((int) view.getTag());
    }

    private void updateTextViewTags(View view) {
        existingSectionsLayout.removeView(view);
        for (int i = 0; i < existingSectionsLayout.getChildCount(); i++) {
            existingSectionsLayout.getChildAt(i).setTag(i);
        }
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void setExistingSectionsLayout(LinearLayout existingSectionsLayout) {
        this.existingSectionsLayout = existingSectionsLayout;
    }
}
