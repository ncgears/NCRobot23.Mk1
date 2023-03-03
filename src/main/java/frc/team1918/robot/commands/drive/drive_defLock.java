
package frc.team1918.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.modules.Burner.BurnerPositions;
import frc.team1918.robot.modules.GreaseTrap.GreaseTrapPositions;
import frc.team1918.robot.modules.HotPlate.HotPlatePositions;
import frc.team1918.robot.modules.Spatula.SpatulaPositions;
//import subsystem
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem.spatulas;

/**
 * A command to reset the gyro to 0.
 * This happens when we have the robot in a known orientation to allow us to track the orientation of the robot.
 * @implNote The reset happens during the end method of the command to ensure that it always executes even if the command completes before the gyro finishes executing the command.
 */
public class drive_defLock extends CommandBase {
  private final DriveSubsystem m_drive;

  /**
   * @param subsystem The drive subsystem this command will run on.
   */
  public drive_defLock(DriveSubsystem subsystem) {
    m_drive = subsystem;
    // addRequirements(m_stove, m_fsr);
  }

  @Override
  public void execute() {
    Helpers.Debug.debug("Drive: Defensive Lock");
    m_drive.brake(true);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}