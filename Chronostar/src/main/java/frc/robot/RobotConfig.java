/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class RobotConfig {
    public void setStartingConfig(){
        
        RobotConfig.setAllMotorsBrake();
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
		RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
        
        RobotMap.rightDriveFollowerOne.set(ControlMode.Follower, RobotMap.rightMasterFalcon);
        RobotMap.leftDriveFollowerOne.set(ControlMode.Follower, RobotMap.leftMasterFalcon);

        
        RobotMap.rightDriveLead.setInverted(false);
        RobotMap.rightDriveFollowerOne.setInverted(InvertType.FollowMaster);

    	RobotMap.leftDriveLead.setInverted(true);
        RobotMap.leftDriveFollowerOne.setInverted(InvertType.FollowMaster);

        RobotMap.leftDriveLead.setSensorPhase(true);
        RobotMap.rightDriveLead.setSensorPhase(true);
    	RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0,0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);
        
    	for(TalonSRX talon:RobotMap.driveMotors) {
    		talon.configContinuousCurrentLimit(RobotStats.driveMotorContinuousCurrentHighGear);
    		talon.configPeakCurrentLimit(RobotStats.driveMotorPeakCurrentHighGear);
            talon.configPeakCurrentDuration(RobotStats.driveMotorPeakCurrentDurationHighGear);
            talon.enableCurrentLimit(true);
        }

    }
    public void setTeleopConfig(){
        RobotConfig.setDriveMotorsCoast();
    }
    public void setAutoConfig(){
        RobotConfig.setDriveMotorsBrake();
    }
    public static void setAllMotorsBrake() {
		for(TalonSRX talon:RobotMap.allMotors){
            talon.setNeutralMode(NeutralMode.Brake);
        }
	}
	public static void setDriveMotorsCoast() {
		for(TalonSRX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}

	public static void setDriveMotorsBrake() {
		for(TalonSRX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Brake);
        }
	}
}
