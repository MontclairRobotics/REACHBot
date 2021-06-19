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
    public Motor(int pin)
    {

    }

    //The threshold which determines when a speed is neutral, proportional, or full.
    public static final double kSpeedErr = 0.05d;

    //Get the PWM pulse width for a given speed.
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
    //Get the PWM duty cycle for a given speed.
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
