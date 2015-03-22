package com.sisa.tabata.workout.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;

/**
 * Created by Laca on 2015.02.27..
 */
@Singleton
public class SerializedWorkoutSectionFactory {

    public SerializedWorkoutSection create(){
        return new SerializedWorkoutSection();
    }
}
