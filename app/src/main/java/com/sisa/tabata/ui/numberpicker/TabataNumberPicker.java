package com.sisa.tabata.ui.numberpicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Customized number picker.
 *
 * @author Laszlo Sisa
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TabataNumberPicker extends NumberPicker {

    private static final String MIN_VALUE_ATTRIBUTE_NAME = "minValue";
    private static final String MAX_VALUE_ATTRIBUTE_NAME = "maxValue";

    /**
     * Constructor.
     *
     * @param context {@link Context}
     */
    public TabataNumberPicker(Context context) {
        super(context);
    }

    /**
     * Constructor.
     *
     * @param context {@link Context}
     * @param attributeSet {@link AttributeSet}
     */
    public TabataNumberPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        processAttributeSet(attributeSet);
    }

    /**
     * Constructor.
     *
     * @param context {@link Context}
     * @param attributeSet {@link AttributeSet}
     * @param defStyle reference to style resource
     */
    public TabataNumberPicker(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        processAttributeSet(attributeSet);
    }

    private void processAttributeSet(AttributeSet attrs) {
        this.setMinValue(attrs.getAttributeIntValue(null, MIN_VALUE_ATTRIBUTE_NAME, 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, MAX_VALUE_ATTRIBUTE_NAME, 0));
    }

}
