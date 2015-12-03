package com.sisa.tabata.ui.listener.workout;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.dialog.VolumeControlDialog;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Volume button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class VolumeButtonClickListener extends AbstractWorkoutActivityButtonClickListener {

    private static final String DIALOG_VOLUME_CONTROL = "dialog_volume_control";

    @Inject
    private VolumeControlDialog volumeControlDialog;
    @InjectView(R.id.volumeButton)
    private ImageButton volumeButton;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        volumeControlDialog.setVolumeButton(volumeButton);
        volumeControlDialog.show(getCheckedFragmentManager(view), DIALOG_VOLUME_CONTROL);
    }

    private FragmentManager getCheckedFragmentManager(View view) {
        Context context = view.getContext();
        isInstanceOf(FragmentActivity.class, context, "View context is not a FragmentActivity");
        return ((FragmentActivity) context).getSupportFragmentManager();
    }

}
