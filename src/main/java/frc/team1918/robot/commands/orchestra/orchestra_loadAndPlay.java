package frc.team1918.robot.commands.orchestra;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.OrchestraSubsystem;

public class orchestra_loadAndPlay extends CommandBase {
  private final OrchestraSubsystem m_orchestra;
  private int m_song;
  private final DriveSubsystem m_drive = new DriveSubsystem();
  
  public orchestra_loadAndPlay(OrchestraSubsystem subsystem){
    m_orchestra = subsystem;
    // m_song = selection;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    // Allow the command to run while disabled
   @Override
   public boolean runsWhenDisabled() {
    return true;
   }
        
  // Called when the command is initially scheduled.
  @Override
  public void initialize(){
    m_song = m_orchestra.getSong();
    m_orchestra.createOrchestra();
    m_orchestra.loadSong(m_song); 
    m_orchestra.playMusic();
  }
 
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }    
}
