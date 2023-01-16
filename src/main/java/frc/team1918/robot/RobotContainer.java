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
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//Util imports
// import frc.team1918.robot.utils.AndButton;
// import frc.team1918.robot.utils.OrPOVButton;

//Subsystems imports
import frc.team1918.robot.subsystems.ClimberSubsystem;
import frc.team1918.robot.subsystems.CollectorSubsystem;
import frc.team1918.robot.subsystems.DriveSubsystem;
import frc.team1918.robot.subsystems.FeederSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem;
import frc.team1918.robot.subsystems.ShooterSubsystem.namedShots;
// import frc.team1918.robot.subsystems.OrchestraSubsystem;
import frc.team1918.robot.subsystems.VisionSubsystem;
//Commands imports
// import frc.team1918.robot.commands.helpers.helpers_debugMessage;
import frc.team1918.robot.commands.climber.*;
import frc.team1918.robot.commands.collector.*;
import frc.team1918.robot.commands.drive.*;
import frc.team1918.robot.commands.feeder.*;
import frc.team1918.robot.commands.shooter.*;
// import frc.team1918.robot.commands.orchestra.*;
import frc.team1918.robot.commands.vision.*;
//CommandGroup imports
import frc.team1918.robot.commandgroups.*;
// import frc.team1918.robot.commandgroups.cg_drive_initOdometry;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //subsystems definitions
    //private final PowerDistribution m_pdp = new PowerDistribution();
    private final Compressor m_air = new Compressor(PneumaticsModuleType.CTREPCM);
    private final ClimberSubsystem m_climber = new ClimberSubsystem();
    private final CollectorSubsystem m_collector = new CollectorSubsystem();
    private final FeederSubsystem m_feeder = new FeederSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final VisionSubsystem m_vision = new VisionSubsystem();
    // private final OrchestraSubsystem m_orchestra = new OrchestraSubsystem();

   /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Enable closed loop control of compressor and enable it
    if(Constants.Air.isDisabled) m_air.disable();

    // Enable the camera server and start capture
    if(Constants.Global.CAMERA_ENABLED) {
      UsbCamera cam = CameraServer.startAutomaticCapture();
      cam.setResolution(320, 240);
      cam.setFPS(25);
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
      private JoystickButton btn_CollectorToggle = new JoystickButton(dj, Constants.OI.Logitech.BTN_START);
      private JoystickButton btn_GyroReset = new JoystickButton(dj, Constants.OI.Logitech.BTN_BACK);
      private POVButton btn_ShooterIncrease = new POVButton(dj, Constants.OI.Logitech.DPAD_UP);
      private POVButton btn_ShooterDecrease = new POVButton(dj, Constants.OI.Logitech.DPAD_DN);
      private JoystickButton btn_ShooterStop = new JoystickButton(dj, Constants.OI.Logitech.BTN_A);
      private JoystickButton btn_FeederFwd = new JoystickButton(dj, Constants.OI.Logitech.BTN_RB);
      private Trigger t_RingLight = new Trigger(() -> dj.getRawAxis(Constants.OI.Logitech.AXIS_RT)>0.3);
      private JoystickButton btn_AimSelectShoot = new JoystickButton(dj, Constants.OI.Logitech.BTN_B);
      private JoystickButton btn_AimSelect = new JoystickButton(dj, Constants.OI.Logitech.BTN_LB);
      private JoystickButton btn_ShootDashboard = new JoystickButton(dj, Constants.OI.Logitech.BTN_Y);
      private JoystickButton btn_ResetClimb = new JoystickButton(dj, Constants.OI.Logitech.BTN_X);
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
      //Whirly
      private JoystickButton btn_WhirlyUp = new JoystickButton(oj, Constants.OI.Logitech.BTN_START);
      //private JoystickButton btn_LockHooks = new JoystickButton(oj, Constants.OI.Logitech.BTN_BACK);
      private POVButton btn_WhirlyRev = new POVButton(oj, Constants.OI.Logitech.DPAD_LEFT);
      private POVButton btn_WhirlyFwd = new POVButton(oj, Constants.OI.Logitech.DPAD_RIGHT);
      private JoystickButton btn_ReleaseHook1 = new JoystickButton(oj, Constants.OI.Logitech.BTN_L);
      private JoystickButton btn_ReleaseHook2 = new JoystickButton(oj, Constants.OI.Logitech.BTN_R);
      //Intake
      // private JoystickButton btn_IntakeReverse = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      // private JoystickButton btn_IntakeForward = new JoystickButton(oj, Constants.OI.Logitech.BTN_RB);
      //Shooting
      private JoystickButton btn_ShootLow = new JoystickButton(oj, Constants.OI.Logitech.BTN_RB);
      // private JoystickButton btn_ShootDashboard = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      private JoystickButton btn_ShootProtected = new JoystickButton(oj, Constants.OI.Logitech.BTN_X);
      private JoystickButton btn_ShootDefault = new JoystickButton(oj, Constants.OI.Logitech.BTN_Y);
      private JoystickButton btn_ShootLine = new JoystickButton(oj, Constants.OI.Logitech.BTN_A);
      private JoystickButton btn_ShootStop = new JoystickButton(oj, Constants.OI.Logitech.BTN_LB);
      private JoystickButton btn_ShootWall = new JoystickButton(oj, Constants.OI.Logitech.BTN_B);
      private Trigger t_IntakeForward = new Trigger(() -> oj.getRawAxis(Constants.OI.Logitech.AXIS_RT)>0.3);
      private Trigger t_IntakeRetractor = new Trigger(() -> oj.getRawAxis(Constants.OI.Logitech.AXIS_LT)>0.3);
      private JoystickButton btn_IntakeReverse = new JoystickButton(oj, Constants.OI.Logitech.BTN_BACK);
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

    //These are the operator buttons
    btn_WhirlyUp.onTrue(new climber_whirlygigUp(m_climber));
    if(Constants.Climber.useAutoClimb) {
      btn_WhirlyFwd.whileTrue(new climber_autoClimb(m_climber));
      btn_WhirlyRev.whileTrue(new climber_rotateRev(m_climber));
      //the following release buttons are for testing only. Remove for competition
      btn_ReleaseHook1.onTrue(new climber_lockHook(m_climber,1).beforeStarting(new climber_unlockHook(m_climber,1).andThen(new WaitCommand(Constants.Climber.kHookReleaseTime))));
      btn_ReleaseHook2.onTrue(new climber_lockHook(m_climber,2).beforeStarting(new climber_unlockHook(m_climber,2).andThen(new WaitCommand(Constants.Climber.kHookReleaseTime))));
    } else {
      btn_WhirlyFwd.whileTrue(new climber_rotateFwd(m_climber));
      btn_WhirlyRev.whileTrue(new climber_rotateRev(m_climber));
      btn_ReleaseHook1.onTrue(new climber_lockHook(m_climber,1).beforeStarting(new climber_unlockHook(m_climber,1).andThen(new WaitCommand(Constants.Climber.kHookReleaseTime))));
      btn_ReleaseHook2.onTrue(new climber_lockHook(m_climber,2).beforeStarting(new climber_unlockHook(m_climber,2).andThen(new WaitCommand(Constants.Climber.kHookReleaseTime))));
      // btn_LockHooks.onTrue(new climber_lockHook(m_climber, 1).alongWith(new climber_lockHook(m_climber, 2)));
    }
    // btn_IntakeForward.onTrue(new feeder_advanceToShooter(m_feeder));
    t_IntakeForward.onTrue(new cg_collector_intakeAndFeed(m_collector, m_feeder)).onFalse(new collector_intakeStop(m_collector).andThen(new collector_retractIntake(m_collector)));
    t_IntakeRetractor.onTrue(new collector_deployRetractor(m_collector,true)).onFalse(new collector_deployRetractor(m_collector, false));
    // t_IntakeReverse.onTrue(new collector_intakeReverse(m_collector));
    btn_IntakeReverse.whileTrue(new collector_intakeReverse(m_collector));
    btn_ShootDashboard.onTrue(new shooter_shootNamed(m_shooter, namedShots.DASHBOARD));
    btn_ShootLow.onTrue(new shooter_shootNamed(m_shooter, namedShots.LOW));
    btn_ShootProtected.onTrue(new shooter_shootNamed(m_shooter, namedShots.PROTECTED));
    btn_ShootLine.onTrue(new shooter_shootNamed(m_shooter, namedShots.LINE));
    btn_ShootDefault.onTrue(new shooter_shootNamed(m_shooter, namedShots.DEFAULT));
    btn_ShootWall.onTrue(new shooter_shootNamed(m_shooter, namedShots.WALL));
    btn_ShootStop.onTrue(new shooter_stopShooter(m_shooter));

    
    //These are the driver buttons
    btn_CollectorToggle.onTrue(new collector_toggleIntake(m_collector));
    btn_FeederFwd.onTrue(new feeder_advance(m_feeder)).onFalse(new feeder_stop(m_feeder));
    btn_ShooterStop.onTrue(new shooter_stopShooter(m_shooter));
    btn_ShooterIncrease.onTrue(new shooter_increaseThrottle(m_shooter));
    btn_ShooterDecrease.onTrue(new shooter_decreaseThrottle(m_shooter));
    btn_GyroReset.onTrue(new drive_resetGyro(m_drive).andThen(new drive_resetOdometry(m_drive, new Pose2d()))); //.andThen(new drive_homeSwerves(m_drive))
    // btn_AimSelectShoot.whileHeld(new cg_vision_aimSelectAndShoot(m_drive, m_feeder, m_shooter, m_vision)).whenReleased(new feeder_stop(m_feeder).andThen(new shooter_stopShooter(m_shooter)));
    btn_AimSelectShoot.whileTrue(new vision_findTargetAndShot(m_drive, m_vision, m_shooter));
    btn_AimSelect.whileTrue(new vision_findTargetAndShot(m_drive, m_vision, m_shooter));
    t_RingLight.onTrue(new vision_setRinglight(m_vision, Constants.Vision.stateLightOn)).onFalse(new vision_setRinglight(m_vision, !Constants.Vision.stateLightOn));
    btn_ResetClimb.onTrue(new climber_resetClimb(m_climber));
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
  public Command getRobotCommand(String name) {
    //This selects a command (or command group) to return
    switch (name) {
      case "resetRobot":
        return new cg_resetRobot(m_collector, m_climber, m_feeder, m_shooter, m_vision);
      case "rumbleNotify":
        return new cg_djRumble(m_vision);
      case "auton_4BallAuto":
        return new cg_auton_4BallAuto(m_drive, m_collector, m_feeder, m_shooter);
      case "auton_al1TwoBall":
        return new cg_auton_AL1TwoBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_al2TwoBall":
        return new cg_auton_AL2TwoBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_al3TwoBall":
        return new cg_auton_AL3TwoBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_ac1OneBall":
        return new cg_auton_AC1OneBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_ar1ThreeBall":
        return new cg_auton_AR1ThreeBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_ar2TwoBall":
        return new cg_auton_AR2TwoBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_ar3FourBall":
        return new cg_auton_AR3FourBall(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_ar4FourBall2":
        return new cg_auton_AR4FourBall2(m_drive, m_collector, m_feeder, m_shooter, m_vision);
      case "auton_BasicShootingAuto":
        return new cg_auton_BasicShootingAuto(m_drive, m_collector, m_feeder, m_shooter);
      case "auton_BasicDriveAuto":
        return new cg_auton_BasicDriveAuto(m_drive, m_collector, m_feeder, m_shooter);
      default:
        return null;
    }
  }
}
