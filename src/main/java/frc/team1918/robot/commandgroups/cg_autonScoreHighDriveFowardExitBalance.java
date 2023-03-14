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
import frc.team1918.robot.commands.drive.drive_followTrajectory;
import frc.team1918.robot.commands.drive.drive_resetOdometry;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.commands.stove.stove_moveBurnerHome;
import frc.team1918.robot.commands.stove.stove_moveHotPlateHome;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.commandgroups.autoncommands.*;

@SuppressWarnings("unused")
public class cg_autonScoreHighDriveFowardExitBalance extends SequentialCommandGroup {
  private final DriveSubsystem m_drive;
  private final StoveSubsystem m_stove;
  private final FiveSecondRuleSubsystem m_fsr;
  private final VisionSubsystem m_vision;

  public cg_autonScoreHighDriveFowardExitBalance(DriveSubsystem drive, StoveSubsystem stove, FiveSecondRuleSubsystem fsr, VisionSubsystem vision) {
    m_drive = drive;
    m_stove = stove;
    m_fsr = fsr;
    m_vision = vision;
    addRequirements(m_drive, m_stove, m_fsr, m_vision);

    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        //setup the odometry in a starting position from the center of the field (negative is right/back)
        //rotation is the initial rotation of the robot from the downstream direction
        new helpers_debugMessage("Auton: Drive Forward And Balance"),
        new cg_SetOdom180(m_drive, m_vision),
        new cg_ScoreHigh(m_drive, m_stove, m_fsr, m_vision, false),
        new cg_DriveForward3p6m(m_drive, m_vision), 
        new cg_Wait(0.6),
        new cg_DriveForward2m(m_drive, m_vision), 
        new cg_Wait(0.1),
        new cg_DriveBackward3m(m_drive, m_vision), 
        new cg_AutoBalance(m_drive),
        new helpers_debugMessage("Auton: Done with auton")
    );
  }
}