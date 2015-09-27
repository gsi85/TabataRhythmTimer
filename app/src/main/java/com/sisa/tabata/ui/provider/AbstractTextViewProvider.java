package com.sisa.tabata.ui.provider;

import com.sisa.tabata.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Abstract text view provider.
 *
 * @author Laszlo Sisa
 */
public abstract class AbstractTextViewProvider {

    private static final int TEXT_VIEW_HEIGHT_DP = 80;
    private static final int REQUIRED_DP = 6;
    private static final float OFFSET = 0.5f;

    /**
     * Creates a new {@link TextView} instance.
     *
     * @param activity {@link Activity}
     * @return {@link TextView}
     */
    protected TextView createTextView(Activity activity) {
        return new TextView(activity);
    }

    /**
     * Sets text view's style.
     *
     * @param activity {@link Activity}
     * @param context {@link Context}
     * @param sectionTextView {@link TextView}
     */
    protected void setStyle(Activity activity, Context context, TextView sectionTextView) {
        int margin = getDp(context, REQUIRED_DP);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        sectionTextView.setWidth(activity.getWindowManager().getDefaultDisplay().getWidth());
        sectionTextView.setHeight(getDp(context, TEXT_VIEW_HEIGHT_DP));
        sectionTextView.setBackgroundResource(R.drawable.bg_section_list);
        sectionTextView.setLayoutParams(layoutParams);
        sectionTextView.setTextColor(Color.WHITE);
        sectionTextView.setClickable(true);
    }

    private int getDp(Context context, int requiredDp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (requiredDp * scale + OFFSET);
    }

}
