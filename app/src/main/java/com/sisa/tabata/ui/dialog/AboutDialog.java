package com.sisa.tabata.ui.dialog;

import java.util.Calendar;

import com.facebook.share.widget.ShareButton;
import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.BuildConfig;
import com.sisa.tabata.R;
import com.sisa.tabata.socialshare.facebook.provider.FacebookShareLinkContentProvider;
import com.sisa.tabata.socialshare.twitter.provider.AnalyticsDecoratorTwitterComposer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

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
    @InjectView(R.id.rateAppButton)
    private ImageButton rateAppButton;
    @InjectView(R.id.facebookShareButton)
    private ShareButton facebookShareButton;
    @InjectView(R.id.twitterTweetButton)
    private Button tweetButton;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private FacebookShareLinkContentProvider facebookShareLinkContentProvider;
    @Inject
    private AnalyticsDecoratorTwitterComposer twitterComposer;

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
        setRateAppButtonListener();
        setTweetButtonListener();
        setupFaceBookShareContent();
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

    private void setRateAppButtonListener() {
        rateAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_play_url))));
            }
        });
    }

    private void setTweetButtonListener() {
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                twitterComposer.composeGenericTweet(view.getContext());
            }
        });
    }

    private void setupFaceBookShareContent() {
        facebookShareButton.setShareContent(facebookShareLinkContentProvider.getShareLinkContent());
    }

}
