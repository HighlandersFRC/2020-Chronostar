// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ButtonMap {
    public static double getThrottle() {
        return OI.driverController.getY(Hand.kLeft);
    }

    public static double getTurn() {
        return OI.driverController.getX(Hand.kRight);
    }

    public static double getLeftY() {
        return OI.driverController.getY(Hand.kLeft);
    }

    public static double getRightY() {
        return OI.driverController.getY(Hand.kRight);
    }

    public static boolean getOperatorXButton() {
        return OI.operatorController.getXButton();
    }

    public static boolean getOperatorYButton() {
        return OI.operatorController.getYButton();
    }

    public static int getOperatorPOV() {
        return OI.operatorController.getPOV();
    }

    public static boolean getOperatorBButton() {
        return OI.operatorController.getBButton();
    }

    public static double getOperatorRightTrigger() {
        return OI.operatorController.getRawAxis(3);
    }

    public static double getOperatorLeftTrigger() {
        return OI.operatorController.getRawAxis(2);
    }

    public static boolean shoot() {
        return OI.operatorController.getAButton();
    }

    public static boolean getDriverRightBumper() {
        return OI.driverController.getBumper(Hand.kRight);
    }
}