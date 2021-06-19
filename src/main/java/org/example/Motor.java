package org.example;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.*;
import com.pi4j.platform.Platforms;
import com.pi4j.util.Console;

public class Motor
{
    private double currentSpeed;

    public Motor(int pin)
    {}

    /**
     * Set the speed of this motor [-1,1]
     * @param value The speed from -1 (full reverse) to 1 (full forward).
     *              0 is brake or coast depending on motor config.
     */
    public void setSpeed(double value)
    { currentSpeed = value; }
    /**
     * Get the current speed of this motor [-1,1]
     * @return The speed from -1 (full reverse) to 1 (full forward).
     *         0 is brake or coast depending on motor config.
     */
    public double getSpeed()
    { return currentSpeed; }

    /**
     * Sets the speed of the motor to zero, either braking
     * or coasting it.
     */
    public void neutralize()
    { currentSpeed = 0; }

    /**
     * Updates the status of the pwm output.
     * UNIMPLEMENTED
     */
    public void updatePwm()
    { /*sus*/ }

    /**
     * The threshold which determines when a speed is neutral, proportional, or full.
     */
    public static final double kSpeedErr = 0.05d;

    /**
     * Get the PWM pulse width for a given speed.
     * @param speed The speed from -1 (full reverse) to 1 (full forward).
     *              0 is brake or coast depending on motor config.
     * @return The pulse width associated with the SparkMAX controller.
     */
    public static double GetPulseWidth(double speed)
    {
        if(speed > kSpeedErr)
        {
            if(speed > 1 - kSpeedErr)
            {
                return 1750; //proportional forward
            }
            else
            {
                return 2250; //full forward
            }
        }
        else if(speed < -kSpeedErr)
        {
            if(speed < -1 + kSpeedErr)
            {
                return 1250; //proportional backward
            }
            else
            {
                return 750; //full backward
            }
        }
        else
        {
            return 1500; //neutral
        }
    }

    /**
     * Get the PWM duty cycle for a given speed.
     * @param speed The speed from -1 (full reverse) to 1 (full forward).
     *              0 is brake or coast depending on motor config.
     * @return The duty cycle associated with the SparkMAX controller.
     */
    public static double GetDutyCycle(double speed)
    {
        if(speed > kSpeedErr)
        {
            if(speed > 1 - kSpeedErr)
            {
                return speed * 100; //proportional forward
            }
            else
            {
                return 100; //full forward
            }
        }
        else if(speed < -kSpeedErr)
        {
            if(speed < -1 + kSpeedErr)
            {
                return -speed * 100; //proportional backward
            }
            else
            {
                return 100; //full backward
            }
        }
        else
        {
            return 0; //neutral
        }
    }
}
