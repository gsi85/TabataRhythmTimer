package com.sisa.tabata.ui.adapter;

import com.sisa.tabata.R;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.listener.workout.MainMenuOnClickListener;
import com.sisa.tabata.ui.listener.workout.SpinnerTextOnClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;

/**
 * Custom implementation of {@link ArrayAdapter} used by the spinner menu on the {@link WorkoutActivity}.
 *
 * @param <T> type of objects array to represent in the list view
 */
@ContextSingleton
public class SpinnerArrayAdapter<T> extends ArrayAdapter<T> {

    private final CharSequence titleText;
    private final MainMenuOnClickListener mainMenuOnClickListener;
    private final SpinnerTextOnClickListener spinnerTextOnClickListener;

    /**
     * DI constructor.
     * @param context {@link Context}
     * @param objects listView objects
     * @param titleText the title text
     * @param mainMenuOnClickListener {@link MainMenuOnClickListener}
     * @param spinnerTextOnClickListener {@link SpinnerTextOnClickListener}
     */
    public SpinnerArrayAdapter(Context context, T[] objects, CharSequence titleText, MainMenuOnClickListener mainMenuOnClickListener,
            final SpinnerTextOnClickListener spinnerTextOnClickListener) {
        super(context, R.layout.spinner_item_layout, objects);
        this.titleText = titleText;
        this.mainMenuOnClickListener = mainMenuOnClickListener;
        this.spinnerTextOnClickListener = spinnerTextOnClickListener;
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
        spinnerTextOnClickListener.onClick(view);
        return view;
    }

    private TextView getTextView(View view) {
        return (TextView) view.findViewById(android.R.id.text1);
    }

}
