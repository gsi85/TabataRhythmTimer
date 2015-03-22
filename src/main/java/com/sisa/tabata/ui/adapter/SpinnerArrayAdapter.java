package com.sisa.tabata.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sisa.tabata.R;

/**
 * Created by Laca on 2015.03.04..
 */
public class SpinnerArrayAdapter<T> extends ArrayAdapter<T> {

    private final CharSequence titleText;

    public SpinnerArrayAdapter(Context context, T[] objects, CharSequence titleText) {
        super(context, R.layout.spinner_item_layout, objects);
        this.titleText = titleText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text = getTextView(view);
        text.setText(titleText);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text = getTextView(view);
        text.setBackgroundResource(R.drawable.bg_header_buttons);
        return view;
    }

    private TextView getTextView(View view) {
        return (TextView) view.findViewById(android.R.id.text1);
    }

}
