package com.sisa.tabata.ui.listener.editor;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import roboguice.inject.ContextSingleton;
import android.view.View;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.activity.SectionEditActivity;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.provider.WorkoutTotalTimeProvider;

/**
 * Section total time change listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SectionTotalTimeChangeListener {

    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;

    /**
     * Sets the number of rounds.
     *
     * @param totalTimeValue {@link TextView} containing total time value
     * @param rounds number of rounds
     */
    public void setRounds(final TextView totalTimeValue, final int rounds) {
        SectionEditActivity sectionEditActivity = getCheckedContext(totalTimeValue);
        WorkoutSection workoutSection = sectionEditActivity.getWorkoutSection();
        workoutSection.setRounds(rounds);
        updateTotalTimeView(workoutSection, totalTimeValue);
    }

    /**
     * Set section time.
     *
     * @param totalTimeValue {@link TextView} containing total time value
     * @param workoutType {@link WorkoutType}
     * @param duration duration
     */
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
        isInstanceOf(SectionEditActivity.class, view.getContext(), "view is not a SectionEditActivity");
        return (SectionEditActivity) view.getContext();
    }

}
