package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team1918.robot.Constants;
import frc.team1918.robot.modules.Burner;
import frc.team1918.robot.modules.Burner.BurnerPositions;
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
		m_GreaseTrap = new GreaseTrap("GreaseTrap", Constants.Stove.GreaseTrap.constants, Constants.Stove.GreaseTrap.gains);
		m_HotPlate = new HotPlate("HotPlate", Constants.Stove.HotPlate.constants, Constants.Stove.HotPlate.gains);
		m_Burner = new Burner("Burner", Constants.Stove.Burner.constants, Constants.Stove.Burner.gains);
		m_Griddle = new Griddle("Griddle", Constants.Stove.Griddle.constants, Constants.Stove.Griddle.gains);

		//make sure we reset things on init
		m_GreaseTrap.moveTo(GreaseTrapPositions.HOME);
		m_HotPlate.moveTo(HotPlatePositions.HOME);
		m_Burner.moveTo(BurnerPositions.HOME);
		m_Griddle.setDirectionAndSpeed(GriddleDirections.STOP, 0.0);
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
	 * Sets the Griddle direction (GriddleDirections) and speed (double)
	 * @param direction GriddleDirections enum representing the direction of griddle
	 * @param speed Double representing the percentage for the griddle
	 */
	public void setGriddleDirectionAndSpeed(GriddleDirections direction, double speed) {
		m_Griddle.setDirectionAndSpeed(direction, speed);
	}

	/**
	 * Moves the GreaseTrap to a designated position
	 * @param position - This is a GreaseTrapPositions enum of {@GreaseTrapPositions}
	 */
	public void moveGreaseTrapTo(GreaseTrapPositions position) {
		m_GreaseTrap.moveTo(position);
	}

	/**
	 * Moves the HotPlate to a designated position
	 * @param position - This is a HotPlatePositions enum of {@HotPlatePositions}
	 */
	public void moveHotPlateTo(HotPlatePositions position) {
		m_HotPlate.moveTo(position);
	}

	/**
	 * Moves the Burner to a designated position
	 * @param position - This is a BurnerPositions enum of {@BurnerPositions}
	 */
	public void moveBurnerTo(BurnerPositions position) {
		m_Burner.moveTo(position);
	}

}