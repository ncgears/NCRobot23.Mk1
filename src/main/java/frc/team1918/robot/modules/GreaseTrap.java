
package frc.team1918.robot.modules;
//Talon SRX/Talon FX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

//Team 1918
import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;

public class GreaseTrap {
    private WPI_TalonSRX m_motor;
    private double m_kP, m_kI, m_kD;
    private int m_kIZone;
    private int m_positionAllowedError, m_positionFullRotation;
    private String m_moduleName;
    public enum GreaseTrapPositions {HOME, LEVEL, DOWN};

 	/**
	 * 1918 GreaseTrap Module v2023.1 - This GreaseTrap module uses a TalonSRX with 775, 550, or Bag motor on a Versa Planetary to unburn pancakes (flip them)
     * The module uses a Versa-Planetary Encoder Stage for positioning data.
     * @param name This is the name of this GreaseTrap module (ie. "GreaseTrap")
     * @param moduleConstants This is a GreaseTrapConstants object containing the data for this module
	 */
    public GreaseTrap(String name, HotPlateConstants moduleConstants){
        m_moduleName = name;
        m_motor = new WPI_TalonSRX(moduleConstants.MotorID);
        m_kP = moduleConstants.kP;
        m_kI = moduleConstants.kI;
        m_kD = moduleConstants.kD;
        m_kIZone = moduleConstants.kIZone;
        m_positionAllowedError = moduleConstants.PositionAllowedError;
        m_positionFullRotation = moduleConstants.SensorTicks;

        m_motor.configFactoryDefault(); //Reset controller to factory defaults to avoid wierd stuff from carrying over
        m_motor.set(ControlMode.PercentOutput, 0); //Set controller to disabled
        m_motor.setNeutralMode(NeutralMode.Brake); //Set controller to brake mode
        m_motor.configSelectedFeedbackSensor(  FeedbackDevice.CTRE_MagEncoder_Absolute, // Local Feedback Source
                                            Constants.Global.PID_PRIMARY,				// PID Slot for Source [0, 1]
                                            Constants.Global.kTimeoutMs);				// Configuration Timeout
                                            m_motor.configFeedbackNotContinuous(moduleConstants.SensorNonContinuous, 0); //Disable continuous feedback tracking (so 0 and 1024 are effectively one and the same)

/*  CTRE SRX Mag Encoder Setup
        turn.configSelectedFeedbackSensor ( FeedbackDevice.CTRE_MagEncoder_Relative,
                                            Constants.Global.PID_PRIMARY,
                                            Constants.Global.kTimeoutMs);
        turn.configSelectedFeedbackSensor ( FeedbackDevice.CTRE_MagEncoder_Absolute,
                                            Constants.Global.PID_AUXILLARY,
                                            Constants.Global.kTimeoutMs);
*/  

        m_motor.setSelectedSensorPosition(0); //reset the talon encoder counter to 0 so we dont carry over a large error from a previous testing
        // m_motor.set(ControlMode.Position, 1024); //set this to some fixed value for testing
        m_motor.setSensorPhase(moduleConstants.SensorPhase); //set the sensor phase based on the constants setting for this module
        m_motor.setInverted(moduleConstants.IsInverted); //set the motor direction based on the constants setting for this module
        m_motor.config_kP(0, m_kP); //set the kP for PID Tuning
        m_motor.config_kI(0, m_kI);
        m_motor.config_kD(0, m_kD);
        m_motor.config_IntegralZone(0, m_kIZone);
        m_motor.configAllowableClosedloopError(Constants.Global.PID_PRIMARY, m_positionAllowedError); 

        //This should accept Arbitrary Feed Forward to compensate for gravity, etc.
        //See https://v5.docs.ctr-electronics.com/en/latest/ch16_ClosedLoop.html#arbitrary-feed-forward
        //it is the "amount needed to break free from gravity, etc."

    }

    /**
     *  Returns the current position of the spatula in encoder counts
     * @return The current encoder position of the spatula
     */
    public double getPositionAbsolute() {
        return m_motor.getSelectedSensorPosition(Constants.Global.PID_PRIMARY);
    }

    /**
     * Moves the spatula to it's home position (starting configuration)
     */
    public void stow() {
        m_motor.set(ControlMode.Position, Constants.Stove.GreaseTrap.Positions.home);
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

    public void moveTo(GreaseTrapPositions position) {
        switch (position) {
            case HOME:
                m_motor.set(ControlMode.Position, Constants.Stove.GreaseTrap.Positions.home);
                break;
            case LEVEL:
                m_motor.set(ControlMode.Position, Constants.Stove.GreaseTrap.Positions.level);
                break;
        }
    }

    public String getModuleName() {
        return m_moduleName;
    }

    /**
     * This function is used to output data to the dashboard for debugging the module, typically done in the {@link DriveSubsystem} periodic.
     */
    public void updateDashboard() {
        Dashboard.Spatula.setSpatulaPosition(m_moduleName, (int) getPositionAbsolute() & 0x3FF);
    }

}