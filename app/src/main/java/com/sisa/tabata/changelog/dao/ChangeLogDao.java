package com.sisa.tabata.changelog.dao;

/**
 * Change log DAO.
 *
 * @author Laszlo Sisa
 */
public interface ChangeLogDao {

    /**
     * Retrieves the change log entry for the given version name.
     *
     * @param versionName version name
     * @return changes in given version
     */
    String getChangeLogForVersionName(final String versionName);

}
