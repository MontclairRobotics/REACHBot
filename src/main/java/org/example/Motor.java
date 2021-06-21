package org.example;

public interface Motor
{
    /**
     * Set the speed of this motor [-1,1]
     * @param value The speed from -1 (full reverse) to 1 (full forward).
     *              0 is brake or coast depending on motor config.
     */
    void setSpeed(double value);
    /**
     * Get the current speed of this motor [-1,1]
     * @return The speed from -1 (full reverse) to 1 (full forward).
     *         0 is brake or coast depending on motor config.
     */
    double getSpeed();

    /**
     * Set the maximum speed of this motor [0,1]
     * @param value The speed which a value of 1 will map to
     *              when setSpeed is called, between 0 and 1.
     */
    void setMaxSpeed(double value);

    /**
     * Updates the status of the motor output.
     * UNIMPLEMENTED
     */
    void update();
}
