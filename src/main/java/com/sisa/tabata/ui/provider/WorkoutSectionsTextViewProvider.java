package com.sisa.tabata.ui.provider;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.listener.editor.SectionTextViewClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTextViewLongClickListener;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.17..
 */
@Singleton
public class WorkoutSectionsTextViewProvider {

    private static final String TEXT_PATTERN = TabataApplication.getAppContext().getString(R.string.section_text_view_pattern);
    private static final int TEXT_VIEW_HEIGHT_DP = 80;

    @Inject
    private WorkoutSectionTotalTimeProvider workoutSectionTotalTimeProvider;
    @Inject
    private SectionTextViewClickListener sectionTextViewClickListener;
    @Inject
    private SectionTextViewLongClickListener sectionTextViewLongClickListener;

    public WorkoutSectionsTextViewProvider(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

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

    private TextView createTextView(WorkoutEditActivity workoutEditActivity) {
        return new TextView(workoutEditActivity);
    }

    private void setStyle(WorkoutEditActivity workoutEditActivity, Context context, TextView sectionTextView) {
        int margin = getDp(context, 6);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        sectionTextView.setWidth(workoutEditActivity.getWindowManager().getDefaultDisplay().getWidth());
        sectionTextView.setHeight(getDp(context, TEXT_VIEW_HEIGHT_DP));
        sectionTextView.setBackgroundResource(R.drawable.bg_section_list);
        sectionTextView.setLayoutParams(layoutParams);
        sectionTextView.setTextColor(Color.WHITE);
        sectionTextView.setClickable(true);
    }

    private Spanned getFormattedText(WorkoutSection currentSection) {
        int rounds = currentSection.getRounds();
        long work = currentSection.getWork();
        long rest = currentSection.getRest();
        String totalTime = workoutSectionTotalTimeProvider.getFormattedTotalTime(currentSection);
        return Html.fromHtml(String.format(TEXT_PATTERN, rounds, work, rest, totalTime));
    }

    private int getDp(Context context, int requiredDp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (requiredDp * scale + 0.5f);
    }

}
