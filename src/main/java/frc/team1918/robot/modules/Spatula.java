
package frc.team1918.robot.modules;
//Talon SRX/Talon FX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

//Team 1918
import frc.team1918.robot.Constants;

public class Spatula {
    private WPI_TalonSRX m_motor;
    private double m_kP, m_kI, m_kD, m_kF, m_kPeakOutput, m_kCruise, m_kAccel;
    private int m_kIZone;
    private int m_positionAllowedError;
    private String m_moduleName;

 	/**
	 * 1918 Spatula Module v2023.1 - This spatula module uses a TalonSRX with 775, 550, or Bag motor on a Versa Planetary to pick up game pieces.
     * The module uses a Versa-Planetary Encoder Stage for positioning data.
     * There is a high limit and low limit switch, connected to the Talon to limit the mechanical travel
	 * @param name This is the name of this spatula module (ie. "SpatulaLeft" or "SpatulaRight")
     * @param moduleConstants This is a SpatulaConstants object containing the data for this module
	 */
    public Spatula(String name, SpatulaConstants moduleConstants){
        m_moduleName = name;
        m_motor = new WPI_TalonSRX(moduleConstants.MotorID);
        m_kPeakOutput = moduleConstants.kPeakOutput;
        m_kP = moduleConstants.kP;
        m_kI = moduleConstants.kI;
        m_kD = moduleConstants.kD;
        m_kF = moduleConstants.kF;
        m_kCruise = moduleConstants.kCruise;
        m_kAccel = moduleConstants.kAccel;
        m_kIZone = moduleConstants.kIZone;
        m_positionAllowedError = moduleConstants.PositionAllowedError;

        m_motor.configFactoryDefault(); //Reset controller to factory defaults to avoid wierd stuff from carrying over
        m_motor.set(ControlMode.PercentOutput, 0); //Set controller to stopped
        m_motor.setNeutralMode(NeutralMode.Brake); //Set controller to brake mode
        m_motor.configSelectedFeedbackSensor(  FeedbackDevice.CTRE_MagEncoder_Absolute, // Local Feedback Source
            Constants.Global.kPidIndex,				// PID Slot for Source [0, 1]
            Constants.Global.kTimeoutMs);				// Configuration Timeout

        //m_motor.configFeedbackNotContinuous(moduleConstants.SensorNonContinuous, 0); //Disable continuous feedback tracking (so 0 and 1024 are effectively one and the same)
        m_motor.configNeutralDeadband(0.001, Constants.Global.kTimeoutMs); //Adjust deadband to 0.1% from default of 4%

        //if the sensor phase is wrong, plot will go opposite direction of target, need to fix before setting inverted
        m_motor.setSensorPhase(moduleConstants.SensorPhase); //set the sensor phase based on the constants setting for this module
        //once sensor phase is correct, set inverted so "forward" is green lights, "reverse" is red lights
        m_motor.setInverted(moduleConstants.IsInverted); //set the motor direction based on the constants setting for this module

        /* Set relevant frame periods to be at least as fast as periodic rate */
		m_motor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.Global.kTimeoutMs);
		m_motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.Global.kTimeoutMs);        

        /* Set peak and nominal outputs */
        m_motor.configNominalOutputForward(0, Constants.Global.kTimeoutMs);
        m_motor.configNominalOutputReverse(0, Constants.Global.kTimeoutMs);
        m_motor.configPeakOutputForward(m_kPeakOutput, Constants.Global.kTimeoutMs);
        m_motor.configPeakOutputReverse(-m_kPeakOutput, Constants.Global.kTimeoutMs);

        /* Set Motion Magic gains in slot0 */
        m_motor.selectProfileSlot(Constants.Global.kPidProfileSlotIndex, Constants.Global.kPidIndex);
        m_motor.config_kF(Constants.Global.kPidProfileSlotIndex, m_kF, Constants.Global.kTimeoutMs);
        m_motor.config_kP(Constants.Global.kPidProfileSlotIndex, m_kP, Constants.Global.kTimeoutMs);
        m_motor.config_kI(Constants.Global.kPidProfileSlotIndex, m_kI, Constants.Global.kTimeoutMs);
        m_motor.config_kD(Constants.Global.kPidProfileSlotIndex, m_kD, Constants.Global.kTimeoutMs);
        //m_motor.config_IntegralZone(0, m_kIZone);
        m_motor.configAllowableClosedloopError(Constants.Global.kPidIndex, m_positionAllowedError); 

        /* Set acceleration and cruise velocity */
        m_motor.configMotionCruiseVelocity(m_kCruise, Constants.Global.kTimeoutMs);
        m_motor.configMotionAcceleration(m_kAccel, Constants.Global.kTimeoutMs);

        /* Zero the sensor on robot boot */
        m_motor.configClearPositionOnLimitF(false, Constants.Global.kTimeoutMs);
        m_motor.configClearPositionOnLimitR(false, Constants.Global.kTimeoutMs);
        // m_motor.setSelectedSensorPosition(0); //reset the talon encoder counter to 0 so we dont carry over a large error from a previous testing
    }

    /**
     *  Returns the current position of the spatula in encoder counts
     * @return The current encoder position of the spatula
     */
    public double getPositionAbsolute() {
        return m_motor.getSelectedSensorPosition(Constants.Global.kPidIndex);
    }

    /**
     * Moves the spatula to it's home position (starting configuration)
     */
    public void stowSpatula() {
        m_motor.set(ControlMode.Position, Constants.Spatula.Left.positionHome);
    }

    /**
     * Sets the brake mode for the spatula (to allow it to move freely)
     * @param brake Boolean indicating if the brake mode should be set to brake (true) or coast (false)
     */
    public void setBrakeMode(String device, boolean brake) {
        if (brake) {
            m_motor.setNeutralMode(NeutralMode.Brake);
        } else {
            m_motor.setNeutralMode(NeutralMode.Coast);
        }
    }

    public String getModuleName() {
        return m_moduleName;
    }

    /**
     * This function is used to output data to the dashboard for debugging the module, typically done in the periodic method.
     */
    public void updateDashboard() {
        // Dashboard.Spatula.setPosition(m_moduleName, (int) getPositionAbsolute() & 0x3FF);
    }

}