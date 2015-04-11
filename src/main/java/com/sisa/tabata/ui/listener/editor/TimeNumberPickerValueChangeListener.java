package com.sisa.tabata.ui.listener.editor;

import java.util.HashMap;
import java.util.Map;

import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.util.TimeFormatter;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.16..
 */
@ContextSingleton
public class TimeNumberPickerValueChangeListener implements NumberPicker.OnValueChangeListener {

    private static final String MINUTE = "minute";
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int ONE = 1;

    @Inject
    private SectionTotalTimeChangeListener sectionTotalTimeChangeListener;
    @InjectView(R.id.totalTimeValue)
    private TextView totalTimeValue;
    @InjectView(R.id.warmUpValue)
    private TextView warmUpValue;
    @InjectView(R.id.workValue)
    private TextView workValue;
    @InjectView(R.id.restValue)
    private TextView restValue;
    @InjectView(R.id.coolDownValue)
    private TextView coolDownValue;

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        TextView relatedTextView = getRelatedTextViewToPicker(picker);
        int currentValue = getCurrentValue(relatedTextView);
        int deltaMultiplier = getMultiplier(picker);
        int delta = calculateDelta(oldVal, newVal, deltaMultiplier);
        int newValue = calculateNewValue(currentValue, delta);
        relatedTextView.setText(TimeFormatter.formatSecondsToMinuteSecond(newValue));
        sectionTotalTimeChangeListener.setTimeSection(totalTimeValue, WorkoutType.valueOf(relatedTextView.getTag().toString()), newValue);
    }

    private TextView getRelatedTextViewToPicker(NumberPicker picker) {
        return buildTextViewMap().get(picker.getId());
    }

    private int getCurrentValue(TextView relatedTextView) {
        return TimeFormatter.getSecondsFromMinuteSecond(relatedTextView.getText().toString());
    }

    private int getMultiplier(NumberPicker picker) {
        return MINUTE.equals(picker.getTag().toString()) ? SECONDS_IN_ONE_MINUTE : ONE;
    }

    private int calculateDelta(int oldVal, int newVal, int deltaMultiplier) {
        return (newVal - oldVal) * deltaMultiplier;
    }

    private int calculateNewValue(int currentValue, int delta) {
        return currentValue + delta;
    }

    private Map<Integer, TextView> buildTextViewMap() {
        Map<Integer, TextView> textViewMap = new HashMap<>();
        textViewMap.put(R.id.warmUpMinutePicker, warmUpValue);
        textViewMap.put(R.id.warmUpSecondPicker, warmUpValue);
        textViewMap.put(R.id.workMinutePicker, workValue);
        textViewMap.put(R.id.workSecondPicker, workValue);
        textViewMap.put(R.id.restMinutePicker, restValue);
        textViewMap.put(R.id.restSecondPicker, restValue);
        textViewMap.put(R.id.coolDownMinutePicker, coolDownValue);
        textViewMap.put(R.id.coolDownSecondPicker, coolDownValue);
        return textViewMap;
    }
}
