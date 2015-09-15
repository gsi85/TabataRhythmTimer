package com.sisa.tabata.ui.dialog;

import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Delete workout section dialog.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class DeleteWorkoutSectionDialog {

    /**
     * Displays workout delete dialog.
     *
     * @param context {@link Activity}
     * @param workout {@link Workout}
     * @param selectedSectionView {@link View}
     * @param existingSectionsLayout {@link LinearLayout}
     */
    public void showDeleteWorkoutSectionDialog(Activity context, Workout workout, View selectedSectionView, LinearLayout existingSectionsLayout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        dialogBuilder.setTitle(context.getString(R.string.dialog_delete_workout_section_title));
        dialogBuilder.setMessage(context.getString(R.string.dialog_delete_workout_section_message));
        dialogBuilder.setNegativeButton(R.string.dialog_button_no, getNegativeButtonListener());
        dialogBuilder.setPositiveButton(R.string.dialog_button_yes, getPositiveButtonListener(workout, selectedSectionView, existingSectionsLayout));
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

    private DialogInterface.OnClickListener getPositiveButtonListener(Workout workout, View selectedSectionView, LinearLayout existingSectionsLayout) {
        return new DeleteWorkoutSectionPositiveButtonListener(workout, selectedSectionView, existingSectionsLayout);
    }

    private final class DeleteWorkoutSectionPositiveButtonListener implements DialogInterface.OnClickListener {

        private final Workout workout;
        private final View selectedSectionView;
        private final LinearLayout existingSectionsLayout;

        private DeleteWorkoutSectionPositiveButtonListener(Workout workout, View selectedSectionView, LinearLayout existingSectionsLayout) {
            this.workout = workout;
            this.selectedSectionView = selectedSectionView;
            this.existingSectionsLayout = existingSectionsLayout;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            removeWorkoutSection(selectedSectionView);
            updateTextViewTags(selectedSectionView);
            dialog.cancel();
        }

        private void removeWorkoutSection(View view) {
            workout.getWorkoutSections().remove((int) view.getTag());
        }

        private void updateTextViewTags(View view) {
            existingSectionsLayout.removeView(view);
            for (int i = 0; i < existingSectionsLayout.getChildCount(); i++) {
                existingSectionsLayout.getChildAt(i).setTag(i);
            }
        }

    }

}
