package com.sisa.tabata.ui.listener.editor;

import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.16..
 */
@Singleton
public class RoundNumberPickerValueChangeListener implements NumberPicker.OnValueChangeListener {

    @Inject
    private SectionTotalTimeChangeListener sectionTotalTimeChangeListener;
    private TextView roundCountValue;

    public RoundNumberPickerValueChangeListener(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        roundCountValue.setText(Integer.toString(newVal));
        sectionTotalTimeChangeListener.setRounds(newVal);
    }

    public void setRoundCountValue(TextView roundCountValue) {
        this.roundCountValue = roundCountValue;
    }
}
