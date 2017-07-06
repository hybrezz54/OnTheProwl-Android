package org.technowolves.ontheprowl.model;

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

    @SerializedName("location")
    @Expose
    String location;

    /**
     * Set the event info
     *
     * @param key The event key
     * @param name The event name
     * @param location The event location
     */
    public Event(String key, String name, String location) {
        this.key = key;
        this.name = name;
        this.location = location;
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

    /**
     * The getter for the event location
     *
     * @return The event location
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setter for the event location
     *
     * @param location The event location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
