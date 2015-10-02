package com.sisa.tabata.media.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.dao.SelectedMusicCheckboxDao;
import com.sisa.tabata.media.dao.SelectedMusicDao;
import com.sisa.tabata.media.transformer.SelectedCheckboxTransformer;

import android.util.Pair;

/**
 * Service for retrieving and updating the music selected by the user.
 *
 * @author Laca
 */
@Singleton
public class SelectedMusicService {

    @Inject
    private SelectedMusicCheckboxDao selectedMusicCheckboxDao;
    @Inject
    private SelectedMusicDao selectedMusicDao;
    @Inject
    private SelectedCheckboxTransformer selectedCheckboxTransformer;

    /**
     * Updates and stores the music selected by user.
     *
     * @param selectedCheckboxes list of selected checkboxes on music select activity
     */
    public void updateSelectedMusic(final List<Pair<String, String>> selectedCheckboxes) {
        selectedMusicCheckboxDao.persistCheckBoxState(selectedCheckboxes);
        selectedMusicDao.persistSelectedSongs(selectedCheckboxTransformer.transformCheckBoxesToSongs(selectedCheckboxes));
    }

    /**
     * Returns saved checkbox states.
     *
     * @return the list of previously selected checkboxes
     */
    public List<Pair<String, String>> getCheckBoxState() {
        return selectedMusicCheckboxDao.getCheckBoxState();
    }

}
