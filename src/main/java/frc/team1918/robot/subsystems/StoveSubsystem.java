package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.utils.Spatula;

/**
 * The STOVE Subsystem manages the GRIDDLE (primary conveyor), HOT PLATE (scoring ramp), and GREASE TRAP (flip ramp). 
 * It is responsible for delivering PANCAKES (cones) and WAFFLES (cubes) off the end if the scoring ramp or back to the flip ramp.
 * It manages the angle of the 
*/
public class StoveSubsystem extends SubsystemBase {
	private static FiveSecondRuleSubsystem instance;
	public enum spatulas {BOTH, LEFT, RIGHT};

	//initialize spatulas
	private static Spatula m_SpatulaLeft = new Spatula("SpatulaLeft", Constants.Spatula.Left.constants); // Left
	private static Spatula m_SpatulaRight = new Spatula("SpatulaRight", Constants.Spatula.Right.constants); // Right
	private Spatula[] modules = {m_SpatulaLeft, m_SpatulaRight};

	/**
	 * Returns the instance of the Intake subsystem.
	 * The purpose of this is to only create an instance if one does not already exist.
	 * @return IntakeSubSystem instance
	 */
	public static FiveSecondRuleSubsystem getInstance() {
		if (instance == null)
			instance = new FiveSecondRuleSubsystem();
		return instance;
	}

	/**
	 * Initializes the IntakeSubsystem class, performs setup steps, etc.
	 */
	public StoveSubsystem() {
		stowSpatula(spatulas.BOTH); //Make sure all spatulas are initialized to their stowed position
	}

	/**
	 * Handles things that should be done every iteration, such as updating the dashboard or checking the status of things.
	 */
	@Override
	public void periodic() {
		updateDashboard();
	}

	public void updateDashboard() {
		for (Spatula module: modules) { //For each spatula, update the dashboard
			module.updateDashboard();
		}
	}

	/**
	 * Moves spatulas to their home positions (starting configuration)
	 * @param spatula - This is an enum of ALL, LEFT, RIGHT
	 */
	public void stowSpatula(spatulas spatula) {
		switch (spatula) {
			case LEFT:
				m_SpatulaLeft.stowSpatula();
				break;
			case RIGHT:
				m_SpatulaRight.stowSpatula();
				break;
			case BOTH:
			default:
				for (Spatula module: modules) {
					module.stowSpatula();
				}
		}
	}
}