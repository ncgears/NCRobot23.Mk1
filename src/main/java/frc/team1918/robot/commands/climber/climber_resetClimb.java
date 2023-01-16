/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commands.climber;

import frc.team1918.robot.Helpers;
import frc.team1918.robot.subsystems.ClimberSubsystem;
import frc.team1918.robot.subsystems.ClimberSubsystem.latchState;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that ...
 */
public class climber_resetClimb extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final ClimberSubsystem m_climber;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public climber_resetClimb(ClimberSubsystem climb) {
    m_climber = climb;
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(subsystem);
  }

  // Allow the command to run while disabled
  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Helpers.Debug.debug("Climber: Reset Hook Locks to Enabled");
    m_climber.setHookMode(1,false);
    m_climber.setHookMode(2,false);
    Helpers.Debug.debug("Climber: Reset Latch State to NONE");
    m_climber.setLatchState(latchState.NONE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
