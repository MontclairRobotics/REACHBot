package org.example;

import com.pi4j.io.pwm.Pwm;

public class SparkMaxPwm implements Motor
{
    private double currentSpeed;
    private double maxSpeed;
    private final BasicPwmController pwm;

    public SparkMaxPwm(BasicPwmController pwm)
    { this.pwm = pwm; }

    public void setSpeed(double value)
    { currentSpeed = value * maxSpeed; }
    public double getSpeed()
    { return currentSpeed; }

    public void setMaxSpeed(double value)
    { maxSpeed = value; }

    public void update()
    {
        pwm.setPulseTime(getPulseTime(currentSpeed));
        pwm.setDutyCycle(getDutyCycle(currentSpeed));
    }

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
    public static double getPulseTime(double speed)
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
    public static double getDutyCycle(double speed)
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
