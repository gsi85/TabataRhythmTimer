package com.sisa.tabata.ui.dialog;

import java.util.Calendar;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.BuildConfig;
import com.sisa.tabata.R;

/**
 * About dialog.
 *
 * @author Laszlo_Sisa
 */
public class AboutDialog extends RoboDialogFragment {

    @InjectView(R.id.aboutVersionName)
    private TextView versionNameTextView;
    @InjectView(R.id.aboutCopyRight)
    private TextView copyRightTextView;
    @InjectView(R.id.aboutCloseButton)
    private ImageButton closeImageButton;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_about, container, false);
    }


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle();
        setVersionName();
        setCopyRightText();
        setCloseButtonListener();
    }

    private void setTitle() {
        getDialog().setTitle(applicationContextProvider.getStringResource(R.string.app_name));
    }

    private void setVersionName() {
        versionNameTextView.setText(BuildConfig.VERSION_NAME);
    }

    private void setCopyRightText() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        copyRightTextView.setText(String.format(copyRightTextView.getText().toString(), currentYear));
    }

    private void setCloseButtonListener() {
        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dismiss();
            }
        });
    }

}
