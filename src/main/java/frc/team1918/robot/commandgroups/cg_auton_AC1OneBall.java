/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.paths.*;
import frc.team1918.robot.Constants;
import frc.team1918.robot.commands.auton.*;
import frc.team1918.robot.commands.collector.*;
import frc.team1918.robot.commands.drive.drive_followTrajectory;
import frc.team1918.robot.commands.drive.drive_resetOdometry;
import frc.team1918.robot.commands.shooter.*;
import frc.team1918.robot.commands.feeder.*;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem.namedShots;

public class cg_auton_AC1OneBall extends SequentialCommandGroup {
  private final CollectorSubsystem m_collector;
  private final DriveSubsystem m_drive;
  private final FeederSubsystem m_feeder;
  private final ShooterSubsystem m_shooter;
  private final VisionSubsystem m_vision;

  public cg_auton_AC1OneBall(DriveSubsystem drive, CollectorSubsystem collector, FeederSubsystem feeder, ShooterSubsystem shooter, VisionSubsystem vision) {
    m_collector = collector;
    m_drive = drive;
    m_feeder = feeder;
    m_shooter = shooter;
    m_vision = vision;
    addRequirements(m_collector, m_drive, m_feeder, m_shooter);

    addCommands(
        new helpers_debugMessage("Auton: Executing Auton AC1 One Ball"),
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new drive_resetOdometry(drive, new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(-0.0))),
        new collector_deployRetractor(m_collector, false),
        new shooter_shootNamed(m_shooter, namedShots.AL1ONE), //shoot from the ball 2 position
        new WaitCommand(Constants.Shooter.kSpinupSeconds), 
        new feeder_advance(m_feeder), //start advancing the feeder
        new WaitCommand(1.0), //give time for shot
        new shooter_stopShooter(m_shooter), //stop shooter
        new shooter_hoodDown(m_shooter), //lower the hood
        new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        new ParallelDeadlineGroup( //do until trajectory complete
          new drive_followTrajectory(m_drive, new TwoMetersBackward()),
          new helpers_debugMessage("Auton: followTrajectory - TwoMetersBackward") //move to ball1
        ),
        new helpers_debugMessage("Auton: Finished executing Auton")
    );
  }
}