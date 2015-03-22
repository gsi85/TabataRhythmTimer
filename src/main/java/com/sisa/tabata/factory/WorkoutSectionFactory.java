package com.sisa.tabata.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.WorkoutSection;

/**
 * Created by Laca on 2015.03.16..
 */
@Singleton
public class WorkoutSectionFactory {

    public WorkoutSection create(){
        return new WorkoutSection();
    }
}
