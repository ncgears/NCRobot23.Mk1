import msvcrt
import sys
import serial
import serial.tools.list_ports
import struct
import time
import keyboard
import threading
from networktables import NetworkTables

controllerPort="COM38" #serial port of usb controller
desiredHeading = 0 #desired heading of robot in degrees
# ip='roborio-1918-frc.local' #ip or name of networktables server
ip='127.0.0.1'
tableName= "RobotController" #networktables table name to store data

cond = threading.Condition()
notified = [False]

for port in serial.tools.list_ports.comports():
    print('{0}: {1} (Ser:{2})'.format(port.name, port.description, port.serial_number))

print('')

def ntConnectionListener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

NetworkTables.initialize(server=ip)
NetworkTables.addConnectionListener(ntConnectionListener, immediateNotify=True)
with cond:
    print("Waiting for Network Tables")
    if not notified[0] :
        cond.wait()

table = NetworkTables.getTable(tableName)
print("Connected to Network Tables!")


def checkHeading(newHeading):
    global desiredHeading
    delta = abs(desiredHeading - newHeading)
    #print('Current desired heading=%s' % desiredHeading)
    #print('New desired heading=%s' % newHeading)
    #print('Delta=%s' % delta)
    if delta > 0:
        print('Saving new desired heading=%s' % newHeading)
        desiredHeading = newHeading
        table.putValue('DesiredHeading',newHeading)

def getControllerHeading(heading = desiredHeading):
    global controllerPort
    if heading < 0:
        heading += 360
    elif heading >= 360:
        heading -= 360
    checkHeading(heading)

def showHeader():
    print('FRC Team 1918 NC GEARS')
    print('Rotary Controller Listener')
    print('by Jim Barstow jim@ncgears.com')
    print('')

showHeader()

try:
    getControllerHeading(90)
    while True:
        time.sleep(0.1)
        if keyboard.is_pressed('q'):
            print('Exit requested')
            break
        elif keyboard.is_pressed('+'):
            getControllerHeading(desiredHeading + 10)
        elif keyboard.is_pressed('-'):
            getControllerHeading(desiredHeading - 10)
except KeyboardInterrupt:
    print('Exit requested')
    exit
finally:
    exit
