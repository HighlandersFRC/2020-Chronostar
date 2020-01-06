/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.sensors.DriveEncoder;
import frc.robot.tools.pathTools.Odometry;

public class DriveTrain extends SubsystemBase {

	private double deadZone = 0.0102;
	private double turn =0;
	private double throttel = 0;
	private double ratio;
	public static DriveEncoder leftMainDrive = new DriveEncoder(RobotMap.leftDriveLead,RobotMap.leftDriveLead.getSelectedSensorPosition(0));
	public static DriveEncoder rightMainDrive = new DriveEncoder(RobotMap.rightDriveLead,RobotMap.rightDriveLead.getSelectedSensorPosition(0));
	private double speed;
	private double f = 0.153;
	private double p = 0.35025;
	private double i = 0.000000;
	private double d = 0;
	private int profile = 0;
	private Odometry autoOdometry;
  	public DriveTrain() {

  	}
	public void startAutoOdometry(double x, double y, double theta){

	};
	public double getDriveTrainX(){
		return autoOdometry.getX();
	}
	public double getDriveTrainY(){
		return autoOdometry.getY();
	}
	public double getDriveTrainHeading(){
		return autoOdometry.gettheta();
	}

	public void setDriveTrainX(double x){
		 autoOdometry.setX(x);
	}
	public void setDriveTrainY(double y){
		 autoOdometry.setY(y);
	}
	public void setDriveTrainHeading(double theta){
		 autoOdometry.setTheta(theta);
	}
	public void setOdometryReversed(boolean reversed){
		 autoOdometry.setReversed(reversed);
	}

	public void initVelocityPIDs(){
		RobotMap.leftDriveLead.selectProfileSlot(profile, 0);
		RobotMap.leftDriveLead.config_kF(profile, f, 0);
		RobotMap.leftDriveLead.config_kP(profile, p, 0);
		RobotMap.leftDriveLead.config_kI(profile, i, 0);
		RobotMap.leftDriveLead.config_kD(profile, d, 0);
		RobotMap.leftDriveLead.set(ControlMode.Velocity, leftMainDrive.convertftpersToNativeUnitsper100ms(speed));
		RobotMap.rightDriveLead.selectProfileSlot(profile, 0);
		RobotMap.rightDriveLead.config_kF(profile, f, 0);
		RobotMap.rightDriveLead.config_kP(profile, p, 0);
		RobotMap.rightDriveLead.config_kI(profile, i, 0);
		RobotMap.rightDriveLead.config_kD(profile, d, 0);
		RobotMap.rightDriveLead.set(ControlMode.Velocity, rightMainDrive.convertftpersToNativeUnitsper100ms(speed));
	}
	
	public void arcadeDrive(){
		double leftPower;
		double rightPower;
		double differential;
		if(Math.abs(ButtonMap.getDriveThrottle())>deadZone){
			throttel = Math.tanh(ButtonMap.getDriveThrottle())*(4/3.14); 
		}
		else{
			throttel = 0;
		}

		ratio = Math.abs(throttel);
		if(Math.abs(ButtonMap.getRotation())>deadZone){
			turn = ButtonMap.getRotation();
		}
		else{
			turn = 0;
		}
		turn = ButtonMap.getRotation();
		differential = turn;
		SmartDashboard.putNumber("differential", differential);
		leftPower = (throttel - (differential));
		rightPower = (throttel + (differential));
	
		if(Math.abs(leftPower)>1) {
			rightPower = Math.abs(rightPower/leftPower)*Math.signum(rightPower);
			leftPower = Math.signum(leftPower);
		}
		else if(Math.abs(rightPower)>1) {
			leftPower = Math.abs(leftPower/rightPower)*Math.signum(leftPower);
			rightPower = Math.signum(rightPower);
		}
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, leftPower);
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, rightPower);
	}
	public void Stop(){
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);

	}
	public void setLeftSpeed(double speed){
		SmartDashboard.putNumber("LeftSpeed", leftMainDrive.getVelocity());
		SmartDashboard.putNumber("RightSpeed", rightMainDrive.getVelocity());
		RobotMap.leftDriveLead.set(ControlMode.Velocity, leftMainDrive.convertftpersToNativeUnitsper100ms(speed));
	}
	public void setRightSpeed(double speed){
		RobotMap.rightDriveLead.set(ControlMode.Velocity, rightMainDrive.convertftpersToNativeUnitsper100ms(speed));

	}
	public void setLeftPercent(double percent){
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
	}
	public void setRightPercent(double percent){
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
	}
	public void stopDriveTrainMotors(){
		for(TalonSRX talon : RobotMap.driveMotorLeads){
			talon.set(ControlMode.PercentOutput, 0);
		}
	}

  @Override
  public void periodic() {
    if(RobotState.isOperatorControl()){
      arcadeDrive();
    }
    // This method will be called once per scheduler run
  }
}
