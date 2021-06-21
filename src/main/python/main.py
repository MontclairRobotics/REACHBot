"""
    Author: Dylan Rafael
    Purpose: REACH Bot, Team 555
    Date of Creation: 6/21/2021
"""


### imports ###
import sys
from typing import Sequence
from utils import spMAXThrottleToDuty

import board
import busio
import adafruit_pca9685


### shared vars ###
verbose = False


### classes ###
class StopPwmOutput(BaseException):
    """An exception which is raised when this program is signalled to
       stop PWM output by the java caller."""
    pass


### functions ###
def error(s: str):
    """Send an error"""
    sys.stdout.write('!' + s)

def debug(s: str):
    """Send a debug message"""
    if verbose:
        for line in s.splitlines():
            sys.stdout.write('"' + line)

def parse(ipt: str) -> Sequence[float]:
    """Parse the input from a string and return a sequence of floats."""

    if ipt[0] == '!':
        raise StopPwmOutput

    spl = ipt.split(',')
    return [float(s) for s in spl]


### main ###
def main():

    global verbose

    pattern = sys.argv[1]
    channels = [int(c, 16) for c in pattern]

    if len(sys.argv) > 2 and sys.argv[2] == 'v':
        verbose = True

    i2c = busio.I2C(board.SCL, board.SDA)
    hat = adafruit_pca9685.PCA9685(i2c)

    # standard frequency: 50 Hz; 20 ms; 20_000 us
    hat.frequency = 50

    # neutralize motors
    for ch in channels:
        hat.channels[ch].duty_cycle = spMAXThrottleToDuty(0)

    debug("Execution started")

    while True:
        cmd = None

        # to avoid a warning for the 'except BaseException':
        # noinspection PyBroadException
        try:

            inp = sys.stdin.readline()
            cmd = parse(inp)

            # echo input
            debug(f"Received: {inp}")

            # set motor speeds
            for i, t in enumerate(cmd):
                hat.channels[channels[i]].duty_cycle = spMAXThrottleToDuty(t)

        except BaseException as e:

            if not isinstance(e, StopPwmOutput):
                # an error has occurred and the program should notify java
                error()

            # gpio should be stopped here
            break

    # de-initialize
    hat.deinit()
    i2c.deinit()

    # die
    exit()


### exec ###
if __name__ == '__main__':
    main()
