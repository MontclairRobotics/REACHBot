throttleErr: float = 0.01
"""The error which is accounted for in throttle values when deciding 
   if a value should represent full forward, reverse or neutral instead
   of proportional."""

dutyMid: float = 0.075
"""The "neutral" duty cycle value used by the Spark MAX motor controller."""
dutyMidErr: float = 0.00125
"""The range of the values which represent neutral motion on the Spark 
   MAX motor controller."""

dutyRange: float = 0.05 - dutyMidErr
"""The range of the values which represent proportional motion on the Spark
   MAX motor controller."""

dutyMax: float = 0.1
"""The middlemost value which represents full forward motion on the Spark
   MAX motor controller."""
dutyMin: float = 0.05
"""The middlemost value which represents full reverse motion on the Spark
   MAX motor controller."""

def to16bit(value: float) -> int:
    """Convert a floating point number in [0,1] to a 16-bit integer."""
    return int(value * 0xffff)

def spMAXThrottleToDuty(throttle: float) -> int:
    """Get the duty cycle used by the Spark MAX motor controller to
       represent the given throttle."""

    if not 1 >= throttle >= -1:
        raise ValueError('bad throttle')

    if throttle > 1 - throttleErr:
        return to16bit(dutyMax)
        # full forward
    elif throttle > throttleErr:
        return to16bit(dutyMid + dutyMidErr + dutyRange * throttle)
        # proportional forward
    elif throttle > -throttleErr:
        return to16bit(dutyMid)
        # neutral
    elif throttle > throttleErr - 1:
        return to16bit(dutyMid - dutyMidErr + dutyRange * throttle)
        # proportional reverse
    else:
        return to16bit(dutyMin)
        # full reverse
