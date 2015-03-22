package com.sisa.tabata.workout.transformer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.workout.factory.SerializedWorkoutFactory;
import com.sisa.tabata.workout.factory.SerializedWorkoutSectionFactory;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.27..
 */
@Singleton
public class SerializedWorkoutTransformer {

    @Inject
    private SerializedWorkoutFactory serializedWorkoutFactory;
    @Inject
    private SerializedWorkoutSectionFactory serializedWorkoutSectionFactory;
    private long totalDurationSecs;

    public SerializedWorkoutTransformer() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public SerializedWorkout transform(Workout workout) {
        SerializedWorkout serializedWorkout = serializedWorkoutFactory.create();
        serializedWorkout.setTimeUnit(workout.getTimeUnit());
        serializedWorkout.setWorkoutName(workout.getName());
        processSections(workout, serializedWorkout);
        return serializedWorkout;
    }

    private void processSections(Workout workout, SerializedWorkout serializedWorkout) {
        totalDurationSecs = 0;
        int sectionCount = 0;
        List<SerializedWorkoutSection> serializedWorkoutSections = new ArrayList<>();
        for (WorkoutSection workoutSection : workout.getWorkoutSections()) {
            sectionCount++;
            serializedWorkoutSections.add(getWarmUpSection(sectionCount, workoutSection));
            serializedWorkoutSections.addAll(getWorkSections(sectionCount, workoutSection));
            serializedWorkoutSections.add(getCoolDownSection(sectionCount, workoutSection));
        }
        serializedWorkout.setSectionCount(sectionCount);
        serializedWorkout.setWorkoutSections(serializedWorkoutSections);
        serializedWorkout.setWorkoutDuration(totalDurationSecs);
    }

    private SerializedWorkoutSection getWarmUpSection(int sectionCount, WorkoutSection workoutSection) {
        return buildWorkoutSection(sectionCount, 1, workoutSection.getRounds(), workoutSection.getWarmUp(), WorkoutType.WARM_UP);
    }

    private List<SerializedWorkoutSection> getWorkSections(int sectionCount, WorkoutSection workoutSection) {
        List<SerializedWorkoutSection> serializedWorkSections = new ArrayList<>();
        long workDuration = workoutSection.getWork();
        long restDuration = workoutSection.getRest();
        for (int i = 1; i <= workoutSection.getRounds(); i++) {
            serializedWorkSections.add(buildWorkoutSection(sectionCount, i, workoutSection.getRounds(), workDuration, WorkoutType.WORK));
            serializedWorkSections.add(buildWorkoutSection(sectionCount, i, workoutSection.getRounds(), restDuration, WorkoutType.REST));
        }
        return serializedWorkSections;
    }

    private SerializedWorkoutSection getCoolDownSection(int sectionCount, WorkoutSection workoutSection) {
        return buildWorkoutSection(sectionCount, workoutSection.getRounds(), workoutSection.getRounds(), workoutSection.getCoolDown(), WorkoutType.COOL_DOWN);
    }

    private SerializedWorkoutSection buildWorkoutSection(int sectionCount, int roundCount, int totalRoundsInSection, long duration, WorkoutType workoutType) {
        SerializedWorkoutSection serializedWorkoutSection = serializedWorkoutSectionFactory.create();
        serializedWorkoutSection.setSectionCount(sectionCount);
        serializedWorkoutSection.setRoundCount(roundCount);
        serializedWorkoutSection.setWorkoutType(workoutType);
        serializedWorkoutSection.setStartTime(totalDurationSecs);
        totalDurationSecs = totalDurationSecs + duration;
        serializedWorkoutSection.setEndTime(totalDurationSecs);
        serializedWorkoutSection.setTotalRoundsInSection(totalRoundsInSection);
        return serializedWorkoutSection;
    }
}
