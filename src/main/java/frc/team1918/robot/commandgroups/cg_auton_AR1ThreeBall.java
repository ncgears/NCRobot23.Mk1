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
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.paths.*;
import frc.team1918.robot.Constants;
import frc.team1918.robot.commands.auton.*;
import frc.team1918.robot.commands.collector.*;
import frc.team1918.robot.commands.drive.drive_followTrajectory;
import frc.team1918.robot.commands.drive.drive_resetOdometry;
import frc.team1918.robot.commands.shooter.*;
import frc.team1918.robot.commands.vision.vision_findTarget;
import frc.team1918.robot.commands.feeder.*;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem.namedShots;

public class cg_auton_AR1ThreeBall extends SequentialCommandGroup {
  private final CollectorSubsystem m_collector;
  private final DriveSubsystem m_drive;
  private final FeederSubsystem m_feeder;
  private final ShooterSubsystem m_shooter;
  private final VisionSubsystem m_vision;

  public cg_auton_AR1ThreeBall(DriveSubsystem drive, CollectorSubsystem collector, FeederSubsystem feeder, ShooterSubsystem shooter, VisionSubsystem vision) {
    m_collector = collector;
    m_drive = drive;
    m_feeder = feeder;
    m_shooter = shooter;
    m_vision = vision;
    addRequirements(m_collector, m_drive, m_feeder, m_shooter);

    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        //setup the odometry in a starting position from the center of the field (negative is right/back)
        //rotation is the initial rotation of the robot from the downstream direction
        new drive_resetOdometry(drive, new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(-95.0))),
        new collector_deployRetractor(m_collector, false),
        new shooter_shootNamed(m_shooter, namedShots.AR1ONE), //shoot from the ball 2 position
        new WaitCommand(Constants.Shooter.kSpinupSeconds), 
        new feeder_advance(m_feeder), //start advancing the feeder
        new WaitCommand(0.5), //give time for shot
        new collector_deployIntake(m_collector), //deploy collector
        new ParallelDeadlineGroup( //do until trajectory complete
          new drive_followTrajectory(m_drive, new ar1BallOne()),
          new helpers_debugMessage("Auton: followTrajectory - ar1BallOne"), //move to ball1
          new collector_intakeForward(m_collector), //start collector
          new shooter_shootNamed(m_shooter, namedShots.AR1TWO) //shoot from the home position
        ),
        new collector_intakeStop(m_collector),
        new collector_retractIntake(m_collector),
        new feeder_advance(m_feeder), //start advancing the feeder
        new WaitCommand(1.5), //give time for shot
        new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        new shooter_shootNamed(m_shooter, namedShots.AR1THREE),
        new ParallelDeadlineGroup( //do until trajectory complete
          new drive_followTrajectory(m_drive, new ar1BallTwo()), //Go to 2nd ball
          // new WaitCommand(0.5),
          new helpers_debugMessage("Auton: followTrajectory - ar1BallTwo"),
          new SequentialCommandGroup( //do until trajectory complete
            new collector_intakeStop(m_collector),
            new collector_retractIntake(m_collector),
            new ParallelRaceGroup( //Advance the feeder for 1.5s or to shooter, whichever comes first
              new feeder_advanceToShooter(m_feeder),
              new WaitCommand(1.5)
            ),
            new collector_deployIntake(m_collector),
            new collector_intakeForward(m_collector)
            )
        ),
        new SequentialCommandGroup( //do until trajectory complete
          // new collector_intakeStop(m_collector),
          // new collector_retractIntake(m_collector),
          new ParallelRaceGroup( //Advance the feeder for 1.5s or to shooter, whichever comes first
            new feeder_advanceToShooter(m_feeder),
            new WaitCommand(1.5)
          )
        ),
        new helpers_debugMessage("Auton: followTrajectory - ar1B2ShootingPosition"),
        new drive_followTrajectory(m_drive, new ar1B2ShootingPosition()),
        // new shooter_shootNamed(m_shooter, namedShots.AR1THREE),
        // new WaitCommand(Constants.Shooter.kSpinupSeconds), 
        new ParallelDeadlineGroup(
          new WaitCommand(1.0),
          new vision_findTarget(m_drive, m_vision)
        ),
        new feeder_advance(m_feeder), //start advancing the feeder
        new WaitCommand(1.25), //give time for shot
        new shooter_stopShooter(m_shooter), //stop shooter
        new shooter_hoodDown(m_shooter), //lower the hood
        new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        new collector_intakeForward(m_collector),
        new collector_intakeStop(m_collector), //stop collector
        new collector_retractIntake(m_collector),  //retract collector
        new helpers_debugMessage("Auton: Done with auton")
    );
  }
}