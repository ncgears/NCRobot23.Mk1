/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1918.robot;

//Global imports
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.cscore.AxisCamera;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Pose2d;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
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
import frc.team1918.robot.subsystems.FiveSecondRuleSubsystem;
import frc.team1918.robot.subsystems.StoveSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
//Commands imports
// import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.commands.fivesecondrule.*;
import frc.team1918.robot.commands.drive.*;
import frc.team1918.robot.commands.stove.*;
import frc.team1918.robot.commands.vision.*;
import frc.team1918.robot.modules.Burner.BurnerPositions;
import frc.team1918.robot.modules.GreaseTrap.GreaseTrapPositions;
import frc.team1918.robot.modules.HotPlate.HotPlatePositions;
//CommandGroup imports
import frc.team1918.robot.commandgroups.*;
// import frc.team1918.robot.commandgroups.cg_drive_initOdometry;

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
    private final Compressor m_air = new Compressor(PneumaticsModuleType.CTREPCM);
    private final FiveSecondRuleSubsystem m_fsr = new FiveSecondRuleSubsystem();
    private final StoveSubsystem m_stove = new StoveSubsystem();
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final VisionSubsystem m_vision = new VisionSubsystem();
    private SendableChooser<Command> m_auto_chooser = new SendableChooser<>();

   /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    buildShuffleboard();

    // Enable closed loop control of compressor and enable it
    if(Constants.Air.isDisabled) m_air.disable();

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
  }

  //button definitions
    //Driver Controller
      private Joystick dj = new Joystick(Constants.OI.OI_JOY_DRIVER);

      private POVButton btn_GreaseTrapHome = new POVButton(dj, Constants.OI.Stadia.DPAD_UP);
      private POVButton btn_GreaseTrapLevel = new POVButton(dj, Constants.OI.Stadia.DPAD_LEFT);
      private POVButton btn_GreaseTrapDown = new POVButton(dj, Constants.OI.Stadia.DPAD_DN);
      private POVButton btn_GreaseTrapFlip = new POVButton(dj, Constants.OI.Stadia.DPAD_RIGHT);

      private JoystickButton btn_HotPlateHome = new JoystickButton(dj, Constants.OI.Stadia.BTN_Y);
      private JoystickButton btn_HotPlateLevel = new JoystickButton(dj, Constants.OI.Stadia.BTN_B);
      private JoystickButton btn_HotPlateDown = new JoystickButton(dj, Constants.OI.Stadia.BTN_A);

      private JoystickButton btn_AutoBalance = new JoystickButton(dj, Constants.OI.Stadia.BTN_X);

      private JoystickButton btn_BurnerHot = new JoystickButton(dj, Constants.OI.Stadia.BTN_RB);
      private JoystickButton btn_BurnerCold = new JoystickButton(dj, Constants.OI.Stadia.BTN_RT);

      private JoystickButton btn_ResetGyro = new JoystickButton(dj, Constants.OI.Stadia.BTN_HAMBURGER);
      private JoystickButton btn_MoveTowardHome = new JoystickButton(dj, Constants.OI.Stadia.BTN_GOOGLE);
      private JoystickButton btn_ResetRobot = new JoystickButton(dj, Constants.OI.Stadia.BTN_FRAME);
      private JoystickButton btn_LED = new JoystickButton(dj, Constants.OI.Stadia.BTN_ELLIPSES);
      
      // private JoystickButton btn_CollectorToggle = new JoystickButton(dj, Constants.OI.Logitech.BTN_START);
      // private JoystickButton btn_GyroReset = new JoystickButton(dj, Constants.OI.Logitech.BTN_BACK);
      // private POVButton btn_ShooterIncrease = new POVButton(dj, Constants.OI.Logitech.DPAD_UP);
      // private POVButton btn_ShooterDecrease = new POVButton(dj, Constants.OI.Logitech.DPAD_DN);
      // private JoystickButton btn_ShooterStop = new JoystickButton(dj, Constants.OI.Logitech.BTN_A);
      // private JoystickButton btn_FeederFwd = new JoystickButton(dj, Constants.OI.Logitech.BTN_RB);
      // private Trigger t_RingLight = new Trigger(() -> dj.getRawAxis(Constants.OI.Logitech.AXIS_RT)>0.3);
      // private JoystickButton btn_AimSelectShoot = new JoystickButton(dj, Constants.OI.Logitech.BTN_B);
      // private JoystickButton btn_AimSelect = new JoystickButton(dj, Constants.OI.Logitech.BTN_LB);
      // private JoystickButton btn_ShootDashboard = new JoystickButton(dj, Constants.OI.Logitech.BTN_Y);
      // private JoystickButton btn_ResetClimb = new JoystickButton(dj, Constants.OI.Logitech.BTN_X);
      //Music Control
      // private JoystickButton btn_MusicPlay = new JoystickButton(dj, Constants.OI.Logitech.BTN_Y);
      // private JoystickButton btn_MusicStop = new JoystickButton(dj, Constants.OI.Logitech.BTN_R);
      // private JoystickButton btn_MusicFwd = new JoystickButton(dj, Constants.OI.Logitech.BTN_B);
      // private JoystickButton btn_MusicBack = new JoystickButton(dj, Constants.OI.Logitech.BTN_X);
      // private JoystickButton btn_MusicReady = new JoystickButton (dj, Constants.OI.Logitech.BTN_A);
      // private Trigger t_PlayMusic = new Trigger(() -> dj.getRawAxis(Constants.OI.Logitech.AXIS_LT)>0.3);
      // private POVButton btn_THROTUP_UP = new POVButton(dj, Constants.OI.Driver.DPAD_THROTUP_UP);
      //   private POVButton btn_THROTUP_UL = new POVButton(dj, Constants.OI.Driver.DPAD_THROTUP_UL);
      //   private POVButton btn_THROTUP_UR = new POVButton(dj, Constants.OI.Driver.DPAD_THROTUP_UR);
      // private POVButton btn_THROTDN_DN = new POVButton(dj, Constants.OI.Driver.DPAD_THROTDN_DN);
      //   private POVButton btn_THROTDN_DL = new POVButton(dj, Constants.OI.Driver.DPAD_THROTDN_DL);
      //   private POVButton btn_THROTDN_DR = new POVButton(dj, Constants.OI.Driver.DPAD_THROTDN_DR);
      //OrPOVButtons are a custom button type to bind 3 DPAD directions to a single command. See utils/OrPOVButton
      // private OrPOVButton orbtn_THROTUP = new OrPOVButton(btn_THROTUP_UP, btn_THROTUP_UL, btn_THROTUP_UR);
      // private OrPOVButton orbtn_THROTDN = new OrPOVButton(btn_THROTDN_DN, btn_THROTDN_DL, btn_THROTDN_DR);


    //Operator Controller
      private Joystick oj = new Joystick(Constants.OI.OI_JOY_OPER);
      // //Whirly
      // private JoystickButton btn_WhirlyUp = new JoystickButton(oj, Constants.OI.Logitech.BTN_START);
      // //private JoystickButton btn_LockHooks = new JoystickButton(oj, Constants.OI.Logitech.BTN_BACK);
      // private POVButton btn_WhirlyRev = new POVButton(oj, Constants.OI.Logitech.DPAD_LEFT);
      // private POVButton btn_WhirlyFwd = new POVButton(oj, Constants.OI.Logitech.DPAD_RIGHT);
      // private JoystickButton btn_ReleaseHook1 = new JoystickButton(oj, Constants.OI.Logitech.BTN_L);
      // private JoystickButton btn_ReleaseHook2 = new JoystickButton(oj, Constants.OI.Logitech.BTN_R);
      // //Intake
      // // private JoystickButton btn_IntakeReverse = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      // // private JoystickButton btn_IntakeForward = new JoystickButton(oj, Constants.OI.Logitech.BTN_RB);
      // //Shooting
      // private JoystickButton btn_ShootLow = new JoystickButton(oj, Constants.OI.Logitech.BTN_RB);
      // // private JoystickButton btn_ShootDashboard = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      // private JoystickButton btn_ShootProtected = new JoystickButton(oj, Constants.OI.Logitech.BTN_X);
      // private JoystickButton btn_ShootDefault = new JoystickButton(oj, Constants.OI.Logitech.BTN_Y);
      // private JoystickButton btn_ShootLine = new JoystickButton(oj, Constants.OI.Logitech.BTN_A);
      // private JoystickButton btn_ShootStop = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      // private JoystickButton btn_ShootWall = new JoystickButton(oj, Constants.OI.Logitech.BTN_B);
      // private Trigger t_IntakeForward = new Trigger(() -> oj.getRawAxis(Constants.OI.Logitech.AXIS_RT)>0.3);
      // private Trigger t_IntakeRetractor = new Trigger(() -> oj.getRawAxis(Constants.OI.Logitech.AXIS_LT)>0.3);
      // private JoystickButton btn_IntakeReverse = new JoystickButton(oj, Constants.OI.Logitech.BTN_BACK);
      // private Trigger t_IntakeReverse = new Trigger(() -> oj.getRawAxis(Constants.OI.Logitech.AXIS_LT)>0.3);
      // private POVButton btn_COLLECTOR_UP = new POVButton(oj, Constants.OI.Operator.DPAD_COLLECTOR_UP);
      // private POVButton btn_COLLECTOR_DOWN = new POVButton(oj, Constants.OI.Operator.DPAD_COLLECTOR_DOWN);
      // private JoystickButton btn_COLLECTOR_TOGGLE = new JoystickButton(oj, Constants.OI.Operator.BTN_TOG_MIDDOWN);

    //Special Bindings (AndButtons)
      // private JoystickButton btn_MECHZERO_KEY1 = new JoystickButton(dj, Constants.OI.Operator.BTN_MECHZERO);
      // private JoystickButton btn_MECHZERO_KEY2 = new JoystickButton(oj, Constants.OI.Operator.BTN_MECHZERO);
      // private AndButton andbtn_MECHZERO = new AndButton(btn_MECHZERO_KEY1,btn_MECHZERO_KEY2); //AndButton requires both to be true


  // public static void setAirDisabled(boolean disabled) {
  //   if(disabled) { m_air.disable(); } else { m_air.enabled(); }
  // }
    
  private void configureButtonBindings() {
    //The buttons here are named based on their functional purpose. This abstracts the purpose from which controller it is attached to.
    //New for 2023: 
    //onTrue (replaces whenPressed and whenActive): schedule on rising edge
    //onFalse (replaces whenReleased and whenInactive): schedule on falling edge
    //whileTrue (replaces whileActiveOnce): schedule on rising edge, cancel on falling edge
    //toggleOnTrue (replaces toggleWhenActive): on rising edge, schedule if unscheduled and cancel if scheduled

    btn_GreaseTrapDown.onTrue(new stove_moveGreaseTrapTo(m_stove, GreaseTrapPositions.DOWN));
    btn_GreaseTrapLevel.onTrue(new stove_moveGreaseTrapTo(m_stove, GreaseTrapPositions.LEVEL));
    btn_GreaseTrapHome.onTrue(new stove_moveGreaseTrapTo(m_stove, GreaseTrapPositions.HOME));
    btn_GreaseTrapFlip.onTrue(new stove_moveGreaseTrapTo(m_stove, GreaseTrapPositions.FLIP)).onFalse(new stove_moveGreaseTrapTo(m_stove, GreaseTrapPositions.LEVEL));

    btn_HotPlateDown.onTrue(new stove_moveHotPlateTo(m_stove, HotPlatePositions.DOWN));
    btn_HotPlateLevel.onTrue(new stove_moveHotPlateTo(m_stove, HotPlatePositions.LEVEL));
    btn_HotPlateHome.onTrue(new stove_moveHotPlateTo(m_stove, HotPlatePositions.HOME));

    btn_BurnerHot.onTrue(new stove_moveBurnerTo(m_stove, m_fsr, BurnerPositions.TOP));
    btn_BurnerCold.onTrue(new stove_moveBurnerTo(m_stove, m_fsr, BurnerPositions.BOTTOM));

    btn_AutoBalance.whileTrue(new drive_autoBalance(m_drive));

    btn_ResetGyro.onTrue(new drive_resetGyro(m_drive));
    btn_MoveTowardHome.whileTrue(new cg_zeroMovingParts(m_stove, m_fsr)); 
    btn_ResetRobot.onTrue(new cg_resetRobot(m_stove, m_fsr, m_vision));
    btn_LED.onTrue(new vision_setRinglight(m_vision, Constants.Vision.stateLightOn)).onFalse(new vision_setRinglight(m_vision, !Constants.Vision.stateLightOn));

    //These are the operator buttons
//    btn_WhirlyUp.onTrue(new climber_whirlygigUp(m_climber));
    // btn_IntakeForward.onTrue(new feeder_advanceToShooter(m_feeder));
//    t_IntakeForward.onTrue(new cg_collector_intakeAndFeed(m_collector, m_feeder)).onFalse(new collector_intakeStop(m_collector).andThen(new collector_retractIntake(m_collector)));
//    t_IntakeRetractor.onTrue(new collector_deployRetractor(m_collector,true)).onFalse(new collector_deployRetractor(m_collector, false));
    // t_IntakeReverse.onTrue(new collector_intakeReverse(m_collector));
//    btn_IntakeReverse.whileTrue(new collector_intakeReverse(m_collector));
//    btn_ShootDashboard.onTrue(new shooter_shootNamed(m_shooter, namedShots.DASHBOARD));
//    btn_ShootLow.onTrue(new shooter_shootNamed(m_shooter, namedShots.LOW));
//    btn_ShootProtected.onTrue(new shooter_shootNamed(m_shooter, namedShots.PROTECTED));
//    btn_ShootLine.onTrue(new shooter_shootNamed(m_shooter, namedShots.LINE));
//    btn_ShootDefault.onTrue(new shooter_shootNamed(m_shooter, namedShots.DEFAULT));
//    btn_ShootWall.onTrue(new shooter_shootNamed(m_shooter, namedShots.WALL));
//    btn_ShootStop.onTrue(new shooter_stopShooter(m_shooter));

    
    //These are the driver buttons
//    btn_CollectorToggle.onTrue(new collector_toggleIntake(m_collector));
//    btn_FeederFwd.onTrue(new feeder_advance(m_feeder)).onFalse(new feeder_stop(m_feeder));
//    btn_ShooterStop.onTrue(new shooter_stopShooter(m_shooter));
//    btn_ShooterIncrease.onTrue(new shooter_increaseThrottle(m_shooter));
//    btn_ShooterDecrease.onTrue(new shooter_decreaseThrottle(m_shooter));
    // btn_GyroReset.onTrue(new drive_resetGyro(m_drive).andThen(new drive_resetOdometry(m_drive, new Pose2d()))); //.andThen(new drive_homeSwerves(m_drive))
    // btn_AimSelectShoot.whileHeld(new cg_vision_aimSelectAndShoot(m_drive, m_feeder, m_shooter, m_vision)).whenReleased(new feeder_stop(m_feeder).andThen(new shooter_stopShooter(m_shooter)));
//    btn_AimSelectShoot.whileTrue(new vision_findTargetAndShot(m_drive, m_vision, m_shooter));
//    btn_AimSelect.whileTrue(new vision_findTargetAndShot(m_drive, m_vision, m_shooter));
    // t_RingLight.onTrue(new vision_setRinglight(m_vision, Constants.Vision.stateLightOn)).onFalse(new vision_setRinglight(m_vision, !Constants.Vision.stateLightOn));
//    btn_ResetClimb.onTrue(new climber_resetClimb(m_climber));
    //Music Control Buttons
    // btn_MusicPlay.onTrue(new orchestra_loadAndPlay(m_orchestra));
    // btn_MusicStop.onTrue(new orchestra_stop(m_orchestra));
    // btn_MusicFwd.onTrue(new orchestra_increaseSong(m_orchestra));
    // btn_MusicBack.onTrue(new orchestra_decreaseSong(m_orchestra));
    // btn_MusicReady.onTrue(new orchestra_primeToPlay(m_orchestra));
    // t_PlayMusic.whenActive(new orchestra_loadAndPlay(m_orchestra));
    // t_PlayMusic.whenActive(new orchestra_stop(m_orchestra));

    // btn_TOGGLE_DEBUG.onTrue(new helpers_toggleDebug());
    // btn_LOCKANGLE.onTrue(new drive_lockAngle(m_drive));
    // btn_UNLOCKANGLE.onTrue(new drive_unlockAngle(m_drive));
    //bind all 3 up and all 3 down for shooter throttle up/down
    // orbtn_THROTUP.onTrue(new shooter_increaseThrottle(m_shooter));
    // orbtn_THROTDN.onTrue(new shooter_decreaseThrottle(m_shooter));
    //bind both buttons requirement
    // andbtn_MECHZERO.onTrue(new drive_moveAllToMechZero(m_drive));
  }


  /**
   * Use this to pass the named command to the main Robot class.
   * @return command
   */
  public Command getAutonomousCommand() {
    return m_auto_chooser.getSelected();
  }

  /**
   * This function returns the robot commands used in the auto_chooser. The purpose is to make it easier to build the possible commands
   * and handle requests for commands that don't exist rather than crashing
   * @return command
   */
  public Command getRobotCommand(String name) {
    //This selects a command (or command group) to return
    switch (name) {
      case "resetRobot":
        return new cg_resetRobot(m_stove, m_fsr, m_vision);
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
      m_auto_chooser.setDefaultOption("AR1 3 Ball", getRobotCommand("auton_ar1ThreeBall"));
      m_auto_chooser.addOption("AR2 2 Ball", getRobotCommand("auton_ar2TwoBall"));
      // m_auto_chooser.addOption("AC1 1 Ball", getRobotCommand("auton_ac1OneBall"));
      // m_auto_chooser.addOption("AL1 2 Ball", getRobotCommand("auton_al1TwoBall"));
      // m_auto_chooser.addOption("AL2 2 Ball #2", getRobotCommand("auton_al2TwoBall"));
      // m_auto_chooser.addOption("AL3 2 Ball #3", getRobotCommand("auton_al3TwoBall"));
      // m_auto_chooser.addOption("Basic Shooting", getRobotCommand("auton_BasicShootingAuto"));
    }
    //SmartDashboard.putData(m_auto_chooser); //put in the smartdash
  }

  private void buildDriverTab(){
    ShuffleboardTab driveTab = Shuffleboard.getTab("Driver");

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
        .withPosition(0, 1)
        .withSize(2,2)
        .withWidget(BuiltInWidgets.kGyro);

    // PhotonCamera
    driveTab.add("Node Cam", new HttpCamera("photonvision_Port_1182_MJPEG_Server", "http://10.19.18.11:1182/stream.mjpg"))
        .withPosition(2,0)
        .withSize(3,3)
        .withProperties(Map.of("Glyph","CAMERA_RETRO","Show Glyph",true,"Show crosshair",true,"Crosshair color","#333333","Show controls",false))
        .withWidget(BuiltInWidgets.kCameraStream);

    // Field
    driveTab.add("Field", m_drive.getField())
        .withPosition(6, 0)
        .withSize(3, 2)
        .withProperties(Map.of("Glyph","CODEPEN","Show Glyph",true))
        .withWidget(BuiltInWidgets.kField);
    
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
    maintTab.add("Reset Robot", new cg_resetRobot(m_stove, m_fsr, m_vision))
        .withPosition(0, 0)
        .withSize(1, 1);

    maintTab.add("Spat Left Speed", 0)
        .withPosition(0, 1)
        .withSize(1,1);
    maintTab.add("Spat Right Speed",0)
        .withPosition(1,1)
        .withSize(1,1);
    maintTab.add("Burner Speed",0)
        .withPosition(2,1)
        .withSize(1,1);
    maintTab.add("HotPlate Speed",0)
        .withPosition(3,1)
        .withSize(1,1);
    maintTab.add("GreaseTrap Speed",0)
        .withPosition(4,1)
        .withSize(1,1);
    maintTab.add("Griddle Speed",0)
        .withPosition(5,1)
        .withSize(1,1);
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

  public void buildShooterTab(){
    ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    // shooterTab.add("SetShotDistanceCloseShot", new SetShooterDistance(m_shooter, ShotDistance.ClosestShot)).withPosition(0, 0).withSize(2, 1);
    // shooterTab.add("StarShooterWheel", new StartShooterWheel(m_shooter, m_ledsubsystem)).withPosition(2, 0).withSize(2, 1);
    // shooterTab.add("WaitUntilCommand", new WaitUntilCommand(m_shooter::isUpToSpeed)).withPosition(4, 0).withSize(2, 1);
    // shooterTab.add("ShootBallsUntilEmpty", new ShootBallsTilEmptyOrThreeSeconds(m_indexer, m_shooter, m_ledsubsystem)).withPosition(6, 0).withSize(2, 1);
    // shooterTab.add("StopShooter", new StopShooterAndIndexerMotors(m_shooter, m_indexer, m_ledsubsystem)).withPosition(8, 0).withSize(2, 1);

    // shooterTab.addBoolean("Is Up To Speed", m_shooter::isUpToSpeed).withPosition(0, 2).withSize(1, 1);
    // shooterTab.addNumber("Closed Loop Error", m_shooter::getClosedLoopError).withPosition(1, 2).withSize(1, 1);
    // shooterTab.add("At Speed", false).withPosition(2, 2).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
    // shooterTab.addNumber("Shooter Loop Count", m_shooter::getLoopCount).withPosition(3, 2).withSize(1, 1);
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
