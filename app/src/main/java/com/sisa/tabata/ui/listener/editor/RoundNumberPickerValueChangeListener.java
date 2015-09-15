package com.sisa.tabata.ui.listener.editor;

import com.google.inject.Inject;
import com.sisa.tabata.R;

import android.widget.NumberPicker;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Round number picker value change listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class RoundNumberPickerValueChangeListener implements NumberPicker.OnValueChangeListener {

    @Inject
    private SectionTotalTimeChangeListener sectionTotalTimeChangeListener;
    @InjectView(R.id.roundCountValue)
    private TextView roundCountValue;
    @InjectView(R.id.totalTimeValue)
    private TextView totalTimeValue;

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        roundCountValue.setText(Integer.toString(newVal));
        sectionTotalTimeChangeListener.setRounds(totalTimeValue, newVal);
    }

}
