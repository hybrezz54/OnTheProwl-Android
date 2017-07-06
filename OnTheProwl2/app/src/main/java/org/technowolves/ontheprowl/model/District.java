package org.technowolves.ontheprowl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for a District
 * for the Gson library
 */
public class District {

    @SerializedName("key")
    @Expose
    String key;

    @SerializedName("display_name")
    @Expose
    String name;

    /**
     * District Constructor
     *
     * @param key The district key.
     * @param name The district name.
     */
    public District(String key, String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * Set the district key.
     *
     * @param key The district key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the district name
     *
     * @param name The district name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the district key.
     *
     * @return The district key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the district name.
     *
     * @return The district name.
     */
    public String getName() {
        return name;
    }
}
