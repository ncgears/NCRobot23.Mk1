package frc.team1918.robot.commands.stove;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.Constants;
import frc.team1918.robot.subsystems.StoveSubsystem;
public class stove_zeroGreaseTrap extends CommandBase {
  // @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final StoveSubsystem m_stove;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public stove_zeroGreaseTrap(StoveSubsystem subsystem) {
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
    m_stove.setGreaseTrapZeroSpeed(Constants.Stove.GreaseTrap.kZeroSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_stove.setGreaseTrapZeroSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}