package frc.team1918.robot.commands.orchestra;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.subsystems.OrchestraSubsystem;

public class orchestra_decreaseSong extends CommandBase {
    private final OrchestraSubsystem m_orchestra;

    public orchestra_decreaseSong(OrchestraSubsystem subsystem){
      m_orchestra = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
    
      // Allow the command to run while disabled
      @Override
      public boolean runsWhenDisabled() {
        return true;
      }
      
    @Override
    public void initialize() {
      m_orchestra.decreaseSong();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }    
}

