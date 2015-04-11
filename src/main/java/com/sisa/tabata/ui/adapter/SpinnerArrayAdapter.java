package com.sisa.tabata.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.listener.workout.MainMenuOnClickListener;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.04..
 */
@ContextSingleton
public class SpinnerArrayAdapter<T> extends ArrayAdapter<T> {


    private final CharSequence titleText;
    private final MainMenuOnClickListener mainMenuOnClickListener;

    public SpinnerArrayAdapter(Context context, T[] objects, CharSequence titleText, MainMenuOnClickListener mainMenuOnClickListener) {
        super(context, R.layout.spinner_item_layout, objects);
        this.titleText = titleText;
        this.mainMenuOnClickListener = mainMenuOnClickListener;
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
        view.setClickable(true);
        view.setTag(text.getText());
        view.setOnClickListener(mainMenuOnClickListener);
        return view;
    }

    private TextView getTextView(View view) {
        return (TextView) view.findViewById(android.R.id.text1);
    }



}
