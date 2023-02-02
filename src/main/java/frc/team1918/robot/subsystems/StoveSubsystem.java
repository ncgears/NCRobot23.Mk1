package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.modules.GreaseTrap;
import frc.team1918.robot.modules.GreaseTrap.GreaseTrapPositions;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * The STOVE Subsystem manages the GRIDDLE (primary conveyor), HOTPLATE (scoring ramp), and GREASETRAP (flip ramp). 
 * It is responsible for delivering PANCAKES (cones) and WAFFLES (cubes) off the end if the scoring ramp or back to the flip ramp.
 * It manages the angle of the 
*/
public class StoveSubsystem extends SubsystemBase {
	private static StoveSubsystem instance;
	public enum ramps {BOTH, HOTPLATE, GREASETRAP}
	
	//initialize HOTPLATE and GREASETRAP
	private WPI_TalonSRX m_HotPlate; //scoring ramp controller
	private GreaseTrap m_GreaseTrap; //flip ramp controller
	
	/**
	 * Returns the instance of the Intake subsystem.
	 * The purpose of this is to only create an instance if one does not already exist.
	 * @return IntakeSubSystem instance
	 */
	public static StoveSubsystem getInstance() {
		if (instance == null)
			instance = new StoveSubsystem();
		return instance;
	}

	/**
	 * Initializes the IntakeSubsystem class, performs setup steps, etc.
	 */
	public StoveSubsystem() {
		stowRamp(ramps.BOTH); //Make sure all spatulas are initialized to their stowed position
	}

	/**
	 * Handles things that should be done every iteration, such as updating the dashboard or checking the status of things.
	 */
	@Override
	public void periodic() {
		updateDashboard();
	}

	public void updateDashboard() {

	}

	/**
	 * Moves ramps to their home positions (starting configuration)
	 * @param ramp - This is an enum of ALL, HOTPLATE, GREASETRAP
	 */
	public void stowRamp(ramps ramp) {
		switch (ramp) {
			case HOTPLATE:
				m_HotPlate.set(ControlMode.Position, Constants.Stove.HotPlate.homePosition);
				break;
			case GREASETRAP:
				m_GreaseTrap.moveTo(GreaseTrapPositions.HOME);
				break;
			case BOTH:
			default:
				m_HotPlate.set(ControlMode.Position, Constants.Stove.HotPlate.homePosition);
				m_GreaseTrap.moveTo(GreaseTrapPositions.HOME);
		}
	}
}