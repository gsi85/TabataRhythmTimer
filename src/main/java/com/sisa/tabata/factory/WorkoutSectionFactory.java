package com.sisa.tabata.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.WorkoutSection;

/**
 * Factory for {@likn WorkoutSection}.
 *
 * @author Laszlo sisa
 */
@Singleton
public class WorkoutSectionFactory {

    /**
     * Creates a new {@link WorkoutSection}.
     *
     * @return {@link WorkoutSection}
     */
    public WorkoutSection create(){
        return new WorkoutSection();
    }
}
