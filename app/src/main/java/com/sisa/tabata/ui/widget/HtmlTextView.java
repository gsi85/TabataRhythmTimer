package com.sisa.tabata.ui.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Widget for representing a {@link TextView} with HTML text.
 *
 * @author Laszlo_Sisa
 */
public class HtmlTextView extends TextView {

    public HtmlTextView(final Context context) {
        super(context);
        init();
    }

    public HtmlTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HtmlTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText(Html.fromHtml(getText().toString()));
    }

}
