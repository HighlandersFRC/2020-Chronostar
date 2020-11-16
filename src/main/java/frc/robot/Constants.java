// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Constants {

    // Motor IDs
    public static final int LEFT_DRIVE_LEAD_ID = 1;
    public static final int RIGHT_DRIVE_LEAD_ID = 3;
    public static final int LEFT_DRIVE_FOLLOWER_ID = 2;
    public static final int RIGHT_DRIVE_FOLLOWER_ID = 4;
    public static final int INTAKE_2_ID = 10;
    public static final int MAG_BELT_ID = 8;
    public static final int MAG_WHEEL_ID = 9;
    public static final int INTAKE_1_ID = 12;
    public static final int LEFT_FLYWHEEL_ID = 5;
    public static final int RIGHT_FLYWHEEL_ID = 6;
    public static final int HOOD_ID = 7;

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

    public static double ticksToFeet(double ticks) {
        return ticks / TICKS_PER_WHEEL_ROTATION * WHEEL_CIRCUMFERENCE;
    }

    public static double feetToTicks(double feet) {
        return feet * TICKS_PER_WHEEL_ROTATION / WHEEL_CIRCUMFERENCE;
    }

    public static double encVelocityToFps(double velocity) {
        return ticksToFeet(velocity) * 10;
    }

    public static double fpsToEncVelocity(double fps) {
        return feetToTicks(fps) / 10;
    }

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / Constants.TICKS_PER_SHOOTER_ROTATION;
    }

    public static int rpmToUnitsPer100Ms(double rpm) {
        return (int) Math.round((rpm / 600) * Constants.TICKS_PER_SHOOTER_ROTATION);
    }

    // Hood constants

    public static final double INITIATION_HOOD_POSITION = 9.5;

    public static double distanceToHoodPos(double camDistance, double lidarDistance) {
        if (Math.abs(camDistance - lidarDistance) > 2) {}

        return 0;
    }
}
