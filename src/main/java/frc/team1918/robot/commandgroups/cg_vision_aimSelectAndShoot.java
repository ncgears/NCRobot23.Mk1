/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.robot.Constants;
import frc.team1918.robot.Constants.Feeder;
import frc.team1918.robot.commands.feeder.feeder_advance;
import frc.team1918.robot.commands.feeder.feeder_advanceIfTarget;
import frc.team1918.robot.commands.feeder.feeder_stop;
import frc.team1918.robot.commands.shooter.shooter_stopShooter;
import frc.team1918.robot.commands.vision.vision_aimAndSelectShot;
import frc.team1918.robot.commands.vision.vision_findTarget;
import frc.team1918.robot.commands.vision.vision_selectShot;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;


public class cg_vision_aimSelectAndShoot extends SequentialCommandGroup {
  private final VisionSubsystem m_vision;
  private final ShooterSubsystem m_shooter;
  private final DriveSubsystem m_drive;
  private final FeederSubsystem m_feeder;
  
  /**
  */
  public cg_vision_aimSelectAndShoot(DriveSubsystem drive, FeederSubsystem feeder, ShooterSubsystem shooter, VisionSubsystem vision) {
    m_vision = vision;
    m_shooter = shooter;
    m_drive = drive;
    m_feeder = feeder;

    /**
     * Creates a sequential command group with the objects to run in sequence.
     * Can also include complex things like other command groups or parallel command groups
     */
    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new vision_findTarget(m_drive, m_vision),
        new vision_selectShot(m_vision, m_shooter),
        new WaitCommand(Constants.Shooter.kSpinupSeconds),
        new feeder_advanceIfTarget(m_feeder, m_vision),
        new WaitCommand(2.0),
        new feeder_stop(m_feeder),
        new shooter_stopShooter(m_shooter)
    );
  }
}