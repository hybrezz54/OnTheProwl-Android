package com.hybrez.ontheprowl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for an Event
 * for the Gson library
 */
public class Event {

    @SerializedName("key")
    @Expose
    String key;

    @SerializedName("name")
    @Expose
    String name;

    /**
     * Set the event info
     *
     * @param key The event key
     * @param name The event name
     */
    public Event(String key, String name) {
        setKey(key);
        setName(name);
    }

    /**
     * The getter for the event key
     *
     * @return The event key
     */
    public String getKey() {
        return key;
    }

    /**
     * The setter for the event key
     *
     * @param key The event key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * The getter for the event name
     *
     * @return The event name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the event name
     *
     * @param name The event name
     */
    public void setName(String name) {
        this.name = name;
    }

}
