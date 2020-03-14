/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * Add your docs here.
 */
public class RobotConfig {

    public void startBaseConfig() {
        RobotContainer.leftDriveFollower1.follow(RobotContainer.leftDriveLead);
        RobotContainer.leftDriveFollower2.follow(RobotContainer.leftDriveLead);
        RobotContainer.rightDriveFollower1.follow(RobotContainer.rightDriveLead);
        RobotContainer.rightDriveFollower2.follow(RobotContainer.rightDriveLead);
        RobotContainer.rightDriveLead.setInverted(true);
        RobotContainer.rightDriveFollower1.setInverted(true);
        RobotContainer.rightDriveFollower2.setInverted(true);
    }

    public void startAutoConfig() {
    }

    public void startTeleopConfig() {
    }
}
