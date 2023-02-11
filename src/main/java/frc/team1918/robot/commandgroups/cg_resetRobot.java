/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot.commandgroups;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.commands.vision.vision_setRinglight;

public class cg_resetRobot extends SequentialCommandGroup {
  private final FiveSecondRuleSubsystem m_fivesecondrule;
  private final StoveSubsystem m_stove;
  private final VisionSubsystem m_vision;
  
  /**
   * This command groups issues all the different robot reset items that have to get reset on disable
   * <ol>
   * <li>set whirlygig to down</li>
   * <li>retract intake</li>
   * </ol>
   * <br>
   * @param stove Stove Subsystem
   * @param fsr FiveSecondRule Subsystem
   * @param vision Vision Subsystem
  */
  public cg_resetRobot(StoveSubsystem stove, FiveSecondRuleSubsystem fsr, VisionSubsystem vision) {
    m_stove = stove;
    m_fivesecondrule = fsr;
    m_vision = vision;
    addRequirements(m_stove, m_fivesecondrule);

    /**
     * Creates a sequential command group with the objects to run in sequence.
     * Can also include complex things like other command groups or parallel command groups
     */
    addCommands(
        //this is a comma separated list of commands, thus, the last one should not have a comma
        new helpers_debugMessage("Starting robot reset sequence"),
        new vision_setRinglight(m_vision, false)
        //new fivesecondrule_stowSpatulas(m_fivesecondrule),
        //new stove_stowRamps(m_stove),
        //new stove_stop(m_stove)
    );
  }
}