package com.sisa.tabata.ui.listener.music;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.media.service.SelectedMusicService;
import com.sisa.tabata.ui.activity.MusicSelectActivity;
import com.sisa.tabata.ui.activity.WorkoutActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import roboguice.inject.ContextSingleton;

/**
 * Music select action button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MusicSelectActionButtonClickListener implements View.OnClickListener {

    private static final String SAVE_ACTION = "save_action";
    private static final String CHECKBOX_KEY_VALUE_DELIMITER = ":";
    private static final int SELECTED_ITEM_KEY_INDEX = 0;
    private static final int SELECTED_ITEM_VALUE_INDEX = 1;

    @Inject
    private SelectedMusicService selectedMusicService;

    @Override
    public void onClick(final View view) {
        MusicSelectActivity musicSelectActivity = getCheckedContext(view);
        if (isSaveAction(view)) {
            updatePlaylist(musicSelectActivity);
        }
        Intent workoutIntent = createIntent(musicSelectActivity);
        musicSelectActivity.startActivity(workoutIntent);
        musicSelectActivity.finish();
    }

    private MusicSelectActivity getCheckedContext(final View view) {
        isInstanceOf(MusicSelectActivity.class, view.getContext(), "View context is not a MusicSelectActivity");
        return (MusicSelectActivity) view.getContext();
    }

    private boolean isSaveAction(final View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private void updatePlaylist(final MusicSelectActivity musicSelectActivity) {
        LinearLayout audioItemsLayout = (LinearLayout) musicSelectActivity.findViewById(R.id.audioItemsLayout);
        List<Pair<String, String>> selectedItemsList = new ArrayList<>();
        searchChildForSelectedCheckbox(audioItemsLayout, selectedItemsList);
        selectedMusicService.updateSelectedMusic(selectedItemsList);
    }

    private void searchChildForSelectedCheckbox(final LinearLayout layout, final List<Pair<String, String>> selectedItems) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof LinearLayout) {
                searchChildForSelectedCheckbox((LinearLayout) child, selectedItems);
            } else if (child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                String[] checkboxValue = String.valueOf(child.getTag()).split(CHECKBOX_KEY_VALUE_DELIMITER);
                selectedItems.add(Pair.create(checkboxValue[SELECTED_ITEM_KEY_INDEX], checkboxValue[SELECTED_ITEM_VALUE_INDEX]));
            }
        }
    }

    @NonNull
    private Intent createIntent(final MusicSelectActivity musicSelectActivity) {
        return new Intent(musicSelectActivity, WorkoutActivity.class);
    }
}
