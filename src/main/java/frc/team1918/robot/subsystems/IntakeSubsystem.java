package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.utils.Spatula;

public class IntakeSubsystem extends SubsystemBase {

	private static IntakeSubsystem instance;
	//initialize spatulas
	private static Spatula m_SpatulaLeft = new Spatula("SpatulaLeft", Constants.Spatula.Left.constants); // Left
	private static Spatula m_SpatulaRight = new Spatula("SpatulaRight", Constants.Spatula.Right.constants); // Right
	private Spatula[] modules = {m_SpatulaLeft, m_SpatulaRight};


	public static IntakeSubsystem getInstance() {
		if (instance == null)
			instance = new IntakeSubsystem();
		return instance;
	}

	public IntakeSubsystem() { //initialize the class
		stowAllSpatula();
	}

	@Override
	public void periodic() {
		updateDashboard();
	}

	public void updateDashboard() {
		for (Spatula module: modules) {
			module.updateDashboard();
		}
	}

	/**
	 * Moves all spatulas to their home positions (starting configuration)
	 */
	public void stowAllSpatula() {
		for (Spatula module: modules) {
			module.stowSpatula();
		}
	}
}