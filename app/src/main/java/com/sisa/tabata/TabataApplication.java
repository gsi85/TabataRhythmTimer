package com.sisa.tabata;

import com.google.inject.Inject;

import android.app.Application;

import roboguice.RoboGuice;

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
