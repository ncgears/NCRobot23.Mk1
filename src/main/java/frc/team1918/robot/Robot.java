/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.net.PortForwarder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// import frc.team1918.robot.subsystems.ClimberSubsystem;
// import frc.team1918.robot.subsystems.CollectorSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public Alliance m_alliance;
  private Command m_autonomousCommand;
  private Command m_disableCommand;
  public static UsbCamera camera;
  // private Command m_initOdom;
  // private Command m_resetGyro;
  

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Disable LiveWindow
    LiveWindow.disableAllTelemetry();

    //Setup camera
    camera = CameraServer.startAutomaticCapture();
    // camera.setResolution(640, 480);
    // camera.setFPS(20);
    camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 30);

    //Create forwarder for photonvision
    //pi4
    PortForwarder.add(5800,"photonvision.local",5800);
    PortForwarder.add(1181,"photonvision.local",1181);
    PortForwarder.add(1182,"photonvision.local",1182);
    //limelight
    // PortForwarder.add(5800,"gloworm.local",5800);
    // PortForwarder.add(1181,"gloworm.local",1181);
    // PortForwarder.add(1182,"gloworm.local",1182);

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    m_disableCommand = m_robotContainer.getRobotCommand("resetRobot");
    if (m_disableCommand != null) m_disableCommand.schedule();
    // m_dc1 = m_robotContainer.getDisableCommand(1);
    // if (m_dc1 != null) m_dc1.schedule();
    // m_dc2 = m_robotContainer.getDisableCommand(2);
    // if (m_dc2 != null) m_dc2.schedule();
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_alliance = DriverStation.getAlliance(); //Put Alliance.Red or Alliance.Blue in Robot.m_alliance
    // m_resetGyro = m_robotContainer.getResetGyroCommand();
    // if (m_resetGyro != null) m_resetGyro.schedule();

    // m_initOdom = m_robotContainer.getInitOdomCommand(); 
    // if (m_initOdom != null) m_initOdom.schedule();

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null && !Constants.Auton.isDisabled) m_autonomousCommand.schedule();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) m_autonomousCommand.cancel();

    // m_resetGyro = m_robotContainer.getResetGyroCommand();
    // if (m_resetGyro != null) m_resetGyro.schedule();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @SuppressWarnings("unused")
  @Override
  public void simulationInit() {
    // NetworkTableInstance nt = NetworkTableInstance.getDefault();
    // nt.stopServer();
    // nt.setServer("10.19.18.11");
    // nt.startClient4("Robot Simulation");

    int m_simgyro = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
    SimDouble angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(m_simgyro,"Yaw"));
    // angle.set(0.0);
  }

  @SuppressWarnings("unused")
  @Override
  public void simulationPeriodic() {
    int m_simgyro = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
    SimDouble angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(m_simgyro,"Yaw"));
    SimDouble pitch = new SimDouble(SimDeviceDataJNI.getSimValueHandle(m_simgyro,"Pitch"));
    // angle.set(angle.get()+0.05);
    // angle.set(0.5);
    // pitch.set(4.5);
  }

}
