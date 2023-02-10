package frc.team1918.paths;

import frc.team1918.lib.control.PIDController;
import frc.team1918.lib.control.SwerveTrajectory;

@SuppressWarnings("unused")
public class main {
    public static void main(String[] args) {
        PIDController controller = new PIDController(1, 0, 0);
        controller.setContinuous(true);
        controller.setInputRange(360);

        controller.setReference(-790);
        System.out.println(controller.calculate(130, 1));
    }
}