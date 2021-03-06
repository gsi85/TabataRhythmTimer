package com.sisa.tabata;

import java.io.InputStream;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.content.Context;

/**
 * Provider of application {@link Context} and resources.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class ApplicationContextProvider {

    private final Context context;

    /**
     * DI constructor.
     *
     * @param context {@link Context}
     */
    @Inject
    public ApplicationContextProvider(final Context context) {
        this.context = context;
    }

    /**
     * Returns the application {@link Context}.
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return context;
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
    public int getIntResource(final int id) {
        return context.getResources().getInteger(id);
    }

    /**
     * Return a localized string from the application's package's
     * default string table.
     *
     * @param id Resource id for the string
     * @return the localized string
     */
    public String getStringResource(final int id) {
        return context.getString(id);
    }

    /**
     * Open a data stream for reading a raw resource.  This can only be used
     * with resources whose value is the name of an asset files -- that is, it can be
     * used to open drawable, sound, and raw resources; it will fail on string
     * and color resources.
     *
     * @param id The resource identifier to open, as generated by the appt
     *           tool.
     *
     * @return InputStream Access to the resource data.
     *
     * @throws NotFoundException Throws NotFoundException if the given ID does not exist.
     *
     */
    public InputStream openRawResource(final int id) {
        return context.getResources().openRawResource(id);
    }
}
