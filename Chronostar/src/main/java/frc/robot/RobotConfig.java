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
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

/**
 * Add your docs here.
 */
public class RobotConfig {
    public void setStartingConfig(){

        RobotConfig.setAllMotorsBrake();
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
		RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
        
        RobotMap.rightDriveFollowerOne.set(ControlMode.Follower, RobotMap.rightDriveLeadID);
        RobotMap.leftDriveFollowerOne.set(ControlMode.Follower, RobotMap.leftDriveLeadID);

        //RobotMap.shooterFollower.set(ControlMode.Follower, RobotMap.shooterMasterID);


        RobotMap.rightDriveLead.setInverted(false);
        RobotMap.rightDriveFollowerOne.setInverted(false);

    	RobotMap.leftDriveLead.setInverted(true);
        RobotMap.leftDriveFollowerOne.setInverted(true);
        
        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);

    	RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0,0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);

        RobotMap.drive.initVelocityPIDs();
        RobotMap.drive.initAlignmentPID();
        //RobotMap.shooter.initShooterPID();
        
        //RobotMap.shooterMaster.configPeakOutputForward(RobotStats.maxShooterPercentVoltage);
        //RobotMap.shooterMaster.configPeakOutputReverse(0);
        //RobotMap.shooterMaster.configClosedLoopPeakOutput(0, RobotStats.maxShooterPercentVoltage);
    }
    public void setTeleopConfig(){
        RobotConfig.setDriveMotorsCoast();
    }
    public void setAutoConfig(){
        RobotConfig.setDriveMotorsBrake();
    }
    public static void setAllMotorsBrake() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }
    public static void setAllMotorsCoast() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsCoast() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsBrake() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Brake);
        }
	}
}
