package com.sisa.tabata.socialshare.facebook.provider;

import com.facebook.share.model.ShareLinkContent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

import android.net.Uri;

/**
 * Facebook share link content provider.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class FacebookShareLinkContentProvider {

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Returns a configured {@link ShareLinkContent}.
     *
     * @return {@link ShareLinkContent}
     */
    public ShareLinkContent getShareLinkContent() {
        return new ShareLinkContent.Builder()
                .setContentTitle(getString(R.string.app_name))
                .setContentUrl(Uri.parse(getString(R.string.app_play_url)))
                .setImageUrl(Uri.parse(getString(R.string.app_play_icon_url)))
                .build();
    }

    private String getString(final int id) {
        return applicationContextProvider.getStringResource(id);
    }

}
