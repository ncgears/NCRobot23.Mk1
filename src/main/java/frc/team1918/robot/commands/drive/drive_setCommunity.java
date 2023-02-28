
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
public class drive_setCommunity extends CommandBase {
  private final DriveSubsystem m_drive;
  private final StoveSubsystem m_stove;
  private final FiveSecondRuleSubsystem m_fsr;
  private final boolean m_community;

  /**
   * @param subsystem The drive subsystem this command will run on.
   * @param stove The stove subsystem
   * @param fsr The fivesecondrule subsystem
   * @param community (boolean) optional parameter to force community true or false
   */
  public drive_setCommunity(DriveSubsystem subsystem, StoveSubsystem stove, FiveSecondRuleSubsystem fsr, boolean community) {
    m_drive = subsystem;
    m_stove = stove;
    m_fsr = fsr;
    m_community = community;
    addRequirements(m_stove, m_fsr);
  }

  @Override
  public void execute() {
    if(m_community) {
      Helpers.Debug.debug("Drive: Entering community");
    } else {
      Helpers.Debug.debug("Drive: Leaving community");
      if(m_stove.getHotPlatePosition()!=HotPlatePositions.HOME) m_stove.moveHotPlateTo(HotPlatePositions.HOME);
      if(m_stove.getGreaseTrapPosition()!=GreaseTrapPositions.HOME) m_stove.moveGreaseTrapTo(GreaseTrapPositions.HOME);
      if(m_stove.getBurnerPosition()!=BurnerPositions.HOME) m_stove.moveBurnerTo(BurnerPositions.HOME);
      if(m_fsr.getSpatulaPosition(spatulas.LEFT)!=SpatulaPositions.HOME) m_fsr.moveSpatulaTo(spatulas.LEFT, SpatulaPositions.HOME);
      if(m_fsr.getSpatulaPosition(spatulas.RIGHT)!=SpatulaPositions.HOME) m_fsr.moveSpatulaTo(spatulas.RIGHT, SpatulaPositions.HOME);
    }
    m_drive.inCommunity = m_community;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}