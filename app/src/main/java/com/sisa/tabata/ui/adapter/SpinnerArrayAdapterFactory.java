package com.sisa.tabata.ui.adapter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.listener.workout.MainMenuOnClickListener;
import com.sisa.tabata.ui.listener.workout.SpinnerTextOnClickListener;

import android.content.Context;

/**
 * Factory for {@link SpinnerArrayAdapter}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SpinnerArrayAdapterFactory {

    @Inject
    private MainMenuOnClickListener mainMenuOnClickListener;
    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    @Inject
    private SpinnerTextOnClickListener spinnerTextOnClickListener;

    /**
     * Creates a configured {@link SpinnerArrayAdapter}.
     *
     * @param context {@link Context}
     * @return {@link SpinnerArrayAdapter}
     */
    public SpinnerArrayAdapter create(final Context context) {
        CharSequence titleText = loadedWorkoutProvider.getLoadedWorkout().getName();
        String[] menuItems = applicationContextProvider.getContext().getResources().getStringArray(R.array.main_menu_items);
        SpinnerArrayAdapter<String> adapter = new SpinnerArrayAdapter<>(context, menuItems, titleText, mainMenuOnClickListener, spinnerTextOnClickListener);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        return adapter;
    }
}
