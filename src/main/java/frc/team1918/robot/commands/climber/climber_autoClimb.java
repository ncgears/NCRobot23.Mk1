/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commands.climber;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.subsystems.ClimberSubsystem;
import frc.team1918.robot.subsystems.ClimberSubsystem.latchState;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that ...
 */
public class climber_autoClimb extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final ClimberSubsystem m_climber;
  private final Debouncer m_releaseDebounce;
  private final Debouncer m_captureDebounce, m_captureDebounce2;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public climber_autoClimb(ClimberSubsystem climb) {
    m_climber = climb;
    m_releaseDebounce = new Debouncer(Constants.Climber.kHookReleaseTime, DebounceType.kRising);
    m_captureDebounce = new Debouncer(Constants.Climber.kHookCaptureTime, DebounceType.kFalling);
    m_captureDebounce2 = new Debouncer(Constants.Climber.kHookCaptureTime +0.5, DebounceType.kFalling);
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (m_climber.getLatchState()) {
      case NONE:
        Helpers.Debug.debug("Climber: Starting auto-climb");
        break;
      case BAR2LATCH:
        Helpers.Debug.debug("Climber: Auto-climb resuming from Bar2 Latch");
        break;
      case BAR2RELEASE:
        Helpers.Debug.debug("Climber: Auto-climb resuming from Bar2 Release");
        break;
      case BAR3LATCH:
        Helpers.Debug.debug("Climber: Auto-climb resuming from Bar3 Latch");
        break;
      case BAR3RELEASE:
        Helpers.Debug.debug("Climber: Auto-climb resuming from Bar3 Release");
        break;
      case BAR4LATCH:
        Helpers.Debug.debug("Climber: Auto-climb resuming from Bar4 Latch");
        break;
      case COMPLETE:
        Helpers.Debug.debug("Climber: Auto-climb already finished");
        break;
      case ABORTED:
        Helpers.Debug.debug("Climber: Auto-climb was previously aborted");
        break;
    }
    m_climber.climberForward();
 }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (m_climber.getLatchState()) { //Normal start of climb
      case NONE:
        if(m_climber.getHookLatch(2)) { //This shouldn't happen unless the robot is already latched or setup wrong
          Helpers.Debug.debug("Auto-climb: Unexpected trigger of Hook 2. Aborting.");
          m_climber.setLatchState(latchState.ABORTED);
          break;
        }
        if((m_climber.getHookLatch(1) && m_climber.getHookLatch(3)) || (m_climber.getHookLatch(1) && !Constants.Climber.requireCaptureBothSides)) { //both sides of climber hooked, or only left required
          Helpers.Debug.debug("Auto-climb: Bar2 Latched");
          m_climber.setLatchState(latchState.BAR2LATCH);
        } else if (m_climber.getHookLatch(1) && !m_climber.getHookLatch(3)) { //left side hooked only
          Helpers.Debug.debug("Auto-climb: Bar2 Latched LEFT SIDE ONLY");
        } else if (!m_climber.getHookLatch(1) && m_climber.getHookLatch(3)) { //right side hooked only
          Helpers.Debug.debug("Auto-climb: Bar2 Latched RIGHT SIDE ONLY");
        }
        break;
      case BAR2LATCH:
        m_climber.setBrakeMode(false); //set coast mode
        // RobotContainer.setAirDisabled(true); //disable the compressor now that we have latched bar2
        if(m_captureDebounce.calculate(m_climber.getHookLatch(2))) {
          Helpers.Debug.debug("Auto-climb: Bar3 Latched");
          Helpers.Debug.debug("Auto-climb: Bar2 Do Release");
          m_climber.setHookMode(1, true);
          m_climber.setLatchState(latchState.BAR2RELEASE);
        }
        break;
      case BAR2RELEASE: //We have to wait here to make sure we let go of Bar 2
        if(m_releaseDebounce.calculate(!m_climber.getHookLatch(1))) {
          Helpers.Debug.debug("Auto-climb: Bar2 Released");
          m_climber.setHookMode(1, false);
          m_climber.setLatchState(latchState.BAR3LATCH);
        }
        break;
      case BAR3LATCH:
        if(m_captureDebounce2.calculate(m_climber.getHookLatch(1))) {
          Helpers.Debug.debug("Auto-climb: Bar4 Latch");
          Helpers.Debug.debug("Auto-climb: Bar3 Do Release");
          m_climber.setHookMode(2, true);
          m_climber.setLatchState(latchState.BAR3RELEASE);
        }
        break;
      case BAR3RELEASE: //We have to wait here to make sure we let go of Bar 3
        if(m_releaseDebounce.calculate(!m_climber.getHookLatch(2))) {
          Helpers.Debug.debug("Auto-climb: Bar3 Released");
          m_climber.setHookMode(2, false);
          m_climber.setLatchState(latchState.BAR4LATCH);
        }
        break;
      case BAR4LATCH:
        Helpers.Debug.debug("Auto-climb: Complete");
        m_climber.setLatchState(latchState.COMPLETE);
        break;
      case ABORTED:
      case COMPLETE:
        break;
      default:
        Helpers.Debug.debug("Auto-climb: No match");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Helpers.Debug.debug("Climber: Stop auto-climb");
    m_climber.climberStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (false || m_climber.getLatchState() == latchState.COMPLETE || m_climber.getLatchState() == latchState.ABORTED);
  }
}
