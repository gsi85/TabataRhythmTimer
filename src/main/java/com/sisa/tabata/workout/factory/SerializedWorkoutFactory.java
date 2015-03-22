package com.sisa.tabata.workout.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.SerializedWorkout;

/**
 * Created by Laca on 2015.02.27..
 */
@Singleton
public class SerializedWorkoutFactory {

    public SerializedWorkout create(){
        return new SerializedWorkout();
    }
}
