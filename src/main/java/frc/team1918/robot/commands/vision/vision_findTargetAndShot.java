
package frc.team1918.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
//import constants and subsystem
import frc.team1918.robot.Constants;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.Constants.Shooter;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.OrchestraSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem.namedShots;


/**
 * A command that runs the drive actions. This passes the OI inputs on to the appropriate drive system (fieldCentricDrive or humanDrive).
 * fieldCentricDrive is simply a call to humanDrive after gyro corrections are made.
 */
public class vision_findTargetAndShot extends CommandBase {
  private final DriveSubsystem m_drive;
  private final VisionSubsystem m_vision;
  private final ShooterSubsystem m_shooter;
  private static Double m_forward;
  private static Double m_strafe;
  private static Double m_rotation;
  private static Double m_pitch;



  /**
   * Creates a new drive_defaultDrive.
   *
   * @param drive The drive subsystem required for this command
   * @param vision The vision subsystem
   */
  public vision_findTargetAndShot(DriveSubsystem drive, VisionSubsystem vision, ShooterSubsystem shooter) {
    m_drive = drive;
    m_vision = vision;
    m_shooter = shooter;
    m_forward = 0.0;
    m_strafe = 0.0;
    m_rotation = 0.0;
    m_pitch = 0.0;
    addRequirements(m_drive, m_vision);
  }

  public void initialize() {
    Helpers.Debug.debug("Vision: Start Target and Shot Assist");
    m_drive.setVisionTargeting(true);
    m_vision.setRinglight(Constants.Vision.stateLightOn);
  }

  @Override
  public void execute() {
    m_pitch = m_vision.getVisionPitch();
    m_rotation = m_vision.getVisionTurn();
    SmartDashboard.putNumber("Vision/TurnValue", m_rotation);
    if(m_rotation != 0) m_drive.unlockAngle(); //unlock angle if rotating
    m_drive.drive(m_forward, m_strafe, m_rotation, Constants.DriveTrain.useFieldCentric);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Helpers.Debug.debug("Vision: Stop Target and Shot Assist");
    Helpers.Debug.debug("current pitch"+m_pitch);
    namedShots m_name = m_vision.selectShot(m_pitch);
    Helpers.Debug.debug("Vision: Selected shot is "+m_name);
    m_shooter.setShotName(m_name);
    switch (m_name) {
      case LOW:
        m_shooter.setShooterSpeed(Constants.Shooter.Shots.LOW.kSpeed);
        m_shooter.raiseHood(Constants.Shooter.Shots.LOW.kHood, Constants.Shooter.Shots.LOW.kHood2);
        break;
      case PROTECTED:
        m_shooter.setShooterSpeed(Constants.Shooter.Shots.PROTECTED.kSpeed);
        m_shooter.raiseHood(Constants.Shooter.Shots.PROTECTED.kHood, Constants.Shooter.Shots.PROTECTED.kHood2);
        break;
      case LINE:
        m_shooter.setShooterSpeed(Constants.Shooter.Shots.LINE.kSpeed);
        m_shooter.raiseHood(Constants.Shooter.Shots.LINE.kHood, Constants.Shooter.Shots.LINE.kHood2);
        break;
      case WALL:
        m_shooter.setShooterSpeed(Constants.Shooter.Shots.WALL.kSpeed);
        m_shooter.raiseHood(Constants.Shooter.Shots.WALL.kHood, Constants.Shooter.Shots.WALL.kHood2);
        break;
      case TARMAC:
        m_shooter.setShooterSpeed(Constants.Shooter.Shots.TARMAC.kSpeed);
        m_shooter.raiseHood(Constants.Shooter.Shots.TARMAC.kHood, Constants.Shooter.Shots.TARMAC.kHood2);
        break;
      case NONE:
      default:
        m_shooter.stopShooter();
        m_shooter.raiseHood(false, false);
        break;  
    }
    m_drive.brake();
    m_drive.setVisionTargeting(false);
    m_vision.setRinglight(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
