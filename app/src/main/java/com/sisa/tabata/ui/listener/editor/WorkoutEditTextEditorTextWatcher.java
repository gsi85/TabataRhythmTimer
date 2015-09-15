package com.sisa.tabata.ui.listener.editor;

import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Workout edit text editor watcher.
 *
 * @author Laszlo Sisa
 */
public class WorkoutEditTextEditorTextWatcher implements TextWatcher {

    private final Workout workout;
    private final View view;

    /**
     * DI constructor.
     *
     * @param workout {@link Workout}
     * @param view {@link View}
     */
    public WorkoutEditTextEditorTextWatcher(Workout workout, View view) {
        this.workout = workout;
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        int viewId = view.getId();
        if (viewId == R.id.workoutNameTextEditor) {
            workout.setName(sequence.toString());
        } else if (viewId == R.id.workoutDescriptionTextEditor) {
            workout.setDescription(sequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
