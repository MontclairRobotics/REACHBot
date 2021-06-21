"""
    Author: Dylan Rafael
    Purpose: REACH Bot, Team 555
    Date of Creation: 6/21/2021
"""


### imports ###
import sys
import board
from adafruit_pca9685 import PCA9685
from busio import I2C
from typing import Sequence
from utils import spMAXThrottleToDuty


### classes ###
class StopPwmOutput(BaseException):
    """An exception which is raised when this program is signalled to
       stop PWM output by the java caller."""
    pass


### functions ###
def error(e: BaseException):
    """Send an error"""
    sys.stderr.write('ERROR: ' + str(e))

def debug(s: str):
    """Send a debug message"""
    sys.stderr.write(s)

def parse(ipt: str) -> Sequence[float]:
    """Parse the input from a string and return a sequence of floats."""

    if ipt[0] == '!':
        raise StopPwmOutput

    spl = ipt.split(',')
    return [float(s) for s in spl]


### main ###
def main():

    pattern = sys.argv[1]
    channels = [int(c, 16) for c in pattern]

    i2c = I2C(board.SCL, board.SDA)
    hat = PCA9685(i2c)

    # standard frequency: 50 Hz; 20 ms; 20_000 us
    hat.frequency = 50

    # neutralize motors
    for ch in channels:
        hat.channels[ch].duty_cycle = spMAXThrottleToDuty(0)

    while True:
        cmd = None

        # to avoid a warning for the 'except BaseException':
        # noinspection PyBroadException
        try:

            inp = sys.stdin.readline()
            cmd = parse(inp)

            # set motor speeds
            for i, t in enumerate(cmd):
                hat.channels[channels[i]].duty_cycle = spMAXThrottleToDuty(t)

        except BaseException as e:

            if not isinstance(e, StopPwmOutput):
                # an error has occurred and the program should notify java
                error(e)

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
