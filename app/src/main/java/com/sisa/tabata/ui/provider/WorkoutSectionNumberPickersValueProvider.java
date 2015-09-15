package com.sisa.tabata.ui.provider;

import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.WorkoutSection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Workout section number picker value provider.
 *
 * @author Laszlo sisa
 */
@Singleton
public class WorkoutSectionNumberPickersValueProvider {

    /**
     * Provides number picker values.
     *
     * @param workoutSection {@link WorkoutSection} data source
     * @return map of number picker values
     */
    public Map<Integer, Integer> getNumberPickerValues(WorkoutSection workoutSection) {
        Map<Integer, Integer> numberPickerValueMap = new HashMap<>();

        numberPickerValueMap.put(R.id.roundCountPicker, workoutSection.getRounds());

        numberPickerValueMap.put(R.id.warmUpMinutePicker, getMinutes(workoutSection.getWarmUp()));
        numberPickerValueMap.put(R.id.warmUpSecondPicker, getRemainingSeconds(workoutSection.getWarmUp()));

        numberPickerValueMap.put(R.id.workMinutePicker, getMinutes(workoutSection.getWork()));
        numberPickerValueMap.put(R.id.workSecondPicker, getRemainingSeconds(workoutSection.getWork()));

        numberPickerValueMap.put(R.id.restMinutePicker, getMinutes(workoutSection.getRest()));
        numberPickerValueMap.put(R.id.restSecondPicker, getRemainingSeconds(workoutSection.getRest()));

        numberPickerValueMap.put(R.id.coolDownMinutePicker, getMinutes(workoutSection.getCoolDown()));
        numberPickerValueMap.put(R.id.coolDownSecondPicker, getRemainingSeconds(workoutSection.getCoolDown()));

        return numberPickerValueMap;
    }

    private int getRemainingSeconds(long value) {
        return (int) (value - TimeUnit.MINUTES.toSeconds(getMinutes(value)));
    }

    private int getMinutes(long value) {
        return (int) TimeUnit.SECONDS.toMinutes(value);
    }
}
