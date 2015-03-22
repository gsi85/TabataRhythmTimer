package com.sisa.tabata.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.EditedWorkoutHolder;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.listener.editor.SectionTextViewClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTextViewLongClickListener;
import com.sisa.tabata.ui.listener.editor.WorkoutEditActionButtonClickListener;
import com.sisa.tabata.ui.listener.editor.WorkoutNameTextEditorTextWatcher;
import com.sisa.tabata.ui.provider.WorkoutSectionsTextViewProvider;
import com.sisa.tabata.ui.provider.WorkoutSectionsUpdateProvider;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.07..
 */
public class WorkoutEditActivity extends RoboActivity {

    private static final int NEW_SECTION_INDEX = -1;

    @InjectView(R.id.existingSectionsLayout)
    private LinearLayout existingSectionsLayout;
    @InjectView(R.id.newSectionTextView)
    private TextView newSectionTextView;
    @InjectView(R.id.workoutNameTextEditor)
    private EditText workoutNameTextEditor;
    @InjectView(R.id.saveButton)
    private ImageButton saveButton;

    @Inject
    private SectionTextViewClickListener sectionTextViewClickListener;
    @Inject
    private WorkoutSectionsUpdateProvider workoutSectionsUpdateProvider;
    @Inject
    private WorkoutSectionsTextViewProvider workoutSectionsTextViewProvider;
    @Inject
    private WorkoutNameTextEditorTextWatcher workoutNameTextEditorTextWatcher;
    @Inject
    private SectionTextViewLongClickListener sectionTextViewLongClickListener;
    @Inject
    private WorkoutEditActionButtonClickListener workoutEditActionButtonClickListener;
    @Inject
    private EditedWorkoutHolder editedWorkoutHolder;
    private Workout editedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_edit);
        setUpEditedWorkout();
        setUpViewDependencies();
        setUpListeners();
    }

    private void setUpEditedWorkout() {
        editedWorkout = editedWorkoutHolder.getEditedWorkout();
        workoutSectionsUpdateProvider.updateWorkoutWithEditedSection(editedWorkout, getIntent());
        workoutSectionsTextViewProvider.createSectionTextViews(editedWorkout, this, getApplicationContext(), existingSectionsLayout);
        workoutNameTextEditor.setText(editedWorkout.getName());
    }

    private void setUpViewDependencies() {
        sectionTextViewClickListener.setWorkoutEditActivity(this);
        newSectionTextView.setTag(NEW_SECTION_INDEX);
        workoutNameTextEditorTextWatcher.setWorkout(editedWorkout);
        sectionTextViewLongClickListener.setWorkout(editedWorkout);
        sectionTextViewLongClickListener.setExistingSectionsLayout(existingSectionsLayout);
        workoutEditActionButtonClickListener.setWorkout(editedWorkout);
        workoutEditActionButtonClickListener.setWorkoutEditActivity(this);
    }

    private void setUpListeners() {
        newSectionTextView.setOnClickListener(sectionTextViewClickListener);
        workoutNameTextEditor.addTextChangedListener(workoutNameTextEditorTextWatcher);
        saveButton.setOnClickListener(workoutEditActionButtonClickListener);
    }

    public Workout getEditedWorkout() {
        return editedWorkout;
    }
}
