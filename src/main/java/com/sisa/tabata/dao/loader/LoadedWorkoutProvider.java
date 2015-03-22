package com.sisa.tabata.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.helper.DatabaseHelper;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.workout.transformer.SerializedWorkoutTransformer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.01..
 */
@Singleton
public class LoadedWorkoutProvider {

    @Inject
    private SerializedWorkoutTransformer serializedWorkoutTransformer;
    @Inject
    private DatabaseHelper databaseHelper;
    private Workout workout;

    public LoadedWorkoutProvider(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void setLoadedWorkoutById(long id) {
        workout = databaseHelper.getWorkoutById(id);
    }

    public SerializedWorkout getLoadedSerializedWorkout() {
        if (workout == null) {
            loadLastUsedWorkout();
        }
        return serializedWorkoutTransformer.transform(workout);
    }

    private void loadLastUsedWorkout() {
        workout = getDummyWorkout();
    }

    public Workout getDummyWorkout() {
        Workout dummyWorkout = new Workout();
        dummyWorkout.setName("Dummy workout");
        dummyWorkout.setTimeUnit(TimeUnit.SECONDS);
        WorkoutSection workoutSection = new WorkoutSection();
        workoutSection.setWarmUp(5);
        workoutSection.setRounds(2);
        workoutSection.setWork(20);
        workoutSection.setRest(10);
        workoutSection.setCoolDown(5);
        dummyWorkout.setWorkoutSections(Arrays.asList(workoutSection));
        return dummyWorkout;
    }
}
