package com.hybrez.ontheprowl.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.config.EventAdapter;
import com.hybrez.ontheprowl.model.Event;

import java.util.ArrayList;

public class SpinnerPreference extends Preference {

    /** Spinner in the preference */
    private AppCompatSpinner mSpinner;

    /** Index of selected item */
    private int mPosition = 0;

    /**
     * Construct a new SpinnerPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     */
    public SpinnerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.preference_event);
    }

    /**
     * Bind the view to the preference
     *
     * @param view The view to bind
     */
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        // init spinner and set its values
        mSpinner = view.findViewById(R.id.spinner_event);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_spinner_item,
//                new ArrayList<String>());
//        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(mPosition);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
    }

}
