package com.sisa.tabata;

import android.app.Application;
import android.content.Context;

/**
 * Created by Laca on 2015.02.22..
 */
public class TabataApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        TabataApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return TabataApplication.context;
    }
}
