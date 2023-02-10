package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.modules.Burner;
import frc.team1918.robot.modules.BurnerConstants;
import frc.team1918.robot.modules.Griddle;
import frc.team1918.robot.modules.Griddle.GriddleDirections;
import frc.team1918.robot.modules.GreaseTrap;
import frc.team1918.robot.modules.GreaseTrap.GreaseTrapPositions;
import frc.team1918.robot.modules.HotPlate;
import frc.team1918.robot.modules.HotPlate.HotPlatePositions;

/**
 * The STOVE Subsystem manages the GRIDDLE (primary conveyor), HOTPLATE (scoring ramp), and GREASETRAP (flip ramp). 
 * It is responsible for delivering PANCAKES (cones) and WAFFLES (cubes) off the end if the scoring ramp or back to the flip ramp.
 * It manages the angle of the 
*/
public class StoveSubsystem extends SubsystemBase {
	private static StoveSubsystem instance;
	public enum ramps {BOTH, HOTPLATE, GREASETRAP}
	
	//initialize modules used in subsystem
	private Griddle m_Griddle; //conveyor module
	private GreaseTrap m_GreaseTrap; //flip ramp module
	private HotPlate m_HotPlate; //scoring ramp controller
	private Burner m_Burner; //elevator controller
	
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
		m_GreaseTrap = new GreaseTrap("GreaseTrap", Constants.Stove.GreaseTrap.constants);
		m_HotPlate = new HotPlate("HotPlate", Constants.Stove.HotPlate.constants);
		m_Burner = new Burner("Burner", Constants.Stove.Burner.constants);
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

	public void setGriddleDirectionAndSpeed(GriddleDirections direction, double speed) {
		switch (direction) {
			case STOP:
				m_Griddle.setSpeed(0);
				break;
			case FORWARD:
				m_Griddle.setSpeed(speed * 1.0);
				break;
			case REVERSE:
				m_Griddle.setSpeed(speed * -1.0);
				break;
		}
	}

	/**
	 * Moves ramps to their home positions (starting configuration)
	 * @param ramp - This is an enum of ALL, HOTPLATE, GREASETRAP
	 */
	public void stowRamp(ramps ramp) {
		switch (ramp) {
			case HOTPLATE:
				m_HotPlate.moveTo(HotPlatePositions.HOME);
				break;
			case GREASETRAP:
				m_GreaseTrap.moveTo(GreaseTrapPositions.HOME);
				break;
			case BOTH:
			default:
				m_HotPlate.moveTo(HotPlatePositions.HOME);
				m_GreaseTrap.moveTo(GreaseTrapPositions.HOME);
		}
	}

	/**
	 * Moves the GreaseTrap to a designated position
	 * @param position - This is a GreaseTrapPositions enum of {@GreaseTrapPositions}
	 */
	public void moveGreaseTrapTo(GreaseTrapPositions position) {
		m_GreaseTrap.moveTo(position);
	}

}