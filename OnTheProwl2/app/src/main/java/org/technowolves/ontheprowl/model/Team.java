package org.technowolves.ontheprowl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for a Team
 * for the Gson library
 */
public class Team {

    @SerializedName("team_number")
    @Expose
    String number;

    @SerializedName("nickname")
    @Expose
    String name;

    @SerializedName("website")
    @Expose
    String website;

    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("rookie_year")
    @Expose
    String rookie;

    @SerializedName("num_competition")
    @Expose
    String competition;

    @SerializedName("award_one")
    @Expose
    int award1;

    @SerializedName("year_one")
    @Expose
    String year1;

    @SerializedName("award_two")
    @Expose
    int award2;

    @SerializedName("year_two")
    @Expose
    String year2;

    @SerializedName("notes")
    @Expose
    String notes;

    @SerializedName("driver_xp")
    @Expose
    float driverExp;

    @SerializedName("hp_xp")
    @Expose
    float humanExp;

    /**
     * Set the team info
     *
     * @param number The team's number
     * @param name The team's name
     */
    public Team(String number, String name) {
        this.number = number;
        this.name = name;
    }

    /** The getter for the team's number */
    public String getNumber() {
        return number;
    }

    /** The setter for the team's number */
    public void setNumber(String number) {
        this.number = number;
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

    /** The getter if the team is in its first year */
    public String getRookie() {
        return rookie;
    }

    /** The setter if the team is in its first year */
    public void setRookie(String rookie) {
        this.rookie = rookie;
    }

    /** The getter for the competition the team has participated in */
    public String getCompetition() {
        return competition;
    }

    /** The setter for the competition the team has participated in */
    public void setCompetition(String competition) {
        this.competition = competition;
    }

    /** The getter for award #1 of the team */
    public int getAward1() {
        return award1;
    }

    /** The setter for award #1 of the team */
    public void setAward1(int award1) {
        this.award1 = award1;
    }

    /** The getter for year the team received award #1 */
    public String getYear1() {
        return year1;
    }

    /** The setter for the year the team received award #1 */
    public void setYear1(String year1) {
        this.year1 = year1;
    }

    /** The getter for award #2 of the team */
    public int getAward2() {
        return award2;
    }

    /** The setter for award #2 of the team */
    public void setAward2(int award2) {
        this.award2 = award2;
    }

    /** The getter for year the team received award #2 */
    public String getYear2() {
        return year2;
    }

    /** The setter for year the team received award #2 */
    public void setYear2(String year2) {
        this.year2 = year2;
    }

    /** The getter for any notes on the team */
    public String getNotes() {
        return notes;
    }

    /** The setter for any notes on the team */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
    public float getDriverExp() {
        return driverExp;
    }

    public void setDriverExp(float driverExp) {
        this.driverExp = driverExp;
    }

    public float getHumanExp() {
        return humanExp;
    }

    public void setHumanExp(float humanExp) {
        this.humanExp = humanExp;
    }
    */
}
