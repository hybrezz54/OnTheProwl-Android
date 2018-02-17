package org.technowolves.ontheprowl.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for a Team object
 */
public class Team {

    /** The number of the team */
    @SerializedName("number")
    @Expose
    private String number;

    /** The info of the team */
    @SerializedName("info")
    @Expose
    private Info info;

    /** The info of the team's robot */
    @SerializedName("Robot")
    @Expose
    private Robot robot;

    /**
     * Construct a team object
     *
     * @param number The team's number
     */
    public Team(String number) {
        this.number = number;
    }

    /**
     * Construct a team object
     *
     * @param number The team's number
     * @param info The team's info
     * @param robot The info of the team's robot
     */
    public Team(String number, Info info, Robot robot) {
        setNumber(number);
        setInfo(info);
        setRobot(robot);
    }

    /** The getter for the team's number */
    public String getNumber() {
        return number;
    }

    /** The setter for the team's number */
    public void setNumber(String number) {
        this.number = number;
    }

    /** The getter for the team's info */
    public Info getInfo() {
        return info;
    }

    /** The setter for the team's info */
    public void setInfo(Info info) {
        this.info = info;
    }

    /** The getter for the info of the team's robot */
    public Robot getRobot() {
        return robot;
    }

    /** The setter for the info fo the team's robot */
    public void setRobot(Robot robot) {
        this.robot = robot;
    }

}
