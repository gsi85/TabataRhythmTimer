package com.sisa.tabata.ui.listener.editor;

import java.util.Arrays;
import java.util.List;

import com.sisa.tabata.R;
import com.sisa.tabata.util.DropDownAnim;

import android.view.View;
import android.widget.LinearLayout;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Section item click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SectionItemClickListener implements View.OnClickListener {

    @InjectView(R.id.warmUpPickerLayout)
    private LinearLayout warmUpPickerLayout;
    @InjectView(R.id.roundCountPickerLayout)
    private LinearLayout roundCountPickerLayout;
    @InjectView(R.id.workPickerLayout)
    private LinearLayout workPickerLayout;
    @InjectView(R.id.restPickerLayout)
    private LinearLayout restPickerLayout;
    @InjectView(R.id.coolDownPickerLayout)
    private LinearLayout coolDownPickerLayout;

    @Override
    public void onClick(View view) {
        setSelectedPickerVisible(view);
    }

    private void setSelectedPickerVisible(View view) {
        for (LinearLayout pickerLayout : buildPickerLayoutList()) {
            if (isSelectedItem(view, pickerLayout)) {
                flipVisibility(pickerLayout);
            } else {
                hidePicker(pickerLayout);
            }
        }
    }

    private List<LinearLayout> buildPickerLayoutList() {
        return Arrays.asList(warmUpPickerLayout, roundCountPickerLayout, workPickerLayout, restPickerLayout, coolDownPickerLayout);
    }

    private boolean isSelectedItem(View view, LinearLayout pickerLayout) {
        return view.getTag().equals(pickerLayout.getTag());
    }

    private void flipVisibility(LinearLayout pickerLayout) {
        if (pickerLayout.getVisibility() == View.GONE) {
            DropDownAnim.expand(pickerLayout);
        } else {
            DropDownAnim.collapse(pickerLayout);
        }
    }

    private void hidePicker(LinearLayout pickerLayout) {
        if (pickerLayout.getVisibility() != View.GONE) {
            DropDownAnim.collapse(pickerLayout);
        }
    }

}
