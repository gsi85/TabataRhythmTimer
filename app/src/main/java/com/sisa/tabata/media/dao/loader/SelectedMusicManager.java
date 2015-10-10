package com.sisa.tabata.media.dao.loader;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.service.SelectedMusicService;

import android.util.Pair;

/**
 * Holds the persisted checkbox state.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SelectedMusicManager {

    @Inject
    private SelectedMusicService selectedMusicService;

    private List<Pair<String, String>> selectedCheckboxes;

    /**
     * Returns the persisted checkbox states.
     *
     * @return the list of selected checkboxes
     */
    public List<Pair<String, String>> getSelectedCheckboxes() {
        return selectedCheckboxes;
    }

    /**
     * Loads the persisted checkbox states.
     */
    public void loadSelectedCheckboxes() {
        this.selectedCheckboxes = selectedMusicService.getCheckBoxState();
    }
}
