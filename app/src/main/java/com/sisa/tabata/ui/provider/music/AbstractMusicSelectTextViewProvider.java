package com.sisa.tabata.ui.provider.music;

import com.sisa.tabata.R;
import com.sisa.tabata.ui.activity.MusicSelectActivity;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Abstract music select text view provider.
 *
 * @author Laszlo Sisa
 */
public abstract class AbstractMusicSelectTextViewProvider {

    private static final float OFFSET = 0.5f;
    private static final int PADDING_ING_PIXEL = 8;

    /**
     * Creates a new {@link TextView}.
     *
     * @param musicSelectActivity {@link MusicSelectActivity} the context
     * @return {@link TextView}
     */
    protected TextView createTextView(final MusicSelectActivity musicSelectActivity) {
        return new TextView(musicSelectActivity);
    }

    /**
     * Sets text view's style.
     *
     * @param activity {@link Activity}
     * @param textView {@link TextView}
     */
    protected void setStyle(Activity activity, TextView textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 1);
        int padding = getDp(activity, PADDING_ING_PIXEL);
        textView.setWidth(activity.getWindowManager().getDefaultDisplay().getWidth());
        textView.setBackgroundResource(R.drawable.bg_header_buttons);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(padding, padding, padding, padding);
        textView.setClickable(true);
    }

    private int getDp(Context context, int requiredDp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (requiredDp * scale + OFFSET);
    }

}
