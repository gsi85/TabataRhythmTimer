package com.sisa.tabata.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.dao.service.DatabaseDataService;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.30..
 */
@Singleton
public class DeleteWorkoutDialog {

    @Inject
    private DatabaseDataService databaseDataService;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    public DeleteWorkoutDialog() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void showDeleteWorkoutDialog(Activity context, View selectedWorkoutView, LinearLayout existingWorkoutLayout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        dialogBuilder.setTitle(context.getString(R.string.dialog_delete_workout_title));
        dialogBuilder.setMessage(context.getString(R.string.dialog_delete_workout_message));
        dialogBuilder.setNegativeButton(R.string.dialog_button_no, getNegativeButtonListener());
        dialogBuilder.setPositiveButton(R.string.dialog_button_yes, getPositiveButtonListener(selectedWorkoutView, existingWorkoutLayout));
        dialogBuilder.show();
    }

    private DialogInterface.OnClickListener getNegativeButtonListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
    }

    private DialogInterface.OnClickListener getPositiveButtonListener(View selectedWorkoutView, LinearLayout existingWorkoutLayout) {
        return new DeleteWorkoutPositiveButtonListener(selectedWorkoutView, existingWorkoutLayout);
    }

    private class DeleteWorkoutPositiveButtonListener implements DialogInterface.OnClickListener {

        private final View selectedWorkoutView;
        private final LinearLayout existingWorkoutLayout;

        private DeleteWorkoutPositiveButtonListener(View selectedWorkoutView, LinearLayout existingWorkoutLayout) {
            this.selectedWorkoutView = selectedWorkoutView;
            this.existingWorkoutLayout = existingWorkoutLayout;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            int id = (int) selectedWorkoutView.getTag();
            databaseDataService.deleteWorkoutById(id);
            refreshLoadedWorkout(id);
            refreshWorkoutTextView(existingWorkoutLayout, selectedWorkoutView);
            dialog.cancel();
        }

        private void refreshLoadedWorkout(int id) {
            if (loadedWorkoutProvider.getLoadedWorkout().getId() == id) {
                loadedWorkoutProvider.loadFirstWorkoutInList();
            }
        }

        private void refreshWorkoutTextView(LinearLayout existingWorkoutLayout, View view) {
            existingWorkoutLayout.removeView(view);
        }
    }

}
