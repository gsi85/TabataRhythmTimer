package com.sisa.tabata.ui.listener.workout;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laca on 2015.03.07..
 */
@Singleton
public class MainMenuOnSelectedListener implements AdapterView.OnItemSelectedListener {

    private Map<CharSequence, Intent> activitiesMap;
    private WorkoutActivity workoutActivity;
    private boolean initialFireFiltered;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        startActivity(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void startActivity(int position) {
        if (initialFireFiltered) {
            Intent activityToStart = activitiesMap.get(getSelectedItemText(position));
            if (activityToStart != null) {
                initialFireFiltered = false;
                workoutActivity.startActivity(activityToStart);
            }
        }
        else{
            initialFireFiltered = true;
        }
    }

    private CharSequence getSelectedItemText(int position) {
        String[] menuItems = TabataApplication.getAppContext().getResources().getStringArray(R.array.main_menu_items);
        return menuItems[position];
    }

    public void setWorkoutActivity(WorkoutActivity workoutActivity) {
        this.workoutActivity = workoutActivity;
        fillUpActivitiesMAp();
    }


    private void fillUpActivitiesMAp() {
        activitiesMap = new HashMap<>();
        activitiesMap.put("EDIT", new Intent(workoutActivity, WorkoutEditActivity.class));
        activitiesMap.put("NEW", new Intent(workoutActivity, WorkoutEditActivity.class));
        //LOAD
    }

}
