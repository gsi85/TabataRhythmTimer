package com.sisa.tabata.ui.listener.editor;

import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.provider.WorkoutSectionTotalTimeProvider;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.17..
 */
@Singleton
public class SectionTotalTimeChangeListener {

    @Inject
    private WorkoutSectionTotalTimeProvider workoutSectionTotalTimeProvider;
    private TextView totalTimeValue;
    private WorkoutSection workoutSection;

    public SectionTotalTimeChangeListener(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void setRounds(int rounds) {
        workoutSection.setRounds(rounds);
        updateTotalTimeView();
    }

    public void setTimeSection(WorkoutType workoutType, long duration) {
        updateWorkoutSection(workoutType, duration);
        updateTotalTimeView();
    }

    private void updateWorkoutSection(WorkoutType workoutType, long duration) {
        if (WorkoutType.WARM_UP == workoutType)
            workoutSection.setWarmUp(duration);
        else if (WorkoutType.WORK == workoutType)
            workoutSection.setWork(duration);
        else if (WorkoutType.REST == workoutType)
            workoutSection.setRest(duration);
        else if (WorkoutType.COOL_DOWN == workoutType)
            workoutSection.setCoolDown(duration);
    }

    private void updateTotalTimeView() {
        totalTimeValue.setText(workoutSectionTotalTimeProvider.getFormattedTotalTime(workoutSection));
    }


    public void setTotalTimeValue(TextView totalTimeValue) {
        this.totalTimeValue = totalTimeValue;
    }

    public void setWorkoutSection(WorkoutSection workoutSection) {
        this.workoutSection = workoutSection;
    }
}
