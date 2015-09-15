package com.sisa.tabata.workout.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;

/**
 * Facotry for {@link SerializedWorkoutSection}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SerializedWorkoutSectionFactory {

    /**
     * Creates a new {@link SerializedWorkoutSection} instance.
     *
     * @return {@link SerializedWorkoutSection}
     */
    public SerializedWorkoutSection create() {
        return new SerializedWorkoutSection();
    }

}
