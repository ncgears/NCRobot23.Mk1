
package frc.team1918.robot;
import frc.team1918.robot.utils.PIDGains;
import frc.team1918.robot.modules.SwerveModuleConstants;
import frc.team1918.robot.modules.SpatulaNamedPositions;
import frc.team1918.robot.utils.TalonConstants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

//Sometimes it is useful to comment out the following to see what variables or what controller buttons are not assigned yet
//@SuppressWarnings("unused") //We silence the "unused variables" warnings in VSCode
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
            //public static int some_named_dio = 0;
        }
        /**
         * IDs of RoboRio Analog IO
         */
        public static final class Analog {
            //public static int some_named_aio = 0;
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
            public static int swerve_fl_turn = 1;
            public static int swerve_fr_turn = 2;
            public static int swerve_rl_turn = 3;
            public static int swerve_rr_turn = 4;
            public static int spatula_left = 11;
            public static int spatula_right = 12; 
            public static int stove_hotplate = 13; //hotplate is the scoring ramp
            public static int stove_greasetrap = 14; //greasetrap is the flip ramp
            public static int stove_burner = 15; //burner is the main conveyor
            public static int stove_aimer = 16; //thing to move hotplate left and right
        }
        /**
         * IDs of Falcons
         */
        public static final class Falcon {
            public static int swerve_fl_drive = 31;
            public static int swerve_fr_drive = 32;
            public static int swerve_rr_drive = 34;
            public static int swerve_rl_drive = 33;
            public static int stove_griddle = 41; //griddle is the primary conveyor
        }
    }

    /**
     * Constants that are Global for the robot
     */
    public static final class Global {
        //Global Constants
        public static final boolean CAMERA_ENABLED = false; //set to false if UsbCamera is removed
        public static final boolean SWERVE_SENSOR_NONCONTINUOUS = false;
        public static final int kTimeoutMs = 30; //Timeout for reporting in DS if action fails, set to 0 to skip confirmation
        public static final int kPidIndex = 0;  //Talon PID index for primary loop
        public static final int kPidProfileSlotIndex = 0; //PID Profile gains slot
        public static final int ROBOT_WIDTH = 21; //Width of the robot frame (from the pivot of the wheels)
        public static final int ROBOT_LENGTH = 28; //Length of the robot frame (from the pivot of the wheels)
        public static final boolean DEBUG_ENABLED_DEFAULT = true; //Default starting state of debug mode
        public static final int DEBUG_RECURRING_TICKS = 100; //Periodic cycles for recurring debug messages
        public static final int DASH_RECURRING_TICKS = 50; //Periodic cycles for dashboard updates
        public final static boolean tuningMode = false; //Enable tunable numbers
    }

    /**
     * Constants for the Air subsystem
     */
    public static final class Air {
        public static final boolean isDisabled = true; //Disable Air
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
        public static final class Balance {
            public static final boolean kUseDefensiveLock = true; //lock the drive train into defensive position when finished balancing
            public static final double kToleranceDegrees = 3.5; //degrees of tolerance for balancing
            public static final double kP = 0.015;
            public static final double kI = 0.0;
            public static final double kD = 0.0;
        }
    }

    /**
     * Constants for the Stove Subsystem
     */
    public static final class Stove {
            public static final class Aimer {
                public static final boolean isDisabled = false;
                public static final double kZeroSpeed = 0.35; //speed for zeroing operations
                public static final double kSpeed = 0.35;
                public static final boolean kSoftLimitEnable = true;
                public static final double kSoftLimitMax = 20000.0;
                public static final double kSoftLimitMin = -20000.0;
                //Controller Setup
                public static final int kMotorID = ID.Talon.stove_aimer; //TalonSRX Motor Controller ID
                public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
                public static final int kSensorTicks = 4096;
                public static final boolean kSensorNotContinuous = false;
                public static final boolean kIsInverted = false; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
                public static final int kAllowedError = 5; //PID Allowed error
                public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
                //PID Setup
                public static final double kP = 0.45; //PID P 
                public static final double kI = 0.0; //PID I
                public static final double kD = 0.0; //PID D
                public static final double kF = 0.32; //PID F
                public static final int kIZone = 0; //PID IZONE
                public static final double kPeakOutput = 1.0;
                public static final double kNeutralDeadband = 0.001; //0.04 default
                public static final double kCruise = 3200; //MotionMagic Cruise
                public static final double kAccel = 4400; //MotionMagic Acceleration
                public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            }
            public static final class Burner {
            public static final boolean isDisabled = false;
            public static final double kZeroSpeed = 0.25; //speed for zeroing operations
            //Controller Setup
            public static final int kMotorID = ID.Talon.stove_burner; //TalonSRX Motor Controller ID
            public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = 0.45; //PID P 
            public static final double kI = 0.0; //PID I
            public static final double kD = 0.0; //PID D
            public static final double kF = 0.32; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = 1.0;
            public static final double kNeutralDeadband = 0.001; //0.04 default
            public static final double kCruise = 3200; //MotionMagic Cruise
            public static final double kAccel = 4400; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            //Named positions
            public static final class Positions {
                public static final double home = 0.0; //postion for home
                public static final double bottom = 4100.0; //position for bottom scoring
                public static final double top = 18300.0; //position for top scoring
            }
        }
        public static final class Griddle {
            public static final boolean kIsDisabled = false;
            //Controller Setup
            public static final int kMotorID = ID.Falcon.stove_griddle; //TalonFX Motor Controller ID
            public static final boolean kSensorPhase = true; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = false; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final double kSpeed = 0.65;
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = 2.8; //PID P 
            public static final double kI = 0.0; //PID I
            public static final double kD = 0.0; //PID D
            public static final double kF = 0.2; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = 1.0;
            public static final double kNeutralDeadband = 0.001; //0.04 default
            public static final double kCruise = 3500; //MotionMagic Cruise
            public static final double kAccel = 4500; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
        }
        public static final class GreaseTrap {
            public static final boolean isDisabled = false;
            public static final double kZeroSpeed = 0.75; //speed for zeroing operations
            //Controller Setup
            public static final int kMotorID = ID.Talon.stove_greasetrap; //TalonSRX Motor Controller ID
            public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = 0.5; //PID P 
            public static final double kI = 0.0; //PID I
            public static final double kD = 0.0; //PID D
            public static final double kF = 0.25; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = 1.0;
            public static final double kNeutralDeadband = 0.001; //0.04 default
            public static final double kCruise = 4000; //MotionMagic Cruise
            public static final double kAccel = 5000; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            //Named positions
            public static final class Positions {
                public static final double home = 0.0; //home/stow/starting config
                public static final double flip = 0.0; //position to goto for flipping pancakes
                public static final double clear = 800.0; //clear of spatula
                public static final double level = 4500.0; //level
                public static final double down = 5700.0; //down
            }
        }
        public static final class HotPlate {
            public static final boolean isDisabled = false;
            public static final double kZeroSpeed = 0.75; //speed for zeroing operations
            //Controller Setup
            public static final int kMotorID = ID.Talon.stove_hotplate; //TalonSRX Motor Controller ID
            public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = 0.5; //PID P 
            public static final double kI = 0.0; //PID I
            public static final double kD = 0.0; //PID D
            public static final double kF = 0.25; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = 1.0;
            public static final double kNeutralDeadband = 0.001; //0.04 default
            public static final double kCruise = 4000; //MotionMagic Cruise
            public static final double kAccel = 5000; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            //Named positions
            public static final class Positions {
                public static final double home = 0.0; //home position
                public static final double clear = 800.0; //clear of spatula
                public static final double level = 4500.0; //level position
                public static final double down = 5400.0; //down position
            }
        }
    }

    /**
     * Constants for the Spatula Modules
     */
    public static final class Spatula {
        public static final double default_kZeroSpeed = 0.4; //speed for zeroing operations
        public static final double default_kP = 0.45; //PID P
        public static final double default_kI = 0.0; //PID I
        public static final double default_kD = 0.0; //PID D
        public static final double default_kF = 0.25; //PID F
        public static final int default_kIZone = 0; //PID IZone
        public static final int default_positionAllowedError = 5; //PID Allowed Error
        public static final double default_kPeakOutput = 1.0; //Peak Controller Output
        public static final double default_kNeutralDeadband = 0.001; //Neutral Deadband
        public static final double default_kCruise = 3600; //Cruise Speed for Motion Magic
        public static final double default_kAccel = 5000; //Accel for Motion Magic
        // current limits
        // current limiting //TODO: Needs tuning, this was borrowed from Team364 example
        // See {@link https://github.com/Team364/BaseFalconSwerve/blob/main/src/main/java/frc/robot/CTREConfigs.java}
        public static final boolean isCurrentLimitEnabled = true;
        public static final int kCurrentLimitAmps = 25;
        public static final int kCurrentThresholdAmps = 40;
        public static final double kCurrentThresholdSecs = 0.1;

        public static final class Left {
            public static final boolean isDisabled = false;
            public static final double kZeroSpeed = default_kZeroSpeed;
            //Controller Setup
            public static final int kMotorID = ID.Talon.spatula_left; //TalonSRX Motor Controller ID
            public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = false; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = Spatula.default_kP; //PID P 
            public static final double kI = Spatula.default_kI; //PID I
            public static final double kD = Spatula.default_kD; //PID D
            public static final double kF = Spatula.default_kF; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = Spatula.default_kPeakOutput;
            public static final double kNeutralDeadband = Spatula.default_kNeutralDeadband; //0.04 default
            public static final double kCruise = Spatula.default_kCruise; //MotionMagic Cruise
            public static final double kAccel = Spatula.default_kAccel; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            //Named positions
            public static final SpatulaNamedPositions positions = new SpatulaNamedPositions(
                66000.0,
                66000.0,
                65500.0,
                0.0,
                66700.0,
                38500.0,
                38500.0,
                51300.0
            );
/*  SAVE for later reference on how to store additional data here
//https://www.baeldung.com/java-enum-values
            public static enum Pos {
                HOME(0.0),
                GRIDDLE(512.0),
                CLEAR(768.0),
                FLOOR(1024.0);
                public final double position;
                private Pos(double position) {
                    this.position = position;
                }
                public double getPosition() { return this.position;}
            }
*/
        }
        public static final class Right {
            public static final boolean isDisabled = false;
            public static final double kZeroSpeed = 0.6;
            //Controller Setup
            public static final int kMotorID = ID.Talon.spatula_right; //TalonSRX Motor Controller ID
            public static final boolean kSensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
            public static final int kSensorTicks = 4096;
            public static final boolean kSensorNotContinuous = false;
            public static final boolean kIsInverted = true; //Once sensor phase is correct, we can invert these so fwd always is green, reverse is always is red
            public static final int kAllowedError = 5; //PID Allowed error
            public static final TalonConstants constants = new TalonConstants(kMotorID, kSensorPhase, kSensorTicks, kSensorNotContinuous, kIsInverted, kAllowedError);
            //PID Setup
            public static final double kP = Spatula.default_kP; //PID P 
            public static final double kI = Spatula.default_kI; //PID I
            public static final double kD = Spatula.default_kD; //PID D
            public static final double kF = Spatula.default_kF; //PID F
            public static final int kIZone = 0; //PID IZONE
            public static final double kPeakOutput = Spatula.default_kPeakOutput;
            public static final double kNeutralDeadband = Spatula.default_kNeutralDeadband; //0.04 default
            public static final double kCruise = Spatula.default_kCruise; //MotionMagic Cruise
            public static final double kAccel = Spatula.default_kAccel; //MotionMagic Acceleration
            public static final PIDGains gains = new PIDGains(kP,kI,kD,kF,kIZone,kPeakOutput,kNeutralDeadband, kCruise,kAccel);
            //Named positions
            public static final SpatulaNamedPositions positions = new SpatulaNamedPositions(
                65500.0,
                65500.0,
                65500.0,
                0.0,
                66700.0,
                37750,
                36000,
                50300
            );
        }
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
        public static final int DEFAULT_TURN_ALLOWED_ERROR = 3; //PID Allowed Error
        public static final double DEFAULT_WHEEL_DIAM_MM = 101.6; //Wheel Diameter of 3in colson
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
        public static final double kMaxSpeedMetersPerSecond = 4.115; //13.5fps calculated;
        public static final boolean kGyroReversed = false;
        // Drive Motor Characterization
        // See {@link https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/controller/SimpleMotorFeedforward.html}
        // How do we determine these numbers? Need to find out. These falcon numbers are from Team364 example
        public static final double driveKS = (0.667 / 12); //Static Gain //divide by 12 to convert from volts to percent output for CTRE
        public static final double driveKV = (2.44 / 12); //Velocity Gain
        public static final double driveKA = (0.27 / 12); //Acceleration Gain

        //Forward Positive, Left Positive, Up Positive (NWU Convention)
        public static final SwerveDriveKinematics kDriveKinematics =
        //TODO 2023: Is the left and right incorrect? should by ++,+-,-+,-- (FL,FR,RL,RR)
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
            public static final double DRIVE_wheelDiamMM = DEFAULT_WHEEL_DIAM_MM; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_fl_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
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
            public static final double DRIVE_wheelDiamMM = DEFAULT_WHEEL_DIAM_MM; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_fr_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
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
            public static final double DRIVE_wheelDiamMM = DEFAULT_WHEEL_DIAM_MM; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_rl_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
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
            public static final double DRIVE_wheelDiamMM = DEFAULT_WHEEL_DIAM_MM; //actual diameter of larger wheel in mm
            public static final boolean DRIVE_isInverted = false;
            public static final int TURN_MC_ID = ID.Talon.swerve_rr_turn; //TalonSRX Motor Controller ID
            public static final boolean TURN_sensorPhase = false; //When forward/reverse of controller doesn't match forward/reverse of sensor, set to true
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
        public static final double kMaxMetersPerSecond = 4.115; //limit full stick speed meters to 13.5fps
        public static final double kMaxRotationRadiansPerSecond = 3.4; //Multiplier for omega of turning the robot
        ////Turn Tuning
        public static final double DT_TURN_MULT_STATIONARY = 1.3; //Turn speed multiplier while not moving
        public static final double DT_TURN_MULT_MOVING = 1.3; //Turn speed multiplier while moving
        public static final boolean DT_TURN_MULT_BEFORE_DB = true; //Apply turn multiplier before deadband
        public static final int DT_TURN_ENCODER_FULL_ROTATION = 1023; //This is for the lamprey2, not the integrated SRX mag encoder (lamprey1 is 1023)
        public static final int kTurnEncoderFullRotation = 4096; //This is for the integrated SRX mag encoder in the gearboxes, not the lamprey
        public static final double kTurnGearRatio = 10.3846154; //The output of the turn gearbox turns 10 times for one module rotation
        ////Drive Tuning
        public static final double DT_FWD_MULT = 1.0; //Fwd throttle multiplier
        public static final double DT_STR_MULT = 1.0; //Str throttle multiplier
        public static final boolean DT_DRIVE_DISABLED = false; //Set to true to disable the drive motors (for lab)
        public static final double DT_WHEEL_DIAM_MM = 101.6; //diameter of drive wheels in millimeters
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
            public final static int AXIS_STRAFE = Stadia.AXIS_LH; //Axis that moves the robot side to side on the field
            public final static int AXIS_FWD = Stadia.AXIS_LV; //Axis that moves the robot up and down the field
            public final static int AXIS_TURN = Stadia.AXIS_RH; //Axis that controls the rotation of the robot
        }

        public static final class Operator {
            public final static int AXIS_AIMER = Stadia.AXIS_LH;
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

        /**
         * This class defines the hardware button and axis IDs for a Stadia Controller.
         * The buttons array is 1-based, but the axis array is 0-based
         */
        public static final class Stadia {
            //DO NOT EDIT THESE
            static final int BTN_A = 1; //A Button
            static final int BTN_B = 2; //B Button
            static final int BTN_X = 3; //X Button
            static final int BTN_Y = 4; //Y Button
            static final int BTN_LB = 5; //Left Bumper (L1)
            static final int BTN_RB = 6; //Right Bumper (R1)
            static final int BTN_L = 7; //Left Stick Press (L3)
            static final int BTN_R = 8; //Right Stick Press (R3)
            static final int BTN_ELLIPSES = 9; //Ellipsis Button (...)
            static final int BTN_HAMBURGER = 10; //Hamburger Button
            static final int BTN_STADIA = 11; //Stadia Button
            static final int BTN_RT = 12; //Right Trigger (R2)
            static final int BTN_LT = 13; //Left Trigger (L2)
            static final int BTN_GOOGLE = 14; //Google Button
            static final int BTN_FRAME = 15; //Square Frame Button
            static final int AXIS_LH = 0; //Left Analog Stick horizontal (right +)
            static final int AXIS_LV = 1; //Left Analog Stick vertical (down +)
            static final int AXIS_LV2 = 2; //Left Analog Stick vertical (down +) - duplicate
            static final int AXIS_RH = 3; //Right Analog Stick horizontal (right +)
            static final int AXIS_RV = 4; //Right Analog Stick vertical (down +)
            static final int AXIS_RV2 = 5; //Right Analog Stick vertical (down +) - duplicate
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
