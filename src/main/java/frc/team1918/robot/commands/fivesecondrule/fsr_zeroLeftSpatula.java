package frc.team1918.robot.commands.fivesecondrule;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.Constants;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem.spatulas;
public class fsr_zeroLeftSpatula extends CommandBase {
  // @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final FiveSecondRuleSubsystem m_fsr;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public fsr_zeroLeftSpatula(FiveSecondRuleSubsystem subsystem) {
    m_fsr = subsystem;
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
    m_fsr.setSpatulaZeroSpeed(Constants.Spatula.Left.kZeroSpeed, spatulas.LEFT);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_fsr.setSpatulaZeroSpeed(0.0, spatulas.LEFT);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}