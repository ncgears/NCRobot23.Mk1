from trajectory_generator import trajectory_generator
from drive.swerve_drive import swerve_drive
import trajectory_io
import math

def main():
    drive = swerve_drive(
        # Wheelbase x/y (length/width)
        0.66, 0.584,
        # Bumper length/width (meters)
        0.914, 0.838,
        # Mass (kg)/moi (kg/m^2)
        54.4, 5.6,
        # Max velocity (RPS)/force (Nm) at 60A, adjusted for gearing
        95, 2.2, #1.1
        # 50, 1,
        # Wheel radius (meters)
        0.071)

    generator = trajectory_generator(drive)

    # array points are [xMeters,yMeters,rotationInRadians]
    # X is width of field, so -X is left, +X is right (from driver view)
    # Y is length of field, so -Y is toward driver station, +Y is away from driver station
    # Rotation is in radians, so 2pi/180 = 6.28rad, or a full rotation
    # if we setup offset from rotational zero, we need to account for this in our auton

    # generator.generate(
    #     [[0,2,0],
    #     [-1.0,0,-0.15]],
    #     "Test"
    # )

    # generator.generate(
    #     [[0,0,0],
    #     [1.0,0,0]],
    #     "OneMeterForward"
    # )

    # generator.generate(
    #     [[0,0,0],
    #     [-1.0,0,0]],
    #     "OneMeterBackward"
    # )

    # generator.generate(
    #     [[0,0,0],
    #     [3.2,0,0]],
    #     "TwoMetersForward"
    # )

    # generator.generate(
    #     [[0,0,0],
    #     [-3.2,0,0]],
    #     "TwoMetersBackward"
    # )

    # generator.generate(
    #     [[-1.0,0,-0.15],
    #     [0,2,-math.pi/2],
    #     [-0.42,6.121,-1]],
    #     "CollectBall3AndBall4"
    # )

    # generator.generate(
    #     [[-0.42,6.121,-1],
    #     [-0.2,0.5,-0.575]],
    #     "GoHome"
    # )

    # ## This is validated on 2022-03-21, do not update.
    # generator.generate(
    #     [
    #         [0,0,0],
    #         [-0.6,0.6,0],
    #         [-0.6,1.8,0],
    #     ],
    #     "ar1BallOne"
    # )

    # ## This is validated on 2022-03-23, do not update.
    # generator.generate(
    #     [
    #         [-0.6,1.8,0],
    #         [-0.9,0.6,-1.0],
    #         [-4.6,0.6,-1.0],
    #     ],
    #     "ar1BallTwo"
    # )

    # ## This is validated on 2022-03-23, do not update.
    # generator.generate(
    #     [
    #         [-4.6,0.6,-1.0],
    #         [-4.5,0.5,-0.55],
    #     ],
    #     "ar1B2ShootingPosition"
    # )

    # ## This is validated on 2022-03-23, do not update.
    # generator.generate(
    #     [
    #         [-3.7,0.4,-0.4],
    #         [-9.1,0.8,-0.4],
    #     ],
    #     "ar3BallThree"
    # )

    # ## This is validated on 2022-03-23, do not update.
    # generator.generate(
    #     [
    #         [-9.1,0.8,-0.4],
    #         [-3.7,0.4,-0.4],
    #     ],
    #     "ar3B4ShootingPosition"
    # )

    # ## This is validated on 2022-03-23, do not update.
    # generator.generate(
    #     [
    #         [0,0,0],
    #         [-2.2,-0.6,0.2],
    #     ],
    #     "al1BallOne"
    # )

    # ## This is validated on 2022-04-06, do not update.
    generator.generate(
        [
            [0.0,0.0,0.0],
            [-2.5,-0.3,0.5],
            [-1.4,-1.3,-0.05],
        ],
        "al2BallOne"
    )

    # generator.generate(
    #     [
    #         [0,0,0],
    #         [0.3, 1.1, -1.4],
    #         [0.3, 1.3, -1.4],
    #         [-1.2, 1.4, -1.5],
    #         [-1.8, -0.7, -1.5],
    #         [-4.4, -0.7, -1.5],
    #         [-4.1, -0.8, -0.80],
    #     ],
    #     "ar4BallOneAndTwo"
    # )

main()