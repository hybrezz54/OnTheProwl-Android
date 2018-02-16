package org.technowolves.ontheprowl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for a Team info object
 */
public class Info {

    /** The team's name */
    @SerializedName("nickname")
    @Expose
    String name;

    /** The team's website */
    @SerializedName("website")
    @Expose
    String website;

    /** The team's location */
    @SerializedName("location")
    @Expose
    String location;

    /** The team's rookie year */
    @SerializedName("rookie_year")
    @Expose
    String rookie;

    /** The number of competitions signed up for this year */
    @SerializedName("num_competitions")
    @Expose
    int competitions;

    /** The team's ranking */
    @SerializedName("ranking")
    @Expose
    int ranking;

    /** The notes on the team */
    @SerializedName("notes")
    @Expose
    String notes;

    /**
     * Construct a new object for the team's info
     *
     * @param name The name of the team
     * @param website The website of the team
     * @param location The location of the team
     * @param rookie The team's rookie year
     * @param competitions The number of competitions the team has signed up for
     * @param ranking The user-specified ranking of the team
     * @param notes The user-specified notes on the team
     */
    public Info(String name, String website, String location, String rookie, int competitions,
                int ranking, String notes) {
        this.name = name;
        this.website = website;
        this.location = location;
        this.rookie = rookie;
        this.competitions = competitions;
        this.ranking = ranking;
        this.notes = notes;
    }

    /** The getter for the team's name */
    public String getName() {
        return name;
    }

    /** The setter for the team's name */
    public void setName(String name) {
        this.name = name;
    }

    /** The getter for the team's website */
    public String getWebsite() {
        return website;
    }

    /** The setter for the team's website */
    public void setWebsite(String website) {
        this.website = website;
    }

    /** The getter for the team's location */
    public String getLocation() {
        return location;
    }

    /** The setter for the team's location */
    public void setLocation(String location) {
        this.location = location;
    }

    /** The getter of the team's rookie year */
    public String getRookie() {
        return rookie;
    }

    /** The setter of the team's rookie year */
    public void setRookie(String rookie) {
        this.rookie = rookie;
    }

    /** Get the number of competitions the team has signed up for this season */
    public int getCompetitions() {
        return competitions;
    }

    /** Set the number of competitions the team has signed up for this season */
    public void setCompetitions(int competitions) {
        this.competitions = competitions;
    }

    /** Get the ranking of the team */
    public int getRanking() {
        return ranking;
    }

    /** Set the ranking of the team */
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    /** The getter for any notes on the team */
    public String getNotes() {
        return notes;
    }

    /** The setter for any notes on the team */
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
