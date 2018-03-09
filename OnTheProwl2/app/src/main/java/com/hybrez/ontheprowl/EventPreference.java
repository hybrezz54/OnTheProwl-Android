package com.hybrez.ontheprowl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hybrez.ontheprowl.model.Event;
import com.hybrez.ontheprowl.util.SpinnerPreference;

import java.util.List;


public class EventPreference extends SpinnerPreference {

    /** Inflater for inflating views */
    private final LayoutInflater mInflator;

    /** List of events */
    private List<Event> mEvents;

    /**
     * Construct a new EventPreference
     *
     * @param context The preference context
     * @param attrs The preference attributes
     */
    public EventPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Construct a new EventPreference
     *
     * @param context The preference context
     * @param attrs The preference attributes
     * @param defStyleAttr The style attributes for the preference
     */
    public EventPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflator = LayoutInflater.from(getContext());
    }

    /**
     * Create view for each spinner item
     *
     * @param position Position of item in spinner
     * @param parent Parent view or layout of the spinner item
     * @return Inflated view for the spinner item
     */
    @Override
    protected View createDropDownView(int position, ViewGroup parent) {
        return mInflator.inflate(android.R.layout.simple_spinner_item, parent, false);
    }

    /**
     * Bind the spinner item view to the preference
     *
     * @param position Position in the spinner
     * @param view View to bind
     */
    @Override
    protected void bindDropDownView(int position, View view) {
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(mEntries[position]);
    }
}
