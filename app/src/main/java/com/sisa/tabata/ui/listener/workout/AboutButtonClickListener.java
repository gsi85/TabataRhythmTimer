package com.sisa.tabata.ui.listener.workout;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.google.inject.Inject;
import com.sisa.tabata.ui.dialog.AboutDialog;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * About button click listener.
 *
 * @author Laszlo_Sisa
 */
@ContextSingleton
public class AboutButtonClickListener extends AbstractWorkoutActivityButtonClickListener {

    private static final String DIALOG_ABOUT = "dialog_about";

    @Inject
    private AboutDialog aboutDialog;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        aboutDialog.show(getCheckedFragmentManager(view), DIALOG_ABOUT);
    }

    private FragmentManager getCheckedFragmentManager(View view) {
        Context context = view.getContext();
        isInstanceOf(FragmentActivity.class, context, "View context is not a FragmentActivity");
        return ((FragmentActivity) context).getSupportFragmentManager();
    }

}
