package com.sisa.tabata.media.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.dao.SelectMusicCheckboxDao;

import android.util.Pair;

/**
 * Service for retrieving and updating the music selected by the user.
 *
 * @author Laca
 */
@Singleton
public class SelectedMusicService {

    @Inject
    private SelectMusicCheckboxDao selectMusicCheckboxDao;

    /**
     * Updates and stores the music selected by user.
     *
     * @param selectedCheckboxes list of selected checkboxes on music select activity
     */
    public void updateSelectedMusic(final List<Pair<String, String>> selectedCheckboxes) {
        selectMusicCheckboxDao.persistCheckBoxState(selectedCheckboxes);
    }

    /**
     * Returns saved checkbox states.
     *
     * @return the list of previously selected checkboxes
     */
    public List<Pair<String, String>> getCheckBoxState() {
        return selectMusicCheckboxDao.getCheckBoxState();
    }

}
