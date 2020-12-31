// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI {
    public static Joystick driverController = new Joystick(0);
    public static Joystick operatorController = new Joystick(1);

    public static JoystickButton driverA = new JoystickButton(driverController, Constants.A);
    public static JoystickButton driverB = new JoystickButton(driverController, Constants.B);
    public static JoystickButton driverX = new JoystickButton(driverController, Constants.X);
    public static JoystickButton driverY = new JoystickButton(driverController, Constants.Y);
    public static JoystickButton driverLB = new JoystickButton(driverController, Constants.LB);
    public static JoystickButton driverRB = new JoystickButton(driverController, Constants.RB);
    public static JoystickButton driverBack = new JoystickButton(driverController, Constants.BACK);
    public static JoystickButton driverStart =
            new JoystickButton(driverController, Constants.START);
    public static JoystickButton driverLS = new JoystickButton(driverController, Constants.LS);
    public static JoystickButton driverRS = new JoystickButton(driverController, Constants.RS);

    public static JoystickButton operatorA = new JoystickButton(operatorController, Constants.A);
    public static JoystickButton operatorB = new JoystickButton(operatorController, Constants.B);
    public static JoystickButton operatorX = new JoystickButton(operatorController, Constants.X);
    public static JoystickButton operatorY = new JoystickButton(operatorController, Constants.Y);
    public static JoystickButton operatorLB = new JoystickButton(operatorController, Constants.LB);
    public static JoystickButton operatorRB = new JoystickButton(operatorController, Constants.RB);
    public static JoystickButton operatorBack =
            new JoystickButton(operatorController, Constants.BACK);
    public static JoystickButton operatorStart =
            new JoystickButton(operatorController, Constants.START);
    public static JoystickButton operatorLS = new JoystickButton(operatorController, Constants.LS);
    public static JoystickButton operatorRS = new JoystickButton(operatorController, Constants.RS);

    public static double getDriverLeftX() {
        return driverController.getRawAxis(0);
    }

    public static double getDriverLeftY() {
        return driverController.getRawAxis(1);
    }

    public static double getDriverRightX() {
        return driverController.getRawAxis(4);
    }

    public static double getDriverRightY() {
        return driverController.getRawAxis(5);
    }

    public static double getDriverLeftTrigger() {
        return driverController.getRawAxis(2);
    }

    public static double getDriverRightTrigger() {
        return driverController.getRawAxis(3);
    }

    public static double getOperatorLeftTrigger() {
        return OI.operatorController.getRawAxis(2);
    }

    public static double getOperatorRightTrigger() {
        return operatorController.getRawAxis(3);
    }

    public static int getOperatorPOV() {
        return operatorController.getPOV();
    }

    public static int getDriverPOV() {
        return driverController.getPOV();
    }
}
