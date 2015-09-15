package com.sisa.tabata.workout.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.SerializedWorkout;

/**
 * Factory for {@link SerializedWorkout}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SerializedWorkoutFactory {

    /**
     * Creates a new {@link SerializedWorkout} instance.
     *
     * @return {@link SerializedWorkout}
     */
    public SerializedWorkout create() {
        return new SerializedWorkout();
    }

}
