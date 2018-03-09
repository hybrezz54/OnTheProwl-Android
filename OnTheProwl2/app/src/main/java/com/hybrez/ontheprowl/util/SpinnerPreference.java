package com.hybrez.ontheprowl.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hybrez.ontheprowl.R;

public abstract class SpinnerPreference extends Preference {

    /** Title for the preference */
    protected String mTitle;

    /** Entries in spinner */
    protected String[] mEntries = new String[0];

    /** Values for entries in spinner */
    protected String[] mEntryValues = new String[0];

    /** Selected index of spinner item */
    private int mPosition = 0;

    /**
     * Construct a new SpinnerPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     */
    public SpinnerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * Construct a new SpinnerPreference object
     *
     * @param context The application context
     * @param attrs The preference attributes
     * @param defStyleAttr The style attributes
     */
    public SpinnerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.preference_spinner);
        init(context, attrs);
    }

    /**
     * Bind the view to the preference
     *
     * @param view The view to bind
     */
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        // get views
        final TextView textView = view.findViewById(R.id.pref_title);
        final Spinner spinner = view.findViewById(R.id.pref_spinner);

        // delegate to spinner click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });

        // set initial values
        textView.setText(mTitle);
        spinner.setSelection(mPosition);

        // set spinner adapter
        spinner.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = createDropDownView(i, viewGroup);

                bindDropDownView(i, view);
                return view;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return mEntries.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return getDropDownView(i, view, viewGroup);
            }

            @Override
            public int getItemViewType(int i) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });

        // persist selected item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                persistString(mEntryValues[position]);
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
        String value = restorePersistedValue ? getPersistedString(null) : (String) defaultValue;

        // set the position of persisted item
        for (int i = 0; i < mEntryValues.length; i++) {
            if (TextUtils.equals(mEntryValues[i], value)) {
                mPosition = i;
                break;
            }
        }
    }

    protected abstract View createDropDownView(int position, ViewGroup parent);

    protected abstract void bindDropDownView(int position, View view);

    /**
     * Set up the xml attributes for the preference
     *
     * @param context The preference context
     * @param attrs The attributes for the preference
     */
    private void init(Context context, AttributeSet attrs) {
        // load the attributes
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpinnerPreference);

        // get the title attribute
        int titleResId = ta.getResourceId(R.styleable.SpinnerPreference_title, 0);
        if (titleResId != 0)
            mTitle = context.getResources().getString(titleResId);

        // get the entries attribute
        int entriesResId = ta.getResourceId(R.styleable.SpinnerPreference_entries, 0);
        if (entriesResId != 0)
            mEntries = context.getResources().getStringArray(entriesResId);

        // get the entry values attribute
        int valuesResId = ta.getResourceId(R.styleable.SpinnerPreference_entryValues, 0);
        if (valuesResId != 0)
            mEntryValues = context.getResources().getStringArray(valuesResId);

        // recycle attributes?
        ta.recycle();
    }

}
