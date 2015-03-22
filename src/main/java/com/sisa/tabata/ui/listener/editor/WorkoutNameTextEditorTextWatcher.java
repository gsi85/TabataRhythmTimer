package com.sisa.tabata.ui.listener.editor;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;

/**
 * Created by Laca on 2015.03.18..
 */
@Singleton
public class WorkoutNameTextEditorTextWatcher implements TextWatcher {

    private Workout workout;

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        workout.setName(sequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
