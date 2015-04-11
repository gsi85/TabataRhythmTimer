package com.sisa.tabata.ui.listener.editor;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;

/**
 * Created by Laca on 2015.03.18..
 */
public class WorkoutEditTextEditorTextWatcher implements TextWatcher {

    private final Workout workout;
    private final View view;

    public WorkoutEditTextEditorTextWatcher(Workout workout, View view) {
        this.workout = workout;
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        if (view.getId() == R.id.workoutNameTextEditor) {
            workout.setName(sequence.toString());
        } else if (view.getId() == R.id.workoutDescriptionTextEditor) {
            workout.setDescription(sequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
