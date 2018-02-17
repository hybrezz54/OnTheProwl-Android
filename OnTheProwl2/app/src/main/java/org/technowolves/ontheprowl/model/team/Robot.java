package org.technowolves.ontheprowl.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model for a Team robot object
 */
public class Robot {

    /** The robot's name */
    @SerializedName("name")
    @Expose
    private String name;

    /** The robot's weight */
    @SerializedName("weight")
    @Expose
    private float weight;

    /** The robot's height */
    @SerializedName("height")
    @Expose
    private float height;

    /** The robot's drivetrain */
    @SerializedName("drivetrain")
    @Expose
    private int drivetrain;

    /** The robot's max speed */
    @SerializedName("speed")
    @Expose
    private float speed;

    /** The robot's autonomous capability */
    @SerializedName("autonomous")
    @Expose
    private int autonomous;

    /** The robot's function */
    @SerializedName("function")
    @Expose
    private int function;

    /** The robot's rating or performance */
    @SerializedName("rating")
    @Expose
    private int rating;

    /** The robot's picture's URI */
    @SerializedName("image_uri")
    @Expose
    private String uri;

    /**
     * Construct a new object for the team's robot
     *
     * @param name The robot's name
     * @param weight The robot's weight
     * @param height The robot's height
     * @param drivetrain The robot's drivetrain
     * @param speed The robot's max speed
     * @param autonomous The robot's autonomous capability
     * @param function The robot's function
     * @param rating The robot's rating or performance
     * @param uri The robot's picture's URI
     */
    public Robot(String name, float weight, float height, int drivetrain, float speed,
                 int autonomous, int function, int rating, String uri) {
        setName(name);
        setWeight(weight);
        setHeight(height);
        setDrivetrain(drivetrain);
        setSpeed(speed);
        setAutonomous(autonomous);
        setFunction(function);
        setRating(rating);
        setURI(uri);
    }

    /**
     * Get the robot's name
     *
     * @return The robot's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the robot's name
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the robot's weight
     *
     * @return The robot's weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Set the robot's weight
     *
     * @param weight The weight to set
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Get the height of the robot
     *
     * @return The height of the robot
     */
    public float getHeight() {
        return height;
    }

    /**
     * Set the height of the robot
     *
     * @param height The height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Get the robot's drivetrain configuration
     *
     * @return The index of the robot's drivetrain
     */
    public int getDrivetrain() {
        return drivetrain;
    }

    /**
     * Set the robot's drivetrain configuration
     *
     * @param drivetrain The index of the drivetrain to set
     */
    public void setDrivetrain(int drivetrain) {
        this.drivetrain = drivetrain;
    }

    /**
     * Get the speed of the robot
     *
     * @return The speed of the robot
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the robot
     *
     * @param speed The speed of the robot to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Get the robot's autnomous capability
     *
     * @return The index of the autonomous capability
     */
    public int getAutonomous() {
        return autonomous;
    }

    /**
     * Set the robot's autonomous capability
     *
     * @param autonomous The index of the autonomous capability
     */
    public void setAutonomous(int autonomous) {
        this.autonomous = autonomous;
    }

    /**
     * Get the robot's function
     *
     * @return The index of the robot's function
     */
    public int getFunction() {
        return function;
    }

    /**
     * Set the robot's function
     *
     * @param function The index of the function to set
     */
    public void setFunction(int function) {
        this.function = function;
    }

    /**
     * Get the robot's rating or performance
     *
     * @return The robot's rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the robot's rating or performace
     *
     * @param rating The rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Get the robot's picture's URI
     *
     * @return The robot's picture's URI
     */
    public String getURI() {
        return uri;
    }

    /**
     * Set the robot's picture's URI
     *
     * @param uri The URI to set
     */
    public void setURI(String uri) {
        this.uri = uri;
    }
}
