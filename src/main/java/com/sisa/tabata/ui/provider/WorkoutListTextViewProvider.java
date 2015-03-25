package com.sisa.tabata.ui.provider;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.service.DatabaseDataService;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewClickListener;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewLongClickListener;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutListTextViewProvider extends AbstractTextViewProvider {

    @Inject
    private DatabaseDataService databaseDataService;
    @Inject
    private WorkoutTextViewClickListener workoutTextViewClickListener;
    @Inject
    private WorkoutTextViewLongClickListener workoutTextViewLongClickListener;

    public WorkoutListTextViewProvider() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void createWorkoutTextViews(WorkoutLoadActivity workoutLoadActivity, Context context, LinearLayout existingWorkoutsLayout) {
        for (Workout workout : databaseDataService.getAllWorkoutsSortedList()) {
            TextView sectionTextView = createTextView(workoutLoadActivity);
            setStyle(workoutLoadActivity, context, sectionTextView);
            sectionTextView.setTag(workout.getId());
            sectionTextView.setText(workout.getName());
            sectionTextView.setOnClickListener(workoutTextViewClickListener);
            sectionTextView.setOnLongClickListener(workoutTextViewLongClickListener);
            existingWorkoutsLayout.addView(sectionTextView);
        }
    }
}
