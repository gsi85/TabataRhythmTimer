package com.sisa.tabata.ui.listener.editor;

import android.view.View;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.provider.WorkoutTotalTimeProvider;

import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.17..
 */
@ContextSingleton
public class SectionTotalTimeChangeListener {

    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;

    public void setRounds(final TextView totalTimeValue, final int rounds) {
        SectionEditActivity sectionEditActivity = getCheckedContext(totalTimeValue);
        WorkoutSection workoutSection = sectionEditActivity.getWorkoutSection();
        workoutSection.setRounds(rounds);
        updateTotalTimeView(workoutSection, totalTimeValue);
    }

    public void setTimeSection(final TextView totalTimeValue, WorkoutType workoutType, long duration) {
        SectionEditActivity sectionEditActivity = getCheckedContext(totalTimeValue);
        WorkoutSection workoutSection = sectionEditActivity.getWorkoutSection();
        updateWorkoutSection(workoutSection, workoutType, duration);
        updateTotalTimeView(workoutSection, totalTimeValue);
    }

    private void updateWorkoutSection(final WorkoutSection workoutSection, WorkoutType workoutType, long duration) {
        if (WorkoutType.WARM_UP == workoutType)
            workoutSection.setWarmUp(duration);
        else if (WorkoutType.WORK == workoutType)
            workoutSection.setWork(duration);
        else if (WorkoutType.REST == workoutType)
            workoutSection.setRest(duration);
        else if (WorkoutType.COOL_DOWN == workoutType)
            workoutSection.setCoolDown(duration);
    }

    private void updateTotalTimeView(final WorkoutSection workoutSection, final TextView totalTimeValue) {
        totalTimeValue.setText(workoutTotalTimeProvider.getFormattedSectionTotalTime(workoutSection));
    }

    private SectionEditActivity getCheckedContext(final View view) {
        //TODO: assert
        if (!(view.getContext() instanceof SectionEditActivity)) {
            throw new IllegalArgumentException("View context is not a WorkoutEditActivity");
        }
        return (SectionEditActivity) view.getContext();
    }

}
