package com.sisa.tabata.ui.dialog;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.BuildConfig;
import com.sisa.tabata.R;
import com.sisa.tabata.changelog.dao.ChangeLogJsonDao;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Change log dialog.
 *
 * @author Laszlo Sisa
 */
public class ChangeLogDialog extends RoboDialogFragment {

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private ChangeLogJsonDao changeLogDao;
    @InjectView(R.id.changesTextView)
    private TextView changesTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_changelog, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle();
        setText();
    }

    private void setTitle() {
        String titlePattern = applicationContextProvider.getStringResource(R.string.change_log_whats_new_title_pattern);
        String versionName = BuildConfig.VERSION_NAME;
        getDialog().setTitle(String.format(titlePattern, versionName));
    }

    private void setText() {
        String changesText = changeLogDao.getChangeLogForVersionName(BuildConfig.VERSION_NAME);
        changesTextView.setText(Html.fromHtml(changesText));
    }

}
