package frc.team1918.robot.commands.stove;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.modules.GreaseTrap.GreaseTrapPositions;
public class stove_moveGreaseTrapDown extends CommandBase {
  // @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final StoveSubsystem m_stove;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public stove_moveGreaseTrapDown(StoveSubsystem subsystem) {
    m_stove = subsystem;
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
    m_stove.moveGreaseTrapTo(GreaseTrapPositions.DOWN);
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