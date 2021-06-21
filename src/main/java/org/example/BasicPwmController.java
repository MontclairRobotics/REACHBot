package org.example;

public interface BasicPwmController
{
    /**
     * Get the time (us) for a pwm pulse.
     * @return The time in microseconds of a pwm pulse.
     */
    double getPulseTime();
    /**
     * Sets the time (us) for a pwm pulse.
     * @param value The time in microseconds of a pwm pulse.
     */
    void setPulseTime(double value);

    /**
     * Get the frequency (Hz) in pwm pulses.
     * @return The frequency in Hertz of pem pulses.
     */
    double getPulseRate();
    /**
     * Set the frequency (Hz) in pwm pulses.
     * @param value The frequency in Hertz of pem pulses.
     */
    void setPulseRate(double value);

    /**
     * The duty cycle of the output pwm.
     * @return The duty cycle in percentage.
     */
    double getDutyCycle();
    /**
     * The duty cycle of the output pwm.
     * @param value The duty cycle in percentage.
     */
    void setDutyCycle(double value);
}
