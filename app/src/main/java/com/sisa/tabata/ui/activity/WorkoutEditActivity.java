package com.sisa.tabata.ui.activity;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.EditedWorkoutProvider;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.listener.editor.SectionTextViewClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTextViewLongClickListener;
import com.sisa.tabata.ui.listener.editor.WorkoutEditActionButtonClickListener;
import com.sisa.tabata.ui.listener.editor.WorkoutEditTextEditorTextWatcher;
import com.sisa.tabata.ui.provider.WorkoutSectionsTextViewProvider;
import com.sisa.tabata.ui.provider.WorkoutSectionsUpdateProvider;
import com.sisa.tabata.ui.provider.WorkoutTotalTimeProvider;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Workout edit form activity.
 *
 * @author Laszlo Sisa
 */
public class WorkoutEditActivity extends RoboActivity {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final String TOTAL_TIME_PATTERN = "%s: %s";

    @InjectView(R.id.existingSectionsLayout)
    private LinearLayout existingSectionsLayout;
    @InjectView(R.id.newSectionTextView)
    private TextView newSectionTextView;
    @InjectView(R.id.totalTimeHeader)
    private TextView totalTimeHeader;
    @InjectView(R.id.workoutNameTextEditor)
    private EditText workoutNameTextEditor;
    @InjectView(R.id.workoutDescriptionTextEditor)
    private EditText workoutDescriptionTextEditor;
    @InjectView(R.id.saveButton)
    private ImageButton saveButton;
    @InjectView(R.id.cancelButton)
    private ImageButton cancelButton;
    @InjectView(R.id.workoutEditAction)
    private TextView workoutEditAction;
    @InjectView(R.id.workoutEditNotificationView)
    private TextView workoutEditNotificationView;

    @Inject
    private SectionTextViewClickListener sectionTextViewClickListener;
    @Inject
    private WorkoutSectionsUpdateProvider workoutSectionsUpdateProvider;
    @Inject
    private WorkoutSectionsTextViewProvider workoutSectionsTextViewProvider;
    @Inject
    private SectionTextViewLongClickListener sectionTextViewLongClickListener;
    @Inject
    private WorkoutEditActionButtonClickListener workoutEditActionButtonClickListener;
    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;
    @Inject
    private EditedWorkoutProvider editedWorkoutProvider;

    private Workout editedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_edit);
        setUpEditedWorkout();
        setUpListeners();
        setUpViewDependencies();
    }

    @Override
    public void onBackPressed() {
        cancelButton.performClick();
    }

    @Override
    protected void onResume() {
        super.onStart();
        String notificationText = getString(R.string.notification_long_press_to_delete_section);
        new NotificationDisplayTimer(workoutEditNotificationView, notificationText, getResources().getInteger(R.integer.long_notification_duration))
                .start();
    }

    private void setUpEditedWorkout() {
        boolean newWorkout = getIntent().getBooleanExtra(NEW_WORKOUT_NAME, false);
        workoutEditAction.setText(newWorkout ? R.string.form_workout_new_action_text : R.string.form_workout_edit_action_text);
        editedWorkout = editedWorkoutProvider.getEditedWorkout();
        workoutSectionsUpdateProvider.updateWorkoutWithEditedSection(editedWorkout, getIntent());
        workoutSectionsTextViewProvider.createSectionTextViews(editedWorkout, this, getApplicationContext(), existingSectionsLayout);
        workoutNameTextEditor.setText(editedWorkout.getName());
        workoutDescriptionTextEditor.setText(editedWorkout.getDescription());
        totalTimeHeader.setText(String.format(TOTAL_TIME_PATTERN, getString(R.string.form_workout_total_time_label),
                workoutTotalTimeProvider.getFormattedWorkoutTotalTime(editedWorkout)));
    }

    private void setUpViewDependencies() {
        newSectionTextView.setTag(getResources().getInteger(R.integer.new_workout_id));
    }

    private void setUpListeners() {
        newSectionTextView.setOnClickListener(sectionTextViewClickListener);
        workoutNameTextEditor.addTextChangedListener(new WorkoutEditTextEditorTextWatcher(editedWorkout, workoutNameTextEditor));
        workoutDescriptionTextEditor.addTextChangedListener(new WorkoutEditTextEditorTextWatcher(editedWorkout, workoutDescriptionTextEditor));
        saveButton.setOnClickListener(workoutEditActionButtonClickListener);
        cancelButton.setOnClickListener(workoutEditActionButtonClickListener);
    }

    public Workout getEditedWorkout() {
        return editedWorkout;
    }
}
