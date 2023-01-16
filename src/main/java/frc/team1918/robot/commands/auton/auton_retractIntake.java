/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commands.auton;

import frc.team1918.robot.Helpers;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that ...
 */
public class auton_retractIntake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) //Dont add "unused" under normal operation
  private final CollectorSubsystem m_collector;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public auton_retractIntake(CollectorSubsystem subsystem) {
    m_collector = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Helpers.Debug.debug("Collector: Intake Stop");
    m_collector.setIntakeSpeed(0);
    Helpers.Debug.debug("Collector: Retract Intake");
    m_collector.setCollectorPosition("retract");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

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
