package com.sisa.tabata;

import com.google.inject.Singleton;

import android.content.Context;

/**
 * Provider of application {@link Context} and resources.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class ApplicationContextProvider {

    private Context context;

    /**
     * Returns the application {@link Context}.
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return context;
    }

    /**
     * Set the application {@link Context}.
     *
     * @param context {@link Context}
     */
    protected void setContext(final Context context) {
        this.context = context;
    }

    /**
     * Return an integer associated with a particular resource ID.
     *
     * @param id The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     *
     * @throws NotFoundException Throws NotFoundException if the given ID does not exist.
     *
     * @return Returns the integer value contained in the resource.
     */
    public int getIntResource(int id) {
        return context.getResources().getInteger(id);
    }

    /**
     * Return a localized string from the application's package's
     * default string table.
     *
     * @param id Resource id for the string
     */
    public String getStringResource(int id) {
        return context.getString(id);
    }
}
