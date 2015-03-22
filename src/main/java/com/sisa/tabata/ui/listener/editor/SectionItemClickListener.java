package com.sisa.tabata.ui.listener.editor;

import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Singleton;
import com.sisa.tabata.util.DropDownAnim;

import java.util.List;

/**
 * Created by Laca on 2015.03.08..
 */
@Singleton
public class SectionItemClickListener implements View.OnClickListener {

    private List<LinearLayout> pickerLayouts;

    @Override
    public void onClick(View view) {
        setSelectedPickerVisible(view);
    }

    private void setSelectedPickerVisible(View view) {
        for (LinearLayout pickerLayout : pickerLayouts) {
            if (isSelectedItem(view, pickerLayout)) {
                flipVisibility(pickerLayout);
            } else {
                hidePicker(pickerLayout);
            }
        }
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

    public void setPickerLayouts(List<LinearLayout> pickerLayouts) {
        this.pickerLayouts = pickerLayouts;
    }
}
