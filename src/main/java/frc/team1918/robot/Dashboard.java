//OI = Operator Interface
package frc.team1918.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * This class reads and writes values to/from the SmartDashboard
 */
public class Dashboard {
    public static final class Climber {
        public static final void setClimberDirection(String direction) { SmartDashboard.putString("Climber/Direction", direction); }
        public static final void setClimberPosition(double position) { SmartDashboard.putNumber("Climber/Position", position); }
        // public static final void setWhirlyPosition(String position) { SmartDashboard.putString("Climber/Whirlygig Position", position); }
        public static final void setWhirlyPosition(String position) { SmartDashboard.putBoolean("Climber/Whirlygig Deployed", (position=="UP")?true:false); }
        public static final void setHook1Left(Boolean value) { SmartDashboard.putBoolean("Climber/Hook1Left", value); }
        public static final void setHook1Right(Boolean value) { SmartDashboard.putBoolean("Climber/Hook1Right", value); }
        public static final void setHook2(Boolean value) { SmartDashboard.putBoolean("Climber/Hook2", value); }
    }
    public static final class Shooter {
        public static final void setCurrentSpeed(double speed) { SmartDashboard.putNumber("Shooter/Shooter Speed",speed); }
        public static final void setHoodPosition(String value) { SmartDashboard.putString("Shooter/Hood Position", value); }
        public static final void setTargetSpeed(double speed) { SmartDashboard.putNumber("Shooter/TargetSpeed",speed); }
        public static final void setShotName(String value) { SmartDashboard.putString("Shooter/Shot Name",value); }
        public static final double getTargetSpeed(double default_val) { return (double) SmartDashboard.getNumber("Shooter/Target Speed",default_val); }
    }
    public static final class Feeder {
        public static final void setFeederDirection(String value) { SmartDashboard.putString("Feeder/Direction", value); }
        public static final void setFeederSpeed(double speed) { SmartDashboard.putNumber("Feeder/Speed", speed); }
        public static final void setFeederBall(boolean value) { SmartDashboard.putBoolean("Feeder/Ball Detected", value); }
    }
    public static final class Vision {
        public static final void setVisionRinglight(boolean value) { SmartDashboard.putBoolean("Vision/Ring Light", value); }
    }
    public static final class Collector {
        public static final void setIntakeDeployed(Boolean value) { SmartDashboard.putBoolean("Collector/Intake Deployed", value); }
        public static final void setIntakeDirection(String value) { SmartDashboard.putString("Collector/Intake Direction", value); }
    }
    public static final class Gyro {
        public static final void setGyroAngle(double angle) { SmartDashboard.putNumber("GyroAngle",angle); }
    }
    public static final class DriveTrain {
        public static final void setTurnPosition(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Position", value); }
        public static final void setTurnSetpoint(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Setpoint", value); }
        public static final void setTurnPositionError(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Position Error", value); }
        public static final void setTurnVelocity(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Turn Velocity", value); }
        public static final void setTurnPositionErrorChange(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Position Error Change", value); }
        public static final void setTurnZeroPosition(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Zero Position", value); }
        public static final void setDriveVelocity(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Drive Velocity", value); }
        public static final void setDriveDistance(String module, double value) { SmartDashboard.putNumber("Swerve/"+module+"/Drive Distance", value); }
        public static final void setHeading(double value) { SmartDashboard.putNumber("Heading", Helpers.General.roundDouble(value,2)); }
        public static final void setX(double value) { SmartDashboard.putNumber("Current X", value); }
        public static final void setY(double value) { SmartDashboard.putNumber("Current Y", value); }
        public static final void setCurrentAngle(double value) { SmartDashboard.putNumber("Current Angle", value); }
        public static final void setTargetAngle(double value) { SmartDashboard.putNumber("Target Angle", value); }
        public static final void setRotationPidOut(double value) { SmartDashboard.putNumber("Rotation PID Out", value); }
        public static final void setDesiredAngle(double value) { SmartDashboard.putNumber("Desired Angle", value); }
        public static final void setCorrectionAngle(double value) { SmartDashboard.putNumber("Correction Omega", value); }
    }
}