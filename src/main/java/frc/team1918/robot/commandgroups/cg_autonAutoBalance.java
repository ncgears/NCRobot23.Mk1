/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.commands.drive.drive_autoBalance;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;

public class cg_autonAutoBalance extends SequentialCommandGroup {
  private final DriveSubsystem m_drive;

  
  /**
   * This command groups balances the robot during auton
  */
  public cg_autonAutoBalance(DriveSubsystem drive) {
    m_drive = drive;
    addRequirements(m_drive);

    /**
     * Creates a sequential command group with the objects to run in sequence.
     * Can also include complex things like other command groups or parallel command groups
     */
    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new helpers_debugMessage("Start robot auto balance sequence"),
        new helpers_debugMessage("simulate drive to charge station"),
        new WaitCommand(0.5),
        new RepeatCommand(
          new SequentialCommandGroup(
            new helpers_debugMessage("autoBalance for .5s"),
            new ParallelDeadlineGroup(
              new WaitCommand(0.5),
              new drive_autoBalance(m_drive)
            ),
            new WaitCommand(0.5)
          )
        ),
        new helpers_debugMessage("Finish robot auto balance sequence")
    );
  }
}