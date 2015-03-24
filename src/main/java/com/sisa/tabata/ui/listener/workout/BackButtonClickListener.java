package com.sisa.tabata.ui.listener.workout;

import android.content.Intent;
import android.widget.Toast;

import com.sisa.tabata.ui.activity.WorkoutActivity;

/**
 * Created by Laca on 2015.03.23..
 */
public class BackButtonClickListener {

    private int backButtonCount;

    public void onClick(WorkoutActivity workoutActivity) {
        if (isPressedTwice()) {
            exitApp(workoutActivity);
        } else {
            showFirstPressNotification(workoutActivity);
        }
    }

    private boolean isPressedTwice() {
        return backButtonCount >= 1;
    }

    private void exitApp(WorkoutActivity workoutActivity) {
        backButtonCount = 0;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        workoutActivity.startActivity(intent);
    }

    private void showFirstPressNotification(WorkoutActivity workoutActivity) {
        Toast.makeText(workoutActivity, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
        backButtonCount++;
    }


}
