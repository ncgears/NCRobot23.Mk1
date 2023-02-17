package frc.team1918.robot.commands.stove;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.modules.Griddle.GriddleDirections;
public class stove_setGriddleDirectionTo extends CommandBase {
  // @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final StoveSubsystem m_stove;
  private final GriddleDirections m_direction;

  /**
   * @param subsystem The subsystem used by this command.
   * @param direction The GriddleDirection to set the griddle to
   */
  public stove_setGriddleDirectionTo(StoveSubsystem subsystem, GriddleDirections direction) {
    m_stove = subsystem;
    m_direction = direction;
    // Use addRequirements() here to declare subsystem dependencies.
    //   addRequirements(subsystem);
  }

  // Allow the command to run while disabled
  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Helpers.Debug.debug("Vision: Ringlight " + status);
    m_stove.setGriddleDirectionAndSpeed(m_direction, 0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}