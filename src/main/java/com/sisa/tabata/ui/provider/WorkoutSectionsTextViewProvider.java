package com.sisa.tabata.ui.provider;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.listener.editor.SectionTextViewClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTextViewLongClickListener;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.17..
 */
@ContextSingleton
public class WorkoutSectionsTextViewProvider extends AbstractTextViewProvider {

    private static final String TEXT_PATTERN = TabataApplication.getAppContext().getString(R.string.section_text_view_pattern);


    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;
    @Inject
    private SectionTextViewClickListener sectionTextViewClickListener;
    @Inject
    private SectionTextViewLongClickListener sectionTextViewLongClickListener;

    public void createSectionTextViews(Workout editedWorkout, WorkoutEditActivity workoutEditActivity, Context context, LinearLayout existingSectionsLayout) {
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
        return Html.fromHtml(String.format(TEXT_PATTERN, rounds, work, rest, totalTime));
    }

}
