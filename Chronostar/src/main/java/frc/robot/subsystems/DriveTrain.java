/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.sensors.DriveEncoder;
import frc.robot.tools.controlLoops.PID;
import frc.robot.tools.pathTools.Odometry;

public class DriveTrain extends SubsystemBase {

	private double deadZone = 0.01;
	private double turn =0;
	private double throttel = 0;
	private static DriveEncoder leftMainDrive = new DriveEncoder(RobotMap.leftDriveLead,RobotMap.leftDriveLead.getSelectedSensorPosition(0));
	private static DriveEncoder rightMainDrive = new DriveEncoder(RobotMap.rightDriveLead,RobotMap.rightDriveLead.getSelectedSensorPosition(0));
	private double vKF = 0.0;
	private double vKP = 0.0;
	private double vKI = 0.000000;
	private double vKD = 0;
	private int profile = 0;
	private PID alignmentPID;
	private double aKP;
	private double aKI;
	private double aKD;
	private double setPointOffset = 0;
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
		RobotMap.leftDriveLead.config_kF(profile, vKF, 0);
		RobotMap.leftDriveLead.config_kP(profile, vKP, 0);
		RobotMap.leftDriveLead.config_kI(profile, vKI, 0);
		RobotMap.leftDriveLead.config_kD(profile, vKD, 0);
		RobotMap.rightDriveLead.selectProfileSlot(profile, 0);
		RobotMap.rightDriveLead.config_kF(profile, vKF, 0);
		RobotMap.rightDriveLead.config_kP(profile, vKP, 0);
		RobotMap.rightDriveLead.config_kI(profile, vKI, 0);
		RobotMap.rightDriveLead.config_kD(profile, vKD, 0);
	}
	
	public void initAlignmentPID(){
		alignmentPID = new PID(aKP, aKD, aKI);
		alignmentPID.setSetPoint(setPointOffset);
		alignmentPID.setMaxOutput(6);
		alignmentPID.setMinInput(-6);
	}
	
	public void arcadeDrive(){
		double leftPower;
		double rightPower;
		double differential;
		if(Math.abs(ButtonMap.getDriveThrottle())>deadZone){
			throttel = Math.tanh(ButtonMap.getDriveThrottle())*(4/Math.PI); 
		}
		else{
			throttel = 0;
		}

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
	public boolean trackVisionTape(){
		Robot.visionCamera.updateVision();
		if(Timer.getFPGATimestamp()-Robot.visionCamera.lastParseTime>0.25){
			alignmentPID.updatePID(0);
		}
		else{
			alignmentPID.updatePID(Robot.visionCamera.getAngle());
		}
			
		setLeftSpeed(alignmentPID.getResult());
		setRightSpeed(-alignmentPID.getResult());
		if(Robot.visionCamera.getAngle()<1){
			return true;
		}
		else{
			return false;
		}
		
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
		for(TalonFX talon : RobotMap.driveMotorLeads){
			talon.set(ControlMode.PercentOutput, 0);
		}
	}

  @Override
  public void periodic() {

  }
  public void teleopPeriodic(){

	arcadeDrive();
  }
}
