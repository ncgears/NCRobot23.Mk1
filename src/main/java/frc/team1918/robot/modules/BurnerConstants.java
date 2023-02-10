package frc.team1918.robot.modules;

public class BurnerConstants {
    //control
    public final int MotorID;
    public final boolean SensorPhase;
    public final int SensorTicks = 4096; //Number of encoder ticks for the encoder
    public final double kPeakOutput = 1.0; //Max output of controller
    public final boolean SensorNonContinuous = false; //usually, true if within 1 rotation, false if it wraps past 0
    public final boolean IsInverted;
    public final int PositionAllowedError;
    public final double kP;
    public final double kI;
    public final double kD;
    public final double kF;
    public final double kCruise;
    public final double kAccel;
    public final int kIZone;

    /**
     * Creates a new constants object for initializing a HotPlate Module
     * @param MotorID - (int) CAN ID of the Motor (TalonSRX)
     * @param SensorPhase - (bool) true to invert the sensor phase
     * @param SensorRotationTicks - (int) count of encoder ticks in a full rotation
     * @param IsInverted - (bool) true to invert the motor control
     * @param AllowedError - (int) maximum allowable error for position control
     * @param kP - (double) Proportional constant for the position control
     * @param kI - (double) Integral constant for the position control
     * @param kD - (double) Derivative constant for the position control
     * @param kF - (double) Feed Forward constant for the position control
     * @param kCruise - (double) Cruise constant for motionmagic
     * @param kAccel - (double) Accelleration constant for motionmagic
     * @param kIZone - (int) Integral Zone constant for the position control
     */
    public BurnerConstants(int MotorID, boolean SensorPhase, boolean IsInverted, int AllowedError, double kP, double kI, double kD, double kF, double kCruise, double kAccel, int kIZone) {
        //turn
        this.MotorID = MotorID;
        this.SensorPhase = SensorPhase;
        this.IsInverted = IsInverted;
        this.PositionAllowedError = AllowedError;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.kCruise = kCruise;
        this.kAccel = kAccel;
        this.kIZone = kIZone;
    }
}
