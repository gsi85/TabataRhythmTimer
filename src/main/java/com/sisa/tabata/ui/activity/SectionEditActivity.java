package com.sisa.tabata.ui.activity;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.08..
 */
public class SectionEditActivity extends RoboActivity {

    private static final int NEW_SECTION_INDEX = -1;

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

    @InjectView(R.id.warmUpPickerLayout)
    private LinearLayout warmUpPickerLayout;
    @InjectView(R.id.roundCountPickerLayout)
    private LinearLayout roundCountPickerLayout;
    @InjectView(R.id.workPickerLayout)
    private LinearLayout workPickerLayout;
    @InjectView(R.id.restPickerLayout)
    private LinearLayout restPickerLayout;
    @InjectView(R.id.coolDownPickerLayout)
    private LinearLayout coolDownPickerLayout;

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
        setViewDependencies();
        setUpListeners();
    }

    private void setUpWorkoutSection() {
        int sectionIndex = getIntent().getIntExtra("workoutSectionId", -1);
        workoutSection = getIntent().getParcelableExtra("workoutSection");
        sectionEditAction.setText(NEW_SECTION_INDEX == sectionIndex ? R.string.form_section_new_action_text : R.string.form_section_edit_action_text);
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

    private void setViewDependencies() {
        sectionItemClickListener.setPickerLayouts(buildPickerLayoutList());
        timeNumberPickerValueChangeListener.setTextViewMap(buildTextViewMap());
        roundNumberPickerValueChangeListener.setRoundCountValue(roundCountValue);
        sectionTotalTimeChangeListener.setWorkoutSection(workoutSection);
        sectionTotalTimeChangeListener.setTotalTimeValue(totalTimeValue);
        sectionEditActionButtonClickListener.setSectionEditActivity(this);
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

    private List<LinearLayout> buildPickerLayoutList() {
        List<LinearLayout> pickerList = new ArrayList<>();
        pickerList.addAll(Arrays.asList(warmUpPickerLayout, roundCountPickerLayout, workPickerLayout, restPickerLayout, coolDownPickerLayout));
        return pickerList;
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

    public WorkoutSection getWorkoutSection() {
        return workoutSection;
    }
}
