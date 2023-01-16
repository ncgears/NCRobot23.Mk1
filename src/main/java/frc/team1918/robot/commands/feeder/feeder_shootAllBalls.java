/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commands.feeder;

import frc.team1918.robot.Constants;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.subsystems.FeederSubsystem;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that ...
 */
public class feeder_shootAllBalls extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final FeederSubsystem m_feeder;
  private final Debouncer m_debouncer = new Debouncer(Constants.Feeder.debounce_delay, Debouncer.DebounceType.kRising);
  /**
   * @param subsystem The subsystem used by this command.
   */
  public feeder_shootAllBalls(FeederSubsystem subsystem) {
    m_feeder = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Helpers.Debug.debug("Feeder: Move all balls into shooter");
    if(m_feeder.hasBall()) {
      m_feeder.runFeeder(true);
    } else {
      Helpers.Debug.debug("Feeder: No balls. None. At all. ");
      this.end(false);
    }
    // if(m_feeder.hasFirstBall() || m_feeder.hasSecondBall()) {
    //   m_feeder.runFeeder(true);
    // } else {
    //   Helpers.Debug.debug("Feeder: No balls to move at position 1 or 2");
    //   this.end(true);
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(!interrupted) Helpers.Debug.debug("Feeder: Auto-stopped, feeder empty");
    m_feeder.stopFeeder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return true; //until limit switches are working
    return m_debouncer.calculate(!m_feeder.hasBall());
  }
}
