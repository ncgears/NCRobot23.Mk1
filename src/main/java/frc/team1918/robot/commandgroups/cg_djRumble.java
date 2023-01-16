/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.commands.vision.vision_setRumble;


public class cg_djRumble extends SequentialCommandGroup {
  private final VisionSubsystem m_vision;
  
  /**
  */
  public cg_djRumble(VisionSubsystem vision) {
    m_vision = vision;

    /**
     * Creates a sequential command group with the objects to run in sequence.
     * Can also include complex things like other command groups or parallel command groups
     */
    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new vision_setRumble(true),
        new WaitCommand(0.25),
        new vision_setRumble(false),
        new WaitCommand(0.25),
        new vision_setRumble(true),
        new WaitCommand(0.25),
        new vision_setRumble(false)
    );
  }
}