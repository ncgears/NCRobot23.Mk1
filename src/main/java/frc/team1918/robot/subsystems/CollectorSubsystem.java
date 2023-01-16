
package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CollectorSubsystem extends SubsystemBase {
  private WPI_TalonSRX coll; //collector controller
  private Solenoid m_coll1; //collector solenoid 1
  private Solenoid m_retractor; //retractor solenoid
  private boolean m_collector_deployed = false;
  public enum intakeDirection {STOP, FORWARD, REVERSE;}
  public intakeDirection currentIntakeDirection = intakeDirection.STOP;

  public CollectorSubsystem() {
    coll = new WPI_TalonSRX(Constants.Collector.id_Motor1);
    m_coll1 = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_CollectorSolenoid);
    m_retractor = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_RetratorSolenoid);

    coll.configFactoryDefault(); 
    coll.set(ControlMode.PercentOutput, 0);
    coll.setNeutralMode(NeutralMode.Coast); 
    coll.setInverted(Constants.Collector.isInverted_Motor1);
    // SupplyCurrentLimitConfiguration(enabled,peak,trigger threshold current,trigger threshold time(s))
    coll.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(
      Constants.Collector.isCurrentLimitEnabled,
      Constants.Collector.kCurrentLimitAmps,
      Constants.Collector.kCurrentThresholdAmps,
      Constants.Collector.kCurrentThresholdSecs));
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateDashboard();
  }

  public void updateDashboard() {
    Dashboard.Collector.setIntakeDirection(currentIntakeDirection.toString());
    // Dashboard.Feeder.setFeederSpeed();
    Dashboard.Collector.setIntakeDeployed(isCollectorDeployed());
  }

  public void setIntakeDirection(intakeDirection direction) {
    currentIntakeDirection = direction;
  }

  public void setIntakeSpeed(double speed) {
    coll.set(ControlMode.PercentOutput, speed);
  }

  public void startIntake(boolean reverse) {
    coll.set(ControlMode.PercentOutput, (reverse) ? -Constants.Collector.kDefaultCollectorSpeed : Constants.Collector.kDefaultCollectorSpeed);
  }

  public void stopIntake() {
    coll.set(ControlMode.PercentOutput, 0);
  }

  public boolean isCollectorDeployed() {
    return m_collector_deployed;
  }

  public void setRetractorPosition(boolean retracted) {
    m_retractor.set((retracted)?Constants.Air.stateRetractorRectracted:!Constants.Air.stateRetractorRectracted);
  }

  public void setCollectorPosition(String position) {
    switch(position) {
      case "deploy":
      case "down":
      case "out":
        //deploy
        m_coll1.set(!Constants.Air.stateCollectorDeployed);
        m_collector_deployed = true;
        break;
      case "retract":
      case "up":
      case "in":
      case "stow":
      default:
        //retract/stow
        m_coll1.set(Constants.Air.stateCollectorDeployed);
        m_collector_deployed = false;
        break;
    }
  }
}
