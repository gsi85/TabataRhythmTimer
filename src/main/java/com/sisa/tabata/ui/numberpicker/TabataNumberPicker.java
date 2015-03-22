package com.sisa.tabata.ui.numberpicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by Laca on 2015.03.08..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TabataNumberPicker extends NumberPicker {

    private static final String MIN_VALUE_ATTRIBUTE_NAME = "minValue" ;
    private static final String MAX_VALUE_ATTRIBUTE_NAME = "maxValue" ;

    public TabataNumberPicker(Context context) {
        super(context);
    }

    public TabataNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public TabataNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }
    private void processAttributeSet(AttributeSet attrs) {
        this.setMinValue(attrs.getAttributeIntValue(null, MIN_VALUE_ATTRIBUTE_NAME, 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, MAX_VALUE_ATTRIBUTE_NAME, 0));
    }

}