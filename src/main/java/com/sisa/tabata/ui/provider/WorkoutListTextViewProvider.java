package com.sisa.tabata.ui.provider;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
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

    private static final String TEXT_PATTERN = TabataApplication.getAppContext().getString(R.string.workout_text_view_pattern);

    @Inject
    private DatabaseDataService databaseDataService;
    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;
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
            sectionTextView.setText(getFormattedText(workout));
            sectionTextView.setOnClickListener(workoutTextViewClickListener);
            sectionTextView.setOnLongClickListener(workoutTextViewLongClickListener);
            existingWorkoutsLayout.addView(sectionTextView);
        }
    }

    private Spanned getFormattedText(Workout workout) {
        String name = workout.getName();
        String description = workout.getDescription();
        String totalTime = workoutTotalTimeProvider.getFormattedWorkoutTotalTime(workout);
        return Html.fromHtml(String.format(TEXT_PATTERN, name, description, totalTime));
    }

}
