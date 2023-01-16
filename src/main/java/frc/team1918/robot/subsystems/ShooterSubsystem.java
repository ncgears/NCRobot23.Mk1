
package frc.team1918.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team1918.robot.Constants;
import frc.team1918.robot.Dashboard;
import frc.team1918.robot.Helpers;
import frc.team1918.robot.utils.CANTalonUtil;
import frc.team1918.robot.utils.CANTalonUtil.Usage;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ShooterSubsystem extends SubsystemBase {
  private WPI_TalonFX shoot; // shooter controller
  private WPI_TalonFX shootFront; // front shooter controller
  private double m_shooter_rps = 0.0; // Current shooter speed
  private double m_shooter_oldrps = 0.0; // Previous shooter speed
  private double m_shooter_rpm = 0.0; 
  private Solenoid m_hood, m_hood2;
  public enum telemetryLevel {DEBUG, BASIC, NONE; }
  public telemetryLevel telemetry = telemetryLevel.DEBUG;
  public enum namedShots {DEFAULT, LOW, PROTECTED , LINE, OUTER, WALL, TARMAC, AL1ONE, AL1TWO, AL2ONE, AL2TWO, AR1ONE, AR1TWO, AR1THREE, AR4ONE, AR4TWO, DASHBOARD, NONE;}
  public namedShots currentShotName = namedShots.NONE;
  /**
   * Creates a new ExampleSubsystem.
   */
  public ShooterSubsystem() {
    //Store the defaults for shooter debugging in the dashboard
    SmartDashboard.putNumber("Debug/Shooter Speed",Constants.Shooter.Shots.DEFAULT.kSpeed);
    SmartDashboard.putBoolean("Debug/Shooter Hood1",Constants.Shooter.Shots.DEFAULT.kHood);
    SmartDashboard.putBoolean("Debug/Shooter Hood2",Constants.Shooter.Shots.DEFAULT.kHood2);
    shoot = new WPI_TalonFX(Constants.Shooter.id_Motor1, Constants.Shooter.canBus);
    shoot.configFactoryDefault();
    shoot.set(ControlMode.PercentOutput, 0);
    shoot.setNeutralMode(NeutralMode.Coast);
    //shoot.setSensorPhase(Constants.Shooter.isSensorInverted_Motor1);
    shoot.setInverted(Constants.Shooter.isInverted_Motor1);
    shoot.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    shoot.config_kP(0, Constants.Shooter.kP);
    shoot.config_kI(0, Constants.Shooter.kI);
    shoot.config_kD(0, Constants.Shooter.kD);
    shoot.config_IntegralZone(0, Constants.Shooter.kIZone);
    // shoot.configNominalOutputForward(0);
    // shoot.configNominalOutputReverse(0);
    shoot.configPeakOutputForward(1);
    shoot.configPeakOutputReverse(0); //no reverse output
    //Setup the front shooter
    shootFront = new WPI_TalonFX(Constants.Shooter.id_Motor2, Constants.Shooter.canBus);
    shootFront.configFactoryDefault();
    shootFront.setNeutralMode(NeutralMode.Coast);
    shootFront.setSensorPhase(Constants.Shooter.isSensorInverted_Motor1);
    shootFront.setInverted(Constants.Shooter.isInverted_Motor2);
    shootFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    shootFront.config_kP(0, Constants.Shooter.kP);
    shootFront.config_kI(0, Constants.Shooter.kI);
    shootFront.config_kD(0, Constants.Shooter.kD);
    shootFront.config_IntegralZone(0, Constants.Shooter.kIZone);
    // shootFront.configNominalOutputForward(0);
    // shootFront.configNominalOutputReverse(0);
    shootFront.configPeakOutputForward(1);
    shootFront.configPeakOutputReverse(0); //no reverse output

    // shootFront.follow(shoot); //follow the other one
    // shootFront.setInverted(InvertType.OpposeMaster);
    // CANTalonUtil.SetTalonCANUsage(shootFront, Usage.kPrimaryPidOnly, true);
    
    //Setup the solenoid
    m_hood = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_HoodSolenoid);
    m_hood2 = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Air.id_Hood2Solenoid);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run and change the shooter speed if requested
    if (m_shooter_rps != m_shooter_oldrps) {
      shoot.set(ControlMode.Velocity, Helpers.General.rpsToTicksPer100ms(m_shooter_rps, Constants.Shooter.kEncoderFullRotation, Constants.Shooter.kShooterReductionFactor)); //Set the target
      shootFront.set(ControlMode.Velocity, Helpers.General.rpsToTicksPer100ms(m_shooter_rps, Constants.Shooter.kEncoderFullRotation, Constants.Shooter.kShooterReductionFactor)); //Set the target
      m_shooter_oldrps=m_shooter_rps;
    }
    updateDashboard();
  }

  public void updateDashboard() {
    switch (telemetry) {
      case DEBUG:
        // Dashboard.Shooter.setCurrentSpeed(getShooterSpeedRPS());
        Dashboard.Shooter.setTargetSpeed(m_shooter_rps);
        Dashboard.Shooter.setHoodPosition(hoodPosition(m_hood.get(),m_hood2.get()));
        Dashboard.Shooter.setShotName(currentShotName.toString());
        break;
      case BASIC:
        Dashboard.Shooter.setCurrentSpeed(getShooterSpeedRPS());
        break;
      case NONE:
      default:
        //nothing to do
        break;
    }
  }

  public void setShotName(namedShots shotName) {
    currentShotName = shotName;
  }

  public double getShooterSpeedRPS() {
    // double rawrps = shoot.getSensorCollection().getIntegratedSensorVelocity(); //This works
    double rawrps = shoot.getSelectedSensorVelocity(0);
    double rps = Helpers.General.roundDouble(Helpers.General.ticksPer100msToRPS(rawrps, Constants.Shooter.kEncoderFullRotation, Constants.Shooter.kShooterReductionFactor),0);
    //Helpers.Debug.debug("shooter_rpm="+rpm,1000);
    return rps;
  }

  public void setShooterSpeed(double RPS) {
    RPS = Math.min(RPS, Constants.Shooter.kMaxShooterSpeed);
    RPS = Math.max(RPS, Constants.Shooter.kMinShooterSpeed);
    m_shooter_rps = RPS;
  }

  public void stopShooter() {
    shoot.set(ControlMode.PercentOutput,0); //just apply 0 power and let it coast.
    shootFront.set(ControlMode.PercentOutput,0); //just apply 0 power and let it coast.
    m_shooter_oldrps = 0;
    m_shooter_rps = 0;
    m_shooter_rpm = 0;
  }

  public void increaseShooterSpeed() {
    m_shooter_rps = Math.min(m_shooter_rps + Constants.Shooter.kSpeedIncrementSize, Constants.Shooter.kMaxShooterSpeed);
    m_shooter_rpm = m_shooter_rps * 60;
    Helpers.Debug.debug("New Shooter Speed:"+m_shooter_rps+"/"+m_shooter_rpm);
  }

  public void decreaseShooterSpeed() {
    m_shooter_rps = Math.max(m_shooter_rps - Constants.Shooter.kSpeedIncrementSize, Constants.Shooter.kMinShooterSpeed);
    m_shooter_rpm = m_shooter_rps * 60;
    Helpers.Debug.debug("New Shooter Speed:"+m_shooter_rps+"/"+m_shooter_rpm);
  }

  public void setShooterSpeedFromDashboard() {
    setShooterSpeed(Dashboard.Shooter.getTargetSpeed(0));
  }

  public String hoodPosition(boolean hood1up, boolean hood2up) {
    String pos = "";
    if (hood1up && hood2up) {
      pos="UP/UP";
    } else if (hood1up && !hood2up) {
      pos="UP/DOWN";
    } else if (!hood1up && hood2up) {
      pos="DOWN/UP";
    } else if (!hood1up && !hood2up) {
      pos="DOWN/DOWN";
    } else {
      pos="UNKNOWN";
    }
    return pos;
  }

  public void raiseHood(boolean hood1up, boolean hood2up) {
    //send command to air to put hood up or down based on boolean
    m_hood.set(hood1up);
    m_hood2.set(hood2up);
    Dashboard.Shooter.setHoodPosition(hoodPosition(hood1up,hood2up));
  }
}
