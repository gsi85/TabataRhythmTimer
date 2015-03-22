package com.sisa.tabata.ui.listener.editor;

import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.util.TimeFormatter;

import java.util.Map;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.16..
 */
@Singleton
public class TimeNumberPickerValueChangeListener implements NumberPicker.OnValueChangeListener {

    private static final String MINUTE = "minute";
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int ONE = 1;

    @Inject
    private SectionTotalTimeChangeListener sectionTotalTimeChangeListener;
    private Map<Integer, TextView> textViewMap;

    public TimeNumberPickerValueChangeListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        TextView relatedTextView = getRelatedTextViewToPicker(picker);
        int currentValue = getCurrentValue(relatedTextView);
        int deltaMultiplier = getMultiplier(picker);
        int delta = calculateDelta(oldVal, newVal, deltaMultiplier);
        int newValue = calculateNewValue(currentValue, delta);
        relatedTextView.setText(TimeFormatter.formatSecondsToMinuteSecond(newValue));
        sectionTotalTimeChangeListener.setTimeSection((WorkoutType) relatedTextView.getTag(), newValue);
    }

    private TextView getRelatedTextViewToPicker(NumberPicker picker) {
        return textViewMap.get(picker.getId());
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

    public void setTextViewMap(Map<Integer, TextView> textViewMap) {
        this.textViewMap = textViewMap;
    }
}
