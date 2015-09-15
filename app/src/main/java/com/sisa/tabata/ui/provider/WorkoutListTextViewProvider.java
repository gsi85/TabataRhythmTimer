package com.sisa.tabata.ui.provider;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewClickListener;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewLongClickListener;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;

/**
 * Provider for workout list text view.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class WorkoutListTextViewProvider extends AbstractTextViewProvider {

    private static final int WORKOUT_TEXT_VIEW_PATTERN = R.string.workout_text_view_pattern;

    @Inject
    private WorkoutDao workoutDao;
    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;
    @Inject
    private WorkoutTextViewClickListener workoutTextViewClickListener;
    @Inject
    private WorkoutTextViewLongClickListener workoutTextViewLongClickListener;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Creates workout text list text view.
     *
     * @param workoutLoadActivity {@link WorkoutLoadActivity}
     * @param context {@link Context}
     * @param existingWorkoutsLayout {@link LinearLayout}
     */
    public void createWorkoutTextViews(WorkoutLoadActivity workoutLoadActivity, Context context, LinearLayout existingWorkoutsLayout) {
        for (Workout workout : workoutDao.getAllWorkoutsSortedList()) {
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
        return Html.fromHtml(String.format(applicationContextProvider.getStringResource(WORKOUT_TEXT_VIEW_PATTERN), name, description, totalTime));
    }

}
