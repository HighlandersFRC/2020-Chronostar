// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Constants {

    // Controller IDs
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    public static final int LB = 5;
    public static final int RB = 6;
    public static final int BACK = 7;
    public static final int START = 8;
    public static final int LS = 9;
    public static final int RS = 10;

    // Motor IDs
    public static final int LEFT_DRIVE_LEAD_ID = 1;
    public static final int RIGHT_DRIVE_LEAD_ID = 3;
    public static final int LEFT_DRIVE_FOLLOWER_ID = 2;
    public static final int RIGHT_DRIVE_FOLLOWER_ID = 4;
    public static final int HIGH_INTAKE_ID = 10;
    public static final int MAG_BELT_ID = 8;
    public static final int MAG_WHEEL_ID = 9;
    public static final int LOW_INTAKE_ID = 12;
    public static final int LEFT_FLYWHEEL_ID = 5;
    public static final int RIGHT_FLYWHEEL_ID = 6;
    public static final int HOOD_ID = 7;
    public static final int CLIMBER_MOTOR_ID = 15;
    public static final int WINCH_ID = 14;

    // Sensor ports
    public static int BEAM_BREAK_1_ID = 0;
    public static int BEAM_BREAK_2_ID = 3;
    public static int BEAM_BREAK_3_ID = 1;

    // Shooter type beat
    public static int TICKS_PER_SHOOTER_ROTATION = 1317;
    public static int MAX_SHOOTER_RPM = 6000;
    public static double MAX_SHOOTER_PERCENTAGE = 0.7;
    public static int SHOOTER_INTEGRAL_RANGE = 439;

    // Drive constants
    public static final double DRIVE_MAX_VOLTAGE = 11.7;
    public static final double DRIVE_CURRENT_PEAK_THRESHOLD = 40;
    public static final double DRIVE_CURRENT_PEAK_TIME = 10;
    public static final double DRIVE_MAX_CURRENT = 39;
    public static final double TICKS_PER_WHEEL_ROTATION = 28672;
    public static final double WHEEL_DIAMETER = 0.5;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public static final SupplyCurrentLimitConfiguration currentLimitEnabled =
            new SupplyCurrentLimitConfiguration(
                    true, DRIVE_MAX_CURRENT, DRIVE_CURRENT_PEAK_THRESHOLD, DRIVE_CURRENT_PEAK_TIME);

    public static final SupplyCurrentLimitConfiguration currentLimitDisabled =
            new SupplyCurrentLimitConfiguration(
                    false,
                    DRIVE_MAX_CURRENT,
                    DRIVE_CURRENT_PEAK_THRESHOLD,
                    DRIVE_CURRENT_PEAK_TIME);

    public static double driveUnitsToFeet(double ticks) {
        return ticks / TICKS_PER_WHEEL_ROTATION * WHEEL_CIRCUMFERENCE;
    }

    public static double driveFeetToUnits(double feet) {
        return feet * TICKS_PER_WHEEL_ROTATION / WHEEL_CIRCUMFERENCE;
    }

    public static double driveUnitsPer100MSToFPS(double velocity) {
        return driveUnitsToFeet(velocity) * 10;
    }

    public static double driveFPSToUnitsPer100MS(double fps) {
        return driveFeetToUnits(fps) / 10;
    }

    public static double unitsPer100MsToRPM(double units) {
        return (units * 600) / Constants.TICKS_PER_SHOOTER_ROTATION;
    }

    public static int shooterRPMToUnitsPer100MS(double rpm) {
        return (int) Math.round((rpm / 600) * Constants.TICKS_PER_SHOOTER_ROTATION);
    }

    public static double shooterUnitsPer100MsToRPM(double units) {
        return (units * 600) / Constants.TICKS_PER_SHOOTER_ROTATION;
    }

    public static double convertCamera(double camDistance) {
        return 0;
    }

    public static double convertLidar(double lidarDistance) {
        return 0;
    }
}
