/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class RobotConfig {

    public void startBaseConfig() {
        RobotMap.leftDriveFollower2.set(ControlMode.Follower, Constants.leftDriveLeadID);
        RobotMap.rightDriveFollower2.set(ControlMode.Follower, Constants.rightDriveLeadID);
        RobotMap.rightDriveLead.setInverted(true);
        RobotMap.rightDriveFollower2.setInverted(true);
        setCurrentLimitsEnabled();
    }

    public void startAutoConfig() {
        setVoltageCompensation(Constants.driveMaxVoltage);
    }

    public void startTeleopConfig() {
    }

    private void setVoltageCompensation(double volts) {
        for (TalonSRX t : RobotMap.allMotors) {
            t.configVoltageCompSaturation(volts);
        }
    }

    private void setCurrentLimitsEnabled() {
        for (TalonSRX t : RobotMap.allMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitEnabled);
        }
    }
    
    private void setCurrentLimitsDisabled() {
        for (TalonSRX t : RobotMap.allMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitDisabled);
        }
    }
}
