/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public class Constants {
    public static final int leftDriveLeadID = 1;
    public static final int rightDriveLeadID = 6;
    public static final int leftDriveFollower1ID = 2;
    public static final int rightDriveFollower1ID = 5;
    public static final int leftDriveFollower2ID = 3;
    public static final int rightDriveFollower2ID = 4;
    public static final double driveMaxVoltage = 11.7;
    public static final double driveCurrentPeakThreshold = 40;
    public static final double driveCurrentPeakTime = 10;
    public static final double driveMaxCurrent = 39;

    public static final SupplyCurrentLimitConfiguration currentLimitEnabled = 
    new SupplyCurrentLimitConfiguration(true, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);

    public static final SupplyCurrentLimitConfiguration currentLimitDisabled = 
    new SupplyCurrentLimitConfiguration(false, driveMaxCurrent, driveCurrentPeakThreshold, driveCurrentPeakTime);
}
