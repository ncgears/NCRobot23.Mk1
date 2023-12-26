/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot;

//Global imports
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//Util imports
// import frc.team1918.robot.utils.AndButton;
// import frc.team1918.robot.utils.OrPOVButton;

//Subsystems imports
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
//Commands imports
// import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.commands.drive.*;
import frc.team1918.robot.commands.vision.*;
//CommandGroup imports
import frc.team1918.robot.commandgroups.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("unused")
public class RobotContainer {
  //subsystems definitions
    //private final PowerDistribution m_pdp = new PowerDistribution();
    // private final Compressor m_air = new Compressor(PneumaticsModuleType.CTREPCM);
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final VisionSubsystem m_vision = new VisionSubsystem();
    private SendableChooser<Command> m_auto_chooser = new SendableChooser<>();
    private static SendableChooser<String> m_auto_cone = new SendableChooser<>();
    private static SendableChooser<String> m_auto_burner = new SendableChooser<>();

   /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    buildShuffleboard();

    // Enable closed loop control of compressor and enable it
    // if(Constants.Air.isDisabled) m_air.disable();

    // Enable the camera server and start capture
    if(Constants.Global.CAMERA_ENABLED) {
      // UsbCamera cam = CameraServer.startAutomaticCapture();
      // cam.setResolution(320, 240);
      // cam.setFPS(25);

    }

    // Set the default command that is run for the robot. Normally, this is the drive command
    if(!Constants.DriveTrain.isDisabled) {
      m_drive.setDefaultCommand(
        new drive_defaultDrive(
          m_drive,
          () -> Helpers.OI.getAxisFwdValue(true),
          () -> Helpers.OI.getAxisStrafeValue(true),
          () -> Helpers.OI.getAxisTurnValue(true)
        )
        // new drive_defaultDrive2(m_drive, dj)
      );
    }
    // if(!Constants.Stove.Aimer.isDisabled) {
    //   m_stove.setDefaultCommand(
    //     new stove_moveAimer(
    //       m_stove,
    //       () -> Helpers.OI.getAimerValue(true)
    //     )
    //   );
    // }
  }

  //button definitions
    //Driver Controller
      private Joystick dj = new Joystick(Constants.OI.OI_JOY_DRIVER);

      private JoystickButton btn_ResetGyro = new JoystickButton(dj, Constants.OI.Stadia.BTN_HAMBURGER);
      private JoystickButton btn_DefLock = new JoystickButton(dj, Constants.OI.Stadia.BTN_RT);

    //Operator Controller
      private Joystick oj = new Joystick(Constants.OI.OI_JOY_OPER);

  private void configureButtonBindings() {
    //The buttons here are named based on their functional purpose. This abstracts the purpose from which controller it is attached to.
    //onTrue (replaces whenPressed and whenActive): schedule on rising edge
    //onFalse (replaces whenReleased and whenInactive): schedule on falling edge
    //whileTrue (replaces whileActiveOnce): schedule on rising edge, cancel on falling edge
    //toggleOnTrue (replaces toggleWhenActive): on rising edge, schedule if unscheduled and cancel if scheduled

    //Drive Buttons
    btn_DefLock.whileTrue(new drive_defLock(m_drive));

    //Maintenance buttons
    btn_ResetGyro.onTrue(new drive_resetGyro(m_drive).andThen(new drive_resetOdometry(m_drive, new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(-180.0)))));
  }


  /**
   * Use this to pass the named command to the main Robot class.
   * @return command
   */
  public Command getAutonomousCommand() {
    System.out.println("Shuffle: Selected auton routine is "+m_auto_chooser.getSelected().getName());
    return m_auto_chooser.getSelected();
  }

  /**
   * This function returns the robot commands used in Robot.java. The purpose is to make it easier to build the possible commands
   * and handle requests for commands that don't exist rather than crashing
   * @return command
   */
  public Command getRobotCommand(String name) {
    //This selects a command (or command group) to return
    switch (name) {
      case "rumbleNotify":
        return new cg_djRumble();
      default:
        return null;
    }
  }


  //From here down is all used for building the shuffleboard

  public void buildShuffleboard(){
    buildAutonChooser();
    buildDriverTab();
    // buildCameraTab();
    buildMaintenanceTab();
    // buildDriverTestTab();
    // buildShooterTab();
    // buildIntakeTestTab();
    // buildClimberTestTab();
    // buildVisionTab();
    // Shuffleboard.selectTab("Driver"); //select the driver tab

    // Shuffleboard.getTab("Combined Test").add(new TestIntakeIndexerAndShooter(m_indexer, m_intake, m_shooter)).withPosition(0, 1).withSize(2, 1);
    // Shuffleboard.getTab("Combined Test").add(new SetForwardLimit(m_intake)).withPosition(0, 3).withSize(2, 1);
    // Shuffleboard.getTab("Arm MM Testing").add("ReSet Intake Arm", new SetArm(m_intake)).withPosition(0, 3).withSize(2, 1);
    // Shuffleboard.getTab("Intake").add("Extend Intake", new ArmMM(m_intake, Intake.INTAKE_ARM_EXTEND)).withPosition(0, 2).withSize(2,1);
    // Shuffleboard.getTab("Intake").add("Retract Intake", new ArmMM(m_intake, Intake.INTAKE_ARM_RETRACT)).withPosition(2,2).withSize(2,1);
    // Shuffleboard.getTab("Arm MM Testing").add(new ResetIntakeArmEncoder(m_intake)).withPosition(0, 2).withSize(2, 1);
    // Shuffleboard.getTab("ShooterPID").add("Shoot" , new ShooterPIDTuning(m_shooter, m_indexer)).withPosition(0, 3);
    // Shuffleboard.getTab("Turn MM Testing").add("Turn MM", new TurnToAngle(m_drive, 0)).withPosition(0, 3).withSize(2, 1);
    // Shuffleboard.getTab("Intake").add(new CollectBalls(m_intake, m_indexer)).withPosition(0, 1).withSize(2, 1);
    // Shuffleboard.getTab("Intake").add(new DropIntakeAndCollectBalls(m_intake, m_indexer)).withPosition(2, 1).withSize(2, 1);
    // Shuffleboard.getTab("Intake").add(new EjectBalls(m_indexer, m_shooter)).withPosition(0, 3).withSize(2, 1);
    
  }

  public void buildAutonChooser() {
    //This builds the auton chooser, giving driver friendly names to the commands from above
    if(Constants.Auton.isDisabled) {
      m_auto_chooser.setDefaultOption("Auton Disabled", getRobotCommand("auton_disabled"));
    } else {
      m_auto_chooser.setDefaultOption("Do Nothing", new cg_autonDoNothing(m_drive, m_vision));
    }
  }

  private void buildDriverTab(){
    ShuffleboardTab driveTab = Shuffleboard.getTab("Driver");
    // .withProperties(Map.of("Show grid",false,"Widget titles","Minimal"));  //doesn't work...

    // The drive tab is roughly 9 x 5 (columns x rows)
    // Camera can be 4 x 4, gyro 
    // driveTab.add("Cargo Cam", new HttpCamera("Cargo Photon", "http://10.50.24.11:5800"))
    //                                         .withWidget(BuiltInWidgets.kCameraStream).withPosition(0, 0)
    //                                         .withSize(4, 4);
    // Add heading and outputs to the driver views
    // Auton Chooser
    driveTab.add("Autonomous Chooser", m_auto_chooser)
        .withPosition(0, 0)
        .withSize(2, 1)
        .withWidget(BuiltInWidgets.kComboBoxChooser);

    // Gyro
    driveTab.add("Gyro", m_drive.getGyro())
        .withPosition(7, 0)
        .withSize(2,2)
        .withWidget(BuiltInWidgets.kGyro);

    // // PhotonCamera
    // driveTab.add("Photon Cam", new HttpCamera("photonvision_Port_1182_MJPEG_Server", "http://10.19.18.11:1182/stream.mjpg"))
    // .withPosition(7,2)
    // .withSize(3,3)
    // .withProperties(Map.of("Glyph","CAMERA_RETRO","Show Glyph",true,"Show crosshair",true,"Crosshair color","#333333","Show controls",false))
    // .withWidget(BuiltInWidgets.kCameraStream);

    // Field
    // driveTab.add("Field", m_drive.getField())
    //     .withPosition(6, 0)
    //     .withSize(3, 2)
    //     .withProperties(Map.of("Glyph","CODEPEN","Robot Icon Size",30,"Show Glyph",true))
    //     .withWidget(BuiltInWidgets.kField);
    
    //FMS Info -- not possible here?
    // driveTab.add("FMS Info","sdfsdf")
    //     .withPosition(6,2)
    //     .withSize(3,1)
    //     .withWidget(BuiltInWidgets.kField);

    // driveTab.add("Left Output", 0).withSize(1, 1).withPosition(4, 2).withWidget(BuiltInWidgets.kDial)
    //                               .withProperties(Map.of("Min", -1, "Max", 1));
    // driveTab.add("Right Output", 0).withSize(1, 1).withPosition(5, 2).withWidget(BuiltInWidgets.kDial)
    //                               .withProperties(Map.of("Min", -1, "Max", 1));

    // Add vision cues below the camera stream block
    // driveTab.add("HighTarget", false).withSize(1, 1).withPosition(0, 2).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("BallTarget", false).withSize(1, 1).withPosition(1, 2).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Pipeline",0).withSize(1, 1).withPosition(2, 2).withWidget(BuiltInWidgets.kDial)
    //                           .withProperties(Map.of("Min", 0, "Max", 2));
    // driveTab.add("Distance", 0).withSize(1, 1).withPosition(3, 2);

    // Add Intake Sensors and Ball Count
    // driveTab.add("Ball Count",0).withSize(1, 1).withPosition(6, 0).withWidget(BuiltInWidgets.kDial)
    //                           .withProperties(Map.of("Min", 0, "Max", 2));
    // driveTab.add("ShootBreak", false).withSize(1, 1).withPosition(7, 0).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("MidBreak", false).withSize(1, 1).withPosition(8, 0).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("IntakeBreak", false).withSize(1, 1).withPosition(9, 0).withWidget(BuiltInWidgets.kBooleanBox);
    // Add Intake Limits
    // driveTab.add("Int. Fwd Hard", false).withSize(1, 1).withPosition(6, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Int. Fwd Soft", false).withSize(1, 1).withPosition(7, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Int. Rev Hard", false).withSize(1, 1).withPosition(8, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Int. Rev Soft", false).withSize(1, 1).withPosition(9, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // // Climber Limits
    // driveTab.add("Clm. Fwd Hard", false).withSize(1, 1).withPosition(6, 2).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Clm. Fwd Soft", false).withSize(1, 1).withPosition(7, 2).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Clm. Rev Hard", false).withSize(1, 1).withPosition(8, 2).withWidget(BuiltInWidgets.kBooleanBox);
    // driveTab.add("Clm. Rev Soft", false).withSize(1, 1).withPosition(9, 2).withWidget(BuiltInWidgets.kBooleanBox);
  }

  private void buildMaintenanceTab(){
    ShuffleboardTab maintTab = Shuffleboard.getTab("Maintenance");
    // maintTab.add("Reset Robot", new cg_resetRobot(m_stove, m_fsr, m_vision))
    //     .withPosition(0, 0)
    //     .withSize(1, 1);
    // maintTab.add("Zero Robot", new cg_zeroMovingParts(m_stove, m_fsr))
    //     .withPosition(1, 0)
    //     .withSize(1, 1);
}

  public void buildDriverTestTab(){
    ShuffleboardTab driveMMTab = Shuffleboard.getTab("Drive Testing");
    // Configuration Values on row 1
    driveMMTab.add("kF", 0.1 )              .withPosition(0, 0).getEntry();
    driveMMTab.add("kP", 0.3 )              .withPosition(1, 0).getEntry();
    driveMMTab.add("kI", 0 )                .withPosition(2, 0).getEntry();
    driveMMTab.add("kD", 0 )                .withPosition(3, 0).getEntry();
    driveMMTab.add("Tgt. Inches", 0)        .withPosition(4, 0).getEntry();
    driveMMTab.add("Tgt. Degrees", 0)       .withPosition(5, 0).getEntry();
    driveMMTab.add("Finish Iterations", 5 ) .withPosition(6, 0).getEntry();

    // Result Values on row 2
    driveMMTab.add("Tgt. Ticks", 0)                                          .withPosition(0, 1);
    // driveMMTab.addNumber("Left Encoder", m_drive::getLeftEncoderValue)  .withPosition(1, 1);
    // driveMMTab.addNumber("Right Encoder", m_drive::getRightEncoderValue).withPosition(2, 1);
    // driveMMTab.addNumber("Gyro Read", m_drive::getRawAngle)             .withPosition(3, 1);
    driveMMTab.add("Run Time", 0)                                            .withPosition(4, 1);
    // driveMMTab.addNumber("Left SP", m_drive::getLeftSetPoint).withPosition(5, 1).withSize(1, 1);
    // driveMMTab.addNumber("Right SP", m_drive::getRightSetPoint).withPosition(6, 1).withSize(1, 1);
    
    // Drive limiters on row 3
    driveMMTab.add("Forward Limiter", 2.5).withPosition(0, 2);
    driveMMTab.add("Rotation Limiter", 2.5).withPosition(1, 2);
    driveMMTab.add("Drive Max", .7).withPosition(2, 2);
    // driveMMTab.add("Update Limits", new UpdateDriveLimiters(m_drive)).withPosition(3, 2).withSize(2, 1);

    // Drive commands on row 4
    // driveMMTab.add("Drive MM 100", new DriveMM(m_drive, 100))        .withPosition(0, 3).withSize(2, 1);
    // driveMMTab.add("Drive MM -100", new DriveMM(m_drive, -100))      .withPosition(2, 3).withSize(2, 1);
    // driveMMTab.add("Drive MM Test", new DriveMMTest(m_drive, 0))     .withPosition(4, 3).withSize(2, 1);

    // Turn commands on row 5
    // driveMMTab.add("Turn MM 90", new TurnToAngle(m_drive, 90))          .withPosition(0, 4).withSize(2, 1);
    // driveMMTab.add("Turn MM -90", new TurnToAngle(m_drive, -90))        .withPosition(2, 4).withSize(2, 1);
    // driveMMTab.add("Turn MM Test", new TurnToAngleTest(m_drive, 0))     .withPosition(4, 4).withSize(2, 1);
  }

  public void buildCameraTab(){
    ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");
    // PhotonCamera
    cameraTab.add("Node Cam", CameraServer.startAutomaticCapture())
        .withPosition(2,0)
        .withSize(3,3)
        .withProperties(Map.of("Glyph","CAMERA_RETRO","Show Glyph",true,"Show crosshair",true,"Crosshair color","#333333","Show controls",false))
        .withWidget(BuiltInWidgets.kCameraStream);
  }

  private void buildIntakeTestTab(){
    ShuffleboardTab intakeTab = Shuffleboard.getTab("Intake");
    intakeTab.add("ResetDriveSpeed", -.5)                  .withPosition(0, 0).withSize(1, 1);
    // intakeTab.add("Extend Limit", Intake.INTAKE_ARM_EXTEND).withPosition(3, 0).withSize(1, 1);

    // intakeTab.add("Intake Fwd Limit", 1)                            .withPosition(1, 1).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // intakeTab.add("Intake Rev Limit", 0)                            .withPosition(2, 1).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // intakeTab.addNumber("Arm Encoder", m_intake::getRelativeEncoder).withPosition(3, 1).withSize(1, 1);
    
    // intakeTab.add("RetractIntakeArm", new ArmMM(m_intake, Intake.INTAKE_ARM_RETRACT)).withPosition(0, 2).withSize(2, 1);
    // intakeTab.add("ExtendIntakeArm", new ArmMM(m_intake, Intake.INTAKE_ARM_EXTEND))  .withPosition(2, 2).withSize(2, 1);
    // intakeTab.add("Reset Extend Limit", new SetExtendLimit(m_intake))                .withPosition(4, 2).withSize(2, 1);

    // intakeTab.add("ResetArmLimitAndEncoder", new ResetArmLimitAndEncoder(m_intake)).withPosition(0, 3).withSize(2, 1);
    // intakeTab.add("TurnOffIntakeArm", new TurnOffIntakeArm(m_intake))              .withPosition(2, 3).withSize(2, 1);

    // intakeTab.add("Move Arm no MM (900)", new ExtendIntakeBangBang(m_intake, 1700)).withPosition(5, 0);
  }

  private void buildClimberTestTab(){
    ShuffleboardTab climberTab = Shuffleboard.getTab("Climber");
    // Testing Information
    climberTab.add("ClimberDownSpeed", -.7).withPosition(0, 0).withSize(1, 1);
    climberTab.add("ClimberUpSpeed", 1)    .withPosition(1, 0).withSize(1, 1);

    // climberTab.addNumber("Encoder", m_climber::getRelativeEncoder)                    .withPosition(1, 1);
    // climberTab.addBoolean("Forward Limit", m_climber::forwardLimitSwitchTriggered)    .withPosition(2, 1);
    // climberTab.addBoolean("Reverse Limit", m_climber::reverseLimitSwitchTriggered)    .withPosition(3, 1);
    // climberTab.addBoolean("Soft Forward Limit", m_climber::getClimberSoftForwardLimit).withPosition(4, 1);
    // climberTab.addBoolean("Soft Reverse Limit", m_climber::getClimberSoftReverseLimit).withPosition(5, 1);
    
    // High bar raise
    // climberTab.add("Raise to MidRung", new HighBarRaise(m_climber, m_ledsubsystem, m_driver_controller)).withPosition(0, 2).withSize(2, 1);
    // climberTab.add("Raise to LowBar", new LowBarRaise(m_climber, m_ledsubsystem, m_driver_controller))  .withPosition(2, 2).withSize(2, 1);
    // climberTab.add("Cancel Climber", new CancelClimber(m_climber, m_ledsubsystem))                      .withPosition(4, 2).withSize(2, 1);
    // climberTab.add("Lower Climber", new LowerToLimitOrTime(m_climber))                  .withPosition(6, 2).withSize(2, 1);
    
    // climberTab.add("TestClimbDown", new TestClimberDown(m_climber))                     .withPosition(0, 3).withSize(2, 1);
    // climberTab.add("TestClimbUp", new TestClimberUp(m_climber))                         .withPosition(2, 3).withSize(2, 1);
    // climberTab.add("Reset to LowerLimit", new DriveClimbertoReverseHardLimit(m_climber)).withPosition(4, 3).withSize(2, 1);
  }

  private void buildVisionTab() {
    ShuffleboardTab visionTab = Shuffleboard.getTab("Vision");

    // visionTab.addNumber("Distance to Target", m_vision::getHubTargetRangeIndex0).withPosition(1, 0);
    // visionTab.addNumber("Target Yaw",         m_vision::getHubTargetRangeIndex1).withPosition(1, 1);

    // visionTab.add("LED on", new LEDon(m_vision))  .withPosition(0, 0);
    // visionTab.add("LED off", new LEDoff(m_vision)).withPosition(0, 1);
    // visionTab.add("Condigure Vision Drive", new configureVisionDrivePID(m_drive)).withPosition(0, 2);
    // visionTab.add("Configure Turn Turn", new configureVisionTurnPID(m_drive))    .withPosition(0, 3);
    // visionTab.add("Configure Cargo controllre", new configureVisionCargoPID(m_drive)).withPosition(0, 4);

    visionTab.add("forward drive speed", 0);
    visionTab.add("Turn speed", 0);
    visionTab.add("Cargo Yaw", 0);

    visionTab.add("Drive kP", 0.4);
    visionTab.add("Drive kD", 0);
    visionTab.add("Drive kI", 0);
    visionTab.add("Drive kF", 0);
    visionTab.add("Turn kP", 0.02);
    visionTab.add("Turn kD", 0);
    visionTab.add("Turn kI", 0);
    visionTab.add("Turn kF", 0);
    visionTab.add("Cargo kP,", 0.011);
  }


}
