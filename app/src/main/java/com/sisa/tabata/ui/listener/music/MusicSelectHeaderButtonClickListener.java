package com.sisa.tabata.ui.listener.music;

import java.util.List;

import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;

/**
 * Music select header button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MusicSelectHeaderButtonClickListener implements View.OnClickListener {

    private static final int SELECTED_ITEM_MARGIN_BOTTOM = 5;
    private static final int UNSELECTED_ITEM_MARGIN_BOTTOM = 1;
    private static final String SELECTED_TEXT_COLOR = "#FFFFFF";
    private static final String SELECTED_TINT_COLOR = "#FF00DDFF";
    private static final String UNSELECTED_COLOR = "#CCCCCC";

    private List<Pair<LinearLayout, LinearLayout>> audioItemContainers;

    @Override
    public void onClick(final View view) {
        String viewTagToDisplay = String.valueOf(view.getTag());
        for (Pair<LinearLayout, LinearLayout> audioItemContainer : audioItemContainers) {
            setVisibility(viewTagToDisplay, audioItemContainer);
        }
    }

    private void setVisibility(final String viewTagToDisplay, final Pair<LinearLayout, LinearLayout> audioItemContainer) {
        if (viewTagToDisplay.equals(audioItemContainer.first.getTag())) {
            setStyle(audioItemContainer, true);
        } else {
            setStyle(audioItemContainer, false);
        }
    }

    private void setStyle(final Pair<LinearLayout, LinearLayout> audioItemContainer, final boolean selected) {
        audioItemContainer.second.setVisibility(selected ? View.VISIBLE : View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) audioItemContainer.first.getLayoutParams();
        layoutParams.setMargins(0, 0, 1, selected ? SELECTED_ITEM_MARGIN_BOTTOM : UNSELECTED_ITEM_MARGIN_BOTTOM);
        for (int i = 0; i < audioItemContainer.first.getChildCount(); i++) {
            View child = audioItemContainer.first.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextColor(Color.parseColor(selected ? SELECTED_TEXT_COLOR : UNSELECTED_COLOR));
            } else if (child instanceof ImageView) {
                ((ImageView) child).setColorFilter(Color.parseColor(selected ? SELECTED_TINT_COLOR : UNSELECTED_COLOR));
            }
        }
    }

    public void setAudioItemContainers(final List<Pair<LinearLayout, LinearLayout>> audioItemContainers) {
        this.audioItemContainers = audioItemContainers;
    }
}
