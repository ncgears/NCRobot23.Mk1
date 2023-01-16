
package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class ClimberSubsystem extends SubsystemBase {
  private WPI_TalonFX climber_1;
  private WPI_TalonFX climber_2;
  private Solenoid hook_release_1;
  private Solenoid hook_release_2;
  private Solenoid whirlySolenoid; 
  private DigitalInput m_hook1CaptureSwitchLeft, m_hook1CaptureSwitchRight, m_hook2CaptureSwitch;
  public enum whirlyState {DOWN, UP;}
  public whirlyState currentWhirlyState = whirlyState.DOWN;
  public enum latchState {NONE, BAR2LATCH, BAR2RELEASE, BAR3LATCH, BAR3RELEASE, BAR4LATCH, COMPLETE, ABORTED;}
  public latchState currentLatchState = latchState.NONE;
  public enum whirlyDirection { STOPPED, FORWARD, REVERSE; }
  public whirlyDirection currentWhirlyDirection = whirlyDirection.STOPPED;
  
  public void setLatchState(latchState current) {
    currentLatchState = current;
  }

  public void resetLatchState() {
    currentLatchState = latchState.NONE;
  }

  public latchState getLatchState() {
    return currentLatchState;
  }

  public boolean getHookLatch(int hook) {
    if (hook==1) return !m_hook1CaptureSwitchLeft.get();
    if (hook==2) return !m_hook2CaptureSwitch.get();
    if (hook==3) return !m_hook1CaptureSwitchRight.get();
    return false;
  }

  public ClimberSubsystem() {
    m_hook1CaptureSwitchLeft = new DigitalInput(Constants.Climber.id_CaptureHook1Left);
    m_hook1CaptureSwitchRight = new DigitalInput(Constants.Climber.id_CaptureHook1Right);
    m_hook2CaptureSwitch = new DigitalInput(Constants.Climber.id_CaptureHook2);
    climber_1 = new WPI_TalonFX(Constants.Climber.id_Motor1, Constants.Climber.canBus);
    climber_2 = new WPI_TalonFX(Constants.Climber.id_Motor2, Constants.Climber.canBus);
    hook_release_1 = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_ClimbHook1Solenoid);
    hook_release_2 = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_ClimbHook2Solenoid);
    whirlySolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_WhirlyGigSolenoid);

    climber_1.configFactoryDefault(); 
    climber_1.set(ControlMode.PercentOutput, 0);
    climber_1.setNeutralMode(NeutralMode.Coast); 
    climber_1.config_kP(0,Constants.Climber.kP); //P value for PID_PRIMARY
    climber_1.config_kI(0,Constants.Climber.kI); //I value for PID_PRIMARY
    climber_1.config_kD(0,Constants.Climber.kD); //D value for PID_PRIMARY
    climber_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,30);
    climber_1.configFeedbackNotContinuous(Constants.Climber.isSensorNotContinuous, 0);
    climber_1.setSensorPhase(Constants.Climber.isSensorInverted_Motor1); 
    climber_1.setInverted(Constants.Climber.isInverted_Motor1);
    climber_1.setSelectedSensorPosition(0);

    climber_2.configFactoryDefault(); 
    climber_2.set(ControlMode.PercentOutput, 0);
    climber_2.setNeutralMode(NeutralMode.Coast); 
    climber_2.config_kP(0,Constants.Climber.kP); //P value for PID_PRIMARY
    climber_2.config_kI(0,Constants.Climber.kI); //I value for PID_PRIMARY
    climber_2.config_kD(0,Constants.Climber.kD); //D value for PID_PRIMARY
    climber_2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,30);
    climber_2.configFeedbackNotContinuous(Constants.Climber.isSensorNotContinuous, 0);
    climber_2.setSensorPhase(Constants.Climber.isSensorInverted_Motor1); 
    climber_2.setInverted(Constants.Climber.isInverted_Motor2);
    climber_2.setSelectedSensorPosition(0);
    // climber_2.setNeutralMode(NeutralMode.Coast);
    // climber_2.follow(climber_1);
    // climber_2.setInverted((Constants.Climber.isInvertedFromMaster_Motor2) ? InvertType.OpposeMaster : InvertType.FollowMaster);
  }
  /**
   * This function raises the Whirlygig. This cannot be undone except for releasing air pressure to reset.
   */
  public void raiseWhirlygig(boolean up) {
    currentWhirlyState = (up) ? whirlyState.UP : whirlyState.DOWN;
    whirlySolenoid.set((up) ? Constants.Air.stateWhirlygigUp : !Constants.Air.stateWhirlygigUp);
  }

  /**
   * This sets the hook mode to locked (false) or unlocked (true)
   * @param hook_id - (int) This is the hook id to lock/unlock (1 or 2)
   * @param unlock - (boolean) This indicates if the hook should be unlocked (true)
   */
  public void setHookMode(int hook_id, boolean unlock) {
    switch(hook_id) {
      case 1:
        hook_release_1.set(unlock);
        break;
      case 2:
        hook_release_2.set(unlock);
        break;
    }
  }

  public void setBrakeMode(boolean brake) {
    climber_1.setNeutralMode((brake)?NeutralMode.Brake:NeutralMode.Coast);
    climber_2.setNeutralMode((brake)?NeutralMode.Brake:NeutralMode.Coast);
  }

  /**
   * This moves the climber to a specific position using the encoder
   * @param target - Encoder position to move to
   */
  public void moveClimberToTarget(int target) {
    climber_1.set(ControlMode.Position,target);
  }

  /**
   * This checks the left capture hook1 switch
   * @return limit switch state
   */
  public boolean isCapturedHook1Left(){
    return !m_hook1CaptureSwitchLeft.get();
  }
  
  /**
   * This checks the right capture hook1 switch
   * @return limit switch state
   */
  public boolean isCapturedHook1Right(){
    return !m_hook1CaptureSwitchRight.get();
  }

  public boolean isCapturedHook2(){
    return !m_hook2CaptureSwitch.get();
  }

  /**
   * This runs the climber in the forward direction
   */
  public void climberForward() {
    climber_1.set(ControlMode.PercentOutput, Constants.Climber.kClimberSpeed);
    climber_2.set(ControlMode.PercentOutput, Constants.Climber.kClimberSpeed);
    currentWhirlyDirection = whirlyDirection.FORWARD;
  }

  /**
   * This runs the climber in the reverse direction
   */
  public void climberReverse() {
    climber_1.set(ControlMode.PercentOutput, Constants.Climber.kClimberSpeed * -1);
    climber_2.set(ControlMode.PercentOutput, Constants.Climber.kClimberSpeed * -1);
    currentWhirlyDirection = whirlyDirection.REVERSE;
  }

  /**
   * This stops the climber
   */
  public void climberStop() {
    climber_1.set(ControlMode.PercentOutput, 0);
    climber_2.set(ControlMode.PercentOutput, 0);
    currentWhirlyDirection = whirlyDirection.STOPPED;
  }

  /**
   * This gets the climbers current encoder position
   */
  public double getClimberPosition() {
    return climber_1.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run, usually used for updating dashboard data
    climber_2.follow(climber_1); //Make sure climber_2 is always following climber_1
    updateDashboard();
  }

  public void updateDashboard() {
    Dashboard.Climber.setClimberPosition(getClimberPosition());
    Dashboard.Climber.setClimberDirection(currentWhirlyDirection.toString());
    Dashboard.Climber.setWhirlyPosition(currentWhirlyState.toString());
    Dashboard.Climber.setHook1Left(isCapturedHook1Left());
    Dashboard.Climber.setHook1Right(isCapturedHook1Right());
    Dashboard.Climber.setHook2(isCapturedHook2());
  }

}
