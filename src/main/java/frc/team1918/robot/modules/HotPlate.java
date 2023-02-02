
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

public class HotPlate {
    private WPI_TalonSRX m_motor;
    private double m_kP, m_kI, m_kD;
    private int m_kIZone;
    private int m_positionAllowedError, m_positionFullRotation;
    private String m_moduleName;

 	/**
	 * 1918 HotPlate Module v2023.1 - This spatula module uses a TalonSRX with 775, 550, or Bag motor on a Versa Planetary to serve scoring pieces
     * The module uses a Versa-Planetary Encoder Stage for positioning data.
     * There is a high limit and low limit switch, connected to the Talon to limit the mechanical travel
	 * @param name This is the name of this spatula module (ie. "HotPlate")
     * @param moduleConstants This is a HotPlateConstants object containing the data for this module
	 */
    public HotPlate(String name, HotPlateConstants moduleConstants){
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

        // turn.setSelectedSensorPosition(0); //reset the talon encoder counter to 0 so we dont carry over a large error from a previous testing
        // turn.set(ControlMode.Position, 1024); //set this to some fixed value for testing
        m_motor.setSensorPhase(moduleConstants.SensorPhase); //set the sensor phase based on the constants setting for this module
        m_motor.setInverted(moduleConstants.IsInverted); //set the motor direction based on the constants setting for this module
        m_motor.config_kP(0, m_kP); //set the kP for PID Tuning
        m_motor.config_kI(0, m_kI);
        m_motor.config_kD(0, m_kD);
        m_motor.config_IntegralZone(0, m_kIZone);
        m_motor.configAllowableClosedloopError(Constants.Global.PID_PRIMARY, m_positionAllowedError); 
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
     * This function is used to output data to the dashboard for debugging the module, typically done in the {@link DriveSubsystem} periodic.
     */
    public void updateDashboard() {
        Dashboard.Spatula.setSpatulaPosition(m_moduleName, (int) getPositionAbsolute() & 0x3FF);
    }

}