package com.sisa.tabata.ui.activity;

import java.util.Map;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.listener.editor.RoundNumberPickerValueChangeListener;
import com.sisa.tabata.ui.listener.editor.SectionEditActionButtonClickListener;
import com.sisa.tabata.ui.listener.editor.SectionItemClickListener;
import com.sisa.tabata.ui.listener.editor.SectionTotalTimeChangeListener;
import com.sisa.tabata.ui.listener.editor.TimeNumberPickerValueChangeListener;
import com.sisa.tabata.ui.numberpicker.TabataNumberPicker;
import com.sisa.tabata.ui.provider.WorkoutSectionLabelsProvider;
import com.sisa.tabata.ui.provider.WorkoutSectionNumberPickersValueProvider;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Workout section edit form activity.
 *
 * @author Laszlo Sisa
 */
public class SectionEditActivity extends RoboActivity {

    private static final String WORKOUT_SECTION_ID = "workoutSectionId";
    private static final String WORKOUT_SECTION = "workoutSection";

    @InjectView(R.id.warmUpLayout)
    private LinearLayout warmUpLayout;
    @InjectView(R.id.roundCountLayout)
    private LinearLayout roundCountLayout;
    @InjectView(R.id.workLayout)
    private LinearLayout workLayout;
    @InjectView(R.id.restLayout)
    private LinearLayout restLayout;
    @InjectView(R.id.coolDownLayout)
    private LinearLayout coolDownLayout;

    @InjectView(R.id.totalTimeValue)
    private TextView totalTimeValue;
    @InjectView(R.id.warmUpValue)
    private TextView warmUpValue;
    @InjectView(R.id.roundCountValue)
    private TextView roundCountValue;
    @InjectView(R.id.workValue)
    private TextView workValue;
    @InjectView(R.id.restValue)
    private TextView restValue;
    @InjectView(R.id.coolDownValue)
    private TextView coolDownValue;

    @InjectView(R.id.warmUpMinutePicker)
    private TabataNumberPicker warmUpMinutePicker;
    @InjectView(R.id.warmUpSecondPicker)
    private TabataNumberPicker warmUpSecondPicker;
    @InjectView(R.id.roundCountPicker)
    private TabataNumberPicker roundCountPicker;
    @InjectView(R.id.workMinutePicker)
    private TabataNumberPicker workMinutePicker;
    @InjectView(R.id.workSecondPicker)
    private TabataNumberPicker workSecondPicker;
    @InjectView(R.id.restMinutePicker)
    private TabataNumberPicker restMinutePicker;
    @InjectView(R.id.restSecondPicker)
    private TabataNumberPicker restSecondPicker;
    @InjectView(R.id.coolDownMinutePicker)
    private TabataNumberPicker coolDownMinutePicker;
    @InjectView(R.id.coolDownSecondPicker)
    private TabataNumberPicker coolDownSecondPicker;

    @InjectView(R.id.sectionEditAction)
    private TextView sectionEditAction;
    @InjectView(R.id.saveSectionButton)
    private ImageButton saveSectionButton;
    @InjectView(R.id.cancelSectionButton)
    private ImageButton cancelSectionButton;

    @Inject
    private SectionItemClickListener sectionItemClickListener;
    @Inject
    private TimeNumberPickerValueChangeListener timeNumberPickerValueChangeListener;
    @Inject
    private RoundNumberPickerValueChangeListener roundNumberPickerValueChangeListener;
    @Inject
    private SectionTotalTimeChangeListener sectionTotalTimeChangeListener;
    @Inject
    private SectionEditActionButtonClickListener sectionEditActionButtonClickListener;
    @Inject
    private WorkoutSectionLabelsProvider workoutSectionLabelsProvider;
    @Inject
    private WorkoutSectionNumberPickersValueProvider workoutSectionNumberPickersValueProvider;
    private WorkoutSection workoutSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_edit);
        setUpWorkoutSection();
        setUpTags();
        setUpListeners();
    }

    @Override
    public void onBackPressed() {
        cancelSectionButton.performClick();
    }

    private void setUpWorkoutSection() {
        int newWorkoutId = getApplicationContext().getResources().getInteger(R.integer.new_workout_id);
        int sectionIndex = getIntent().getIntExtra(WORKOUT_SECTION_ID, newWorkoutId);
        workoutSection = getIntent().getParcelableExtra(WORKOUT_SECTION);
        sectionEditAction.setText(newWorkoutId == sectionIndex ? R.string.form_section_new_action_text : R.string.form_section_edit_action_text);
        setTextViewValues();
        setNumberPickerValues();
    }

    private void setTextViewValues() {
        Map<Integer, String> textValueMap = workoutSectionLabelsProvider.getTextViewLabels(workoutSection);
        totalTimeValue.setText(textValueMap.get(totalTimeValue.getId()));
        warmUpValue.setText(textValueMap.get(warmUpValue.getId()));
        roundCountValue.setText(textValueMap.get(roundCountValue.getId()));
        workValue.setText(textValueMap.get(workValue.getId()));
        restValue.setText(textValueMap.get(restValue.getId()));
        coolDownValue.setText(textValueMap.get(coolDownValue.getId()));
    }

    private void setNumberPickerValues() {
        Map<Integer, Integer> pickerValueMap = workoutSectionNumberPickersValueProvider.getNumberPickerValues(workoutSection);
        warmUpMinutePicker.setValue(pickerValueMap.get(warmUpMinutePicker.getId()));
        warmUpSecondPicker.setValue(pickerValueMap.get(warmUpSecondPicker.getId()));
        roundCountPicker.setValue(pickerValueMap.get(roundCountPicker.getId()));
        workMinutePicker.setValue(pickerValueMap.get(workMinutePicker.getId()));
        workSecondPicker.setValue(pickerValueMap.get(workSecondPicker.getId()));
        restMinutePicker.setValue(pickerValueMap.get(restMinutePicker.getId()));
        restSecondPicker.setValue(pickerValueMap.get(restSecondPicker.getId()));
        coolDownMinutePicker.setValue(pickerValueMap.get(coolDownMinutePicker.getId()));
        coolDownSecondPicker.setValue(pickerValueMap.get(coolDownSecondPicker.getId()));
    }

    private void setUpTags() {
        warmUpValue.setTag(WorkoutType.WARM_UP);
        workValue.setTag(WorkoutType.WORK);
        restValue.setTag(WorkoutType.REST);
        coolDownValue.setTag(WorkoutType.COOL_DOWN);
    }

    private void setUpListeners() {
        warmUpLayout.setOnClickListener(sectionItemClickListener);
        roundCountLayout.setOnClickListener(sectionItemClickListener);
        workLayout.setOnClickListener(sectionItemClickListener);
        restLayout.setOnClickListener(sectionItemClickListener);
        coolDownLayout.setOnClickListener(sectionItemClickListener);
        warmUpMinutePicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        warmUpSecondPicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        workMinutePicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        workSecondPicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        restMinutePicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        restSecondPicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        coolDownMinutePicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        coolDownSecondPicker.setOnValueChangedListener(timeNumberPickerValueChangeListener);
        roundCountPicker.setOnValueChangedListener(roundNumberPickerValueChangeListener);
        saveSectionButton.setOnClickListener(sectionEditActionButtonClickListener);
        cancelSectionButton.setOnClickListener(sectionEditActionButtonClickListener);
    }

    public WorkoutSection getWorkoutSection() {
        return workoutSection;
    }
}
