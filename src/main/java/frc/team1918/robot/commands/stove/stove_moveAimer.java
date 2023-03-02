
package frc.team1918.robot.commands.stove;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
//import constants and subsystem
import frc.team1918.robot.Constants;
import frc.team1918.robot.subsystems.StoveSubsystem;


/**
 * A command that runs the drive actions. This passes the OI inputs on to the appropriate drive system (fieldCentricDrive or humanDrive).
 * fieldCentricDrive is simply a call to humanDrive after gyro corrections are made.
 */
public class stove_moveAimer extends CommandBase {
  private final StoveSubsystem m_stove;
  private final double m_aim;

  /**
   * Creates a new drive_defaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param forward The control input for driving forwards/backwards
   * @param strafe The control input for driving sideways
   * @param rotation The control input for turning
   */
  public stove_moveAimer(StoveSubsystem subsystem, double aim) {
    m_stove = subsystem;
    m_aim = aim;
    // addRequirements(m_stove);
  }

  @Override
  public void execute() {
    if (m_aim != 0) {
      m_stove.moveAimer(m_aim);
    } else {
      m_stove.moveAimer(0.0);
    }
  }
}
