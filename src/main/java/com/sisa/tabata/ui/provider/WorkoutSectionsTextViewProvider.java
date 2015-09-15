package com.sisa.tabata.ui.provider;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.listener.editor.SectionTextViewClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTextViewLongClickListener;

/**
 * Workout sections text view provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class WorkoutSectionsTextViewProvider extends AbstractTextViewProvider {

    private static final int SECTION_TEXT_VIEW_PATTERN = R.string.section_text_view_pattern;

    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;
    @Inject
    private SectionTextViewClickListener sectionTextViewClickListener;
    @Inject
    private SectionTextViewLongClickListener sectionTextViewLongClickListener;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Provides workout section text views.
     *
     * @param editedWorkout {@link Workout}
     * @param workoutEditActivity {@link WorkoutEditActivity}
     * @param context {@link Context}
     * @param existingSectionsLayout {@link LinearLayout}
     */
    public void createSectionTextViews(Workout editedWorkout, WorkoutEditActivity workoutEditActivity, Context context,
            LinearLayout existingSectionsLayout) {
        for (int i = 0; i < editedWorkout.getWorkoutSections().size(); i++) {
            TextView sectionTextView = createTextView(workoutEditActivity);
            setStyle(workoutEditActivity, context, sectionTextView);
            sectionTextView.setTag(i);
            sectionTextView.setText(getFormattedText(editedWorkout.getWorkoutSections().get(i)));
            sectionTextView.setOnClickListener(sectionTextViewClickListener);
            sectionTextView.setOnLongClickListener(sectionTextViewLongClickListener);
            existingSectionsLayout.addView(sectionTextView);
        }
    }

    private Spanned getFormattedText(WorkoutSection currentSection) {
        int rounds = currentSection.getRounds();
        long work = currentSection.getWork();
        long rest = currentSection.getRest();
        String totalTime = workoutTotalTimeProvider.getFormattedSectionTotalTime(currentSection);
        return Html.fromHtml(String.format(applicationContextProvider.getStringResource(SECTION_TEXT_VIEW_PATTERN), rounds, work, rest, totalTime));
    }

}
