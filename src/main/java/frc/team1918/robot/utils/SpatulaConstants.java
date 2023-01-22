package frc.team1918.robot.utils;

public class SpatulaConstants {
    //control
    public final int spatulaMotorID;
    public final boolean spatulaSensorPhase;
    public final int spatulaSensorTicks = 4096; //Number of encoder ticks for the encoder
    public final boolean spatulaSensorNonContinuous = false; //usually, true if within 1 rotation, false if it wraps past 0
    public final boolean spatulaIsInverted;
    public final int spatulaPositionAllowedError;
    public final double spatulaP;
    public final double spatulaI;
    public final double spatulaD;
    public final int spatulaIZone;

    /**
     * Creates a new constants object for initializing a spatula
     * @param MotorID - (int) CAN ID of the Motor (TalonSRX)
     * @param SensorPhase - (bool) true to invert the sensor phase
     * @param SensorRotationTicks - (int) count of encoder ticks in a full rotation
     * @param IsInverted - (bool) true to invert the motor control
     * @param AllowedError - (int) maximum allowable error for position control
     * @param kP - (double) Proportional constant for the position control
     * @param kI - (double) Integral constant for the position control
     * @param kD - (double) Derivative constant for the position control
     * @param kIZone - (int) Integral Zone constant for the position control
     */
    public SpatulaConstants(int MotorID, boolean SensorPhase, boolean IsInverted, int AllowedError, double kP, double kI, double kD, int kIZone) {
        //turn
        this.spatulaMotorID = MotorID;
        this.spatulaSensorPhase = SensorPhase;
        this.spatulaIsInverted = IsInverted;
        this.spatulaPositionAllowedError = AllowedError;
        this.spatulaP = kP;
        this.spatulaI = kI;
        this.spatulaD = kD;
        this.spatulaIZone = kIZone;
    }
}
