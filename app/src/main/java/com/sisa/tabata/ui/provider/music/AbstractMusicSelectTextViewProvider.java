package com.sisa.tabata.ui.provider.music;

import com.google.inject.Inject;
import com.sisa.tabata.media.dao.loader.SelectedMusicManager;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Abstract music select text view provider.
 *
 * @author Laszlo Sisa
 */
public abstract class AbstractMusicSelectTextViewProvider {

    private static final String CHECKBOX_TAG_FORMAT_PATTERN = "%s:%s";
    private static final float OFFSET = 0.5f;
    private static final int PADDING_ING_PIXEL = 8;
    private static final LinearLayout.LayoutParams ITEM_LAYOUT_PARAM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
    private static final LinearLayout.LayoutParams ITEM_CONTAINER_LAYOUT_PARAM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    static {
        ITEM_CONTAINER_LAYOUT_PARAM.setMargins(0, 0, 0, 1);
    }

    @Inject
    private SelectedMusicManager selectedMusicManager;

    /**
     * Adds view to container.
     *
     * @param container     {@link LinearLayout}
     * @param formattedText the formatted text of the text view
     * @param category      audio item's category
     * @param valueKey      audio item'S key
     */
    protected void addView(final LinearLayout container, final Spanned formattedText, final String category, final String valueKey) {
        Context context = container.getContext();
        TextView textView = new TextView(context);
        CheckBox checkBox = new CheckBox(context);
        LinearLayout itemLayout = createItemLayout(context);
        setItemStyle(context, checkBox);
        setItemStyle(context, textView);
        checkBox.setGravity(Gravity.CENTER_VERTICAL);
        checkBox.setTag(String.format(CHECKBOX_TAG_FORMAT_PATTERN, category, valueKey));
        checkBox.setChecked(isChecked(category, valueKey));
        itemLayout.addView(checkBox);
        itemLayout.addView(textView);
        textView.setText(formattedText);
        container.addView(itemLayout);
    }

    private LinearLayout createItemLayout(final Context context) {
        LinearLayout itemLayout = new LinearLayout(context);
        itemLayout.setLayoutParams(ITEM_CONTAINER_LAYOUT_PARAM);
        itemLayout.setBackgroundColor(Color.BLACK);
        return itemLayout;
    }

    private void setItemStyle(Context context, View view) {
        int padding = getDp(context, PADDING_ING_PIXEL);
        view.setLayoutParams(ITEM_LAYOUT_PARAM);
        view.setBackgroundColor(Color.BLACK);
        view.setPadding(padding, padding, padding, padding);
    }

    private int getDp(Context context, int requiredDp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (requiredDp * scale + OFFSET);
    }

    private boolean isChecked(final String category, final String valueKey) {
        return selectedMusicManager.getSelectedCheckboxes().contains(Pair.create(category, valueKey));
    }

}
