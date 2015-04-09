package com.sisa.tabata.ui.listener.workout;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ui.dialog.VolumeControlDialog;

/**
 * Created by Laca on 2015.03.26..
 */
@Singleton
public class VolumeButtonClickListener implements View.OnClickListener {

    @Inject
    private VolumeControlDialog volumeControlDialog;

    @Override
    public void onClick(View view) {
        volumeControlDialog.show(getCheckedFragmentManager(view), "dialog_volume_control");
    }

    private FragmentManager getCheckedFragmentManager(View view) {
        Context context = view.getContext();
        //TODO: replace with Assert
        if (!(context instanceof FragmentActivity)) {
            throw new IllegalArgumentException("View context is not a FragmentActivity");
        }
        return ((FragmentActivity) context).getSupportFragmentManager();
    }

}
