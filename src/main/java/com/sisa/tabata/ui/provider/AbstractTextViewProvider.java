package com.sisa.tabata.ui.provider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sisa.tabata.R;

/**
 * Created by Laca on 2015.03.25..
 */
public abstract class AbstractTextViewProvider {

    private static final int TEXT_VIEW_HEIGHT_DP = 80;

    protected TextView createTextView(Activity workoutEditActivity) {
        return new TextView(workoutEditActivity);
    }

    protected void setStyle(Activity activity, Context context, TextView sectionTextView) {
        int margin = getDp(context, 6);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        return (int) (requiredDp * scale + 0.5f);
    }

}
