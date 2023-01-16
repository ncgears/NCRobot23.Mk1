/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.paths.*;
import frc.team1918.robot.commands.auton.*;
import frc.team1918.robot.commands.collector.*;
import frc.team1918.robot.commands.drive.drive_followTrajectory;
import frc.team1918.robot.commands.shooter.*;
import frc.team1918.robot.commands.feeder.*;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem.namedShots;

public class cg_auton_BasicShootingAuto extends SequentialCommandGroup {
  private final CollectorSubsystem m_collector;
  private final DriveSubsystem m_drive;
  private final FeederSubsystem m_feeder;
  private final ShooterSubsystem m_shooter;

  public cg_auton_BasicShootingAuto(DriveSubsystem drive, CollectorSubsystem collector, FeederSubsystem feeder, ShooterSubsystem shooter) {
    m_collector = collector;
    m_drive = drive;
    m_feeder = feeder;
    m_shooter = shooter;
    addRequirements(m_collector, m_drive, m_feeder, m_shooter);

    addCommands(
        new helpers_debugMessage("Auton: Executing Auton BasicShootingAuto"),
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new collector_deployRetractor(m_collector, false),
        new helpers_debugMessage("Auton: Start shooter, tarmac"),
        new ParallelDeadlineGroup( //do until trajectory complete
          new SequentialCommandGroup(
            new WaitCommand(0.5), //wait for shooter to be at speed
            new helpers_debugMessage("Auton: Advance feeder for 1.5 seconds"),
            new feeder_advance(m_feeder),
            new collector_deployIntake(m_collector), //deploy collector
            new WaitCommand(1.5)
          ),
          new shooter_shootNamed(m_shooter, namedShots.DEFAULT) //start the shooter with no hood
          ),
        new helpers_debugMessage("Auton: Stop shooter and feeder"),
        new shooter_stopShooter(m_shooter), //stop shooter
        new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        new collector_intakeForward(m_collector), //start collector
        new ParallelDeadlineGroup( //do until trajectory complete
          // new WaitCommand(3.0), //placeholder for trajectory follower
          new drive_followTrajectory(m_drive, new TwoMetersForward()),
          new helpers_debugMessage("Auton: followTrajectory - TwoMetersForward") //move to ball1
        ),
        new helpers_debugMessage("Auton: Stop and Retract Intake"),
        new collector_retractIntake(m_collector),  //retract collector
        new collector_intakeStop(m_collector), //stop collector
        // new ParallelDeadlineGroup( //do until trajectory complete
        //   // new WaitCommand(3.0), //placeholder for trajectory follower
        //   new drive_followTrajectory(m_drive, new TwoMetersBackward()),
        //   new helpers_debugMessage("Auton: followTrajectory - TwoMetersBackward") //move to ball1
        // ),
        // new ParallelDeadlineGroup( //do until trajectory complete
        //   new SequentialCommandGroup(
        //     new WaitCommand(0.5), //wait for shooter to be at speed
        //     new helpers_debugMessage("Auton: Advance feeder for 1.5 seconds"),
        //     new feeder_advance(m_feeder),
        //     new WaitCommand(1.5)
        //   ),
        //   new shooter_shootNamed(m_shooter, namedShots.DEFAULT) //start the shooter with no hood
        //   ),
        new helpers_debugMessage("Auton: Stop shooter and feeder"),
        new shooter_stopShooter(m_shooter), //stop shooter
        new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        new helpers_debugMessage("Auton: Finished executing Auton")
    );
  }
}