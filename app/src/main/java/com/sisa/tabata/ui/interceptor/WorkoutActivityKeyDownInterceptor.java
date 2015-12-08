package com.sisa.tabata.ui.interceptor;

import com.sisa.tabata.R;

import android.view.KeyEvent;
import android.widget.Spinner;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Workout activity key down interceptor.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class WorkoutActivityKeyDownInterceptor {

    @InjectView(R.id.mainMenuSpinner)
    private Spinner mainMenu;

    /**
     * Handles key down event.
     *
     * @param keyCode the key code
     */
    public void handle(final int keyCode) {
        if (isMenuKeyPressed(keyCode)) {
            openMainMenu();
        }
    }

    private boolean isMenuKeyPressed(final int keyCode) {
        return keyCode == KeyEvent.KEYCODE_MENU;
    }

    private void openMainMenu() {
        mainMenu.performClick();
    }

}
