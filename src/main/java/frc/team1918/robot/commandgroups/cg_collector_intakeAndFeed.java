/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.robot.commands.collector.*;
import frc.team1918.robot.commands.feeder.*;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;

public class cg_collector_intakeAndFeed extends SequentialCommandGroup {
  private final CollectorSubsystem m_collector;
  private final FeederSubsystem m_feeder;

  public cg_collector_intakeAndFeed(CollectorSubsystem collector, FeederSubsystem feeder) {
    m_collector = collector;
    m_feeder = feeder;
    addRequirements(m_collector, m_feeder);

    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        // new helpers_debugMessage("Auton: followTrajectory - OnePointEightMetersForward"), //move to ball1
        new collector_deployRetractor(m_collector, false),
        new collector_deployIntake(m_collector), //deploy collector
        new WaitCommand(0.2), //wait for collector
        new collector_intakeForward(m_collector), //start collector
        new feeder_advanceToShooter(m_feeder)
        // new ParallelCommandGroup( //do until trajectory complete
          // new feeder_advanceToShooter(m_feeder)
        // )
        // new collector_intakeStop(m_collector), //stop collector
        // new collector_retractIntake(m_collector),  //retract collector
        // new WaitCommand(1.0), //wait for shooter to be at speed
        // new feeder_shootAllBalls(m_feeder), //advance all balls to shooter - this has built in delay
        // new feeder_advance(m_feeder), //start advancing the feeder
        // new feeder_stop(m_feeder), //stop the feeder -- should be handled by shootAllBalls
        // new helpers_debugMessage("Auton: Done with auton")
    );
  }
}