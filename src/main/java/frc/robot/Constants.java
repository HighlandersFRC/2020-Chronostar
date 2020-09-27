// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Constants {

    // Motor IDs
    public static final int leftDriveLeadID = 1;
    public static final int rightDriveLeadID = 3;
    public static final int leftDriveFollower1ID = 2;
    public static final int rightDriveFollower1ID = 4;
    public static final int intake2MotorID = 10;
    public static final int magBeltID = 8;
    public static final int magWheelID = 9;
    public static final int intakeMotorID = 12;
    public static final int leftFlywheelID = 5;
    public static final int rightFlywheelID = 6;
    public static final int winchID = 14;

    // Sensor ports
    public static int beamBreak1Port = 0;
    public static int beamBreak2Port = 3;
    public static int beamBreak3Port = 1;

    // Shooter type beat
    public static int ticksPerShooterRotation = 1317;
    public static int maxRPM = 4000;
    public static double maxPercentage = 0.5;
    public static int shooterIntegralRange = 439;

    // Drive constants
    public static final double driveMaxVoltage = 11.7;
    public static final double driveCurrentPeakThreshold = 40;
    public static final double driveCurrentPeakTime = 10;
    public static final double driveMaxCurrent = 39;
    public static final double driveTicksPerWheelRotation = 28672;
    public static final double wheelDiam = 0.5;
    public static final double wheelCircum = wheelDiam * Math.PI;

    public static final SupplyCurrentLimitConfiguration currentLimitEnabled =
            new SupplyCurrentLimitConfiguration(
                    true, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);

    public static final SupplyCurrentLimitConfiguration currentLimitDisabled =
            new SupplyCurrentLimitConfiguration(
                    false, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);

    public static double ticksToFeet(double ticks) {
        return ticks / driveTicksPerWheelRotation * wheelCircum;
    }

    public static double feetToTicks(double feet) {
        return feet * driveTicksPerWheelRotation / wheelCircum;
    }

    public static double encVelocityToFps(double velocity) {
        return ticksToFeet(velocity) * 10;
    }

    public static double fpsToEncVelocity(double fps) {
        return feetToTicks(fps) / 10;
    }
}
