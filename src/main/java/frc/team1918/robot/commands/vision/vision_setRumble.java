
package frc.team1918.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import constants and subsystem
import frc.team1918.robot.Helpers;

/**
 * A command that runs the drive actions. This passes the OI inputs on to the appropriate drive system (fieldCentricDrive or humanDrive).
 * fieldCentricDrive is simply a call to humanDrive after gyro corrections are made.
 */
public class vision_setRumble extends CommandBase {
  boolean rumble = false;

  /**
   * @param rumble true to rumble, false to stop
   */
  public vision_setRumble(boolean rumble) {
    this.rumble = rumble;
  }

  public void initialize() {
    // Helpers.OI.rumble(rumble);
    SmartDashboard.putBoolean("Vision/TooFar", rumble);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
