package com.hybrez.ontheprowl.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hybrez.ontheprowl.model.Event;

import java.util.List;

/**
 * Create a new adapter to handle showing events in
 * the spinner
 */
public class EventAdapter extends ArrayAdapter<Event> {

    /** the application context */
    private Context context;

    /** The list of events in the adapter */
    private List<Event> events;

    /**
     * Construct a new EventAdapter object
     *
     * @param context The application context
     * @param resource The view containing
     * @param events The list of events to add to the adapter
     */
    public EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.context = context;
        this.events = events;
    }

    /**
     * Get the spinner view from the adapter
     *
     * @param position The position of element in the spinner dropdown
     * @param convertView The view to be used for each element
     * @param parent The ViewGroup the ConvertView belongs to
     * @return The view of each item in the adapter
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the event object and init variables
        Event event = getItem(position);

        // find text view or create one
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent,
                    false);
        }

        // set text view value
        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(event.getName());

        return convertView;
    }

    /**
     * Get number of events in adapter
     *
     * @return The number of events
     */
    @Override
    public int getCount() {
        return events.size();
    }

    /**
     * Get the position of the event
     *
     * @param position The position of the event in the adapter
     * @return The position of the selected event
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
}
