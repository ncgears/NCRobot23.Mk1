package frc.team1918.robot.commands.orchestra;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.OrchestraSubsystem;

public class orchestra_primeToPlay extends CommandBase {
    private final OrchestraSubsystem m_orchestra;
    private final DriveSubsystem m_drive = new DriveSubsystem();

    public orchestra_primeToPlay(OrchestraSubsystem subsystem){
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
        m_drive.brake();
        m_orchestra.enableOrchestra();
      }
    
      // Returns true when the command should end.
      @Override
      public boolean isFinished() {
        return true;
      }    
  }