
package frc.team1918.robot;
import frc.team1918.robot.utils.SwerveModuleConstants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

//Sometimes it is useful to comment out the following to see what variables or what controller buttons are not assigned yet
@SuppressWarnings("unused") //We silence the "unused variables" warnings in VSCode
/**
 * Constants let us quickly define the characteristics of our robot without having to search through code
 */
public class Constants {

    /**
     * ID abstracts the IDs from the rest of the constants to make it easier to detect conflicts
     */
    public static final class ID {
        /**
         * IDs of RoboRio Digital IO
         */
        public static final class DIO {
            public static int beambreak_top = 0;
            public static int beambreak_bottom = 1;
            public static int feeder_switch = 2;
            public static int whirly_hook1_capture_left = 4;
            public static int whirly_hook1_capture_right = 3;
            public static int whirly_hook2_capture = 5;
        }
        /**
         * IDs of RoboRio Analog IO
         */
        public static final class Analog {
        }
        /**
         * IDs of RoboRio Relays
         */
        public static final class Relay {
            public static int ringlight = 0;
        }
        /**
         * IDs of Talons
         */
        public static final class Talon {
            public static int swerve_fr_turn = 1;
            public static int feeder = 6;
            public static int swerve_fl_turn = 7;
            public static int swerve_rl_turn = 8;
            public static int swerve_rr_turn = 11;
            public static int intake = 16;

        }
        /**
         * IDs of Falcons
         */
        public static final class Falcon {
            public static int swerve_fr_drive = 36;
            public static int swerve_rr_drive = 37;
            public static int swerve_rl_drive = 38;
            public static int swerve_fl_drive = 39;
            public static int shooter = 42;
            public static int shooterFront = 41;
            public static int climber_master = 33;
            public static int climber_follower = 35;
        }
        /**
         * IDs of Solenoids
         */
        public static final class Solenoid {
            public static int collector = 0;
            public static int hood2 = 1;
            //public static int unused1 = 2;
            public static int whirlygig = 3;
            public static int hood = 4;
            public static int climbhook1 = 5;
            public static int climbhook2 = 6;
            public static int retractor = 7;
        }
    }

    /**
     * Constants that are Global for the robot
     */
    public static final class Global {
        //Global Constants
        public static final boolean CAMERA_ENABLED = true; //set to false if UsbCamera is removed
        public static final boolean SWERVE_SENSOR_NONCONTINUOUS = false;
        public static final int kTimeoutMs = 30; //Timeout for reporting in DS if action fails, set to 0 to skip confirmation
        public static final int PID_PRIMARY = 0;  //Talon PID slot for primary loop
        public static final int ROBOT_WIDTH = 21; //Width of the robot frame (from the pivot of the wheels)
        public static final int ROBOT_LENGTH = 28; //Length of the robot frame (from the pivot of the wheels)
        public static final boolean DEBUG_ENABLED_DEFAULT = true; //Default starting state of debug mode
        public static final int DEBUG_RECURRING_TICKS = 100; //Periodic cycles for recurring debug messages
        public static final int DASH_RECURRING_TICKS = 50; //Periodic cycles for dashboard updates
        public final static boolean tuningMode = false; //Enable tunable numbers
    }

    /**
     * Constants for the Autonomous subsystem
     */
    public static final class Auton {
        public static final boolean isDisabled = false; //Disable autonomous
        // Auton is defined in robot.java
        // public static final String autonToRun = "auton_BasicShootingAuto"; //4BallAuto, BasicDriveAuto, BasicShootingAuto, None //Name of the auton to run (these are in the bottom of RobotContainer)
        public static final double kMaxSpeedMetersPerSecond = 0.5;
        public static final double kMaxAccelMetersPerSecondSquared = 0.1;
        public static final double kMaxOmega = (kMaxSpeedMetersPerSecond / Math.hypot(0.584 / 2.0, 0.66 / 2.0));
        public static final double kPTranslationController = 0.0;
        public static final double kPThetaController = 0.0;
    }

    /**
     * Constants for the Pneumantics system
     * This is not a subsystem, the pneumatics are controlled directly in their respective subsystems
     */
    public static final class Air {
        public static final boolean isDisabled = false; //Disable compressor
        public static final int id_CollectorSolenoid = ID.Solenoid.collector; //ID of solenoid for collector
        public static final int id_RetratorSolenoid = ID.Solenoid.retractor; //ID of solenoid for retractor
        public static final int id_HoodSolenoid = ID.Solenoid.hood; //ID of solenoid for hood control
        public static final int id_Hood2Solenoid = ID.Solenoid.hood2;
        public static final int id_ClimbHook1Solenoid = ID.Solenoid.climbhook1; //ID of solenoid for climber hook 1
        public static final int id_ClimbHook2Solenoid = ID.Solenoid.climbhook2; //ID of solenoid for climber hook 2
        public static final int id_WhirlyGigSolenoid = ID.Solenoid.whirlygig; //ID of solenoid for whirlygig
        public static final boolean stateCollectorDeployed = false; //State of the solenoid when COLL1 is deployed
        public static final boolean stateRetractorRectracted = false; //State of the solenoid when retractor retracted
        public static final boolean stateHoodUp = true; //State of the solenoid when HOOD is up
        public static final boolean stateHood2Up = true;
        public static final boolean stateClimbHookLocked = false; //State of the solenoid when the hook is locked
        public static final boolean stateWhirlygigUp = true; //State of the solenoid when CLIMBER is up
    }
 
    /**
     * Constants for the Collector subsystem
     */
    public static final class Collector {
        public static final boolean isDisabled = false; //Disable the collector subsystem
        public static final int id_Motor1 = ID.Talon.intake; 
        public static final boolean isInverted_Motor1 = false;
        public static final int id_ColorSensor1 = 4;
        public static final double kDefaultCollectorSpeed = 0.65;
        public static final boolean isCurrentLimitEnabled = true;
        public static final int kCurrentLimitAmps = 5;
        public static final int kCurrentThresholdAmps = 20;
        public static final double kCurrentThresholdSecs = 0.2;
    }
   
    /**
     * Constants for the Feeder subsystem
     */
    public static final class Feeder {
        public static final boolean isDisabled = false; //Disable the feeder subsystem
        public static final int id_BeamBreak1 = ID.DIO.beambreak_bottom; //ID of the Beam Break 1 DIO (intake)
        public static final int id_BeamBreak2 = ID.DIO.beambreak_top; //ID of the Beam Break 2 DIO (shooter)
        public static final int id_FeederSwitch = ID.DIO.feeder_switch; //ID of the Shoe Switch
        public static final int id_Motor1 = ID.Talon.feeder; //ID of the Feeder Motor 1 Controller
        public static final boolean isInverted_Motor1 = true; //Invert motor direction
        public static final double speed_Motor1 = 0.8; //Feeder Motor 1 Speed
        public static final double debounce_delay = 3.0; //Seconds to wait for feeder beams
        public static final boolean isCurrentLimitEnabled = true;
        public static final int kCurrentLimitAmps = 1;
        public static final int kCurrentThresholdAmps = 2;
        public static final double kCurrentThresholdSecs = 0.1;
    }

    /**
     * Constants for the Shooter subsystem
     */
    public static final class Shooter {
        public static final String canBus = "rio"; //name of canbus for swerve modules, if not "rio"
        public static final boolean isDisabled = false; //Disable the shooter subsystem
        public static final int id_Motor1 = ID.Falcon.shooter; //ID of the Shooter Motor 1 Controller
        public static final int id_Motor2 = ID.Falcon.shooterFront; //ID of the Shooter Motor 2 Controller
        public static final int kEncoderFullRotation = 2048; //Falcon integrated encoder is 2048
        public static final boolean isSensorInverted_Motor1 = true;
        public static final boolean isInverted_Motor1 = false; //Invert motor direction
        public static final boolean isInverted_Motor2 = true; //Invert motor direction
        public static final int kMaxShooterSpeed = 150; //Max RPS of the Shooter Motor
        public static final double kDefaultShooterSpeed = 76; //Default RPS of the Shooter Motor
        public static final int kMinShooterSpeed = 0; //Min RPM of the Shooter Motor
        public static final double kSpeedIncrementSize = 1; //RPMs to change the shooter speed per increment
        public static final double kP = 0.10;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kIZone = 0.0;
        public static final double kArbitraryFeedForward = 1/kMaxShooterSpeed;
        public static final double kPreShooterSpeed = 1.0; //speed of the preshooter
        public static final double kShooterReductionFactor = 70.0/82.0;
        public static final double kSpinupSeconds = 0.3; //seconds to wait for shooter to be at speed (during auton)

        public static final class Shots {
            public static final class DEFAULT {
                public static final int kSpeed = 56;
                public static final boolean kHood = !Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class TARMAC {//Confirmed
                public static final int kSpeed = 56;
                public static final boolean kHood = !Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class LOW {
                public static final int kSpeed = 28;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class PROTECTED {//testing for hangar
                public static final int kSpeed = 73;  //73
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class LINE {
                public static final int kSpeed = 65;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class OUTER {
                public static final int kSpeed = 65;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class WALL {//Confirmed
                public static final int kSpeed = 65;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AR1ONE {
                public static final int kSpeed = 59;
                public static final boolean kHood = !Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class AR1TWO {
                public static final int kSpeed = 62;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AR1THREE {
                public static final int kSpeed = 59;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AR4ONE {
                public static final int kSpeed = 67;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AR4TWO {
                public static final int kSpeed = 67;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AL1ONE {
                public static final int kSpeed = 56;
                public static final boolean kHood = !Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class AL1TWO {
                public static final int kSpeed = 66;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
            public static final class AL2ONE {
                public static final int kSpeed = 60;
                public static final boolean kHood = !Air.stateHoodUp;
                public static final boolean kHood2 = !Air.stateHood2Up;
            }
            public static final class AL2TWO {
                public static final int kSpeed = 73;
                public static final boolean kHood = Air.stateHoodUp;
                public static final boolean kHood2 = Air.stateHood2Up;
            }
        }
    }

    /**
     * Constants for the Climber subsystem
     */
    public static final class Climber {
        public static final String canBus = "rio"; //name of canbus for swerve modules, if not "rio"
        public static final boolean isDisabled = false; //Disable the climber subsystem
        public static final boolean useAutoClimb = true; //Enable autoclimb function
        public static final boolean requireCaptureBothSides = true; //Require left+right capture to start autoclimb
        public static final int id_Motor1 = ID.Falcon.climber_master;
        public static final int id_Motor2 = ID.Falcon.climber_follower; //follower of Motor1
        public static final int id_CaptureHook1Left = ID.DIO.whirly_hook1_capture_left; //DIO for hook1 capture left
        public static final int id_CaptureHook1Right = ID.DIO.whirly_hook1_capture_right; //DIO for hook1 capture right
        public static final int id_CaptureHook2 = ID.DIO.whirly_hook2_capture; //DIO for hook2 capture
        public static final boolean isInverted_Motor1 = true;
        public static final boolean isInverted_Motor2 = false;
        public static final boolean isInvertedFromMaster_Motor2 = true; //false for same direction as master
        public static final boolean isSensorInverted_Motor1 = false;
        public static final boolean isSensorNotContinuous = false;
        public static final double kClimberSpeed = 1.0; //Speed at which the climber controllers operate (in fixed speed mode)
        public static final double kHookReleaseTime = 0.75; //Time in seconds to wait before re-locking hook after unlocking
        public static final double kHookCaptureTime = 0.18; //Time to wait before hook is considered captured
        public static final double kP = 0.2;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
    }

    /**
     * Constants for the Swerve Modules
     */
    public static final class Swerve {
        public static final String canBus = "rio"; //name of canbus for swerve modules, if not "rio"
        public static final boolean homeOnInit = true; //true to go to zero position on init
        public static final boolean USE_OPTIMIZATION = true; //false to disable shortest path optimization
        public static final boolean USE_DRIVE_PID = false; //true to enable PID based drive control
        // turn pid defaults (used in module definitions)
        public static final double DEFAULT_TURN_P = 2.8; //PID P
        public static final double DEFAULT_TURN_I = 0.0; //PID I
        public static final double DEFAULT_TURN_D = 0.0; //PID D
        public static final int DEFAULT_TURN_IZONE = 0; //PID IZone
        public static final int DEFAULT_TURN_ALLOWED_ERROR = 5; //PID Allowed Error
        public static final double DEFAULT_WHEEL_DIAM_MM = 76.2; //Wheel Diameter of 3in colson
        // current limits
        // Swerve current limiting //TODO: Needs tuning, this was borrowed from Team364 example
        // See {@link https://github.com/Team364/BaseFalconSwerve/blob/main/src/main/java/frc/robot/CTREConfigs.java}
        public static final boolean isTurnCurrentLimitEnabled = false;
        public static final int kTurnCurrentLimitAmps = 25;
        public static final int kTurnCurrentThresholdAmps = 40;
        public static final double kTurnCurrentThresholdSecs = 0.1;
        public static final boolean isDriveCurrentLimitEnabled = false;
        public static final int kDriveCurrentLimitAmps = 35;
        public static final int kDriveCurrentThresholdAmps = 60;
        public static final double kDriveCurrentThresholdSecs = 0.3;
        // swerve control definitions
        public static final double kHomeOffsetRadians = 0.0; //3 * (Math.PI/4); //135 - radians to offset the zero point of the wheels
        public static final double kMaxModuleAngularSpeedRadiansPerSecond = 2 * Math.PI;
        public static final double kMaxModuleAngularAccelerationRadiansPerSecondSquared = 2 * Math.PI;
        public static final double kMaxSpeedMetersPerSecond = 3.770; //12.0fps calculated; 13.7fps per Mike
        public static final boolean kGyroReversed = false;
        // Drive Motor Characterization
        // See {@link https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/controller/SimpleMotorFeedforward.html}
        // How do we determine these numbers? Need to find out. These falcon numbers are from Team364 example
        public static final double driveKS = (0.667 / 12); //Static Gain //divide by 12 to convert from volts to percent output for CTRE
        public static final double driveKV = (2.44 / 12); //Velocity Gain
        public static final double driveKA = (0.27 / 12); //Acceleration Gain

        //Forward Positive, Left Positive, Up Positive (NWU Convention)
        public static final SwerveDriveKinematics kDriveKinematics =
        new SwerveDriveKinematics(
            new Translation2d(Units.inchesToMeters(Global.ROBOT_LENGTH / 2), Units.inchesToMeters(-Global.ROBOT_WIDTH / 2)),
            new Translation2d(Units.inchesToMeters(Global.ROBOT_LENGTH / 2), Units.inchesToMeters(Global.ROBOT_WIDTH / 2)),
            new Translation2d(Units.inchesToMeters(-Global.ROBOT_LENGTH / 2), Units.inchesToMeters(-Global.ROBOT_WIDTH / 2)),
            new Translation2d(Units.inchesToMeters(-Global.ROBOT_LENGTH / 2), Units.inchesToMeters(Global.ROBOT_WIDTH / 2))
        );
        /**
         * Constants for Front Left Swerve Module
         */
        public static final class FL {
            public static final boolean isDisabled = false;
            public static final int DRIVE_MC_ID = ID.Falcon.swerve_fl_drive; //Falcon500 Motor Controller ID
            public static final double DRIVE_wheelDiamMM = 74.0; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_fl_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = true; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final boolean TURN_isInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final double TURN_kP = Swerve.DEFAULT_TURN_P; //PID P (only change to override default)
            public static final double TURN_kI = Swerve.DEFAULT_TURN_I; //PID I (only change to override default)
            public static final double TURN_kD = Swerve.DEFAULT_TURN_D; //PID D (only change to override default)
            public static final int TURN_kIZone = Swerve.DEFAULT_TURN_IZONE; //PID IZONE (only change to override default)
            public static final int TURN_ALLOWED_ERROR = Swerve.DEFAULT_TURN_ALLOWED_ERROR; //PID Allowed error  (only change to override default)
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MC_ID, DRIVE_isInverted, TURN_MC_ID, TURN_sensorPhase, TURN_isInverted, TURN_ALLOWED_ERROR, TURN_kP, TURN_kI, TURN_kD, TURN_kIZone, DRIVE_wheelDiamMM);
        }
        /**
         * Constants for Front Right Swerve Module
         */
        public static final class FR {
            public static final boolean isDisabled = false; 
            public static final int DRIVE_MC_ID = ID.Falcon.swerve_fr_drive; //Falcon500 Motor Controller ID
            public static final double DRIVE_wheelDiamMM = 74.0; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_fr_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = true; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final boolean TURN_isInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final double TURN_kP = Swerve.DEFAULT_TURN_P; //PID P (only change to override default)
            public static final double TURN_kI = Swerve.DEFAULT_TURN_I; //PID I (only change to override default)
            public static final double TURN_kD = Swerve.DEFAULT_TURN_D; //PID D (only change to override default)
            public static final int TURN_kIZone = Swerve.DEFAULT_TURN_IZONE; //PID IZONE (only change to override default)
            public static final int TURN_ALLOWED_ERROR = Swerve.DEFAULT_TURN_ALLOWED_ERROR; //PID Allowed error  (only change to override default)
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MC_ID, DRIVE_isInverted, TURN_MC_ID, TURN_sensorPhase, TURN_isInverted, TURN_ALLOWED_ERROR, TURN_kP, TURN_kI, TURN_kD, TURN_kIZone, DRIVE_wheelDiamMM);
        }
        /**
         * Constants for Rear Left Swerve Module
         */
        public static final class RL {
            public static final boolean isDisabled = false;
            public static final int DRIVE_MC_ID = ID.Falcon.swerve_rl_drive; //Falcon500 Motor Controller ID
            public static final double DRIVE_wheelDiamMM = 73.0; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_rl_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = true; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final boolean TURN_isInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final double TURN_kP = Swerve.DEFAULT_TURN_P; //PID P (only change to override default)
            public static final double TURN_kI = Swerve.DEFAULT_TURN_I; //PID I (only change to override default)
            public static final double TURN_kD = Swerve.DEFAULT_TURN_D; //PID D (only change to override default)
            public static final int TURN_kIZone = Swerve.DEFAULT_TURN_IZONE; //PID IZONE (only change to override default)
            public static final int TURN_ALLOWED_ERROR = Swerve.DEFAULT_TURN_ALLOWED_ERROR; //PID Allowed error  (only change to override default)
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MC_ID, DRIVE_isInverted, TURN_MC_ID, TURN_sensorPhase, TURN_isInverted, TURN_ALLOWED_ERROR, TURN_kP, TURN_kI, TURN_kD, TURN_kIZone, DRIVE_wheelDiamMM);
        }
        /**
         * Constants for Rear Right Swerve Module
         */
        public static final class RR { //Rear Right
            public static final boolean isDisabled = false;
            public static final int DRIVE_MC_ID = ID.Falcon.swerve_rr_drive; //Falcon500 Motor Controller ID
            public static final double DRIVE_wheelDiamMM = 72.0; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_rr_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = true; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final boolean TURN_isInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final double TURN_kP = Swerve.DEFAULT_TURN_P; //PID P (only change to override default)
            public static final double TURN_kI = Swerve.DEFAULT_TURN_I; //PID I (only change to override default)
            public static final double TURN_kD = Swerve.DEFAULT_TURN_D; //PID D (only change to override default)
            public static final int TURN_kIZone = Swerve.DEFAULT_TURN_IZONE; //PID IZONE (only change to override default)
            public static final int TURN_ALLOWED_ERROR = Swerve.DEFAULT_TURN_ALLOWED_ERROR; //PID Allowed error  (only change to override default)
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MC_ID, DRIVE_isInverted, TURN_MC_ID, TURN_sensorPhase, TURN_isInverted, TURN_ALLOWED_ERROR, TURN_kP, TURN_kI, TURN_kD, TURN_kIZone, DRIVE_wheelDiamMM);
        }
    }

    /**
     * Constants for the DriveTrain subsystem
     */
    public static final class DriveTrain {
        public static final boolean isDisabled = false; 
        ////Global Tuning
        public static final boolean useDriveStraight = true; //enable driveStraight functionality in drive() method
        public static final boolean useFieldCentric = true; //use field-centric drive. This should always be true except for testing?
        public static final boolean useDefensiveLock = false; //use defensiveLock strategy when braking putting swerve into X pattern
        public static final double kDriveStraight_P = 0.065; //kP for driveStraight correction
        public static final double kMaxMetersPerSecond = 3.677; //limit full stick speed meters to 12.0fps
        public static final double kMaxRotationRadiansPerSecond = 3.4; //Multiplier for omega of turning the robot
        ////Turn Tuning
        public static final double DT_TURN_MULT_STATIONARY = 1.3; //Turn speed multiplier while not moving
        public static final double DT_TURN_MULT_MOVING = 1.3; //Turn speed multiplier while moving
        public static final boolean DT_TURN_MULT_BEFORE_DB = true; //Apply turn multiplier before deadband
        public static final int DT_TURN_ENCODER_FULL_ROTATION = 4095; //This is for the lamprey2, not the integrated SRX mag encoder (lamprey1 is 1023)
        public static final int kTurnEncoderFullRotation = 4096; //This is for the integrated SRX mag encoder in the gearboxes, not the lamprey
        public static final double kTurnGearRatio = 10.3846154; //The output of the turn gearbox turns 10 times for one module rotation
        ////Drive Tuning
        public static final double DT_FWD_MULT = 1.0; //Fwd throttle multiplier
        public static final double DT_STR_MULT = 1.0; //Str throttle multiplier
        public static final boolean DT_DRIVE_DISABLED = false; //Set to true to disable the drive motors (for lab)
        public static final double DT_WHEEL_DIAM_MM = 76.2; //diameter of drive wheels in millimeters
        public static final int DT_DRIVE_ENCODER_FULL_ROTATION = 2048; //falcon integrated encoder is 2048
        //Falcon500 = 6380RPM  free speed : 945RPM Calculated
        public static final int DT_DRIVE_FIRST_GEARONE = 16; //swerve drive first gear set input teeth
        public static final int DT_DRIVE_FIRST_GEARTWO = 36; //swerve drive first gear set output teeth
        public static final int DT_DRIVE_SECOND_GEARONE = 15; //swerve drive second gear set input teeth
        public static final int DT_DRIVE_SECOND_GEARTWO = 45; //swerve drive second gear set output teeth
        public static final double DT_DRIVE_CONVERSION_FACTOR = 0.148148; //first_gearone / first_geartwo * second_gearone / second_geartwo
        // public static final double DT_DRIVE_CONVERSION_FACTOR = (DT_DRIVE_FIRST_GEARONE / DT_DRIVE_FIRST_GEARTWO) * (DT_DRIVE_SECOND_GEARONE / DT_DRIVE_SECOND_GEARTWO); //Conversion factor to correct RPM from SparkMax getVelocity()
    }
    
    public static final class Vision {
        public static final boolean isDisabled = false;
        public static final boolean stateLightOn = true;
        public static final int id_RingLight = ID.Relay.ringlight; //Relay ID of Ringlight SS Relay
        public static final double kErrorCorrection_P = 0.65; //Proportional value for multiplying vision angle correction
        public static final double kTurnP = 0.17;
        public static final double kTurnD = 0.0;
        public static final double kOffsetDegrees = 0.0; //Manual offset adjustment; +right; -left
        public static final double kMinTurnPower = 0.25; //Minimum power for turning during vision
        public static final double kCloseEnough = 0.04; //Percentage of allowed error
    }
    /**
     * Constants for the Operator Interface
     * The OI is based on 2 Logitech Controllers, a driver and an operator, setup for swerve drive.
     * The driver left stick controls the forward rate (up/down), and strafe rate (left/right).
     * The driver right stick controls the rotation rate (left/right).
     */
    public static final class OI { //we define the axis' here because they are not bound in robotContainer.
        public static final int OI_JOY_DRIVER = 0; //ID of Driver Joystick
        public static final int OI_JOY_OPER = 1; //ID of Operator Joystick
        public static final double OI_JOY_MIN_DEADBAND = 0.1; //Deadband for analog joystick axis minimum
        public static final double OI_JOY_MAX_DEADBAND = 0.9; //Deadband for analog joystick axis minimum

        /**
         * Constants for the Driver controller
         */
        public static final class Driver {
            public final static int AXIS_STRAFE = Logitech.AXIS_LH; //Axis that moves the robot side to side on the field
            public final static int AXIS_FWD = Logitech.AXIS_LV; //Axis that moves the robot up and down the field
            public final static int AXIS_TURN = Logitech.AXIS_RH; //Axis that controls the rotation of the robot
        }

        /**
         * This class defines the hardware button and axis IDs for a Logitech F310 Controller.
         * The buttons array is 1-based, but the axis array is 0-based
         */
        public static final class Logitech {
            //DO NOT EDIT THESE
            static final int BTN_A = 1; //A Button
            static final int BTN_B = 2; //B Button
            static final int BTN_X = 3; //X Button
            static final int BTN_Y = 4; //Y Button
            static final int BTN_LB = 5; //Left Bumper (L1)
            static final int BTN_RB = 6; //Right Bumper (R1)
            static final int BTN_BACK = 7; //Back Button (Select)
            static final int BTN_START = 8; //Start Button
            static final int BTN_L = 9; //Left Stick Press (L3)
            static final int BTN_R = 10; //Right Stick Press (R3)
            static final int AXIS_LH = 0; //Left Analog Stick horizontal
            static final int AXIS_LV = 1; //Left Analog Stick vertical
            static final int AXIS_LT = 2; //Analog Left Trigger
            static final int AXIS_RT = 3; //Analog Right Trigger
            static final int AXIS_RH = 4; //Right Analog Stick horizontal
            static final int AXIS_RV = 5; //Right Analog Stick vertical
            static final int DPAD_UP = 0;
            static final int DPAD_UPRIGHT = 45;
            static final int DPAD_RIGHT = 90;
            static final int DPAD_DNRIGHT = 135;
            static final int DPAD_DN = 180;
            static final int DPAD_DNLEFT = 225;
            static final int DPAD_LEFT = 270;
            static final int DPAD_UPLEFT = 315;
            static final int DPAD_IDLE = -1; 
        }
    }
}
