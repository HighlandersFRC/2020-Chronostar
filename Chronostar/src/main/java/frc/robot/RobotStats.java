package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class RobotStats {

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
    public static final int rightFlyWheelID = 6;

    // Sensor ports
    public static int beamBreak1Port = 0;
    public static int beamBreak2Port = 3;
    public static int beamBreak3Port = 1;

    // Drive constants
    public static final double driveMaxVoltage = 11.7;
    public static final double driveCurrentPeakThreshold = 40;
    public static final double driveCurrentPeakTime = 10;
    public static final double driveMaxCurrent = 39;

    public static final SupplyCurrentLimitConfiguration currentLimitEnabled = 
    new SupplyCurrentLimitConfiguration(true, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);

    public static final SupplyCurrentLimitConfiguration currentLimitDisabled = 
    new SupplyCurrentLimitConfiguration(false, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);
}
