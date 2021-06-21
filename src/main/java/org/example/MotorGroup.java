package org.example;

public interface MotorGroup extends Motor
{
    void setSpeed(int index, double value);
    double getSpeed(int index);
    void setMaxSpeed(int index, double value);
}