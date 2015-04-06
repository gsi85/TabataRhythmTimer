package com.sisa.tabata;

import android.app.Application;
import android.content.Context;
import com.google.inject.Inject;
import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.22..
 */
public class TabataApplication extends Application {

    //TODO: remove this
    private static Context context;

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.injectMembers(getApplicationContext(), this);
        applicationContextProvider.setContext(getApplicationContext());
        TabataApplication.context = getApplicationContext();
    }

    @Deprecated
    public static Context getAppContext() {
        return TabataApplication.context;
    }
}
