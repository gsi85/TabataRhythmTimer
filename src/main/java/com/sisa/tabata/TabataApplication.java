package com.sisa.tabata;

import roboguice.RoboGuice;
import android.app.Application;

import com.google.inject.Inject;

/**
 * Base class of application to maintain global state.
 *
 * @author Laszlo Sisa
 */
public class TabataApplication extends Application {

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.injectMembers(getApplicationContext(), this);
        applicationContextProvider.setContext(getApplicationContext());
    }

}
